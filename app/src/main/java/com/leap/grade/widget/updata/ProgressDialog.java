package com.leap.grade.widget.updata;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leap.base.util.DeviceInfoUtil;
import com.leap.base.util.FileUtil;
import com.leap.base.util.ToastUtil;
import com.leap.grade.R;
import com.leap.grade.data.UpdateModel;
import com.leap.grade.widget.updata.widget.UpdateTask;

import pub.devrel.easypermissions.AfterPermissionGranted;

class ProgressDialog extends Dialog {
  private Context mContext;
  private ProgressBar progressBar;
  private TextView tvProgress;

  ProgressDialog(Context context) {
    super(context, R.style.alert_dialog);
    this.mContext = context;
    initViews();
  }

  private void initViews() {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    View rootView = LayoutInflater.from(mContext).inflate(R.layout.dialog_update_progress, null,
        false);
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
        DeviceInfoUtil.getScreenWidth() * 11 / 15, RelativeLayout.LayoutParams.WRAP_CONTENT);
    setContentView(rootView, params);
    progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
    tvProgress = (TextView) rootView.findViewById(R.id.tv_progress);
  }

  @AfterPermissionGranted(11)
  void downloadApk(UpdateModel model, String filePath) {
    setCancelable(model.isForce());
    UpdateTask updateTask = new UpdateTask(mContext, model.getPosition(), progressBar, tvProgress,
        filePath);
    updateTask.setSuccessListener(new UpdateTask.OnSuccessListener<String>() {
      @Override
      public void onSuccess(String data) {
        FileUtil.installApk(data);
        dismiss();
      }
    });
    updateTask.setErrorListener(new UpdateTask.OnErrorListener<String>() {
      @Override
      public void onError(String data) {
        ToastUtil.showFailure(mContext.getString(R.string.update_failure));
        dismiss();
      }
    });
    updateTask.execute(model.getDownloadUrl());
  }
}
