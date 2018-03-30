package com.leap.grade.widget.validator.rules;

import com.leap.grade.widget.validator.Rule;

/**
 * ExactLengthRule : 精确长度确认
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public class ExactLengthRule implements Rule {
  private String message = "Must be at most 16 characters long";
  private int length;

  public ExactLengthRule(int length, String message) {
    this.length = length;
    this.message = message;
  }

  @Override
  public boolean validate(String value) {
    return (value == null && length == 0) || (value != null && value.length() == length);

  }

  @Override
  public String getErrorMessage() {
    return message;
  }
}
