package com.leap.grade.widget.validator.filter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * LengthInputFilter : 最大长度输入限制
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public class LengthInputFilter implements InputFilter {
  private int length;

  public LengthInputFilter(int length) {
    this.length = length;
  }

  public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
      int dend) {
    // 删除等特殊字符，直接返回
    if ("".equals(source.toString())) {
      return null;
    }
    return source.subSequence(0,
        source.length() > length - dest.length() ? length - dest.length() : source.length());
  }
}
