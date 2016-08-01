package cn.ccx.iosunlock;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Scroller;


/**
 * Created by chenchangxing on 16/5/16.
 */
public class ClockRingView extends ImageView {

    OnFinishListener mOnFinishListener;
    Scroller mScroller;
    int[] xscreen = new int[2];

    public ClockRingView(Context context) {
        super(context);
        mScroller = new Scroller(context);
    }

    public ClockRingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getRawX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (x > ((View) getParent()).getLeft() + getWidth() / 2 && x < ((View) getParent()).getRight() - getWidth() / 2) {
                    ((View)getParent()).scrollTo(-x + ((View) getParent()).getLeft() + getWidth() / 2, 0);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {

                getLocationOnScreen(xscreen);
                if (xscreen[0] + getWidth() / 2 > ((View) getParent()).getWidth() / 2 + ((View) getParent()).getLeft() && xscreen[0] < ((View) getParent()).getRight() - getWidth() / 2)  {
                    mScroller.startScroll(((View) getParent()).getScrollX(),
                            0,
                            -(((View) getParent()).getRight() - xscreen[0] - getWidth()),
                            0, 500);
                } else if (xscreen[0] <= ((View) getParent()).getWidth() / 2 + ((View) getParent()).getLeft()) {
                    mScroller.startScroll(((View)getParent()).getScrollX(),
                            0,
                            -((View)getParent()).getScrollX(),
                            0, 500);
                }
                invalidate();
                break;
            }
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        } else {
            getLocationOnScreen(xscreen);
            if (xscreen[0] > ((View)getParent()).getLeft()) {
                if (mOnFinishListener != null) {
                    mOnFinishListener.onFinish();
                }
            }
        }
    }

    public OnFinishListener getOnFinishListener() {
        return mOnFinishListener;
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        mOnFinishListener = onFinishListener;
    }

    public interface OnFinishListener {
        void onFinish();
    }
}
