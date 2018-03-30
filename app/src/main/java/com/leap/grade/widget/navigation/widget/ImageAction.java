package com.leap.grade.widget.navigation.widget;

/**
 * ImageAction :
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public class ImageAction implements NavigationBarAction {
  private int mDrawable;

  public ImageAction(int drawable) {
    mDrawable = drawable;
  }

  @Override
  public int getDrawable() {
    return mDrawable;
  }

  @Override
  public String getText() {
    return null;
  }
}
