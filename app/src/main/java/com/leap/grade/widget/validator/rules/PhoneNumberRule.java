package com.leap.grade.widget.validator.rules;

/**
 * PhoneNumberRule : 手机号长度效验
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public class PhoneNumberRule extends RegexRule {

  public PhoneNumberRule(String message) {
    super("^\\d{11}$", message);
  }
}
