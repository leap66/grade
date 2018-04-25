package com.leap.grade.widget.autoscroll;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.leap.base.util.DensityUtil;

/**
 * AutoScrollView : 带滚动监听的scrollview
 * <p>
 * </> Created by ylwei on 2018/4/20.
 */
public class AutoScrollView extends ScrollView {
  // 目的是达到一个延迟的效果
  private static final float MOVE_FACTOR = 0.3f;
  // 松开手指后, 界面回到正常位置需要的动画时间
  private static final int ANIM_TIME = 450;
  // 手指按下时的Y值, 用于在移动时计算移动距离
  // 如果按下时不能上拉和下拉，会在手指移动时更新为当前手指的Y值
  private float startY;
  private AutoScrollViewListener listener;
  // ScrollView的子View， 也是ScrollView的唯一一个子View
  private View rootView;

  private int deltaY;
  private float contentViewY;
  int offset = 0, lastOffset;
  // 用于记录正常的布局位置
  private Rect originalRect = new Rect();
  private int height;

  public void setScrollViewListener(AutoScrollViewListener listener) {
    this.listener = listener;
  }

  public AutoScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
    height = -DensityUtil.dip2px(20);
  }

  /**
   * 获取子View
   */
  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    if (getChildCount() > 0) {
      rootView = getChildAt(0);
    }
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    print("changed--:" + offset);
    // ScrollView中的唯一子控件的位置信息, 这个位置信息在整个控件的生命周期中保持不变
    if (rootView != null) {
      originalRect.set(rootView.getLeft(), rootView.getTop(), rootView.getRight(),
          rootView.getBottom());
      rootView.layout(originalRect.left, originalRect.top + lastOffset, originalRect.right,
          originalRect.bottom + lastOffset);
    }
  }

  /**
   * 在触摸事件中, 处理上拉和下拉的逻辑
   */
  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {

    if (rootView == null) {
      return super.dispatchTouchEvent(ev);
    }

    int action = ev.getAction();

    switch (action) {
    case MotionEvent.ACTION_DOWN:
      // 记录按下时的Y值
      startY = ev.getY();

      break;

    case MotionEvent.ACTION_UP:
      int curTop = rootView.getTop();
      listener.onScrollChanged(offset, true);
      boundBack(curTop);

      break;
    case MotionEvent.ACTION_MOVE:

      // 计算手指移动的距离
      float nowY = ev.getY();
      deltaY = (int) (nowY - startY);

      contentViewY = rootView.getY();
      // 计算移动偏移量
      offset = (int) (deltaY * MOVE_FACTOR) + lastOffset;

      // 随着手指的移动而移动布局
      // if(top>4&&top<300){
      // int offsetT= (int) (deltaY * 0.6);
      // scrollViewListener.onScrollChanged(offsetT, false);
      // }else

      listener.onScrollChanged(offset, false);

      rootView.layout(originalRect.left, originalRect.top + offset, originalRect.right,
          originalRect.bottom + offset);

      int top = originalRect.top + offset;
      int bottom = originalRect.bottom + offset;

      print("Action__-offset:" + offset + "---contentViewY:" + contentViewY + "nowY:" + nowY
          + "--startY:" + startY + "--deltaY:" + deltaY + "lastOffset:" + lastOffset);

      break;
    default:
      break;
    }

    return super.dispatchTouchEvent(ev);
  }

  /**
   * 将内容布局移动到原位置 可以在UP事件中调用, 也可以在其他需要的地方调用, 如手指移动到当前ScrollView外时
   */
  private void boundBack(int curTop) {
    // 设置回到正常的布局位置
    if (contentViewY > 0) {
      rootView.layout(originalRect.left, originalRect.top, originalRect.right, originalRect.bottom);

      TranslateAnimation anim = new TranslateAnimation(0, 0, curTop - originalRect.top, 0);
      anim.setInterpolator(new DecelerateInterpolator());
      anim.setDuration(ANIM_TIME);

      rootView.startAnimation(anim);
      lastOffset = 0;
      offset = 0;
    } else {
      // 最大上移300 offset= -600 -300
      if (contentViewY < height) {// height
        // 那么就需要回到-300
        rootView.layout(originalRect.left, originalRect.top + height, originalRect.right,
            originalRect.bottom + height);// height

        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, 0);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.setDuration(ANIM_TIME);

        rootView.startAnimation(anim);
        lastOffset = height;
      } else {
        lastOffset = height;// offset; 现在写的height
      }

    }
  }

  private void print(String msg) {
    Log.e("ScrollView", msg);
  }
}
