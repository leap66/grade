package com.leap.grade.widget.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leap.base.util.DensityUtil;
import com.leap.base.util.IsEmpty;
import com.leap.grade.R;
import com.leap.grade.databinding.NavigationBarBinding;
import com.leap.grade.widget.navigation.widget.ImageAction;
import com.leap.grade.widget.navigation.widget.ImageTextAction;
import com.leap.grade.widget.navigation.widget.NavigationBarAction;
import com.leap.grade.widget.navigation.widget.NavigationBarClickListener;
import com.leap.grade.widget.navigation.widget.TextAction;

import java.util.ArrayList;
import java.util.List;

public class NavigationBar extends LinearLayout {
  private NavigationBarBinding binding;
  private NavigationBarClickListener listener;
  private List<View> actionList;
  private Context context;
  private int background;
  private int navigationIcon;
  private String title;
  private int titleSize;
  private int titleColor;
  private int actionImage;
  private String action;
  private int actionSize;
  private int actionColor;

  public NavigationBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    actionList = new ArrayList<>();
    initAttributeSet(attrs);
    initViews();
  }

  private void initAttributeSet(AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
    if (typedArray != null) {
      background = typedArray.getResourceId(R.styleable.NavigationBar_bgColor,
          ContextCompat.getColor(context, R.color.white));
      navigationIcon = typedArray.getResourceId(R.styleable.NavigationBar_navigationIcon,
          R.mipmap.ic_title_back);
      actionImage = typedArray.getResourceId(R.styleable.NavigationBar_actionImage, 0);
      title = typedArray.getString(R.styleable.NavigationBar_titleText);
      titleSize = (int) typedArray.getDimension(R.styleable.NavigationBar_titleSize, 18);
      titleColor = typedArray.getColor(R.styleable.NavigationBar_titleTextColor,
          ContextCompat.getColor(context, R.color.charcoalGrey));
      action = typedArray.getString(R.styleable.NavigationBar_actionText);
      actionColor = typedArray.getColor(R.styleable.NavigationBar_actionTextColor,
          ContextCompat.getColor(context, R.color.coolGrey));
      actionSize = (int) typedArray.getDimension(R.styleable.NavigationBar_actionTextSize, 16);
      typedArray.recycle();
    }
  }

  private void initViews() {
    binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.navigation_bar, null,
        true);
    binding.setPresenter(new Presenter());
    binding.rootLayout.setBackgroundColor(background);
    binding.backIv.setImageResource(navigationIcon);
    if (actionImage != 0)
      binding.actionImage.setImageResource(actionImage);
    binding.titleTv.setText(title);
    binding.titleTv.setTextColor(titleColor);
    binding.titleTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, titleSize);
    if (IsEmpty.string(action))
      binding.actionTv.setVisibility(GONE);
    binding.actionTv.setText(action);
    binding.actionTv.setTextColor(actionColor);
    binding.actionTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, actionSize);
    addView(binding.getRoot(),
        new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
  }

  public void setListener(NavigationBarClickListener listener) {
    this.listener = listener;
  }

  public class Presenter {

    public void onBack() {
      if (!IsEmpty.object(listener))
        listener.onBack();
    }

    public void onActionImage() {
      if (!IsEmpty.object(listener))
        listener.performAction(binding.actionImage);
    }

    public void onActionText() {
      if (!IsEmpty.object(listener))
        listener.performAction(binding.actionTv);
    }
  }

  public void addAction(NavigationBarAction action) {
    if (actionList.size() > 2)
      return;
    actionList.add(inflaterAction(action));
    binding.actionLl.removeAllViews();
    for (View view : actionList)
      binding.actionLl.addView(view);
  }

  private View inflaterAction(NavigationBarAction action) {
    View view;
    if (action instanceof ImageAction) {
      ImageView imageView = new ImageView(getContext());
      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
      imageView.setImageResource(action.getDrawable());
      int padding = DensityUtil.dip2px(11);
      imageView.setPadding(padding, padding, padding, padding);
      view = imageView;
      view.setLayoutParams(binding.actionImage.getLayoutParams());
    } else if (action instanceof TextAction) {
      TextView textView = new TextView(getContext());
      textView.setText(action.getText());
      textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, actionSize);
      textView.setTextColor(actionColor);
      textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
      textView.setPadding(DensityUtil.dip2px(4), 0, DensityUtil.dip2px(12), 0);
      view = textView;
      view.setLayoutParams(binding.actionImage.getLayoutParams());
    } else if (action instanceof ImageTextAction) {
      TextView textView = new TextView(getContext());
      textView.setText(action.getText());
      textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, actionSize);
      textView.setTextColor(actionColor);
      textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
      textView.setPadding(DensityUtil.dip2px(4), 0, DensityUtil.dip2px(12), 0);
      textView.setCompoundDrawablePadding(DensityUtil.dip2px(2));
      Drawable leftDrawable = ContextCompat.getDrawable(getContext(), action.getDrawable());
      if (leftDrawable != null) {
        leftDrawable.setBounds(0, 0, leftDrawable.getIntrinsicWidth(),
            leftDrawable.getIntrinsicHeight());
        textView.setCompoundDrawables(leftDrawable, null, null, null);
      }
      view = textView;
      view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
          ViewGroup.LayoutParams.MATCH_PARENT));
    } else {
      return null;
    }
    view.setTag(action);
    view.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!IsEmpty.object(listener))
          listener.performAction(view);
      }
    });
    return view;
  }
}
