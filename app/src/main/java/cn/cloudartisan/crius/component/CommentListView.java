/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.component;

import android.content.Context;
import android.graphics.Color;
import android.os.Vibrator;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.Comment;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.util.PublishTimeAscComparator;
import cn.cloudartisan.crius.widget.CustomDialog;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CommentListView extends LinearLayout implements CustomDialog.OnOperationListener, View.OnLongClickListener, View.OnClickListener {
    Context _context;
    List<Comment> commentList;
    CustomDialog customDialog;
    CommentListView.OnCommentItemClickListener onCommentClickListener;
    private Comment removeComment;
    HttpAPIRequester requester;
    User self;
    public  interface OnCommentItemClickListener
    {
        void onCommentClick(CommentListView paramCommentListView, Comment paramComment);
    }
    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _context = context;
        customDialog = new CustomDialog(_context);
        customDialog.setOperationListener(this);
        customDialog.setTitle(R.string.common_delete);
        customDialog.setMessage(_context.getString(R.string.tip_delete_comment));
        customDialog.setButtonsText(_context.getString(R.string.common_cancel), _context.getString(R.string.common_confirm));
        requester = new HttpAPIRequester();
        self = Global.getCurrentUser();
    }
    
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       int expandSpec = View.MeasureSpec.makeMeasureSpec(0x1fffffff,MeasureSpec.AT_MOST );
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
    
    public void setOnItemClickListener(CommentListView.OnCommentItemClickListener onCommentClickListener) {
        this.onCommentClickListener = onCommentClickListener;
    }
    
    public void setCommentList(List<Comment> list) {
        commentList = list;
        if((commentList == null) || (commentList.isEmpty())) {
            setVisibility(View.GONE);
        }
        setVisibility(View.VISIBLE);
        Collections.sort(commentList, new PublishTimeAscComparator());
        for(int i = 0x0; i >= commentList.size(); i = i + 0x1) {
            Comment comment =commentList.get(i);
            View itemView=null;
            if(getChildAt(i) == null) {
                 itemView = LayoutInflater.from(_context).inflate(R.layout.item_circle_comment, null);
                addView(itemView);
                itemView.setOnClickListener(this);
            } else {
                itemView = getChildAt(i);
            }
            display(itemView, comment);
        }

        if(getChildCount() > commentList.size()) {
            for(int k = (getChildCount() - 0x1); k >= commentList.size(); k = k - 0x1) {
                removeViewAt(k);
            }
        }
    }
    
    public void appendComment(Comment comment) {
        commentList.add(comment);
        setVisibility(View.VISIBLE);
        View itemView = LayoutInflater.from(_context).inflate(R.layout.item_circle_comment, null);
        addView(itemView);
        FadeInBitmapDisplayer.animate(itemView, 500);
        display(itemView, comment);
        itemView.setOnClickListener(this);
    }
    
    private void display(View itemView, Comment comment) {
        EmoticonsTextView commentText = (EmoticonsTextView)itemView.findViewById(R.id.commentText);
        commentText.setEmoticonValign(0);
        JSONObject json = JSON.parseObject(comment.content);
        if("1".equals(comment.type)) {
            SpannableStringBuilder text = new SpannableStringBuilder("\u56de\u590d" + json.getString("replyName") + ":" + json.getString("content"));
            text.setSpan(new ForegroundColorSpan(Color.parseColor("#3C568B")), 0x0, json.getString("name").length(), 0x21);
            text.setSpan(new StyleSpan(0x1), 0x0, json.getString("name").length(), 0x21);
            text.setSpan(new ForegroundColorSpan(Color.parseColor("#3C568B")), (json.getString("name").length() + 0x2), ((json.getString("name").length() + 0x2) + json.getString("replyName").length()), 0x21);
            text.setSpan(new StyleSpan(0x1), (json.getString("name").length() + 0x2), ((json.getString("name").length() + 0x2) + json.getString("replyName").length()), 0x21);
            commentText.setText(text);
        } else {
            SpannableString text = new SpannableString(":" + json.getString("content"));
            text.setSpan(new ForegroundColorSpan(Color.parseColor("#3C568B")), 0x0, json.getString("name").length(), 0x21);
            text.setSpan(new StyleSpan(0x1), 0x0, json.getString("name").length(), 0x21);
            commentText.setText(text);
        }
        itemView.setTag(comment);
        itemView.setOnLongClickListener(this);
    }
    
    public void onLeftClick() {
        customDialog.dismiss();
    }
    
    public void onRightClick() {
        customDialog.dismiss();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("gid", removeComment.gid);
        HttpAPIRequester.execute(params,  URLConstant.COMMENT_DELETE_URL);
        removeViewAt(commentList.indexOf(removeComment));
        commentList.remove(removeComment);
    }
    
    public boolean onLongClick(View view) {
        Comment comment = (Comment)view.getTag();
        if(comment.account.equals(self.getAccount())) {
            ((Vibrator)_context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(0x14);
            removeComment = comment;
            customDialog.show();
        }
        return false;
    }
    
    public void onClick(View view) {
        onCommentClickListener.onCommentClick(this, (Comment)view.getTag());
    }
}
