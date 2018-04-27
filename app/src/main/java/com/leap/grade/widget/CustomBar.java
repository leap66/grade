package com.leap.grade.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leap.base.adapter.AppBindingAdapter;
import com.leap.grade.R;

public class CustomBar extends LinearLayout {
  private Context context;
  private String action, actionImage;
  private boolean showDivider, showChevron;
  private int actionIcon, actionIvSize;
  private ImageView actionIv;
  private TextView actionTv;

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
      actionIcon = typedArray.getResourceId(R.styleable.CustomBar_customHintIcon, 0);
      action = typedArray.getString(R.styleable.CustomBar_customHint);
      actionImage = typedArray.getString(R.styleable.CustomBar_customHintImage);
      actionIvSize = (int) typedArray.getDimension(R.styleable.CustomBar_customIvSize, 20);
      typedArray.recycle();
    }
  }

  private void initViews() {
    LayoutInflater.from(context).inflate(R.layout.widget_custom_bar, this);
    actionIv = (ImageView) findViewById(R.id.custom_bar_hint_iv);
    ImageView chevronIv = (ImageView) findViewById(R.id.custom_bar_chevron_iv);
    actionTv = (TextView) findViewById(R.id.custom_bar_hint_tv);
    View divider = findViewById(R.id.custom_bar_divider_view);
    divider.setVisibility(showDivider ? VISIBLE : GONE);
    chevronIv.setVisibility(showChevron ? VISIBLE : GONE);
  }

  private void setAttrs() {
    if (action != null)
      actionTv.setText(action);
    if (actionIcon != 0)
      actionIv.setImageResource(actionIcon);
    if (actionImage != null)
      AppBindingAdapter.loadImageUrl(actionIv, actionImage);
    actionIv.setVisibility(actionIcon != 0 ? VISIBLE : actionImage != null ? VISIBLE : GONE);
    formatLayout(actionIv);
  }

  public void updateView(View view) {
    LinearLayout contentLl = (LinearLayout) findViewById(R.id.custom_bar_content_ll);
    contentLl.removeAllViews();
    contentLl.addView(view);
    contentLl.setVisibility(view == null ? GONE : VISIBLE);
  }

  public void setAction(String action) {
    this.action = action;
    if (action != null)
      actionTv.setText(action);
  }

  public void setActionImage(String actionImage) {
    this.actionImage = actionImage;
    if (actionImage != null)
      AppBindingAdapter.loadImageUrl(actionIv, actionImage);
    actionIv.setVisibility(actionIcon != 0 ? VISIBLE : actionImage != null ? VISIBLE : GONE);
  }

  @BindingAdapter("customHintImage")
  public static void loadUrl(CustomBar customBar, String actionImage) {
    customBar.setActionImage(actionImage);
  }

  @BindingAdapter("customHint")
  public static void loadHint(CustomBar customBar, String hint) {
    customBar.setAction(hint);
  }

  private void formatLayout(View target) {
    LinearLayout.LayoutParams lp = (LayoutParams) target.getLayoutParams();
    lp.height = lp.width = actionIvSize;
    target.setLayoutParams(lp);
  }
}
