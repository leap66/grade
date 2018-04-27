package com.leap.grade.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.markzhai.recyclerview.BaseViewAdapter;
import com.github.markzhai.recyclerview.SingleTypeAdapter;
import com.leap.base.data.base.BAddress;
import com.leap.base.data.base.BUcn;
import com.leap.base.listener.OnConfirmListener;
import com.leap.base.util.DeviceInfoUtil;
import com.leap.base.util.IsEmpty;
import com.leap.grade.R;
import com.leap.grade.databinding.DialogAddressPickerBinding;
import com.leap.grade.util.DialogUtil;

import java.util.List;

import static com.taobao.accs.ACCSManager.mContext;

/**
 * AddressPickerDialog : 地址选择器
 * <p>
 * </> Created by ylwei on 2018/3/30.
 */
public abstract class AddressPickerDialog extends AppCompatDialog {
  private OnConfirmListener<AddressPickerDialog, BAddress> listener;
  private DialogAddressPickerBinding binding;
  private AnimationDrawable animationDrawable;
  private SingleTypeAdapter<BUcn> adapter;
  private BAddress address;
  private int currentIndex;

  public AddressPickerDialog(@NonNull Context context) {
    super(context, R.style.style_dialog);
    initView();
  }

  private void initView() {
    binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
        R.layout.dialog_address_picker, null, false);
    binding.setPresenter(new Presenter());
    binding.setIndex(5);// 显示 请选择 字样为红色
    animationDrawable = (AnimationDrawable) binding.progressbarIv.getDrawable();
    adapter = new SingleTypeAdapter<>(getContext(), R.layout.item_address_list);
    adapter.setPresenter(new Presenter());
    LinearLayoutManager manager = new LinearLayoutManager(mContext);
    manager.setOrientation(LinearLayoutManager.VERTICAL);
    binding.rootRcv.setLayoutManager(manager);
    binding.rootRcv.setAdapter(adapter);
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
    window.setGravity(Gravity.BOTTOM);
    window.setWindowAnimations(R.style.animation_bottom_rising);
    window.getDecorView().setPadding(0, 0, 0, 0);
    WindowManager.LayoutParams lp = window.getAttributes();
    lp.height = DeviceInfoUtil.getScreenHeight() * 3 / 5;
    lp.width = -1;
    window.setAttributes(lp);
  }

  public class Presenter implements BaseViewAdapter.Presenter {

    public void onCancel() {
      dismiss();
    }

    public void onTitle(int index) {
      binding.setIndex(index);
      BUcn bUcn;
      if (index == 0) {
        bUcn = null;
      } else if (index == 1) {
        bUcn = address.getProvince();
      } else if (index == 2) {
        bUcn = address.getCity();
      } else if (index == 3) {
        bUcn = address.getDistrict();
      } else {
        return;
      }
      currentIndex = index;
      startLoading();
      loadDate(bUcn);
    }

    public void onItem(BUcn ucn) {
      binding.setIndex(5);
      if (currentIndex == 0) {
        address.setProvince(ucn);
        address.setCity(null);
        address.setDistrict(null);
        address.setStreet(null);
      } else if (currentIndex == 1) {
        address.setCity(ucn);
        address.setDistrict(null);
        address.setStreet(null);
      } else if (currentIndex == 2) {
        address.setDistrict(ucn);
        address.setStreet(null);
      } else if (currentIndex == 3) {
        address.setStreet(ucn);
        listener.onConfirm(AddressPickerDialog.this, address);
        dismiss();
        return;
      }
      currentIndex++;
      binding.setItem(address);
      startLoading();
      loadDate(ucn);
    }
  }

  protected void setAddress(BAddress address) {
    this.address = address;
    binding.setItem(address);
    if (IsEmpty.object(address) || IsEmpty.object(address.getProvince())) {
      loadDate(null);
    } else {
      currentIndex = 3;
      loadDate(address.getDistrict());
    }
  }

  public AddressPickerDialog setListener(
      OnConfirmListener<AddressPickerDialog, BAddress> listener) {
    this.listener = listener;
    return this;
  }

  protected abstract void loadDate(BUcn ucn);

  protected void loadFail(String errorMsg) {
    if (!isShowing())
      return;
    stopLoading();
    DialogUtil.getErrorDialog(getContext(), errorMsg).show();
  }

  protected void loadSuccess(List<BUcn> addressList) {
    if (!isShowing())
      return;
    stopLoading();
    int index = formatBUcn(addressList);
    adapter.set(addressList);
    adapter.notifyDataSetChanged();
    binding.rootRcv.scrollToPosition(index);
  }

  private int formatBUcn(List<BUcn> addressList) {
    int i = 0;
    BUcn bUcn = new BUcn();
    if (currentIndex == 0) {
      bUcn = address.getProvince();
    } else if (currentIndex == 1) {
      bUcn = address.getCity();
    } else if (currentIndex == 2) {
      bUcn = address.getDistrict();
    } else if (currentIndex == 3) {
      bUcn = address.getStreet();
    }
    if (IsEmpty.object(bUcn))
      return 0;
    for (BUcn temp : addressList) {
      if (temp.getName().equals(bUcn.getName())) {
        temp.setNewUcn(true);
        return i;
      }
      i++;
    }
    return 0;
  }

  @Override
  public void show() {
    animationDrawable.start();
    super.show();
  }

  @Override
  public void dismiss() {
    animationDrawable.stop();
    super.dismiss();
  }

  private void startLoading() {
    binding.progressbarIv.setVisibility(View.VISIBLE);
  }

  private void stopLoading() {
    binding.progressbarIv.setVisibility(View.GONE);
  }
}
