package com.leap.grade.widget.validator;

/**
 * Rule : 效验实体继承
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public interface Rule {

  boolean validate(String value);

  String getErrorMessage();
}
