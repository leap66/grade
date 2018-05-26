package com.leap.grade.util;

import android.content.Context;

import com.leap.grade.widget.sweet.SweetDialog;
import com.leap.grade.widget.sweet.widget.SweetClickListener;
import com.leap.grade.widget.sweet.widget.SweetType;

/**
 * DialogUtil : Dialog工具类
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public class DialogUtil {

  public static SweetDialog getErrorDialog(Context context, String errorMsg) {
    return new SweetDialog(context).setHead(errorMsg).setCancel(null).setAutoDismiss(true)
        .setType(SweetType.FAILED);
  }

  public static SweetDialog getErrorDialog(Context context, int errorMsg) {
    return getErrorDialog(context, context.getString(errorMsg));
  }

  public static SweetDialog getConfirmDialog(Context context, String title, String content,
      String cancel, String confirm, SweetClickListener callback) {
    return new SweetDialog(context).setHead(title).setContent(content).setCancel(cancel)
        .setConfirm(confirm).setOnConfirmListener(callback);
  }

  public static SweetDialog getConfirmDialog(Context context, int title, int content, int cancel,
      int confirm, SweetClickListener callback) {
    return getConfirmDialog(context, context.getString(title), context.getString(content),
        context.getString(cancel), context.getString(confirm), callback);
  }

  public static SweetDialog getConfirmDialog(Context context, String title, String content,
      SweetClickListener callback) {
    return new SweetDialog(context).setHead(title).setContent(content)
        .setOnConfirmListener(callback);
  }

  public static SweetDialog getConfirmDialog(Context context, int title, int content,
      SweetClickListener callback) {
    return getConfirmDialog(context, context.getString(title), context.getString(content),
        callback);
  }

  public static SweetDialog getHintDialog(Context context, String hintMsg) {
    return new SweetDialog(context).setContent(hintMsg).setCancel(null).setAutoDismiss(true);
  }

  public static SweetDialog getSuccessDialog(Context context, String hintMsg) {
    return new SweetDialog(context).setHead(hintMsg).setCancel(null)
        .setType(SweetType.SUCCESS).setAutoDismiss(true);
  }

  public static SweetDialog getCustomDialog(Context context, String title, String content,
      String cancel, String confirm, int resId, SweetClickListener callback) {
    return new SweetDialog(context).setHead(title).setContent(content).setCancel(cancel)
        .setConfirm(confirm).setImageResId(resId).setOnConfirmListener(callback);
  }
}
