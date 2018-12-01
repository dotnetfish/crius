package cn.cloudartisan.crius.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.bean.LiveInfo;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.component.WebImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenqu on 2016/1/28.
 */
public class LivesListAdapter extends BaseAdapter {
    protected Context context;
    protected List<LiveInfo> list =new ArrayList<>();
    User self;
    public LivesListAdapter(Context c, List<LiveInfo> list) {
        context = c;
        this.list = list;
        self = Global.getCurrentUser();
    }

    public int getCount() {
        return list.size();
    }

    public LiveInfo getItem(int position) {
        return (LiveInfo)list.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.lives_item, null);
            viewHolder = new ViewHolder();
            viewHolder.logo1 = (WebImageView) view.findViewById(R.id.tlogo1);
            viewHolder.logo2 = (WebImageView) view.findViewById(R.id.tlogo_2);
            viewHolder.team1 = (TextView)view.findViewById(R.id.team1);
            viewHolder.time = (TextView)view.findViewById(R.id.time);
            viewHolder.team2 = (TextView) view.findViewById(R.id.team2);
            //viewHolder.link = (TextView)view.findViewById(R.id.linkTitle);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        LiveInfo news=this.getItem(position);
        viewHolder.logo1.load(news.getLogo1());
       // String date=android.text.format.DateFormat.format("yyyy-MM-dd HH:mm",news.getPublishTime()).toString();
        viewHolder.team2.setText(news.getTeam2());
        //Log.e("debug",news.getLogo1());
        viewHolder.logo2.load(news.getLogo2());
        viewHolder.team1.setText(String.valueOf(news.getTeam1()));
        viewHolder.time.setText(news.getTime());
        return  view;

    }
    class ViewHolder {

        TextView team1;
        TextView team2;
        TextView time;

        WebImageView logo1;
        WebImageView logo2;
        //TextView news_date;
    }
}
