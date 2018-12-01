package cn.cloudartisan.crius.ui.BBS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.TypeReference;
import cn.cloudartisan.crius.client.constant.CIMConstant;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.adapter.MyCircleListViewAdapter;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.Article;
import cn.cloudartisan.crius.bean.Page;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.component.MyCircleLoadMoreListView;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.db.ArticleDBManager;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.ui.base.CIMMonitorFragment;
import cn.cloudartisan.crius.ui.trend.ArticleDetailedActivity;
import cn.cloudartisan.crius.ui.trend.CircleMessageListActivity;
import cn.cloudartisan.crius.ui.trend.MyCircleHomeActivity;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by kenqu on 2016/8/2.
 */
public class BBSFragment  extends CIMMonitorFragment implements MyCircleLoadMoreListView.OnRefreshListener, HttpAPIResponser, AdapterView.OnItemClickListener {
    public static final String ACTION_DELETE_ARTICLE = "com.farsunset.cim.DELETE_ARTICLE";
    protected MyCircleListViewAdapter adapter;
    MyCircleLoadMoreListView circleListView;
    int currentPage=1;
    BBSFragment.DeleteArticleReceiver deleteArticleReceiver;
    private List<Article> list=new ArrayList<>();
    HttpAPIRequester requester;
    User self;

    public void initComponents() {
        //setDisplayHomeAsUpEnabled(true);
        self = Global.getCurrentUser();
        circleListView = (MyCircleLoadMoreListView)findViewById(R.id.circleListView);
        circleListView.setOnRefreshListener(this);
        ((WebImageView)circleListView.findViewById(R.id.icon)).load(FileURLBuilder.getUserIconUrl(self.getAccount()));
        circleListView.setOnItemClickListener(this);
        list.addAll(ArticleDBManager.getManager().queryArticles(self.getAccount(), currentPage));
        adapter = new MyCircleListViewAdapter(this.getContext(), list);
        circleListView.setAdapter(adapter);
        requester = new HttpAPIRequester(this);
        requester.execute(null, new TypeReference<MyCircleHomeActivity>() {
        }.getType(), URLConstant.ARTICLE_MYLIST_URL);
        deleteArticleReceiver = new BBSFragment.DeleteArticleReceiver();
        //registerReceiver(deleteArticleReceiver, deleteArticleReceiver.getIntentFilter());
    }

    public void onClick(View v) {
    }

    public void onShowNextPage() {
        currentPage = (currentPage + 0x1);
        requester.execute(null, new TypeReference<MyCircleHomeActivity>() {

        }.getType(), URLConstant.ARTICLE_MYLIST_URL);
    }

    public void onSuccess(Object data, List datalist, Page page, String code, String url) {
        //   circleListView.refreshComplete();
        circleListView.showMoreComplete(true);
       // setProgressBarIndeterminateVisibility(false);
        if(CIMConstant.ReturnCode.CODE_200.equals(code)) {
            if((datalist != null) && (!datalist.isEmpty())) {
                if(currentPage == 0x1) {
                    list.clear();
                    ArticleDBManager.getManager().save(datalist);
                }
                list.addAll(datalist);
                adapter.notifyDataSetChanged();
                return;
            }
            if(currentPage > 0x1) {
                currentPage = (currentPage - 0x1);
            }
        }
    }

    public void onFailed(Exception e) {
        circleListView.showMoreComplete(true);
    }

    public Map getRequestParams(String code) {
        apiParams.put("account", self.getAccount());
        apiParams.put("currentPage", Integer.valueOf(currentPage));
        return apiParams;
    }

    public void onRequest() {
        //setProgressBarIndeterminateVisibility(true);
    }

    public int getConentLayout() {
        return R.layout.activity_trend_my_circle;
    }

    public int getActionBarTitle() {
        return R.string.label_circle_my;
    }

   /* public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.user_circle_page, menu);
       return super.onCreateOptionsMenu(menu);
    }*/

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.bar_menu_msg_list:
            {
                Intent intent = new Intent(this.getContext(), CircleMessageListActivity.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this.getContext(), ArticleDetailedActivity.class);
        intent.putExtra("article", (Serializable)list.get((position - 0x1)));
        startActivity(intent);
    }
    public class DeleteArticleReceiver
            extends BroadcastReceiver
    {
        public DeleteArticleReceiver() {}

        public IntentFilter getIntentFilter()
        {
            IntentFilter localIntentFilter = new IntentFilter();
            localIntentFilter.addAction("com.farsunset.cim.DELETE_ARTICLE");
            return localIntentFilter;
        }

        public void onReceive(Context paramContext, Intent paramIntent)
        {
            Article article = (Article)paramIntent.getSerializableExtra("article");
            BBSFragment.this.list.remove(paramContext);
            BBSFragment.this.adapter.notifyDataSetChanged();
        }
    }
}
