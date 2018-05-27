package com.leap.grade.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * CustomIcon : 组件自定义IconView
 * <p>
 * </> Created by ylwei on 2018/5/26.
 */
public class CustomIcon extends AppCompatTextView {
  public CustomIcon(Context context, AttributeSet attrs) {
    super(context, attrs);
    setTypeface(Typeface.createFromAsset(getContext().getAssets(), "iconfont.ttf"));
  }
}
