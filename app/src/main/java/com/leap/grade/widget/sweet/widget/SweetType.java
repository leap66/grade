package com.leap.grade.widget.sweet.widget;

/**
 * SweetType : SweetDialog样式
 * <p>
 * </> Created by ylwei on 2018/5/25.
 */
public enum SweetType {
  SUCCESS("成功"), FAILED("失败"), WARNING("警告"), DEFAULT("默认");

  SweetType(String name) {
    this.name = name;
  }

  private String name;

  public String getName() {
    return name;
  }
}
