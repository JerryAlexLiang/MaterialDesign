package com.liangyang.materialdesign;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {


    //加载数据源
    private DataInfo[] dataInfos = {
            new DataInfo(1,"图片1", R.drawable.bg01),
            new DataInfo(2,"图片2", R.drawable.bg02),
            new DataInfo(3,"图片3", R.drawable.bg03),
            new DataInfo(4,"图片4", R.drawable.bg04),
            new DataInfo(5,"图片5", R.drawable.bg05),
            new DataInfo(6,"图片6", R.drawable.bg06),
            new DataInfo(7,"图片7", R.drawable.bg07),
            new DataInfo(8,"图片8", R.drawable.bg08),
            new DataInfo(9,"图片9", R.drawable.bg09),
            new DataInfo(10,"图片10", R.drawable.bg10)};

    //数据源集合
    private List<DataInfo> dataInfoList = new ArrayList<>();

    private boolean isList = true;

    private DrawerLayout mDrawerLayout;
    private View nav_header_name;
    private View nav_header_email;
    private View nav_header_imageView;
    private FloatingActionButton floatActionBtn;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);//将Toolbar设置为Actionbar
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //设置标题栏导航栏图标
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //设置NavigationView头部控件的点击事件
        View headerView = navigationView.getHeaderView(0);//获取头部控件
        nav_header_imageView = headerView.findViewById(R.id.nav_icon_image);
        nav_header_name = headerView.findViewById(R.id.user_name);
        nav_header_email = headerView.findViewById(R.id.email);
        nav_header_imageView.setOnClickListener(this);
        nav_header_name.setOnClickListener(this);
        nav_header_email.setOnClickListener(this);

        //设置NavigationView中的menu的默认选中状态栏
        navigationView.setCheckedItem(R.id.nav_menu_call);//也可以在XML文件中设置
        //设置NavigationView中的menu的点击监听事件
        navigationView.setNavigationItemSelectedListener(this);

        //初始化FloatingActionButton --> SnackBar
        floatActionBtn = (FloatingActionButton) findViewById(R.id.fab);
        floatActionBtn.setOnClickListener(this);

        //初始化数据源
        initData();

        //初始化RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //初始化适配器
        adapter = new RecyclerViewAdapter(MainActivity.this,dataInfoList);
        //绑定适配器
        recyclerView.setAdapter(adapter);

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        //刷新条颜色
        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

    }

    /**
     * 刷新数据
     */
    private void refreshData() {

        new Thread(new Runnable() {
            @Override
            public void run() {



                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        initData();
                        //刷新适配器
                        adapter.notifyDataSetChanged();
                        mSwipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    /**
     * 初始换数据源
     */
    private void initData() {

        dataInfoList.clear();
        for (int i = 0; i < 50; i++) {
            //随机放置
            Random random = new Random();
            int index = random.nextInt(dataInfos.length);
            dataInfoList.add(dataInfos[index]);
        }

//        for (int i = 0; i < dataInfos.length; i++) {
//            dataInfoList.add(dataInfos[i]);
//        }
    }

    /**
     * 控件的点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nav_icon_image:
                // TODO: 2017/2/20 开发上传头像功能
                Toast.makeText(MainActivity.this, "你点击了头像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user_name:
                // TODO: 2017/2/20 开发更改用户名功能
                Toast.makeText(MainActivity.this, "用户名：" + "user_name: Jerry", Toast.LENGTH_SHORT).show();
                break;
            case R.id.email:
                // TODO: 2017/2/20 开发更改邮箱地址功能
                Toast.makeText(MainActivity.this, "邮箱：" + "e-mail address： 2378624832@qq.com", Toast.LENGTH_SHORT).show();
                break;

            case R.id.fab:
                //FloatingActionButton的点击监听事件

                Snackbar.make(view, "切换模式", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isList){
                                    isList = false;
                                    //切换网格模式
                                    //创建布局管理器  GridLayoutManager(垂直方向滚动，参数二表示列数，按列排序)
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,2);
                                    recyclerView.setLayoutManager(gridLayoutManager);
                                }else {
                                    isList = true;
                                    //切换列表模式
                                    //创建布局管理器
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                }
                                Toast.makeText(MainActivity.this, "Mode Changed", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
        }
    }

    /**
     * 设置NavigationView中的menu的点击监听事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_menu_call:
                Toast.makeText(MainActivity.this, "call: 1234567890", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_menu_friends:
                Toast.makeText(MainActivity.this, "你点击了朋友圈", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_menu_location:
                Toast.makeText(MainActivity.this, "当前位置： ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_menu_mail:
                Toast.makeText(MainActivity.this, "mail: 1234567@qq.com", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_menu_task:
                Toast.makeText(MainActivity.this, "我的任务：", Toast.LENGTH_SHORT).show();
                break;
        }
        //关闭DrawerLayout
        mDrawerLayout.closeDrawers();
        return true;
    }

    /**
     * Toolbar 创建menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * Toolbar - menu的item的点击监听事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.toolbar_menu_backup:
                Toast.makeText(MainActivity.this, "你点击了Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_menu_delete:
                Toast.makeText(MainActivity.this, "你点击了Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_menu_settings:
                Toast.makeText(MainActivity.this, "你点击了Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
