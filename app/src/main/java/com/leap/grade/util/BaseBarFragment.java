package com.leap.grade.util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * BaseBarFragment : 基础Fragment
 * <p>
 * </> Created by ylwei on 2018/4/24.
 */
public abstract class BaseBarFragment extends RxFragment {
  protected Context context;
  protected LifecycleProvider provider;
  protected boolean isVisible;
  protected boolean isPrepare;
  protected boolean isImmersion;
  protected ImmersionBar immersionBar;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;
    provider = this;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = initComponent(inflater, null);
    createEventHandlers();
    loadData(savedInstanceState);
    EventBus.getDefault().register(this);
    return rootView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (isLazyLoad()) {
      isPrepare = true;
      isImmersion = true;
      onLazyLoad();
    }
    if (useStatusBar())
      initStatusBar();
  }

  protected void initStatusBar() {
    if (statusBarView() != null)
      ImmersionBar.setTitleBar(getActivity(), statusBarView());
    immersionBar = ImmersionBar.with(getActivity(), this);
    if (isDarkFont())
      immersionBar.statusBarDarkFont(true, 0.2f);
    immersionBar.init();
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
      isVisible = true;
      onVisible();
    } else {
      isVisible = false;
      onInvisible();
    }
  }

  protected void onVisible() {
    onLazyLoad();
  }

  protected void onInvisible() {
  }

  private void onLazyLoad() {
    if (isVisible && isPrepare) {
      isPrepare = false;
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
    if (!hidden && immersionBar != null)
      immersionBar.init();
  }

  @Override
  public void onDestroy() {
    if (immersionBar != null) {
      if (immersionBar.getBarParams() != null) {
        // 去除statusBarViewByHeight持有的引用。
        immersionBar.getBarParams().statusBarViewByHeight = null;
      }
      immersionBar.destroy();
    }
    EventBus.getDefault().unregister(this);
    super.onDestroy();
  }
}
