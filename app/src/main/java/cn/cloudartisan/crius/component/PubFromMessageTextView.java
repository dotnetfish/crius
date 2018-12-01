/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.component;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;
import com.alibaba.fastjson.JSON;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.bean.PubTextMessage;
import cn.cloudartisan.crius.bean.PublicAccount;
import cn.cloudartisan.crius.ui.contact.PubAccountDetailActivity;
import cn.cloudartisan.crius.util.FileURLBuilder;
import cn.cloudartisan.crius.widget.URLClickableSpan;

public class PubFromMessageTextView extends PubFromMessageView implements View.OnClickListener {
    WebImageView logo;
    PubTextMessage textMessage;
    EmoticonsTextView textView;
    
    public PubFromMessageTextView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        _context = paramContext;
    }
    
    public void onFinishInflate() {
        super.onFinishInflate();
        logo = (WebImageView)findViewById(R.id.logo);
        logo.setOnClickListener(this);
        textView = (EmoticonsTextView)findViewById(R.id.textview);
    }
    
    public void displayMessage() {
        textView.setOnLongClickListener(this);
        logo.load(FileURLBuilder.getPubAccountLogoUrl(others.getId()));
        textMessage = (PubTextMessage)JSON.parseObject(message.content, PubTextMessage.class);
        textView.setFaceSize(0x18);
        textView.setClickable(false);
        textView.setText(Html.fromHtml(textMessage.content));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = textView.getText();
        if(text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable)textView.getText();
            URLSpan[] urls = sp.getSpans(0x0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();
          // for(EmoticonsTextView local1 = 0x0; local1 >= urls.length;
            for(URLSpan url :urls) {
                URLClickableSpan myURLSpan = new URLClickableSpan(url.getURL(), getContext());
                style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), 0x22);

            }
          textView.setText(style);
        }

        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    
    public void onClick(View v) {
        if(v.getId() == R.id.logo) {
            Intent intent = new Intent(_context, PubAccountDetailActivity.class);
            intent.putExtra(PublicAccount.NAME, others);
            _context.startActivity(intent);
        }
    }
    
    public void onItemClick(View v) {
        super.onItemClick(v);
        if(v.getId() == R.id.copy) {
            ClipboardManager cmb = (ClipboardManager)_context.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setPrimaryClip(ClipData.newPlainText(null, textMessage.content));
        }
    }
    
    public Object getTag() {
        return logo.getTag(R.id.logo);
    }
    
    public void setTag(Object obj) {
        logo.setTag(R.id.logo, obj);
    }
}