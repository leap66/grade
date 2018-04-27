package com.leap.grade.util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * BaseBarFragment : 基础Fragment
 * <p>
 * </> Created by ylwei on 2018/4/24.
 */
public abstract class BaseBarFragment extends RxFragment {
  protected Context context;
  protected boolean mIsVisible;
  protected boolean mIsPrepare;
  protected boolean mIsImmersion;
  protected ImmersionBar mStatusBar;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = initComponent(inflater, null);
    createEventHandlers();
    loadData(savedInstanceState);
    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (isLazyLoad()) {
      mIsPrepare = true;
      mIsImmersion = true;
      onLazyLoad();
    }
    if (useStatusBar())
      initStatusBar();
  }

  protected void initStatusBar() {
    if (statusBarView() != null)
      ImmersionBar.setTitleBar(getActivity(), statusBarView());
    mStatusBar = ImmersionBar.with(getActivity(), this);
    if (isDarkFont())
      mStatusBar.statusBarDarkFont(true, 0.2f);
    mStatusBar.init();
  }

  protected View statusBarView() {
    return null;
  }

  protected boolean isDarkFont() {
    return false;
  }

  protected boolean isLazyLoad() {
    return true;
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (getUserVisibleHint()) {
      mIsVisible = true;
      onVisible();
    } else {
      mIsVisible = false;
      onInvisible();
    }
  }

  protected void onVisible() {
    onLazyLoad();
  }

  protected void onInvisible() {
  }

  private void onLazyLoad() {
    if (mIsVisible && mIsPrepare) {
      mIsPrepare = false;
    }
  }

  protected abstract View initComponent(LayoutInflater inflater, ViewGroup container);

  protected abstract void loadData(Bundle savedInstanceState);

  protected void createEventHandlers() {
  }

  protected boolean useStatusBar() {
    return true;
  }

  @Override
  public void onHiddenChanged(boolean hidden) {
    super.onHiddenChanged(hidden);
    if (!hidden && mStatusBar != null)
      mStatusBar.init();
  }

  @Override
  public void onDestroy() {
    if (mStatusBar != null) {
      if (mStatusBar.getBarParams() != null) {
        // 去除statusBarViewByHeight持有的引用。
        mStatusBar.getBarParams().statusBarViewByHeight = null;
      }
      mStatusBar.destroy();
    }
    super.onDestroy();
  }
}
