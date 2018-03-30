package com.leap.grade.widget.validator;

/**
 * ValidateResultCall : 效验回调接口
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public interface ValidateResultCall {

  void onSuccess();

  void onFailure(String message);
}
