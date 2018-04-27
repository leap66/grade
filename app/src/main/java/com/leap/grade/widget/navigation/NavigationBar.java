package com.leap.grade.widget.navigation;

import android.content.Context;
import android.content.res.TypedArray;
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
import com.leap.grade.widget.navigation.widget.ImageAction;
import com.leap.grade.widget.navigation.widget.ImageTextAction;
import com.leap.grade.widget.navigation.widget.NavigationBarAction;
import com.leap.grade.widget.navigation.widget.NavigationBarClickListener;
import com.leap.grade.widget.navigation.widget.TextAction;

import java.util.ArrayList;
import java.util.List;

public class NavigationBar extends LinearLayout {
  private NavigationBarClickListener listener;
  private List<View> actionList;
  private Context context;
  private int background, navigationIcon, actionImage;
  private String title, action;
  private int titleSize, titleColor, actionSize, actionColor;
  private boolean showIcon;
  private LinearLayout actionLl;
  private ImageView backIv, actionIv;
  private TextView actionTv;

  public NavigationBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    actionList = new ArrayList<>();
    initAttributeSet(attrs);
    initViews();
    initListener();
  }

  private void initAttributeSet(AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
    if (typedArray != null) {
      background = ContextCompat.getColor(context,
          typedArray.getResourceId(R.styleable.NavigationBar_bgColor, R.color.white));
      navigationIcon = typedArray.getResourceId(R.styleable.NavigationBar_navigationIcon,
          R.mipmap.ic_title_back);
      showIcon = typedArray.getBoolean(R.styleable.NavigationBar_showIcon, true);
      actionImage = typedArray.getResourceId(R.styleable.NavigationBar_actionImage, 0);
      title = typedArray.getString(R.styleable.NavigationBar_titleText);
      titleSize = (int) typedArray.getDimension(R.styleable.NavigationBar_titleSize, 18);
      titleColor = typedArray.getColor(R.styleable.NavigationBar_titleTextColor,
          ContextCompat.getColor(context, R.color.white));
      action = typedArray.getString(R.styleable.NavigationBar_actionText);
      actionColor = typedArray.getResourceId(R.styleable.NavigationBar_actionTextColor,
          ContextCompat.getColor(context, R.color.coolGrey));
      actionSize = (int) typedArray.getDimension(R.styleable.NavigationBar_actionTextSize, 16);
      typedArray.recycle();
    }
  }

  private void initViews() {
    LayoutInflater.from(context).inflate(R.layout.navigation_bar, this);
    // NavigationBarBinding barBinding = DataBindingUtil.bind(getChildAt(0));
    LinearLayout rootLl = (LinearLayout) findViewById(R.id.root_layout);
    backIv = (ImageView) findViewById(R.id.back_iv);
    backIv.setVisibility(showIcon ? VISIBLE : INVISIBLE);
    TextView titleTv = (TextView) findViewById(R.id.title_tv);
    actionLl = (LinearLayout) findViewById(R.id.action_ll);
    actionIv = (ImageView) findViewById(R.id.action_iv);
    actionTv = (TextView) findViewById(R.id.action_tv);
    rootLl.setBackgroundColor(background);
    backIv.setImageResource(navigationIcon);
    titleTv.setTextColor(titleColor);
    titleTv.setText(title);
    titleTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, titleSize);
    if (IsEmpty.string(action))
      actionTv.setVisibility(GONE);
    if (actionImage != 0)
      actionIv.setImageResource(actionImage);
    actionTv.setText(action);
    actionTv.setTextColor(actionColor);
    actionTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, actionSize);
  }

  private void initListener() {
    backIv.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!IsEmpty.object(listener))
          listener.onBack();
      }
    });
    actionIv.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!IsEmpty.object(listener))
          listener.performAction(v);
      }
    });
    actionTv.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!IsEmpty.object(listener))
          listener.performAction(v);
      }
    });
  }

  public void setListener(NavigationBarClickListener listener) {
    this.listener = listener;
  }

  public void addAction(NavigationBarAction action) {
    if (actionList.size() > 2)
      return;
    actionList.add(inflaterAction(action));
    actionLl.removeAllViews();
    for (View view : actionList)
      actionLl.addView(view);
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
      view.setLayoutParams(actionIv.getLayoutParams());
    } else if (action instanceof TextAction) {
      TextView textView = new TextView(getContext());
      textView.setText(action.getText());
      textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, actionSize);
      textView.setTextColor(actionColor);
      textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
      textView.setPadding(DensityUtil.dip2px(4), 0, DensityUtil.dip2px(12), 0);
      view = textView;
      view.setLayoutParams(actionIv.getLayoutParams());
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
