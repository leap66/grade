package com.leap.grade.widget.validator.rules;

import com.leap.grade.widget.validator.Rule;

/**
 * MaxLengthRule : 最大长度确认
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public class MaxLengthRule implements Rule {
  private String message;
  private int length;

  public MaxLengthRule(int length, String message) {
    this.length = length;
    this.message = message;
  }

  @Override
  public boolean validate(String value) {
    return !(value == null || value.length() == 0) && value.length() <= length;
  }

  @Override
  public String getErrorMessage() {
    return message;
  }
}
