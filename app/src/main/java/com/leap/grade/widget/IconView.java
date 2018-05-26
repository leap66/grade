package com.leap.grade.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.leap.base.util.IsEmpty;
import com.leap.grade.R;

public class IconView extends AppCompatTextView {
  private String iconCite;
  private int iconSize, iconColor;
  private boolean iconUse;

  public IconView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initAttrs(attrs);
    setAttrs();
  }

  private void initAttrs(AttributeSet attrs) {
    TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IconView);
    if (typedArray != null) {
      iconCite = typedArray.getString(R.styleable.IconView_icon_cite);// Icon图标字符码，与text属性相当
      iconSize = typedArray.getDimensionPixelSize(R.styleable.IconView_icon_size, 0);// Icon大小，与textSize属性相当
      iconColor = typedArray.getInt(R.styleable.IconView_icon_color, 0);// Icon颜色，与textColor属性相当
      iconUse = typedArray.getBoolean(R.styleable.IconView_icon_use, false);// 是否启用自定义参数
      typedArray.recycle();
    }
  }

  private void setAttrs() {
    if (iconUse) {
      if (!IsEmpty.string(iconCite))
        setText(iconCite);
      if (iconSize != 0)
        setTextSize(iconSize);
      if (iconColor != 0)
        setTextColor(ContextCompat.getColor(getContext(), iconColor));
    }
    setTypeface(Typeface.createFromAsset(getContext().getAssets(), "iconfont.ttf"));
  }

  public void setIconUse(boolean iconUse) {
    this.iconUse = iconUse;
    setAttrs();
  }
}