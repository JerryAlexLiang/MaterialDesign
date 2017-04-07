package com.liangyang.materialdesign;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.FaceRequest;
import com.iflytek.cloud.RequestListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = getClass().getCanonicalName();
    private final int REQUEST_PICTURE_CHOOSE = 1;
    private final int REQUEST_CAMERA_IMAGES = 2;
    private FaceRequest faceRequest;
    private Toast toast;
    private byte[] imageData = null;
    private Bitmap image = null;
    private EditText editTextUserPassword;
    private String UserPassword;
    private ProgressDialog progressDialog;
    private File pictureFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_key));//初始化SDK 千万不能忘记写appid后边的等号  id是自己其官网上申请的
        SpeechUtility.createUtility(this, "appid=" + "587c8416");//初始化SDK 千万不能忘记写appid后边的等号  id是自己其官网上申请的
        //忘记会报 10274无效 key
        faceRequest = new FaceRequest(this);
        findViewById();
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);//设置是否可取消
        progressDialog.setTitle("请稍等...");
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (null != faceRequest) {
                    faceRequest.cancel();
                }
            }
        });
    }

    private void findViewById() {
        editTextUserPassword = (EditText)findViewById(R.id.editTextUserPassword);
        findViewById(R.id.buttonRegister).setOnClickListener(LoginActivity.this);
        findViewById(R.id.buttonPicture).setOnClickListener(LoginActivity.this);
        findViewById(R.id.buttonVerification).setOnClickListener(LoginActivity.this);
        findViewById(R.id.imageViewHead).setOnClickListener(LoginActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonPicture:
                pictureFile = new File(Environment.getExternalStorageDirectory(),
                        "picture"+System.currentTimeMillis()/1000+".jpg");
                Intent picIntent = new Intent();
                picIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                picIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pictureFile));
                picIntent.putExtra(MediaStore.Images.Media.ORIENTATION,0);
                startActivityForResult(picIntent,REQUEST_CAMERA_IMAGES);
                break;
            case R.id.buttonRegister:
                UserPassword = ((EditText)findViewById(R.id.editTextUserPassword)).getText().toString();
                if(TextUtils.isEmpty(UserPassword)){
                    showToast("请输入您的密钥");
                    return;
                }else if(null != imageData){
                    progressDialog.setMessage("注册中...");
                    progressDialog.show();
                    faceRequest.setParameter(SpeechConstant.AUTH_ID, UserPassword);//将授权标识和密码上传服务器记录
                    faceRequest.setParameter(SpeechConstant.WFR_SST,"reg");//业务类型train Or verify
                    faceRequest.sendRequest(imageData,requestListener);
                }else showToast("请先进行图像拍摄...");
                break;
            case R.id.buttonVerification:
                UserPassword = ((EditText)findViewById(R.id.editTextUserPassword)).getText().toString();
                if(TextUtils.isEmpty(UserPassword)){
                    showToast("密钥不能为空哦...");
                }else if(imageData != null){
                    progressDialog.setMessage("验证中,请稍等...");
                    progressDialog.show();//6--12字符 不能以数字开头
                    faceRequest.setParameter(SpeechConstant.AUTH_ID, UserPassword);
                    faceRequest.setParameter(SpeechConstant.WFR_SST,"verify");
                    faceRequest.sendRequest(imageData,requestListener);
                }else showToast("请先捕捉头像...");
                break;
        }
    }

    /**
     * 处理拍完照后，跳转到裁剪界面
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "运行到了onActivityResult");
        if(resultCode != RESULT_OK){
            Log.i(TAG,"requestCode未成功");
            return;
        }
        String fileSrc = null;
        if(requestCode == REQUEST_PICTURE_CHOOSE){
            if("file".equals(data.getData().getScheme())){
                fileSrc = data.getData().getPath();
                Log.i(TAG,"file "+fileSrc);
            }else {
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(data.getData(), proj, null, null, null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                fileSrc = cursor.getString(idx);
                cursor.close();
            }
            HeadTrim.corpPicture(this, Uri.fromFile(new File(fileSrc)));
        }else if(requestCode == REQUEST_CAMERA_IMAGES){
            if(null == pictureFile){
                showToast("拍照失败，请重试...");
                return;
            }
            fileSrc = pictureFile.getAbsolutePath();
            updataGallery(fileSrc);
            HeadTrim.corpPicture(this,Uri.fromFile(new File(fileSrc)));
        }else if(requestCode == HeadTrim.REQUEST_CROP_IMAGE){
            Bitmap bitmap = data.getParcelableExtra("data");
            Log.i(TAG,"bitmp是否为空");
            if(null != bitmap){
                HeadTrim.saveBitmapToFile(LoginActivity.this,bitmap);
            }
            fileSrc = HeadTrim.getImagePath(LoginActivity.this);//获取图片保存路径
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            image = BitmapFactory.decodeFile(fileSrc,options);
            options.inSampleSize = Math.max(1, (int) Math.ceil(Math.max(
                    (double) options.outWidth / 1024f,
                    (double) options.outHeight / 1024f
            )));
            options.inJustDecodeBounds = false;
            image = BitmapFactory.decodeFile(fileSrc,options);
            //如果imageBitmap 为空图片不能正常获取
            if(null == image){
                showToast("图片信息无法正常获取");
                return;
            }
            int degree = HeadTrim.readPictureDegree(fileSrc);
            if(degree != 0){
                image = HeadTrim.rotateImage(degree,image);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream);
            imageData = byteArrayOutputStream.toByteArray();
            ((ImageView)findViewById(R.id.imageViewHead)).setImageBitmap(image);
        }
    }

    @Override
    public void finish() {
        if(null != progressDialog){
            progressDialog.dismiss();
        }
        super.finish();
    }

    private void updataGallery(String fileName) {
        MediaScannerConnection.scanFile(this, new String[]{fileName}, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    @Override
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });
    }

    /**
     * 请求对象监听  ， 对服务器返回来的数据进行解析  JSON格式
     */
    private RequestListener requestListener = new RequestListener() {
        @Override
        public void onEvent(int i, Bundle bundle) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {
            if(null != progressDialog){
                progressDialog.dismiss();
            }
            try {
                String result = new String(bytes,"utf-8");
                Log.i(TAG,result);
                JSONObject object = new JSONObject(result);
                String type = object.optString("sst");//获取业务类型
                if("reg".equals(type)){//注册
                    register(object);
                }else if("verify".equals(type)){//校验
                    verify(object);
                }else if("detect".equals(type)){
                    detect(object);
                }else if("aligm".equals(type)){
                    //align(object);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCompleted(SpeechError speechError) {//完成后
            if(null != progressDialog){
                progressDialog.dismiss();
            }
            if(speechError != null ){
                switch (speechError.getErrorCode()){
                    case ErrorCode.MSP_ERROR_ALREADY_EXIST:
                        showToast("密钥已被注册，请更换后再试...");
                        break;
                    default:showToast(speechError.getPlainDescription(true));
                        break;
                }
            }
        }
    };
    //检测
    private void detect(JSONObject object) throws JSONException{
        int ret = object.getInt("ret");
        if(ret != 0){
            showToast("检测失败");
        }else if("success".equals(object.get("rst"))){
            JSONArray jsonArray = object.getJSONArray("face");
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStrokeWidth(Math.max(image.getWidth(), image.getHeight()) / 100f);

            Bitmap bitmap = Bitmap.createBitmap(image.getWidth(),image.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(image,new Matrix(),null);
            for(int i = 0;i<jsonArray.length();i++){
                float x1 = (float) jsonArray.getJSONObject(i).getJSONObject("position").getDouble("left");
                float y1 = (float) jsonArray.getJSONObject(i).getJSONObject("position").getDouble("top");
                float x2 = (float) jsonArray.getJSONObject(i).getJSONObject("position").getDouble("right");
                float y2 = (float) jsonArray.getJSONObject(i).getJSONObject("position").getDouble("bottom");
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(new Rect((int)x1,(int)x2,(int)y1,(int)y2),paint);
            }
            image = bitmap;
            ((ImageView)findViewById(R.id.imageViewHead)).setImageBitmap(image);
        }else {
            showToast("检测失败");
        }
    }

    /**
     * 校验
     * @param object
     */
    private void verify(JSONObject object) throws JSONException{
        int ret = object.getInt("ret");
        Log.i(TAG,"ret校验"+ret);
        if(ret != 0){
            showToast("校验失败..."+ret);
        }else if("success".equals(object.get("rst"))){
            if(object.getBoolean("verf")){
                showToast("验证通过");
                editTextUserPassword.setText(null);
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }else if(!object.getBoolean("verg")){
                showToast("验证不通过");
            }else showToast("验证失败");
        }
    }

    /**
     * 如果收回的数据类型是注册 进行一下处理
     * @param object
     */
    private void register(JSONObject object) throws JSONException{
        int ret = object.getInt("ret");//解析ret返回值  0代表成功 -1失败  或者其他的错误异常代码
        if(ret != 0){
            showToast("注册失败");
            return;
        }else if("success".equals(object.get("rst"))){
            showToast("注册成功");
            editTextUserPassword.setText(null);
        }else showToast("注册失败，错误");

    }

    //土司对话框
    private void showToast(final String s){
        toast.setText(s);
        toast.show();
    }
}
