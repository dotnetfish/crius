package cn.cloudartisan.crius.ui.trend;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.bean.Article;
import cn.cloudartisan.crius.bean.Comment;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.db.MessageDBManager;
import cn.cloudartisan.crius.ui.base.CIMMonitorActivity;
import cn.cloudartisan.crius.util.FileURLBuilder;
import cn.cloudartisan.crius.util.StringUtils;

import java.util.List;

public class TrendCenterActivity extends CIMMonitorActivity implements View.OnClickListener {
    TextView newBottleMsgCountLabel;
    TextView newMsgCountLabel;
    View newMsgHintView;
    TextView newMsgTabCount;
    View newMsgTabDot;
    WebImageView newMsgUserIcon;

    @Override
    public int getActionBarTitle() {
        return R.string.common_trend;
    }

    public int getConentLayout() {
        return R.layout.activity_trend;
    }

    @Override
    public void initComponents() {
        findViewById(R.id.nearbyItem).setOnClickListener(this);
        findViewById(R.id.circleItem).setOnClickListener(this);
        findViewById(R.id.bottleItem).setOnClickListener(this);
        findViewById(R.id.shakeItem).setOnClickListener(this);
        newMsgHintView = findViewById(R.id.newMsgHintView);
        newMsgUserIcon = (WebImageView)findViewById(R.id.newMsgUserIcon);
    }



    public void onResume() {
        super.onResume();
        /*View tabView =( (HomeActivity)getActivity()).getConversationFragment();
        newMsgTabDot = tabView.findViewById(R.id.badge_new_dot);
        newMsgTabCount = (TextView)tabView.findViewById(R.id.badge_unread_count);
        newBottleMsgCountLabel = (TextView)findViewById(R.id.bottle_new_msg_count_label);
        newMsgCountLabel = (TextView)findViewById(R.id.new_msg_count_label);
        long count = MessageDBManager.getManager().countNewMessage(new String[] {"801", "802"});
        if(count > 0) {
            newMsgCountLabel.setVisibility(View.VISIBLE);
            newMsgCountLabel.setText(String.valueOf(count));
        } else {
            newMsgCountLabel.setVisibility(View.GONE);
            newMsgCountLabel.setText("");
        }*/
        List<Message> list = MessageDBManager.getManager().queryNewMoments("800");

    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.shakeItem:
            {
                startActivity(new Intent(this, SNSShakeActivity.class));
                break;
            }
            case R.id.nearbyItem:
            {
                startActivity(new Intent(this, NearbyUserListActivity.class));
                break;
            }
            case R.id.bottleItem:
            {
                startActivity(new Intent(this, DriftBottleActivity.class));
                break;
            }
            case R.id.circleItem:
            {
                Intent cintent = new Intent(this, SNSCircleListActivity.class);
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
