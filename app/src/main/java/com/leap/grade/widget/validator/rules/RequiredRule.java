package com.leap.grade.widget.validator.rules;

import com.leap.grade.widget.validator.Rule;

/**
 * RequiredRule : 非空效验
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public class RequiredRule implements Rule {
  private String message;

  public RequiredRule(String message) {
    this.message = message;
  }

  @Override
  public boolean validate(String value) {
    return value != null && !value.isEmpty();
  }

  @Override
  public String getErrorMessage() {
    return message;
  }
}
