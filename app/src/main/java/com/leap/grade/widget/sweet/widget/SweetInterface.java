package com.leap.grade.widget.sweet.widget;

/**
 * SweetInterface : 自定义弹框接口
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public interface SweetInterface<T> {

  T setHead(int title);

  T setContent(int content);

  T setCancel(int content);

  T setConfirm(int content);

  T setHead(String title);

  T setContent(String content);

  T setCancel(String content);

  T setConfirm(String content);

  T setOnCancelListener(SweetClickListener listener);

  T setOnConfirmListener(SweetClickListener listener);

  T setEnableOnBack(boolean support);

  T setImageResId(int resId);

  T setAutoDismiss(boolean auto);
}
