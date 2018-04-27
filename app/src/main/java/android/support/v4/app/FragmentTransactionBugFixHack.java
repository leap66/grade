package android.support.v4.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FragmentTransactionBugFixHack :
 * <p>
 * </> Created by ylwei on 2018/4/26.
 */
public class FragmentTransactionBugFixHack {

  public static void reorderIndices(FragmentManager fragmentManager) {
    if (!(fragmentManager instanceof FragmentManagerImpl))
      return;
    FragmentManagerImpl fragmentManagerImpl = (FragmentManagerImpl) fragmentManager;
    if (fragmentManagerImpl.mAvailIndices != null) {
      Collections.sort(fragmentManagerImpl.mAvailIndices, Collections.reverseOrder());
    }

    // Recursively reorder indices of all child fragments.
    List<Fragment> fragments = fragmentManager.getFragments();
    // The support library FragmentManager returns null if none.
    if (fragments != null) {
      for (Fragment fragment : fragments) {
        // For some reason, the fragments in the list of fragments might be
        // null.
        if (fragment != null) {
          reorderIndices(fragment.getChildFragmentManager());
        }
      }
    }
  }

  public static void injectFragmentTransactionAvailIndicesAutoReverseOrder(
      FragmentManager fragmentManager) {
    if (fragmentManager == null || !(fragmentManager instanceof FragmentManagerImpl))
      return;
    FragmentManagerImpl fragmentManagerImpl = (FragmentManagerImpl) fragmentManager;
    if (fragmentManagerImpl.mAvailIndices != null
        && fragmentManagerImpl.mAvailIndices instanceof ReverseOrderArrayList)
      return;
    ArrayList<Integer> backupList = fragmentManagerImpl.mAvailIndices;
    fragmentManagerImpl.mAvailIndices = new ReverseOrderArrayList<>();
    if (backupList != null) {
      fragmentManagerImpl.mAvailIndices.addAll(backupList);
    }
  }
}
