package com.leap.grade.widget;

import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.leap.grade.R;

import java.text.MessageFormat;

/**
 * CountTimeTextView : 倒计时控件
 * <p>
 * </> Created by ylwei on 2018/3/30.
 */
public class CountTimeTextView extends AppCompatTextView {
  private long totalTime = 60000;
  private CountDownTimer timer;
  private boolean isCount;

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
  public CountTimeTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setGravity(Gravity.CENTER);
    setEnabled(false);
    setText(getResources().getString(R.string.send_code));
    setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_send_selector));
  }

  public void startCount() {
    this.startCount(totalTime);
  }

  public void startCount(long totalTime) {
    this.totalTime = totalTime;
    long tickTime = 1000;
    setEnabled(false);
    isCount = true;
    timer = new CountDownTimer(totalTime, tickTime) {
      @Override
      public void onTick(long millisUntilFinished) {
        String temp = MessageFormat.format(getResources().getString(R.string.send_code_during),
            millisUntilFinished / 1000);
        setText(temp);
      }

      @Override
      public void onFinish() {
        isCount = false;
        setEnabled(true);
        setText(getResources().getString(R.string.send_code));
      }
    };
    timer.start();
  }

  public void finishCount() {
    if (timer != null) {
      timer.cancel();
      timer.onFinish();
    }
    // setEnabled(true);
  }

  public void setTotalTime(long totalTime) {
    this.totalTime = totalTime;
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    finishCount();
  }

  @Override
  public void setEnabled(boolean enabled) {
    if (!isCount)
      super.setEnabled(enabled);
  }
}
