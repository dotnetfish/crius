

package cn.cloudartisan.crius.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.bean.Friend;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.util.FileURLBuilder;

import java.util.ArrayList;
import java.util.List;

public class GroupMemberInviteAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    GroupMemberInviteAdapter.OnChechedListener OnChechedListener;
    protected Context context;
    protected List<Friend> list=new ArrayList<>();
    ArrayList<String> checkedUsers = new ArrayList<String>();
    
    public GroupMemberInviteAdapter(Context c, List<Friend> list, GroupMemberInviteAdapter.OnChechedListener l) {
        context = c;
        this.list = list;
        OnChechedListener = l;
    }
    
    public int getCount() {
        return list.size();
    }
    
    public Friend getItem(int position) {
        return list.get(position);
    }
    
    public long getItemId(int position) {
        return 0;
    }
    
    public View getView(int index, View friendItemView, ViewGroup parent) {
        Friend target = getItem(index);
        GroupMemberInviteAdapter.ViewHolder viewHolder = friendItemView==null?new GroupMemberInviteAdapter.ViewHolder():(GroupMemberInviteAdapter.ViewHolder)friendItemView.getTag();;
        if(friendItemView == null) {
            friendItemView = LayoutInflater.from(context).inflate(R.layout.item_invite_groupmember, null);

            viewHolder.username = (TextView)friendItemView.findViewById(R.id.username);
            viewHolder.icon = (WebImageView)friendItemView.findViewById(R.id.icon);
            viewHolder.checkbox = (CheckBox)friendItemView.findViewById(R.id.checkbox);
            viewHolder.checkbox.setVisibility(View.VISIBLE);
            friendItemView.setTag(viewHolder);
        }
        friendItemView.setOnClickListener(this);
        viewHolder.checkbox.setTag(target.account);
        viewHolder.checkbox.setOnCheckedChangeListener(null);
        if(checkedUsers.contains(target.account)) {
            viewHolder.checkbox.setChecked(true);
        } else {
            viewHolder.checkbox.setChecked(false);
        }
        viewHolder.checkbox.setOnCheckedChangeListener(this);
        viewHolder.icon.load(FileURLBuilder.getUserIconUrl(target.account), R.drawable.icon_head_default);
        viewHolder.username.setText(target.name);
        return friendItemView;
    }
    
    class ViewHolder {
        CheckBox checkbox;
        WebImageView icon;
        TextView username;
    }
    
    public void onCheckedChanged(CompoundButton compoundbutton, boolean flag) {
        if(flag) {
            checkedUsers.add(compoundbutton.getTag().toString());
        } else {
            checkedUsers.remove(compoundbutton.getTag());
        }
        OnChechedListener.onChecked(compoundbutton, compoundbutton.getTag().toString(), flag);
    }
    
    public ArrayList getCheckedUsers() {
        return checkedUsers;
    }
    
    public void onClick(View v) {
        GroupMemberInviteAdapter.ViewHolder viewHolder = (GroupMemberInviteAdapter.ViewHolder)v.getTag();
        viewHolder.checkbox.setChecked((!viewHolder.checkbox.isChecked()));
    }

    public  interface OnChechedListener
    {
         void onChecked(CompoundButton paramCompoundButton, String paramString, boolean paramBoolean);
    }
}
