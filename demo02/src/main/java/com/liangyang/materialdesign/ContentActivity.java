package com.liangyang.materialdesign;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton floatActionBtn;
    private boolean isBlack = true;
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        //接受数据
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int image_id = intent.getIntExtra("image_id", 0);
        String content = intent.getStringExtra("content");

        //初始化视图
        Toolbar toolbar = (Toolbar) findViewById(R.id.content_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView imageView = (ImageView) findViewById(R.id.content_image);
        contentTextView = (TextView) findViewById(R.id.tv_content);

        //初始化FloatingActionButton
        floatActionBtn = (FloatingActionButton) findViewById(R.id.float_action_btn);
        floatActionBtn.setOnClickListener(this);

        //设置标题栏
        setSupportActionBar(toolbar);
        //设置标题栏导航栏图标
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //加载数据
        //使用CollapsingToolbarLayout时必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上不会显示
        collapsingToolbarLayout.setTitle(name);
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.YELLOW);//设置还没有收缩状态下的字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色

        Picasso.with(this).load(image_id).into(imageView);
        contentTextView.setText(content);

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
            case android.R.id.home:   //系统默认id
                //返回上一层
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.float_action_btn:
                /*
                //FloatingActionButton的点击监听事件
                Snackbar.make(view, "切换模式", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isList) {
                                    isList = false;
                                    //切换网格模式
                                    //创建布局管理器  GridLayoutManager(垂直方向滚动，参数二表示列数，按列排序)
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                                    recyclerView.setLayoutManager(gridLayoutManager);
                                } else {
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
                 */
                //FloatingActionButton的点击监听事件
                Snackbar.make(v, "改变字体颜色", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isBlack) {
                                    isBlack = false;
                                    contentTextView.setTextColor(Color.BLACK);

                                } else {
                                    isBlack = true;
                                    contentTextView.setTextColor(Color.GRAY);
                                }

                            }
                        })
                        .show();
                break;
        }
    }
}
