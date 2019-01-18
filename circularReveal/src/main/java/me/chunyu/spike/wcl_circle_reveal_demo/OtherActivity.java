package me.chunyu.spike.wcl_circle_reveal_demo;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 跳转的Activity
 * <p>
 * Created by wangchenlong on 16/2/26.
 */
public class OtherActivity extends AppCompatActivity {

    @Bind(R.id.other_fab_circle) FloatingActionButton mFabCircle;
    @Bind(R.id.other_tv_container) TextView mTVContainer;
    @Bind(R.id.other_iv_close) ImageView mIvClose;
    @Bind(R.id.other_rl_container) RelativeLayout mRlContainer;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ButterKnife.bind(this);

        //显示逻辑, 设置入场和退场动画, 爆炸的动画效果.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation(); // 入场动画
            setupExitAnimation(); // 退场动画
        } else {
            initViews();
        }
    }

    /**
     * 分享元素切换使用弧度规矩, 即arc_motion, 90°旋转过去和回来. 在动画结束后, 页面显示使用爆炸效果.
     */
    // 入场动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        //arc_motion角度变换
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override public void onTransitionStart(Transition transition) {

            }

            @Override public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override public void onTransitionCancel(Transition transition) {

            }

            @Override public void onTransitionPause(Transition transition) {

            }

            @Override public void onTransitionResume(Transition transition) {

            }
        });
    }

    // 动画展示
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        GuiUtils.animateRevealShow(
                this, mRlContainer,
                mFabCircle.getWidth() / 2, R.color.colorAccent,
                new GuiUtils.OnRevealAnimationListener() {
                    @Override public void onRevealHide() {

                    }

                    @Override public void onRevealShow() {
                        initViews();
                    }
                });
    }

    // 退出动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitAnimation() {
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(300);
    }

    // 初始化视图
    private void initViews() {
        new Handler(Looper.getMainLooper()).post(() -> {
            Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
            animation.setDuration(300);
            mTVContainer.startAnimation(animation);
            mIvClose.setAnimation(animation);
            mTVContainer.setVisibility(View.VISIBLE);
            mIvClose.setVisibility(View.VISIBLE);
        });
    }

    // 退出按钮:退出逻辑, 退出动画, 凝聚的动画效果.
    public void backActivity(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            onBackPressed();
        } else {
            defaultBackPressed();
        }
    }

    // 退出事件
    //退出动画是凝聚效果, 同样使用的是CircularReveal.
    //退出和显示的区别就是起始和终止的半径不同.
    @Override public void onBackPressed() {
        GuiUtils.animateRevealHide(
                this, mRlContainer,
                mFabCircle.getWidth() / 2, R.color.colorAccent,
                new GuiUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {
                        defaultBackPressed();
                    }

                    @Override
                    public void onRevealShow() {

                    }
                });
    }

    // 默认回退
    private void defaultBackPressed() {
        super.onBackPressed();
    }
}
