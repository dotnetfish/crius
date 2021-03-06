/**
  * Generated by smali2java 1.0.0.558
  * Copyright (C) 2013 Hensence.com
  */

package cn.cloudartisan.crius.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.bean.Friend;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.util.CharacterParser;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.util.*;

public class FriendListViewAdapter extends BaseAdapter {
    protected Context context;
    protected List<Friend> list=new ArrayList<>();
    
    public FriendListViewAdapter(Context c, List<Friend> list) {
        context = c;
        this.list = list;
    }
    
    public int getCount() {
        return list.size();
    }
    
    public Friend getItem(int position) {
        return list.get(position);
    }
    
    public long getItemId(int position) {
        return 0x0;
    }
    
    public void notifyDataSetChanged() {
        setFristChar();
        Collections.sort(list, new FriendListViewAdapter.NameAscComparator());
        super.notifyDataSetChanged();
    }
    
    public int getSectionForPosition(int position) {
        return ((Friend)list.get(position)).fristChar.charAt(0);
    }
    
    public int getPositionForSection(int section) {

        for (int i=0;i<getCount();i++)
        {
            if((this.list.get(i)).fristChar.toUpperCase().charAt(0) == section){
                return  i;
            }
        }
        return  -1;
    }
    
    public View getView(int index, View friendItemView, ViewGroup parent) {
        Friend target = getItem(index);
        FriendListViewAdapter.ViewHolder viewHolder = friendItemView == null?
                new FriendListViewAdapter.ViewHolder():(FriendListViewAdapter.ViewHolder)friendItemView.getTag();;
        if(friendItemView == null) {
            friendItemView = LayoutInflater.from(context).inflate(R.layout.item_contact_friend, null);

            viewHolder.fristChar = (TextView)friendItemView.findViewById(R.id.fristChar);
            viewHolder.username = (TextView)friendItemView.findViewById(R.id.username);
            viewHolder.motto = (TextView)friendItemView.findViewById(R.id.motto);
            viewHolder.icon = (WebImageView)friendItemView.findViewById(R.id.child_item_head);
            friendItemView.setTag(viewHolder);
        }
        int section = getSectionForPosition(index);
        if(index == getPositionForSection(section)) {
            viewHolder.fristChar.setVisibility(View.VISIBLE);
            viewHolder.fristChar.setText(target.fristChar);
        } else {
            viewHolder.fristChar.setVisibility(View.GONE);
        }
        viewHolder.icon.load(FileURLBuilder.getUserIconUrl(target.account), R.drawable.icon_head_default);
        viewHolder.username.setText(target.name);
        viewHolder.motto.setText(target.motto);
        return friendItemView;
    }
    
    class ViewHolder {
        TextView fristChar;
        WebImageView icon;
        TextView motto;
        TextView username;
    }
    
    private void setFristChar() {

        for (Friend localFriend :this.list)
        {

            localFriend.fristChar = CharacterParser.getInstance().getSelling(localFriend.name).substring(0, 1).toUpperCase();
            if (!localFriend.fristChar.matches("[A-Z]")) {
                localFriend.fristChar = "#";
            }
        }
    }

    public class NameAscComparator
            implements Comparator<Friend>
    {
        public NameAscComparator() {}

        public int compare(Friend paramFriend1, Friend paramFriend2)
        {
            if ((paramFriend1.fristChar.equals("@")) || (paramFriend2.fristChar.equals("#"))) {
                return -1;
            }
            if ((paramFriend1.fristChar.equals("#")) || (paramFriend2.fristChar.equals("@"))) {
                return 1;
            }
            return paramFriend1.fristChar.compareTo(paramFriend2.fristChar);
        }
    }
}
