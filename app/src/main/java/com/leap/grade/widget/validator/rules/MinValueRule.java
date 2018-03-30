package com.leap.grade.widget.validator.rules;

import com.leap.base.util.IsEmpty;
import com.leap.grade.widget.validator.Rule;

import java.math.BigDecimal;

/**
 * 最小值验证 null 和 "" 也会通过
 * <p>
 * </> Created by ylwei on 2017/8/5.
 */
public class MinValueRule implements Rule {
  private float minValue;
  private String message;

  public MinValueRule(float minValue, String message) {
    this.minValue = minValue;
    this.message = message;
  }

  @Override
  public boolean validate(String value) {
    if (IsEmpty.string(value))
      return true;
    try {
      return new BigDecimal(value).floatValue() >= minValue;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public String getErrorMessage() {
    return message;
  }
}
