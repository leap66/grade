package android.support.v4.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * ReverseOrderArrayList :
 * <p>
 * </> Created by ylwei on 2018/4/26.
 */
public class ReverseOrderArrayList<T extends Comparable> extends ArrayList<T> {
  @Override
  public boolean add(T object) {
    boolean value = super.add(object);
    Collections.sort(this, Collections.reverseOrder());
    return value;
  }

  @Override
  public void add(int index, T object) {
    super.add(index, object);
    Collections.sort(this, Collections.reverseOrder());
  }

  @Override
  public boolean addAll(Collection<? extends T> collection) {
    boolean value = super.addAll(collection);
    Collections.sort(this, Collections.reverseOrder());
    return value;
  }

  @Override
  public boolean addAll(int index, Collection<? extends T> collection) {
    boolean value = super.addAll(index, collection);
    Collections.sort(this, Collections.reverseOrder());
    return value;
  }

  @Override
  protected void removeRange(int fromIndex, int toIndex) {
    super.removeRange(fromIndex, toIndex);
    Collections.sort(this, Collections.reverseOrder());
  }

  @Override
  public boolean remove(Object object) {
    boolean value = super.remove(object);
    Collections.sort(this, Collections.reverseOrder());
    return value;
  }

  @Override
  public boolean removeAll(Collection<?> collection) {
    boolean value = super.removeAll(collection);
    Collections.sort(this, Collections.reverseOrder());
    return value;
  }

  @Override
  public T remove(int index) {
    T value = super.remove(index);
    Collections.sort(this, Collections.reverseOrder());
    return value;
  }
}