package com.leap.grade.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.leap.base.listener.OnTextChangedListener;
import com.leap.base.util.DensityUtil;
import com.leap.base.util.IsEmpty;
import com.leap.grade.R;

/**
 * ClearEditText : 统一输入框控件
 * <p>
 * </> Created by ylwei on 2018/2/24.
 */
public class ClearEditText extends AppCompatEditText {
  private Drawable clearIcon;

  public ClearEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    setSelectAllOnFocus(true);
    setClearIcon(R.mipmap.ic_clear);
    addTextChangedListener(new OnTextChangedListener() {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        updateClearIcon(!IsEmpty.string(s.toString()) && hasFocus());
      }
    });
  }

  @Override
  protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
    super.onFocusChanged(focused, direction, previouslyFocusedRect);
    boolean empty = IsEmpty.string(getText().toString());
    updateClearIcon(focused && !empty);
  }

  @Override
  @SuppressLint("ClickableViewAccessibility")
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_UP) {
      if (getCompoundDrawables()[2] != null) {
        if (event.getX() > (getWidth() - getTotalPaddingRight())
            && (event.getX() < ((getWidth() - getPaddingRight())))) {
          this.setText(null);
        }
      }
    }
    return super.onTouchEvent(event);
  }

  private void updateClearIcon(boolean show) {
    setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
        show ? clearIcon : null, getCompoundDrawables()[3]);
    setCompoundDrawablePadding(DensityUtil.dip2px(5));
  }

  public void setClearIcon(int clearResId) {
    clearIcon = ContextCompat.getDrawable(getContext(), clearResId);
    clearIcon.setBounds(0, 0, clearIcon.getIntrinsicWidth(), clearIcon.getIntrinsicHeight());
  }
}
