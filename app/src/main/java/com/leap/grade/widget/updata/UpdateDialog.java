package com.leap.grade.widget.updata;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.leap.base.util.FileUtil;
import com.leap.grade.R;
import com.leap.grade.data.UpdateModel;
import com.leap.grade.databinding.DialogUpdateBinding;

import java.io.File;

public class UpdateDialog extends Dialog {
  private DialogUpdateBinding binding;
  private UpdateModel model;
  private Context mContext;
  private String filePath;

  public UpdateDialog(Context context, UpdateModel model) {
    super(context, R.style.alert_dialog);
    this.mContext = context;
    this.model = model;
    filePath = FileUtil.getFilePath(model.getFileName());
    initView();
  }

  private void initView() {
    binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_update, null,
        true);
    binding.setPresenter(new Presenter());
    binding.setItem(model);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(binding.getRoot());
    setCanceledOnTouchOutside(false);
    setCancelable(!model.isForce());
  }

  public class Presenter {
    /**
     * 下次更新
     */
    public void onCancel() {
      dismiss();
    }

    /**
     * 立即更新
     */
    public void onUpData() {
      String currentMd5 = "-1";
      try {
        currentMd5 = FileUtil.getFileMD5String(filePath);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (currentMd5.equals(model.getMd5())) {
        FileUtil.installApk(filePath);
        dismiss();
      } else {
        if (new File(filePath).exists())
          model.setPosition(new File(filePath).length());
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.downloadApk(model, filePath);
        progressDialog.show();
        dismiss();
      }
    }
  }
}
