/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package cn.cloudartisan.crius.component;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.util.AppTools;

public class NearbyPullRefreshListView extends ListView implements AbsListView.OnScrollListener {
    private static final int DONE = 0x3;
    private static final int LOADING = 0x4;
    private static final int LOADING_MORE = 0x5;
    private static final int LOADING_MORE_DONE = 0x6;
    private static final int PULL_To_REFRESH = 0x1;
    private static final int RATIO = 0x3;
    private static final int REFRESHING = 0x2;
    private static final int RELEASE_To_REFRESH=0;
    private int firstItemIndex;
    private View footer;
    private int footerState;
    private View header;
    private int headerHeight;
    private boolean isRecored;
    ProgressBar progressBar;
    private NearbyPullRefreshListView.OnRefreshListener refreshListener;
    private int startY;
    private int state;

    public NearbyPullRefreshListView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        setOnScrollListener(this);
        init(paramContext);
    }

    public void init(Context context) {
        header = LayoutInflater.from(context).inflate(R.layout.layout_nearby_listheader, null);
        footer = LayoutInflater.from(context).inflate(R.layout.list_footer, null);
        AppTools.measureView(header);
        headerHeight = (header.getMeasuredHeight() / 0x2);
        addHeaderView(header, 0x0, false);
        addFooterView(footer, 0x0, false);
        footer.setVisibility(View.GONE);
        header.setPadding(0x0, (headerHeight * -0x1), 0x0, 0x0);
        progressBar = (ProgressBar) header.findViewById(R.id.nerby_progressBar);
    }

    public void onScroll(AbsListView arg0, int firstVisiableItem, int visibleItemCount, int count) {
    }

    public void doRefresh() {
        state = REFRESHING;
        changeHeaderViewByState();
        onRefresh();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:

                if ((firstItemIndex == 0) && (!isRecored)) {
                    isRecored = true;
                    startY = (int) event.getY();
                }
            case 1:

                if ((state != 0x2) && (state != 0x4)) {
                    if (state == 0x1) {
                        state = 0x3;
                        changeHeaderViewByState();
                    } else if (state == 0) {
                        state = 0x2;
                        changeHeaderViewByState();
                        onRefresh();
                    }
                } else {
                    isRecored = false;
                }
            case 2:

                int tempY = (int) event.getY();
                if ((firstItemIndex == 0) && (!isRecored)) {
                    isRecored = true;
                    startY = tempY;
                } else if ((state != 0x2) && (isRecored) && (state != 0x4)) {
                    if (state == 0) {
                        setSelection(0x0);
                        if (((tempY - startY) / 0x3) < headerHeight) {
                            if ((tempY - startY) > 0) {
                                state = 0x1;
                                changeHeaderViewByState();
                            }
                        } else if ((tempY - startY) <= 0) {
                            state = 0x3;
                            changeHeaderViewByState();
                        }
                    } else if (state == 0x1) {
                        if (((tempY - startY) / 0x3) >= headerHeight) {
                            state = 0x0;
                            changeHeaderViewByState();
                        } else if ((tempY - startY) <= 0) {
                            state = 0x3;
                            changeHeaderViewByState();
                        }
                    } else if (state == 0x3) {
                        if ((tempY - startY) > 0) {
                            state = 0x1;
                            changeHeaderViewByState();
                        }
                    } else if (state == 0x1) {
                        header.setPadding(0x0, ((headerHeight * -0x1) + ((tempY - startY) / 0x3)), 0x0, 0x0);
                    } else if ((state == 0) && ((((tempY - startY) / 0x3) - headerHeight) <= 0)) {
                        header.setPadding(0x0, (((tempY - startY) / 0x3) - headerHeight), 0x0, 0x0);
                    } else {
                        float alpha = (float) (headerHeight - Math.abs(header.getPaddingTop())) / (float) headerHeight;
                        progressBar.setAlpha((float) (int) (255.0f * alpha));
                        break;
                    }
                }


        }
        return super.onTouchEvent(event);
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    private void changeHeaderViewByState() {
        switch (state) {
            case 2: {
                header.setPadding(0x0, 0x0, 0x0, 0x0);
                break;
            }
            case 3: {
                header.setPadding(0x0, (headerHeight * -0x1), 0x0, 0x0);
                break;
            }
            case 0:
            case 1: {
                break;
            }
        }
    }

    public void setOnRefreshListener(NearbyPullRefreshListView.OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void refreshComplete() {
        state = 0x3;
        changeHeaderViewByState();
    }

    private void onRefresh() {
        if (refreshListener != null) {
            refreshListener.onRefresh();
        }
    }

    public void showMoreComplete(boolean flag) {
        footerState = 0x6;
    }

    public interface OnRefreshListener {
        void onShowNextPage();
       void onRefresh();
    }
}