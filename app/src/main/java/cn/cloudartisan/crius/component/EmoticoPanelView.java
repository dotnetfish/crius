/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.component;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.adapter.ViewPaperAdapter;
import cn.cloudartisan.crius.app.CriusApplication;

import java.util.ArrayList;
import java.util.List;

public class EmoticoPanelView extends LinearLayout implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener {
    private Context context;
    int emoticoPadding;
    EmoticoPanelView.OnEmoticoSelectedListener emoticoSelectedListener;
    int emoticoSize;
    LinearLayout emoticoViewPagerTagPanel;
    ViewPager viewPager;
    ViewPaperAdapter viewPaperAdapter;
    //pagerListView = new ArrayList();
    List<View> pagerListView = new ArrayList();
    int pageIndex = 0x0;
    
    public EmoticoPanelView(Context context) {
        super(context);
        this.context = context;
    }
    
    public EmoticoPanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
    
    public void onFinishInflate() {
        super.onFinishInflate();
        emoticoSize = context.getResources().getDimensionPixelOffset(R.dimen.inputpanel_emotico_size);
        emoticoPadding = context.getResources().getDimensionPixelOffset(R.dimen.inputpanel_emotico_padding);
        emoticoViewPagerTagPanel = (LinearLayout)findViewById(R.id.emoticoViewPagerTagPanel);
        viewPager = (ViewPager)findViewById(R.id.emoticoViewPager);
        /*for(int var1 = 0; var1 < (CriusApplication.emoticonList.size() - 1) / 28 + 1; ++var1) {
            GridView var2 = (GridView)LayoutInflater.from(this.context).inflate(R.layout.emoticon_gridview,null);
            ArrayList var3 = new ArrayList();
            ArrayList var4 = new ArrayList();
            if(CriusApplication.emoticonList.size() < (var1 + 1) * 28) {
                var3.addAll(CriusApplication.emoticonList.subList(var1 * 28, CriusApplication.emoticonList.size()));
                var4.addAll(CriusApplication.emoticonKeyList.subList(var1 * 28, CriusApplication.emoticonKeyList.size()));
            } else {
                var3.addAll(CriusApplication.emoticonList.subList(var1 * 28, (var1 + 1) * 28));
                var4.addAll(CriusApplication.emoticonKeyList.subList(var1 * 28, (var1 + 1) * 28));
            }

            var2.setAdapter(new EmoticoPanelView.EmoticoGridViewAdapter(var3, var4));
            var2.setId(var1);
            var2.setOnItemClickListener(this);
            this.pagerListView.add(var2);
        }*/

        this.viewPaperAdapter = new ViewPaperAdapter(this.pagerListView);
        this.viewPager.setAdapter(this.viewPaperAdapter);
        this.viewPager.setOnPageChangeListener(this);
        this.emoticoViewPagerTagPanel.getChildAt(0).setSelected(true);
    }
    
    public void onItemClick(AdapterView<?> gridView, View view, int index, long arg3) {
        String key =CriusApplication.emoticonKeyList.get(((gridView.getId() * 0x1c) + index));
        emoticoSelectedListener.onEmoticoSelected(key);
    }
    
    public void setOnEmoticoSelectedListener(EmoticoPanelView.OnEmoticoSelectedListener emoticoSelectedListener) {
        this.emoticoSelectedListener = emoticoSelectedListener;
    }
    
    public void onPageScrollStateChanged(int arg0) {
    }
    
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }
    
    public void onPageSelected(int index) {
        if(pageIndex != index) {
            emoticoViewPagerTagPanel.getChildAt(index).setSelected(true);
            emoticoViewPagerTagPanel.getChildAt(pageIndex).setSelected(false);
            pageIndex = index;
        }
    }


    public class EmoticoGridViewAdapter
            extends BaseAdapter
    {
        List<String> pagerEmoticoKeyList = new ArrayList();
        List<String> pagerEmoticoList = new ArrayList();

        public EmoticoGridViewAdapter(List<String> paramList,List<String> keyList)
        {
            this.pagerEmoticoList = paramList;

            this.pagerEmoticoKeyList = keyList;
        }

        public int getCount()
        {
            return this.pagerEmoticoList.size();
        }

        public Object getItem(int paramInt)
        {
            return null;
        }

        public long getItemId(int paramInt)
        {
            return 0L;
        }

        public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
        {
            ImageView var4;
            if(paramView == null) {
                var4 = new ImageView(EmoticoPanelView.this.context);
                var4.setLayoutParams(new AbsListView.LayoutParams(EmoticoPanelView.this.emoticoSize, EmoticoPanelView.this.emoticoSize));
                var4.setPadding(EmoticoPanelView.this.emoticoPadding,
                        EmoticoPanelView.this.emoticoPadding,
                        EmoticoPanelView.this.emoticoPadding, EmoticoPanelView.this.emoticoPadding);
            } else {
                var4 = (ImageView)paramView;
            }

            var4.setImageResource(EmoticoPanelView.this.context.getResources().getIdentifier(EmoticoPanelView.this.context.getPackageName() +
                    ":drawable/" + this.pagerEmoticoList.get(paramInt), null, null));
            return var4;
        }
    }

    public  interface OnEmoticoSelectedListener
    {
        void onEmoticoSelected(String paramString);
    }
}
