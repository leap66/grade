package com.leap.grade.widget.navigation.widget;

/**
 * TextAction :
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public class TextAction implements NavigationBarAction {
  private String action;

  public TextAction(String action) {
    this.action = action;
  }

  @Override
  public int getDrawable() {
    return 0;
  }

  @Override
  public String getText() {
    return action;
  }
}
