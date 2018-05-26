package com.leap.grade.widget.smartrefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.leap.grade.R;
import com.leap.grade.widget.smartrefresh.base.layout.BaseFooterView;
import com.leap.grade.widget.smartrefresh.base.layout.BaseHeaderView;
import com.leap.grade.widget.smartrefresh.base.layout.PullRefreshLayout;
import com.leap.grade.widget.smartrefresh.base.support.impl.Loadable;
import com.leap.grade.widget.smartrefresh.base.support.impl.Refreshable;
import com.leap.grade.widget.smartrefresh.base.support.utils.CanPullUtil;

/**
 * 定义了下拉刷新和上推加载
 */
public class SmartRefreshLayout extends PullRefreshLayout {
  private DefaultPullLayout mCenterViewContainer;
  private View contentView;
  private int headerIndex = -1;
  private int footerIndex = -1;
  private boolean immediately = true;
  private ProgressBar progressBarRefresh, progressBarMore;
  private Drawable headerDrawableBg, footDrawableBg;
  private TextView loadingRefreshText, loadingMoreText;
  private int headerBg = ContextCompat.getColor(getContext(), R.color.white);
  private int footerBg = ContextCompat.getColor(getContext(), R.color.white);
  private boolean isRefresh, isLoadMore;
  private boolean stateBg = true;

  public SmartRefreshLayout(Context context) {
    this(context, null);
  }

