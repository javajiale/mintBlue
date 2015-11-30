package com.fangjl.mintblue.slide;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.fangjl.mintblue.R;

/**
 * Created by sjj on 2015/11/19.
 */
public class RightTouch extends AppCompatActivity implements View.OnTouchListener{
    //手指右滑动时的最小速度
    private static final int XSPEED_MIN = 200;

    //手指右滑动的最小距离
    private static final int XDISTANCE_MIN = 150;

    //记录手指按下时的横坐标
    private float xDown;

    //记录手指移动时的横坐标
    private float xMove;

    //用于计算手指滑动的速度
    private VelocityTracker mVelocityTracker;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                //活动的距离
                int distanceX = (int) (xMove - xDown);
                //获取瞬时速度
                int xSpeed = getScrollVelocity();
                //当距离大于设定的最小距离切速度大于设定的最小速度的时候
                if(distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
                    finish();
                    //设置切换动画
                    overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                }
                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return true;
    }

    //创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
    private void createVelocityTracker(MotionEvent event) {
        if(mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    //回收VelocityTracker对象
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    //获取手指在content界面滑动的速度。
    // @return 滑动速度，以每秒钟移动了多少像素值为单位。

    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

}
