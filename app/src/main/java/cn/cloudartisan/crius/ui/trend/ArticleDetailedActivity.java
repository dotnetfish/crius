/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package cn.cloudartisan.crius.ui.trend;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import cn.cloudartisan.crius.client.constant.CIMConstant;
import cn.cloudartisan.crius.client.model.Message;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.adapter.CustomCommentListAdapter;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.*;
import cn.cloudartisan.crius.component.SNSImageView;
import cn.cloudartisan.crius.component.SimpleInputPanelView;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.db.ArticleDBManager;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.ui.base.CIMMonitorActivity;
import cn.cloudartisan.crius.util.AppTools;
import cn.cloudartisan.crius.util.FileURLBuilder;
import cn.cloudartisan.crius.util.StringUtils;
import cn.cloudartisan.crius.widget.CustomDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleDetailedActivity extends CIMMonitorActivity implements AdapterView.OnItemClickListener, SimpleInputPanelView.OnOperationListener, HttpAPIResponser, CustomDialog.OnOperationListener {
    protected CustomCommentListAdapter adapter;
    Article article;
    private List<Comment> commentList;
    HttpAPIRequester commentRequester;
    CustomDialog customDialog;
    SimpleInputPanelView inputPanelView;
    ListView listView;
    User self;

    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
        article = (Article) getIntent().getSerializableExtra("article");
        commentList = article.getCommentList();
        adapter = new CustomCommentListAdapter(this, commentList);
        listView = (ListView) findViewById(R.id.listView);
        inputPanelView = (SimpleInputPanelView) findViewById(R.id.inputPanelView);
        inputPanelView.setOnOperationListener(this);
        displayHeaderView();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        commentRequester = new HttpAPIRequester(this);
        self = Global.getCurrentUser();
        apiParams.put("articleId", article.gid);
        apiParams.put("type", "0");
    }

    public void displayHeaderView() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.layout_article_detailed_header, null);
        WebImageView imageView = (WebImageView) headerView.findViewById(R.id.icon);
        imageView.load(FileURLBuilder.getUserIconUrl(article.account), R.drawable.icon_head_default);
        //(WebImageView) headerView.findViewById(R.id.icon);
        JSONObject json = JSON.parseObject(article.content);
        if (StringUtils.isNotEmpty(json.getString("content"))) {
            headerView.findViewById(R.id.text).setVisibility(View.VISIBLE);

            ((TextView) headerView.findViewById(R.id.text)).setText(json.getString("content"));
        }
        ((TextView) headerView.findViewById(R.id.name)).setText(json.getString("name"));
        ((TextView) headerView.findViewById(R.id.time)).setText(AppTools.howTimeAgo(this,
                Long.valueOf(article.timestamp).longValue()));
        if (!StringUtils.isEmpty(article.thumbnail)) {
            List<SNSImage> snsImageList = (List) JSON.parseObject(article.thumbnail, new TypeReference<ArticleDetailedActivity>(){}.getType(), new Feature[0x0]);
            if (snsImageList.size() == 0x1) {
                headerView.findViewById(R.id.singleImageView).setVisibility(View.VISIBLE);
                SNSImage snsImage=(SNSImage) snsImageList.get(0x0);
                ((SNSImageView) headerView.findViewById(R.id.singleImageView)).display(article, snsImage,snsImage.ow,snsImage.oh);
            }
            LinearLayout rootImageViewPanel = (LinearLayout) headerView.findViewById(R.id.rootImageViewPanel);
            rootImageViewPanel.setVisibility(View.VISIBLE);
            for (int L = 0x0; L < (((snsImageList.size() - 0x1) / 0x3) + 0x1); L = L + 0x1) {
                ((LinearLayout) headerView.findViewById(R.id.rootImageViewPanel)).getChildAt(L).setVisibility(View.VISIBLE);
                for (int i = (L * 0x3); i < snsImageList.size();  i = i + 0x1){
                    ((LinearLayout) rootImageViewPanel.getChildAt(L)).getChildAt((i - (L * 0x3))).setVisibility(View.VISIBLE);
                    ((SNSImageView) ((LinearLayout) rootImageViewPanel.getChildAt(L)).getChildAt((i - (L * 0x3)))).display(article, (SNSImage) snsImageList.get(i));
                }
            }
        }
       /* (article.type.equals("1"))){
           final  JSONObject link = JSON.parseObject(article.content).getJSONObject("link");
            headerView.findViewById(R.id.linkPanel).setVisibility(View.VISIBLE);
            headerView.findViewById(R.id.linkPanel).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(this, MMWebViewActivity.class);
                    intent.putExtra("url", link.getString("link"));
                    startActivity(intent);
                }
            });
            ( (TextView) headerView.findViewById(R.id.linkTitle)).setText(link.getString("title"));
        }*/
        listView.addHeaderView(headerView);
    }

    public void onClick(View v) {
        v.getId();
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
        if (index == 0) {
            return;
        }
        Comment comment = (Comment) commentList.get((index - 0x1));
        if (comment.account.equals(self.getAccount())) {
            resetApiParams();
            return;
        }
        String name = JSON.parseObject(comment.content).getString("name");
        apiParams.put("replyAccount", comment.account);
        apiParams.put("replyName", name);
        apiParams.put("type", "1");
        apiParams.put("commentId", comment.gid);
        inputPanelView.setHint(getString(R.string.hint_comment, new Object[]{name}));
        inputPanelView.show();
    }

    public void onSendContent(String content) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", self.getName());
        map.put("content", content);
        map.put("type", apiParams.get("type").toString());
        if ("1".equals(apiParams.get("type"))) {
            map.put("commentId", String.valueOf(apiParams.get("commentId")));
            map.put("replyAccount", apiParams.get("replyAccount").toString());
            map.put("replyName", apiParams.get("replyName").toString());
        }
        apiParams.put("content", JSON.toJSONString(map));
        Comment comment = new Comment();
        comment.account = self.getAccount();
        comment.articleId = apiParams.get("articleId").toString();
        comment.content = apiParams.get("content").toString();
        comment.type = apiParams.get("type").toString();
        comment.timestamp = String.valueOf(System.currentTimeMillis());
        commentList.add(comment);
        adapter.notifyDataSetChanged();
        listView.setSelection(listView.getBottom());
        commentRequester.execute(new TypeReference<ArticleDetailedActivity>(){}.getType(), null, URLConstant.COMMENT_PUBLISH_URL);
        inputPanelView.reset();
    }

    public void onSuccess(Object data, List list, Page page, String code, String url) {
        if (CIMConstant.ReturnCode.CODE_200.equals(code)) {
            resetApiParams();
        }
    }

    public void onFailed(Exception e) {
    }

    public Map getRequestParams(String code) {
        apiParams.put("articleId", article.gid);
        apiParams.put("authorAccount", article.account);
        apiParams.put("name", self.getName());
        apiParams.put("account", self.getAccount());
        return apiParams;
    }

    public void onRequest() {
    }

    public void resetApiParams() {
        apiParams.remove("replyAccount");
        apiParams.remove("replyName");
        apiParams.remove("commentId");
        apiParams.put("type", "0");
        inputPanelView.setContent(null);
        inputPanelView.setHint(null);
    }

    public int getConentLayout() {
        return R.layout.activity_article_detailed;
    }

    public int getActionBarTitle() {
        return R.string.common_detail;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (self.getAccount().equals(article.account)) {
            getMenuInflater().inflate(R.menu.single_icon, menu);
            menu.findItem(R.id.menu_icon).setIcon(R.drawable.ofm_delete_icon);
            customDialog = new CustomDialog(this);
            customDialog.setOperationListener(this);
            customDialog.setTitle(R.string.common_delete);
            customDialog.setMessage(getString(R.string.tip_delete_article));
            customDialog.setButtonsText(getString(R.string.common_cancel), getString(R.string.common_confirm));
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_icon: {
                customDialog.show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRightClick() {
        customDialog.dismiss();
        HashMap<String, Object> apiParams = new HashMap<String, Object>();
        apiParams.put("gid", article.gid);
        HttpAPIRequester.execute(apiParams, URLConstant.ARTICLE_DELETE_URL);
        ArticleDBManager.getManager().deleteById(article.gid);
        Intent intent = new Intent();
        intent.putExtra("article", article);
        intent.setAction("com.farsunset.cim.DELETE_ARTICLE");
        sendBroadcast(intent);
        finish();
    }

    public void onLeftClick() {
        customDialog.dismiss();
    }

    public void onKeyboardShow() {
    }

    public void onMessageReceived(Message message) {
        if ((message.getType().equals("801")) || (message.getType().equals("802"))) {
            JSONObject json = JSON.parseObject(message.getContent());
            String articleId = json.getString("articleId");
            if (articleId.equals(article.gid)) {
                Comment comment = new Comment();
                comment.account = json.getString("account");
                comment.articleId = articleId;
                comment.content = json.getString("content");
                comment.type = json.getString("type");
                comment.timestamp = json.getString("timestamp");
                commentList.add(comment);
                adapter.notifyDataSetChanged();
                listView.setSelection(listView.getBottom());
            }
        }
    }
}
