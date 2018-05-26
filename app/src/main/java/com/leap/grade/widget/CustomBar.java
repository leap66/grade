package com.leap.grade.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leap.base.adapter.AppBindingAdapter;
import com.leap.base.util.DensityUtil;
import com.leap.base.util.IsEmpty;
import com.leap.base.util.ViewUtil;
import com.leap.grade.R;

public class CustomBar extends LinearLayout {
  private Context context;
  private String hintStr, imageLink, iconStr;
  private boolean showDivider, showChevron, showImage, showIcon;
  private int imageRes, iconSize, iconColor, iconBg, actionSize;
  private ImageView image;
  private TextView hintTv;
  private IconView iconTv;

  public CustomBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    initAttributeSet(attrs);
    initViews();
    setAttrs();
  }

  private void initAttributeSet(AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomBar);
    if (typedArray != null) {
      showDivider = typedArray.getBoolean(R.styleable.CustomBar_customShowDivider, true);
      showChevron = typedArray.getBoolean(R.styleable.CustomBar_customShowChevron, true);
      showImage = typedArray.getBoolean(R.styleable.CustomBar_customShowImage, false);
      showIcon = typedArray.getBoolean(R.styleable.CustomBar_customShowIcon, false);
      imageRes = typedArray.getResourceId(R.styleable.CustomBar_customImageRes, 0);
      imageLink = typedArray.getString(R.styleable.CustomBar_customImageLink);
      actionSize = (int) typedArray.getDimension(R.styleable.CustomBar_customActionSize, 20);
      hintStr = typedArray.getString(R.styleable.CustomBar_customHint);
      iconSize = (int) typedArray.getDimension(R.styleable.CustomBar_customIconSize, 16);
      iconColor = typedArray.getColor(R.styleable.CustomBar_customIconColor,
          ContextCompat.getColor(context, R.color.softBlue));
      iconBg = typedArray.getColor(R.styleable.CustomBar_customIconBg, 0);
      iconStr = typedArray.getString(R.styleable.CustomBar_customIcon);
      typedArray.recycle();
    }
  }

  private void initViews() {
    LayoutInflater.from(context).inflate(R.layout.widget_custom_bar, this);
    image = (ImageView) findViewById(R.id.custom_bar_image);
    hintTv = (TextView) findViewById(R.id.custom_bar_hint);
    iconTv = (IconView) findViewById(R.id.custom_bar_icon);
    View divider = findViewById(R.id.custom_bar_divider_view);
    divider.setVisibility(showDivider ? VISIBLE : GONE);
    IconView chevronIc = (IconView) findViewById(R.id.custom_bar_chevron_ic);
    chevronIc.setVisibility(showChevron ? VISIBLE : GONE);
  }

  private void setAttrs() {
    if (hintStr != null)
      hintTv.setText(hintStr);
    if (imageRes != 0)
      image.setImageResource(imageRes);
    if (imageLink != null)
      AppBindingAdapter.loadImageUrl(image, imageLink);
    image.setVisibility(
        !showImage ? GONE : imageRes != 0 ? VISIBLE : imageLink != null ? VISIBLE : GONE);
    iconTv.setVisibility(!showIcon ? GONE : IsEmpty.string(iconStr) ? GONE : VISIBLE);
    iconTv.setText(iconStr);
    iconTv.setTextSize(DensityUtil.px2dp(iconSize));
    iconTv.setTextColor(iconColor);
    if (iconBg != 0) {
      iconTv.setTextColor(ContextCompat.getColor(context, R.color.white));
      ViewUtil.setDrawable(4, 1, iconBg, iconBg, iconTv);
    }
    formatLayout(image, iconTv);
  }

  public void updateView(View view) {
    LinearLayout contentLl = (LinearLayout) findViewById(R.id.custom_bar_content_ll);
    contentLl.removeAllViews();
    contentLl.addView(view);
    contentLl.setVisibility(view == null ? GONE : VISIBLE);
  }

  public void setAction(String hintStr) {
    this.hintStr = hintStr;
    if (hintStr != null)
      hintTv.setText(hintStr);
  }

  public void setActionImage(String imageLink) {
    this.imageLink = imageLink;
    if (imageLink != null)
      AppBindingAdapter.loadImageUrl(image, imageLink);
    image.setVisibility(imageRes != 0 ? VISIBLE : imageLink != null ? VISIBLE : GONE);
  }

  @BindingAdapter("customImageLink")
  public static void loadUrl(CustomBar customBar, String actionImage) {
    customBar.setActionImage(actionImage);
  }

  @BindingAdapter("customHint")
  public static void loadHint(CustomBar customBar, String hint) {
    customBar.setAction(hint);
  }

  private void formatLayout(View... targets) {
    for (View target : targets) {
      LinearLayout.LayoutParams lp = (LayoutParams) target.getLayoutParams();
      lp.height = lp.width = actionSize;
      target.setLayoutParams(lp);
    }
  }
}
