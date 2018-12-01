/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.ui;

import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.ui.base.CIMMonitorFragment;
import android.view.View;
import android.widget.TextView;
import cn.cloudartisan.crius.component.WebImageView;
import android.content.Intent;
import cn.cloudartisan.crius.ui.trend.SNSShakeActivity;
import cn.cloudartisan.crius.ui.trend.NearbyUserListActivity;
import cn.cloudartisan.crius.ui.trend.DriftBottleActivity;
import cn.cloudartisan.crius.ui.trend.SNSCircleListActivity;
import cn.cloudartisan.crius.db.MessageDBManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import cn.cloudartisan.crius.bean.Article;
import com.alibaba.fastjson.JSON;
import cn.cloudartisan.crius.util.FileURLBuilder;
import cn.cloudartisan.crius.util.StringUtils;
import cn.cloudartisan.crius.bean.Comment;
import java.util.List;

public class TrendCenterFragment extends CIMMonitorFragment implements View.OnClickListener {
    TextView newBottleMsgCountLabel;
    TextView newMsgCountLabel;
    View newMsgHintView;
    TextView newMsgTabCount;
    View newMsgTabDot;
    WebImageView newMsgUserIcon;
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_trend, container, false);
    }
    
    public void onViewCreated(View view, Bundle savedInstanceState) {
        findViewById(R.id.nearbyItem).setOnClickListener(this);
        findViewById(R.id.circleItem).setOnClickListener(this);
        findViewById(R.id.bottleItem).setOnClickListener(this);
        findViewById(R.id.shakeItem).setOnClickListener(this);
        newMsgHintView = findViewById(R.id.newMsgHintView);
        newMsgUserIcon = (WebImageView)findViewById(R.id.newMsgUserIcon);
    }
    
    public void onResume() {
        super.onResume();
        View tabView =( (HomeActivity)getActivity()).getConversationFragment();
        newMsgTabDot = tabView.findViewById(R.id.badge_new_dot);
        newMsgTabCount = (TextView)tabView.findViewById(R.id.badge_unread_count);
        newBottleMsgCountLabel = (TextView)findViewById(R.id.bottle_new_msg_count_label);
        newMsgCountLabel = (TextView)findViewById(R.id.new_msg_count_label);
        long count = MessageDBManager.getManager().countNewMessage(new String[] {"801", "802"});
        if(count > 0x0) {
            newMsgCountLabel.setVisibility(View.VISIBLE);
            newMsgCountLabel.setText(String.valueOf(count));
        } else {
            newMsgCountLabel.setVisibility(View.GONE);
            newMsgCountLabel.setText("");
        }
        List<cn.cloudartisan.crius.bean.Message> list = MessageDBManager.getManager().queryNewMoments("800");
        if((list != null) && (!list.isEmpty())) {
            newMsgTabDot.setVisibility(View.VISIBLE);
            newMsgHintView.setVisibility(View.VISIBLE);
            String account = JSON.parseObject(((cn.cloudartisan.crius.bean.Message)list.get(0x0)).content).getString("account");
            newMsgUserIcon.load(FileURLBuilder.getUserIconUrl(account), R.drawable.icon_head_default);
        } else {
            newMsgTabDot.setVisibility(View.GONE);
            newMsgHintView.setVisibility(View.GONE);
        }
        long bcount = MessageDBManager.getManager().countNewByType("700");
        if((count + bcount) > 0x0) {
            newMsgTabCount.setText(String.valueOf((count + bcount)));
            newMsgTabCount.setVisibility(View.VISIBLE);
        } else {
            newMsgTabCount.setText("");
            newMsgTabCount.setVisibility(View.GONE);
        }
        if(bcount > 0x0) {
            newBottleMsgCountLabel.setVisibility(View.VISIBLE);
            newBottleMsgCountLabel.setText(String.valueOf(bcount));
            return;
        }
        newBottleMsgCountLabel.setText("");
        newBottleMsgCountLabel.setVisibility(View.GONE);
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.shakeItem:
            {
                startActivity(new Intent(getActivity(), SNSShakeActivity.class));
                break;
            }
            case R.id.nearbyItem:
            {
                startActivity(new Intent(getActivity(), NearbyUserListActivity.class));
                break;
            }
            case R.id.bottleItem:
            {
                startActivity(new Intent(getActivity(), DriftBottleActivity.class));
                break;
            }
            case R.id.circleItem:
            {
                Intent cintent = new Intent(getActivity(), SNSCircleListActivity.class);
                startActivity(cintent);
                newMsgHintView.setVisibility(View.GONE);
                newMsgTabDot.setVisibility(View.GONE);
                newMsgTabCount.setVisibility(View.GONE);
                newMsgTabCount.setText("");
                newMsgCountLabel.setVisibility(View.GONE);
                newMsgCountLabel.setText("");
                MessageDBManager.getManager().readByType("800");
                break;
            }
            case R.id.newMsgHintView:
            case R.id.newMsgUserIcon:
            case R.id.new_msg_count_label:
            case R.id.bottle_new_msg_count_label:
            {
                break;
            }
        }
    }
    
    public void onMessageReceived(cn.cloudartisan.crius.client.model.Message msg) {
        if("800".equals(msg.getType())) {
            Article article = (Article)JSON.parseObject(msg.getContent(), Article.class);
            newMsgHintView.setVisibility(View.VISIBLE);
            newMsgTabDot.setVisibility(View.VISIBLE);
            newMsgUserIcon.load(FileURLBuilder.getUserIconUrl(article.account), R.drawable.icon_head_default);
        }
        if("700".equals(msg.getType())) {
            newBottleMsgCountLabel.setVisibility(View.VISIBLE);
            if(StringUtils.isNotEmpty(newBottleMsgCountLabel.getText())) {
                int count = Integer.parseInt(newBottleMsgCountLabel.getText().toString()) + 0x1;
                newBottleMsgCountLabel.setText(String.valueOf(count));
            } else {
                newBottleMsgCountLabel.setText("1");
            }
            if(StringUtils.isNotEmpty(newMsgTabCount.getText())) {
                int count = Integer.parseInt(newMsgTabCount.getText().toString()) + 0x1;
                newMsgTabCount.setText(String.valueOf(count));
            } else {
                newMsgTabCount.setText("1");
            }
            newMsgTabCount.setVisibility(View.VISIBLE);
        }
        if(!"801".equals(msg.getType())) {
            if(!"802".equals(msg.getType())) {
            }
            return;
        }
        Comment comment = (Comment)JSON.parseObject(msg.getContent(), Comment.class);
        newMsgTabDot.setVisibility(View.GONE);
        newMsgTabCount.setVisibility(View.VISIBLE);
        newMsgCountLabel.setVisibility(View.VISIBLE);
        if(StringUtils.isNotEmpty(newMsgTabCount.getText())) {
            int count = Integer.parseInt(newMsgTabCount.getText().toString()) + 0x1;
            newMsgTabCount.setText(String.valueOf(count));
            newMsgCountLabel.setText(String.valueOf(count));
        } else {
            newMsgTabCount.setText("1");
            newMsgCountLabel.setText("1");
        }
        newMsgUserIcon.load(FileURLBuilder.getUserIconUrl(comment.account), R.drawable.icon_head_default);
    }
}