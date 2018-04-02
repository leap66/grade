package com.leap.grade.util.exit;

import android.view.KeyEvent;

/**
 * LongPressHolder : 长按返回键退出
 * <p>
 * </> Created by ylwei on 2018/4/2.
 */
public class LongPressHolder {

  private ExitInterface exitInterface;

  public LongPressHolder(ExitInterface exitInterface) {
    this.exitInterface = exitInterface;
  }

  /**
   * 在dispatchKeyEvent()中调用，返回false则说明未处理
   */
  public boolean dispatchKeyEvent(KeyEvent event) {
    int keyCode = event.getKeyCode();
    switch (keyCode) {
    case KeyEvent.KEYCODE_BACK:
      if (event.isLongPress()) {
        exitInterface.exit();
        return true;
      } else {
        exitInterface.showExitTip();
        return true;
      }
    }
    return false;
  }
}