  public SmartRefreshLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SmartRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    setChildrenDrawingOrderEnabled(true);
    mCenterViewContainer = new DefaultPullLayout(context);
    addView(mCenterViewContainer);
    if (attrs != null) {
      TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SmartRefreshLayout);
      stateBg = array.getBoolean(R.styleable.SmartRefreshLayout_state_bg, true);
      headerBg = array.getColor(R.styleable.SmartRefreshLayout_header_bg, Color.WHITE);
      footerBg = array.getColor(R.styleable.SmartRefreshLayout_foot_bg, Color.WHITE);
      headerDrawableBg = array.getDrawable(R.styleable.SmartRefreshLayout_header_drawable_bg);
      footDrawableBg = array.getDrawable(R.styleable.SmartRefreshLayout_foot_drawable_bg);
      array.recycle();
    }
    mHeader = (DefaultHeaderView) LayoutInflater.from(context)
        .inflate(R.layout.smart_refresh_header, this, false);
    addView((DefaultHeaderView) mHeader);
    mFooter = (DefaultFooterView) LayoutInflater.from(context)
        .inflate(R.layout.smart_refresh_footer, this, false);
    progressBarRefresh = (ProgressBar) ((DefaultHeaderView) mHeader).findViewById(R.id.refresh_pb);
    progressBarMore = (ProgressBar) ((DefaultFooterView) mFooter).findViewById(R.id.footer_pb);
    loadingRefreshText = (TextView) ((DefaultHeaderView) mHeader).findViewById(R.id.refresh_tv);
    loadingMoreText = (TextView) ((DefaultFooterView) mFooter).findViewById(R.id.footer_tv);
    addView((DefaultFooterView) mFooter);
    initAttrs();
  }

  /**
   * 初始化xml参数属性
   */
  private void initAttrs() {
    setStateBg(stateBg);
    setHeaderBg(headerBg);
    setFooterBg(footerBg);
    setBackgroundColor(headerBg);
    if (null != headerDrawableBg) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        setBackground(headerDrawableBg);
      }
    }
  }

  @Override
  public void addView(View child, int index, ViewGroup.LayoutParams params) {
    if (child instanceof Refreshable) {
      mHeader.setPullRefreshLayout(this);
    } else if (child instanceof Loadable) {
      mFooter.setPullRefreshLayout(this);
    } else if (child instanceof DefaultPullLayout || CanPullUtil.getPullAble(child) == null) {
      // do nothing
    } else {
      pullable = CanPullUtil.getPullAble(child);
      mPullView = child;
      contentView = child;
    }
    super.addView(child, index, params);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    // 记录头尾位置
    headerIndex = -1;
    for (int index = 0; index < getChildCount(); index++) {
      if (getChildAt(index) == mHeader) {
        headerIndex = index;
        break;
      }
    }
    footerIndex = -1;
    for (int index = 0; index < getChildCount(); index++) {
      if (getChildAt(index) == mFooter) {
        footerIndex = index;
        break;
      }
    }
  }

  public boolean isRefreshing() {
    return ((DefaultHeaderView) mHeader).isRefreshing();
  }

  public boolean isLoading() {
    return ((DefaultFooterView) mFooter).isLoading();
  }

  public void setHeaderBg(int headerBg) {
    this.headerBg = headerBg;
  }

  public void setFooterBg(int footerBg) {
    this.footerBg = footerBg;
  }

  public void setHeaderBg(Drawable headerDrawableBg) {
    this.headerDrawableBg = headerDrawableBg;
  }

  public void setFooterBg(Drawable footDrawableBg) {
    this.footDrawableBg = footDrawableBg;
  }

  /**
   * 确保头尾最后画
   */
  @Override
  protected int getChildDrawingOrder(int childCount, int i) {
    if (headerIndex < 0 && footerIndex < 0) {
      return i;
    }
    if (i == childCount - 1) {
      return headerIndex;
    }
    if (i == childCount - 2) {
      return footerIndex;
    }
    int bigIndex = footerIndex > headerIndex ? footerIndex : headerIndex;
    int smallIndex = footerIndex < headerIndex ? footerIndex : headerIndex;
    if (i >= smallIndex && i < bigIndex - 1) {
      return i + 1;
    }
    if (i >= bigIndex - 1) {
      return i + 2;
    }
    return i;
  }

  @Override
  protected boolean onScroll(float y) {
    if (mHeader != null && y >= 0 && hasHeader) {
      boolean intercept = mHeader.onScroll(y);
      if (y != 0) {
        if (!isRefresh) {
          setBackgroundColor(headerBg);
          if (null != headerDrawableBg) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
              setBackground(headerDrawableBg);
            }
          }
          isLoadMore = false;
        }
        isRefresh = true;
        return intercept;
      }
    }
    if (mFooter != null && y <= 0 && hasFooter) {
      boolean intercept = mFooter.onScroll(y);
      if (y != 0) {
        if (!isLoadMore) {
          setBackgroundColor(footerBg);
          if (null != footDrawableBg) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
              setBackground(footDrawableBg);
            }
          }
          isRefresh = false;
        }
        isLoadMore = true;
        return intercept;
      }
    }
    return false;
  }

  @Override
  protected void onScrollChange(int stateType) {
    if (mHeader != null) {
      mHeader.onScrollChange(stateType);
    }
    if (mFooter != null) {
      mFooter.onScrollChange(stateType);
    }
  }

  /**
   * 增加控制：没有设置头部或尾部时，不能滚动
   */
  @Override
  public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
      int dyUnconsumed) {
    if (dyUnconsumed > 0 && (!hasFooter || isRefreshing()) && isChildScrollToBottom()) {
      return;
    } else if (dyUnconsumed < 0 && (!hasHeader || isLoading()) && isChildScrollToTop()) {
      return;
    }
    super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
  }

  public boolean isChildScrollToTop() {
    return !ViewCompat.canScrollVertically(mPullView, -1);
  }

  public boolean isChildScrollToBottom() {
    if (mPullView instanceof RecyclerView) {
      RecyclerView recyclerView = (RecyclerView) mPullView;
      RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
      int count = recyclerView.getAdapter().getItemCount();
      if (layoutManager instanceof LinearLayoutManager && count > 0) {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == count - 1) {
          return true;
        }
      } else if (layoutManager instanceof StaggeredGridLayoutManager) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        int[] lastItems = new int[2];
        staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastItems);
        int lastItem = Math.max(lastItems[0], lastItems[1]);
        if (lastItem == count - 1) {
          return true;
        }
      } else if (layoutManager instanceof com.tonicartos.superslim.LayoutManager) {
        com.tonicartos.superslim.LayoutManager slimLayoutManager = (com.tonicartos.superslim.LayoutManager) layoutManager;
        if (slimLayoutManager.getChildCount() > 0) {
          int lastItem = slimLayoutManager.findLastCompletelyVisibleItemPosition();
          if (lastItem == count - 1) {
            return true;
          }
        } else {
          return true;
        }
      }
      return false;
    } else if (mPullView instanceof AbsListView) {
      final AbsListView absListView = (AbsListView) mPullView;
      int count = absListView.getAdapter().getCount();
      int fristPos = absListView.getFirstVisiblePosition();
      if (fristPos == 0 && absListView.getChildAt(0).getTop() >= absListView.getPaddingTop()) {
        return false;
      }
      int lastPos = absListView.getLastVisiblePosition();
      if (lastPos > 0 && count > 0 && lastPos == count - 1) {
        return true;
      }
      return false;
    } else if (mPullView instanceof ScrollView) {
      ScrollView scrollView = (ScrollView) mPullView;
      View view = scrollView.getChildAt(scrollView.getChildCount() - 1);
      if (view != null) {
        int diff = (view.getBottom() - (scrollView.getHeight() + scrollView.getScrollY()));
        if (diff == 0) {
          return true;
        }
      }
    } else if (mPullView instanceof NestedScrollView) {
      NestedScrollView nestedScrollView = (NestedScrollView) mPullView;
      View view = nestedScrollView.getChildAt(nestedScrollView.getChildCount() - 1);
      if (view != null) {
        int diff = (view.getBottom()
            - (nestedScrollView.getHeight() + nestedScrollView.getScrollY()));
        if (diff == 0) {
          return true;
        }
      }
    }
    return false;
  }

  public void hideView() {
    mCenterViewContainer.setVisibility(View.GONE);
    contentView.setVisibility(View.VISIBLE);
    mPullView = contentView;
    pullable = CanPullUtil.getPullAble(contentView);
  }

  /**
   * 要求刷新框立即出现
   */
  public void startRefresh() {
    if (mHeader != null) {
      immediately = true;
      mHeader.startRefresh();
    }
  }

  public void stopRefresh() {
    if (mHeader != null) {
      mHeader.stopRefresh();
    }
  }

  /**
   * 立即刷新时，直接显示在目标位置
   */
  public int startMoveTo(float startY, float endY) {
    if (immediately) {
      immediately = false;
      return startMoveBy(endY, 0);
    } else {
      return startMoveBy(startY, endY - startY);
    }
  }

  public void setOnRefreshListener(BaseHeaderView.OnRefreshListener listener) {
    if (this.mHeader != null && this.mHeader instanceof BaseHeaderView) {
      ((BaseHeaderView) this.mHeader).setOnRefreshListener(listener);
    }
  }

  public void setOnLoadListener(BaseFooterView.OnLoadListener listener) {
    if (this.mFooter != null && this.mFooter instanceof BaseFooterView) {
      ((BaseFooterView) this.mFooter).setOnLoadListener(listener);
    }
  }

  public void setTopPadding(int topPadding) {
    if (this.mHeader != null && this.mHeader instanceof DefaultHeaderView) {
      ((DefaultHeaderView) this.mHeader).setTopPadding(topPadding);
    }
  }

  public void setStateBg(boolean stateBg) {
    this.stateBg = stateBg;
    int color = ContextCompat.getColor(getContext(),
        stateBg ? R.color.charcoalGrey : R.color.white);
    loadingRefreshText.setTextColor(color);
    loadingMoreText.setTextColor(color);
    Drawable drawable = ContextCompat.getDrawable(getContext(),
        stateBg ? R.drawable.pb_circle_theme : R.drawable.pb_circle_white);
    if (null != drawable) {
      progressBarRefresh.setIndeterminateDrawable(drawable);
      progressBarMore.setIndeterminateDrawable(drawable);
    }
  }

  public void finishLoad(boolean isMore) {
    if (isRefreshing())
      stopRefresh();
    if (isLoading())
      stopLoad();
    hideView();
    setHasFooter(isMore);
  }

}
