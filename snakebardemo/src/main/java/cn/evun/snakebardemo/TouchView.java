package cn.evun.snakebardemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Shuai.Li13 on 2017/7/13.
 */

public class TouchView extends TextView {

    private int downX;
    private int downY;
    private boolean isMove;
    //屏幕密度
    private float density = getResources().getDisplayMetrics().density;

    public TouchView(Context context) {
        super(context, null);

    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TouchView);
        isMove = typedArray.getBoolean(R.styleable.TouchView_isMove, true);
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //相对于控件左边缘的距离
                downX = (int) event.getRawX();
                downY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int moveY = (int) event.getRawY();

                int dx = moveX - downX;
                int dy = moveY - downY;

                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) getLayoutParams();
                layoutParams.leftMargin = layoutParams.leftMargin + (int) (dx * density);
                layoutParams.topMargin = layoutParams.topMargin + (int) (dy * density) ;

                setLayoutParams(layoutParams);
                requestLayout();

                downX = moveX;
                downY = moveY;

                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return isMove;
    }
}
