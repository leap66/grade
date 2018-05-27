package com.leap.grade.widget.sweet;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.leap.grade.R;
import com.leap.base.util.DeviceInfoUtil;
import com.leap.base.util.IsEmpty;
import com.leap.grade.databinding.DialogSweetBinding;
import com.leap.grade.widget.sweet.widget.SweetClickListener;
import com.leap.grade.widget.sweet.widget.SweetInterface;
import com.leap.grade.widget.sweet.widget.SweetType;

/**
 * LoadingDialog : 加载框
 * <p>
 * </> Created by ylwei on 2018/3/28.
 */
public class SweetDialog extends Dialog implements SweetInterface<SweetDialog> {
  private DialogSweetBinding binding;
  private SweetClickListener onCancelListener;
  private SweetClickListener onConfirmListener;
  private boolean auto;

  public SweetDialog(@NonNull Context context) {
    super(context, R.style.style_dialog);
    binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.dialog_sweet,
        null, false);
    binding.setPresenter(new Presenter());
    setCanceledOnTouchOutside(false);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(binding.getRoot());
    initComponent();
  }

  private void initComponent() {
    Window window = getWindow();
    if (IsEmpty.object(window))
      return;
    WindowManager.LayoutParams lp = window.getAttributes();
    lp.width = DeviceInfoUtil.getScreenWidth() * 3 / 4;
    window.setAttributes(lp);
  }

  public class Presenter {

    public void onCancel() {
      dismiss();
      if (!IsEmpty.object(onCancelListener))
        onCancelListener.onClick(SweetDialog.this);
    }

    public void onConfirm() {
      if (auto)
        dismiss();
      if (!IsEmpty.object(onConfirmListener))
        onConfirmListener.onClick(SweetDialog.this);
    }
  }

  @Override
  public SweetDialog setHead(String title) {
    binding.titleTv.setVisibility(IsEmpty.string(title) ? View.GONE : View.VISIBLE);
    binding.titleTv.setText(title);
    return this;
  }

  @Override
  public SweetDialog setContent(String content) {
    binding.contentTv.setVisibility(IsEmpty.string(content) ? View.GONE : View.VISIBLE);
    binding.contentTv.setText(content);
    return this;
  }

  @Override
  public SweetDialog setCancel(String content) {
    binding.cancelBtn.setVisibility(IsEmpty.string(content) ? View.GONE : View.VISIBLE);
    binding.cancelBtn.setText(content);
    if (IsEmpty.string(content))
      binding.sweetLine.setVisibility(View.GONE);
    return this;
  }

  @Override
  public SweetDialog setConfirm(String content) {
    binding.confirmBtn.setVisibility(IsEmpty.string(content) ? View.GONE : View.VISIBLE);
    binding.confirmBtn.setText(content);
    if (IsEmpty.string(content))
      binding.confirmBtn.setVisibility(View.GONE);
    return this;
  }

  @Override
  public SweetDialog setOnCancelListener(SweetClickListener listener) {
    onCancelListener = listener;
    return this;
  }

  @Override
  public SweetDialog setOnConfirmListener(SweetClickListener listener) {
    onConfirmListener = listener;
    return this;
  }

  @Override
  public SweetDialog setEnableOnBack(boolean support) {
    setCancelable(support);
    return this;
  }

  @Override
  public SweetDialog setHead(int title) {
    setHead(getContext().getString(title));
    return this;
  }

  @Override
  public SweetDialog setContent(int content) {
    setContent(getContext().getString(content));
    return this;
  }

  @Override
  public SweetDialog setCancel(int content) {
    setCancel(getContext().getString(content));
    return this;
  }

  @Override
  public SweetDialog setConfirm(int content) {
    setConfirm(getContext().getString(content));
    return this;
  }

  @Override
  public SweetDialog setImageResId(int resId) {
    binding.customIv.setVisibility(resId == 0 ? View.GONE : View.VISIBLE);
    binding.customIv.setImageResource(resId);
    return this;
  }

  @Override
  public SweetDialog setType(SweetType type) {
    if (!IsEmpty.object(type)) {
      binding.customIc.setVisibility(View.VISIBLE);
      switch (type) {
      case SUCCESS:
        binding.customIc.setText(getContext().getString(R.string.icon_success));
        binding.customIc.setTextColor(ContextCompat.getColor(getContext(), R.color.softBlue));
        break;
      case FAILED:
        binding.customIc.setText(getContext().getString(R.string.icon_error));
        binding.customIc.setTextColor(ContextCompat.getColor(getContext(), R.color.lipstick));
        break;
      case WARNING:
        binding.customIc.setText(getContext().getString(R.string.icon_error));
        binding.customIc.setTextColor(ContextCompat.getColor(getContext(), R.color.mango));
        break;
      }
    }
    return this;
  }

  @Override
  public SweetDialog setAutoDismiss(boolean auto) {
    this.auto = auto;
    return this;
  }
}
