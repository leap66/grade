package com.leap.grade.widget.validator;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Validator : 输入框效验
 * <p>
 * </> Created by ylwei on 2018/3/29.
 */
public class Validator {
  private Map<TextView, List<Rule>> validations = new LinkedHashMap<>();
  private List<View> viewList = new ArrayList<>();

  public void register(final TextView textView, Rule... rules) {
    textView.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        for (View view : viewList) {
          view.setEnabled(validateAll());
        }
      }
    });

    validations.put(textView, Arrays.asList(rules));
  }

  public void bindEnable(View... views) {
    this.viewList.addAll(Arrays.asList(views));
  }

  public void unregister(TextView textView) {
    validations.remove(textView);
  }

  public void validateAll(ValidateResultCall resultCall) {
    for (TextView key : validations.keySet()) {
      for (Rule rule : validations.get(key)) {
        if (!rule.validate(String.valueOf(key.getText()))) {
          resultCall.onFailure(rule.getErrorMessage());
          return;
        }
      }
    }
    resultCall.onSuccess();
  }

  private boolean validateAll() {
    for (TextView key : validations.keySet()) {
      for (Rule rule : validations.get(key)) {
        if (!rule.validate(String.valueOf(key.getText()))) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean validate(TextView textView) {
    List<Rule> rules = validations.get(textView);
    if (rules == null) {
      return true;
    } else {
      for (Rule rule : rules) {
        boolean validate = rule.validate(String.valueOf(textView.getText()));
        if (!validate) {
          return false;
        }
      }
    }
    return true;
  }
}
