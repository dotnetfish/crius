package cn.cloudartisan.crius.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import cn.cloudartisan.crius.R;

/**
 * Created by kenqu on 2016/1/28.
 */
public  class PushLoadMoreListView extends ListView implements AbsListView.OnScrollListener {
    private static final int LOADING_MORE = 5;
    private static final int LOADING_MORE_DONE = 6;
    private View footer;
    private int footerHeight;
    private int footerState;

    private OnRefreshListener refreshListener;
    boolean hasMore = true;

    public PushLoadMoreListView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        setOnScrollListener(this);
        init(paramContext);
    }

   // protected   abstract View getHeaderView(Context context);
   // protected  abstract  View getFooterView(Context context);

    public void init(Context context) {

       footer = LayoutInflater.from(context).inflate(R.layout.list_footer, null);

        footerHeight = footer.getMeasuredHeight();

        addFooterView(footer, 0x0, false);
        footer.setVisibility(View.GONE);

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
                if(this.footerState != LOADING_MORE) {
                    this.refreshListener.onShowNextPage();
                    this.footerState = LOADING_MORE;
                }
            }
        }
    }

    public void setOnRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void showMoreComplete(boolean hasMore) {
        this.hasMore = hasMore;
        footerState = LOADING_MORE_DONE;
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
