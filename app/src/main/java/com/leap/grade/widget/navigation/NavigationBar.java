package com.leap.grade.widget.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leap.base.util.IsEmpty;
import com.leap.grade.R;
import com.leap.grade.widget.IconView;
import com.leap.grade.widget.navigation.widget.NavigationBarClickListener;

/**
 * NavigationBar : 统一TitleBar
 * <p>
 * </> Created by ylwei on 2018/5/25.
 */
public class NavigationBar extends LinearLayout {
  private NavigationBarClickListener listener;
  private Context context;
  private int background, titleSize, titleColor, actionSize, actionColor;
  private String backStr, titleStr, actionStr, actionSub;
  private boolean showIcon;
  private TextView titleTv;
  private IconView backIc, actionIc, actionSubIc;

  public NavigationBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;
    initAttributeSet(attrs);
    initViews();
    initListener();
  }

  private void initAttributeSet(AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
    if (typedArray != null) {
      background = ContextCompat.getColor(context,
          typedArray.getResourceId(R.styleable.NavigationBar_bgColor, R.color.white));
      actionColor = ContextCompat.getColor(context,
          typedArray.getResourceId(R.styleable.NavigationBar_actionColor, R.color.blue_grey));
      titleColor = typedArray.getColor(R.styleable.NavigationBar_titleColor,
          ContextCompat.getColor(context, R.color.charcoalGrey));
      showIcon = typedArray.getBoolean(R.styleable.NavigationBar_showBack, true);
      backStr = typedArray.getString(R.styleable.NavigationBar_backStr);
      titleStr = typedArray.getString(R.styleable.NavigationBar_titleStr);
      actionStr = typedArray.getString(R.styleable.NavigationBar_actionStr);
      actionSub = typedArray.getString(R.styleable.NavigationBar_actionSub);
      titleSize = (int) typedArray.getDimension(R.styleable.NavigationBar_titleSize, 18);
      actionSize = (int) typedArray.getDimension(R.styleable.NavigationBar_actionSize, 20);
      typedArray.recycle();
    }
  }

  private void initViews() {
    LayoutInflater.from(context).inflate(R.layout.navigation_bar, this);
    LinearLayout rootLl = (LinearLayout) findViewById(R.id.bar_root_layout);
    backIc = (IconView) findViewById(R.id.bar_back_ic);
    titleTv = (TextView) findViewById(R.id.bar_title_tv);
    actionIc = (IconView) findViewById(R.id.bar_action_ic);
    actionSubIc = (IconView) findViewById(R.id.bar_action_sub);
    rootLl.setBackgroundColor(background);
    if (showIcon) {
      String temp = getContext().getString(R.string.icon_arrow_left);
      formatTv(!IsEmpty.string(backStr) ? backStr : temp, actionColor, actionSize, backIc);
    } else {
      backIc.setVisibility(GONE);
    }
    formatTv(titleStr, titleColor, titleSize, titleTv);
    formatTv(actionStr, actionColor, actionSize, actionIc);
    formatTv(actionSub, actionColor, actionSize, actionSubIc);
  }

  private void initListener() {
    backIc.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!IsEmpty.object(listener))
          listener.onBack();
      }
    });
    actionIc.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!IsEmpty.object(listener))
          listener.onAction();
      }
    });
    actionSubIc.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!IsEmpty.object(listener))
          listener.onActionSub();
      }
    });
  }

  public void setListener(NavigationBarClickListener listener) {
    this.listener = listener;
  }

  public TextView getTitleTv() {
    return titleTv;
  }

  public IconView getBackIc() {
    return backIc;
  }

  public IconView getActionIc() {
    return actionIc;
  }

  public IconView getActionSubIc() {
    return actionSubIc;
  }

  private void formatTv(String str, int color, int size, TextView target) {
    target.setText(str);
    target.setTextColor(color);
    target.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
  }
}
