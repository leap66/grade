package com.leap.grade.util.exit;

import android.view.KeyEvent;

/**
 * TwicePressHolder : 按两次返回键退出
 * <p>
 * </> Created by ylwei on 2018/4/2.
 */
public class TwicePressHolder {
  private long lastTime = 0;
  private ExitInterface exitInterface;

  private int delay = 1000;

  /**
   * 按两次返回键退出
   */
  public TwicePressHolder(ExitInterface exitInterface, int delay) {
    this.exitInterface = exitInterface;
    this.delay = delay;
  }

  /**
   * 在onKeyDown()中调用，返回false则说明未处理
   */
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
      if ((System.currentTimeMillis() - lastTime) > delay) {
        exitInterface.showExitTip();
        lastTime = System.currentTimeMillis();
      } else {
        exitInterface.exit();
      }
      return true;
    }
    return false;
  }
}
