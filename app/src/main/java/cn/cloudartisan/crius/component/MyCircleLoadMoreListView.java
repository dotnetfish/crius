/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.util.AppTools;

public class MyCircleLoadMoreListView extends ListView implements AbsListView.OnScrollListener {
    private static final int LOADING_MORE = 0x5;
    private static final int LOADING_MORE_DONE = 0x6;
    private View footer;
    private int footerHeight;
    private int footerState;
    private View header;
    private MyCircleLoadMoreListView.OnRefreshListener refreshListener;
    boolean hasMore = true;
    
    public MyCircleLoadMoreListView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        setOnScrollListener(this);
        init(paramContext);
    }
    
    public void init(Context context) {
        header = LayoutInflater.from(context).inflate(R.layout.layout_circle_listheader, null);
        footer = LayoutInflater.from(context).inflate(R.layout.list_footer, null);
        AppTools.measureView(header);
        footerHeight = footer.getMeasuredHeight();
        addHeaderView(header, 0x0, false);
        addFooterView(footer, 0x0, false);
        footer.setVisibility(View.GONE);
        header.setPadding(0x0, 0x0, 0x0, 0x0);
        footer.setPadding(0x0, 0x0, 0x0, (footerHeight * -0x1));
    }
    
    public void onScroll(AbsListView arg0, int firstVisiableItem, int visibleItemCount, int count) {
    }
    
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if(scrollState == 0) {
            if(view.getLastVisiblePosition() == view.getCount() - 1) {
                this.footer.setVisibility(VISIBLE);
                if(!this.hasMore) {
                    this.footer.findViewById(R.id.footer_progressBar).setVisibility(GONE);
                    this.footer.findViewById(R.id.footer_hint).setVisibility(VISIBLE);
                    return;
                }

                this.footer.findViewById(R.id.footer_progressBar).setVisibility(VISIBLE);
                this.footer.findViewById(R.id.footer_hint).setVisibility(GONE);
                if(this.footerState != 5) {
                    this.refreshListener.onShowNextPage();
                    this.footerState = 5;
                }
            }
        }
    }
    
    public void setOnRefreshListener(MyCircleLoadMoreListView.OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }
    
    public void showMoreComplete(boolean hasMore) {
        this.hasMore = hasMore;
        footerState = 0x6;
        footer.findViewById(R.id.footer_progressBar).setVisibility(View.GONE);
        if(!hasMore) {
            footer.findViewById(R.id.footer_hint).setVisibility(View.VISIBLE);
        }
    }

    public  interface OnRefreshListener
    {
         void onShowNextPage();
    }
}
