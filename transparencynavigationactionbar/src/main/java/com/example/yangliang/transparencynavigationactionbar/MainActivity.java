package com.example.yangliang.transparencynavigationactionbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 创建日期：2019/1/17 on 下午7:00
 * 描述: 沉浸式状态栏
 * 作者: liangyang
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button buttonOne;
    private Button buttonTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置沉浸式状态栏背景为白色
        //设置沉浸式状态栏背景为白色
        FrameLayout actionBarLayout = (FrameLayout) findViewById(R.id.toolbar);
        actionBarLayout.setBackgroundColor(Color.WHITE);
        //沉浸式状态栏背景为白色时候，设置状态栏字体颜色为黑色
        changeStatusBarTextColor(true);

        TextView titleName = (TextView) findViewById(R.id.default_toolbar_title);
        titleName.setTextColor(Color.BLACK);

        buttonOne = (Button) findViewById(R.id.btn_one);
        buttonTwo = (Button) findViewById(R.id.btn_two);

        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                OneActivity.actionStart(MainActivity.this);
                break;

            case R.id.btn_two:
                TwoActivity.actionStart(MainActivity.this);
                break;

            default:
                break;
        }
    }
}
