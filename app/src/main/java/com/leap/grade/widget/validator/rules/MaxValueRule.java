package com.leap.grade.widget.validator.rules;

import com.leap.base.util.IsEmpty;
import com.leap.grade.widget.validator.Rule;

import java.math.BigDecimal;

/**
 * 最大值验证 null 和 "" 也会通过
 * <p>
 * </> Created by weiyl on 2017/8/5.
 */
public class MaxValueRule implements Rule {
  private float maxValue;
  private String message;

  public MaxValueRule(float maxValue, String message) {
    this.maxValue = maxValue;
    this.message = message;
  }

  @Override
  public boolean validate(String value) {
    if (IsEmpty.string(value))
      return true;
    try {
      return new BigDecimal(value).floatValue() <= maxValue;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public String getErrorMessage() {
    return message;
  }
}
