package com.leap.grade.util;

import java.io.Serializable;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * FragmentIntent : 自定义FragmentIntent，类Intent
 * <p>
 * </> Created by ylwei on 2018/4/26.
 */
public class FragmentIntent {
  public static final int POP = 1;
  public static final int PUSH = 2;
  public static final int REPLACE = 3;
  public static final int CLEAR_PUSH = 4;
  public static final int PUSH_SINGLE = 5;
  public static final int SINGLE_TOP = 6;
  private Bundle extras = new Bundle();
  private BaseIntentFragment target;
  private FragmentManager manager;
  private int containerId;
  private int flag;
  private String popTargetName;

  public void start(FragmentManager manager) {
    if (target != null)
      target.setIntent(this);
    if (POP == flag) {
      FragmentTransaction transaction = manager.beginTransaction();
      List<Fragment> fragments = manager.getFragments();
      if (fragments != null && fragments.size() > 0) {
        for (int i = fragments.size() - 1; i >= 0; i--) {
          boolean stop = false;
          if (validator(fragments.get(i))) {
            transaction.remove(fragments.get(i));
            //
            for (int j = i - 1; j >= 0; j--) {
              if (validator(fragments.get(j))) {
                if (popTargetName != null) {
                  if (popTargetName.equals(fragments.get(j).getClass().getName())) {
                    ((BaseIntentFragment) fragments.get(j)).onNewIntent(this);
                    stop = true;
                  }
                } else {
                  ((BaseIntentFragment) fragments.get(j)).onNewIntent(this);
                  stop = true;
                }
                break;
              }
            }
            if (stop) {
              break;
            }
          }
        }
      }
      transaction.commitAllowingStateLoss();
    } else if (target != null) {
      if (!target.canShow()) {
        return;
      }
      if (PUSH == flag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(containerId, target, target.getClass().getName());
        transaction.commitAllowingStateLoss();
      } else if (CLEAR_PUSH == flag) {
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
          if (!fragments.isEmpty()) {
            for (Fragment item : fragments) {
              if (validator(item))
                transaction.remove(item);
            }
          }
        }
        transaction.commitAllowingStateLoss();
        // FragmentTransactionBugFixHack.reorderIndices(manager);
        transaction = manager.beginTransaction();
        transaction.add(containerId, target, target.getClass().getName());
        transaction.commitAllowingStateLoss();
      } else if (REPLACE == flag) {
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null && !fragments.isEmpty()) {
          for (int i = fragments.size() - 1; i >= 0; i--) {
            if (validator(fragments.get(i))) {
              transaction.remove(fragments.get(i));
              break;
            }
          }
        }
        transaction.add(containerId, target, target.getClass().getName());
        transaction.commitAllowingStateLoss();
      } else if (PUSH_SINGLE == flag) {
        Fragment fragment = manager.findFragmentByTag(target.getClass().getName());
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment != null) {
          List<Fragment> fragments = manager.getFragments();
          if (fragments != null) {
            for (int i = fragments.size() - 1; i >= 0; i--) {
              if (validator(fragments.get(i))) {
                if (!fragments.get(i).getClass().getName().equals(target.getClass().getName())) {
                  transaction.remove(fragments.get(i));
                } else {
                  ((BaseIntentFragment) fragments.get(i)).onNewIntent(this);
                  break;
                }
              }
            }
          }
        } else {
          transaction.add(containerId, target, target.getClass().getName());
        }
        transaction.commitAllowingStateLoss();
      } else if (SINGLE_TOP == flag) {
        Fragment fragment = manager.findFragmentByTag(target.getClass().getName());
        FragmentTransaction transaction = manager.beginTransaction();
        if (fragment != null) {
          List<Fragment> fragments = manager.getFragments();
          if (fragments != null) {
            for (int i = fragments.size() - 1; i >= 0; i--) {
              if (validator(fragments.get(i))) {
                if (fragments.get(i).getClass().getName().equals(target.getClass().getName())) {
                  ((BaseIntentFragment) fragments.get(i)).onNewIntent(this);
                  transaction.show(fragments.get(i));
                } else {
                  transaction.hide(fragments.get(i));
                }
              }
            }
          }
        } else {
          transaction.add(containerId, target, target.getClass().getName());
        }
        transaction.commitAllowingStateLoss();
      }
    }
  }

  public FragmentIntent putExtra(String name, Serializable value) {
    extras.putSerializable(name, value);
    return this;
  }

  public FragmentIntent putExtra(String name, String value) {
    extras.putString(name, value);
    return this;
  }

  public FragmentIntent putExtra(String name, int value) {
    extras.putInt(name, value);
    return this;
  }

  public FragmentIntent putExtra(String name, boolean value) {
    extras.putBoolean(name, value);
    return this;
  }

  public Bundle getExtras() {
    return extras;
  }

  public void setExtras(Bundle extras) {
    if (extras != null)
      this.extras = extras;
  }

  public Serializable getSerializableExtra(String name) {
    return extras.getSerializable(name);
  }

  public String getStringExtra(String name) {
    return extras.getString(name);
  }

  public int getIntExtra(String name, int defaultValue) {
    return extras.getInt(name, defaultValue);
  }

  public boolean getBooleanExtra(String name, boolean defaultValue) {
    return extras.getBoolean(name, defaultValue);
  }

  public BaseIntentFragment getTarget() {
    return target;
  }

  public void setTarget(BaseIntentFragment target) {
    this.target = target;
  }

  public int getContainerId() {
    return containerId;
  }

  public void setContainerId(int containerId) {
    this.containerId = containerId;
  }

  public int getFlag() {
    return flag;
  }

  public void setFlag(int flag) {
    this.flag = flag;
  }

  public FragmentManager getManager() {
    return manager;
  }

  public void setManager(FragmentManager manager) {
    this.manager = manager;
  }

  public String getPopTargetName() {
    return popTargetName;
  }

  public void setPopTargetName(String popTargetName) {
    this.popTargetName = popTargetName;
  }

  private boolean validator(Fragment fragment) {
    return fragment != null && fragment instanceof BaseIntentFragment;
  }
}