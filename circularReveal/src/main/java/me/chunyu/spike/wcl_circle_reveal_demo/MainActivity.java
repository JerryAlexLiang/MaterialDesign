package me.chunyu.spike.wcl_circle_reveal_demo;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * 创建日期：2017/8/11 on 下午5:15
 * 描述:本文主要内容:
 (1) 修改分享元素的滑动轨迹, 以90°圆弧方式出现和返回, 即transition动画.
 (2) 生成和销毁页面的爆炸和凝聚效果, 即CircularReveal的使用方式.
 * 作者: liangyang
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.fab) FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Fab的跳转事件
    public void startOtherActivity(View view) {
        //确保版本号大于5.0, 支持Material Design的动画效果.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this, mFab, mFab.getTransitionName());
            startActivity(new Intent(this, OtherActivity.class), options.toBundle());
        } else {
            startActivity(new Intent(this, OtherActivity.class));
        }
    }
}
