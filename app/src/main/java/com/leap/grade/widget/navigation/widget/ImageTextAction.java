package com.leap.grade.widget.navigation.widget;

/**
 * ImageAction :
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public class ImageTextAction implements NavigationBarAction {
  private int drawable;
  private String action;

  public ImageTextAction(int drawable, String action) {
    this.drawable = drawable;
    this.action = action;
  }

  @Override
  public int getDrawable() {
    return drawable;
  }

  @Override
  public String getText() {
    return action;
  }

}
