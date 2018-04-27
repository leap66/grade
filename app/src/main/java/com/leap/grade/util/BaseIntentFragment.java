package com.leap.grade.util;

/**
 * BaseIntentFragment : 支持FragmentIntent的基础Fragment
 * <p>
 * </> Created by ylwei on 2018/4/26.
 */
public abstract class BaseIntentFragment extends BaseBarFragment {
  protected FragmentIntent intent = new FragmentIntent();

  public void setIntent(FragmentIntent intent) {
    this.intent = intent;
  }

  public void onNewIntent(FragmentIntent intent) {
    this.intent = intent;
  }

  public boolean canShow() {
    return true;
  }

  public void finish() {
    FragmentIntent intent = new FragmentIntent();
    intent.setFlag(FragmentIntent.POP);
    if (getParentFragment() != null) {
      intent.start(getParentFragment().getChildFragmentManager());
    } else if (getActivity() != null) {
      intent.start(getActivity().getSupportFragmentManager());
    }
  }

  public void startFragment(FragmentIntent intent) {
    if (intent.getContainerId() == 0) {
      intent.setContainerId(this.getId());
    }
    if (intent.getManager() != null) {
      intent.start(intent.getManager());
    } else {
      if (getParentFragment() != null) {
        intent.start(getParentFragment().getChildFragmentManager());
      } else if (getActivity() != null) {
        intent.start(getActivity().getSupportFragmentManager());
      }
    }
  }
}
