package com.leap.grade.widget.validator.rules;

import android.widget.TextView;

import com.leap.grade.widget.validator.Rule;

/**
 * ConfirmationRule : 输入字段效验
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public class ConfirmationRule implements Rule {
  private String message;
  private TextView field;

  public ConfirmationRule(TextView field, String message) {
    this.field = field;
    this.message = message;
  }

  @Override
  public boolean validate(String value) {
    return field.getText().equals(value);
  }

  @Override
  public String getErrorMessage() {
    return message;
  }
}
