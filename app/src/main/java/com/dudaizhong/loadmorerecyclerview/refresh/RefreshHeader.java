package com.dudaizhong.loadmorerecyclerview.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dudaizhong.loadmorerecyclerview.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by Markable on 2017/1/17.
 * Github: https://github.com/ddz-mark
 * Info:
 */

public class RefreshHeader extends FrameLayout implements PtrUIHandler {

    private TextView mImageView;
    private TextView mTvRemind;

    RotateAnimation mRotateAnimation;

    /**
     * 状态识别
     */
    private int mState;

    private static final int MARGIN_RIGHT = 100;
    /**
     * 重置
     * 准备刷新
     * 开始刷新
     * 结束刷新
     */
    public static final int STATE_RESET = -1;
    public static final int STATE_PREPARE = 0;
    public static final int STATE_BEGIN = 1;
    public static final int STATE_FINISH = 2;

    public RefreshHeader(Context context) {
        super(context);
        initView();
    }

    public RefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.part_refresh_layout_top, this, false);
        mImageView = (TextView) view.findViewById(R.id.image);
        mTvRemind = (TextView) view.findViewById(R.id.tv_remain);
        addView(view);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        mState = STATE_RESET;
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mState = STATE_PREPARE;
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mState = STATE_BEGIN;
        //隐藏商品logo,开启跑步动画
        rotate();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mState = STATE_FINISH;
        rotate();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        //处理提醒字体
        switch (mState) {
            case STATE_PREPARE:
                //logo设置
                /*mIvMan.setAlpha(ptrIndicator.getCurrentPercent());
                mIvGoods.setAlpha(ptrIndicator.getCurrentPercent());
                FrameLayout.LayoutParams mIvManLayoutParams = (LayoutParams) mIvMan.getLayoutParams();
                if (ptrIndicator.getCurrentPercent() <= 1) {
                    mIvMan.setScaleX(ptrIndicator.getCurrentPercent());
                    mIvMan.setScaleY(ptrIndicator.getCurrentPercent());
                    mIvGoods.setScaleX(ptrIndicator.getCurrentPercent());
                    mIvGoods.setScaleY(ptrIndicator.getCurrentPercent());
                    int marginRight = (int) (MARGIN_RIGHT - MARGIN_RIGHT * ptrIndicator.getCurrentPercent());
                    mIvManLayoutParams.setMargins(0, 0, marginRight, 0);
                    mIvMan.setLayoutParams(mIvManLayoutParams);
                }*/
                if (ptrIndicator.getCurrentPercent() < 1.2) {
                    mTvRemind.setText("下拉刷新...");
                } else {
                    mTvRemind.setText("松开刷新...");
                }
                break;
            case STATE_BEGIN:
                mTvRemind.setText("更新中...");
                break;
            case STATE_FINISH:
                mTvRemind.setText("加载完成...");
                break;
        }
    }

    public void rotate() {
        mRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mImageView.setAnimation(mRotateAnimation);
        mRotateAnimation.setDuration(1000);
        mImageView.startAnimation(mRotateAnimation);
    }
}
