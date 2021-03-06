/**
 * Generated by smali2java 1.0.0.558
 * Copyright (C) 2013 Hensence.com
 */

package cn.cloudartisan.crius.ui.dashboard;

import android.content.Intent;
import android.media.SoundPool;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.adapter.ImageAdapter;
import cn.cloudartisan.crius.adapter.LivesListAdapter;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.Module;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.*;
import cn.cloudartisan.crius.component.*;
import cn.cloudartisan.crius.db.ConfigDBManager;
import cn.cloudartisan.crius.db.MessageDBManager;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.service.MessageNotifyService;
import cn.cloudartisan.crius.ui.base.CIMMonitorFragment;
import cn.cloudartisan.crius.util.AppTools;
import cn.cloudartisan.crius.util.MessageUtil;
import cn.cloudartisan.crius.widget.CustomDialog;

import java.util.*;

public class DashBoardFragment extends CIMMonitorFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, HttpAPIResponser, PushLoadMoreListView.OnRefreshListener {
    public static final String ACTION_APPEND_CHAT = "com.farsunset.cim.MESSAGE_APPEND";
    public static final String ACTION_CHANGE_SEND_STATUS = "com.farsunset.cim.SEND_STATUS_CHANGED";
    public static final String ACTION_DELETE_CHAT = "com.farsunset.cim.DELETE_APPEND";
    public static final String ACTION_UPDATE_ITEM = "com.farsunset.cim.UPDATE_ITEM";
    public static String[] ignoredTypes = new String[]{"2", "101", "103", "104", "106", "107"};
    private static final String ADLOADER = "adloader";
    private static final String NEWSLOADER = "NEWSLOADER";
    List<Module> modules = new ArrayList<>();
    CustomDialog customDialog;

    HttpAPIRequester requester;
    User self;

    ViewFlow viewFlow;
    ImageAdapter imageAdapter;
    List<ADInfo> ads;
    List<LiveInfo> newList;
    LivesListAdapter newsAdapter;
    PushLoadMoreListView newsListView;
    SoundPool soudPool;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newList = new ArrayList<>();
        self = Global.getCurrentUser();

        this.requester = new HttpAPIRequester(this);
        this.ads = new ArrayList<>();
        this.soudPool = new SoundPool(10, 3, 5);
        this.soudPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int arg10, int arg11) {

                soundPool.play(R.raw.goals_sound, 1, 1, 0, 0, 1);


            }
        });

        requester.execute(new TypeReference<DashBoardFragment>() {
                }.getType(),
                null,
                URLConstant.ADS_GET, ADLOADER, "GET");

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_dashboard, container, false);
        newsListView = (PushLoadMoreListView) view.findViewById(R.id.circleListView);
        newsListView.setOnRefreshListener(this);
        this.newsAdapter = new LivesListAdapter(this.getContext(), this.newList);
        newsListView.setAdapter(this.newsAdapter);
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LiveInfo info = newsAdapter.getItem(position);
                Intent intent = new Intent(getContext(), BaseWebActivity.class);
                intent.putExtra("url", info.getLink());
                intent.putExtra("title", info.getTeam1() + " vs " + info.getTeam2());
                startActivity(intent);
            }
        });
        currentPage = -1;
        onShowNextPage();
        viewFlow = (ViewFlow) view.findViewById(R.id.viewflow);
        imageAdapter = new ImageAdapter(this.getContext(), this.ads);
        viewFlow.setAdapter(imageAdapter, 5);
        CircleFlowIndicator indic = (CircleFlowIndicator) view.findViewById(R.id.viewflowindic);
        viewFlow.setFlowIndicator(indic);
        return view;
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (!AppTools.netWorkAvailable(getActivity())) {
            findViewById(R.id.networkWarnPanel).setVisibility(View.VISIBLE);
        }

    }


    public void loadMessage() {
      /*  dataList.clear();
        dataList.addAll(MessageDBManager.getManager().getRecentMessage(self.getAccount(), includedMessageTypes));
        adapter.notifyDataSetChanged();
        if(dataList.isEmpty()) {
            findViewById(R.id.nochatbgimage).setVisibility(View.VISIBLE);
            return;
        }
        findViewById(R.id.nochatbgimage).setVisibility(View.GONE);*/
    }

    private void showNewMsgLable() {
        /*View tabView =( (HomeActivity)getActivity()).getDashBaordFragment();
        newMsgLabel = (TextView)tabView.findViewById(R.id.badge_unread_count);
        int sum = MessageDBManager.getManager().queryIncludedNewCount(includedMessageTypes);
        if(sum > 0) {
            newMsgLabel.setText(sum > 0x63 ? "99+" : String.valueOf(sum));
            newMsgLabel.setVisibility(View.VISIBLE);
            return;
        }
        newMsgLabel.setVisibility(View.GONE);*/
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



        /*MessageItemSource data =( (ChatItem)dataList.get(position)).source;
        view.findViewById(R.id.item_newmsg_label).setVisibility(View.GONE);
        if(data instanceof Group) {
            Intent intent = new Intent(getActivity(), GroupChatActivity.class);
            intent.putExtra("othersId", ((Group)data).groupId);
            intent.putExtra("othersName", ((Group)data).name);
            startActivity(intent);
        }
        if(data instanceof Friend) {
            Intent intent = new Intent(getActivity(), FriendChatActivity.class);
            intent.putExtra("othersId", ((Friend)data).account);
            intent.putExtra("othersName", ((Friend)data).name);
            startActivity(intent);
        }
        if(data instanceof PublicAccount) {
            Intent intent = new Intent(getActivity(), PubAccountChatActivity.class);
            intent.putExtra(PublicAccount.NAME, (PublicAccount)data);
            startActivity(intent);
        }
        if(data instanceof SystemMsg) {
            Intent intent = new Intent(getActivity(), SystemMessageActivity.class);
            startActivity(intent);
        }*/
    }

    public void onMessageReceived(cn.cloudartisan.crius.client.model.Message message) {

        Message msg = MessageUtil.transform(message);
        if (Arrays.asList(ignoredTypes).contains(msg.type)) {
            if ((msg.type.equals("3")) && ("1".equals(ConfigDBManager.getManager().queryValue(message.getSender())))) {
                return;
            }
            // findViewById(R.id.nochatbgimage).setVisibility(View.GONE);
            // appendMessage(msg);
            //showNewMsgLable();

        }
    }

    public void onLeftClick() {
        customDialog.dismiss();
    }

    public void onRightClick() {
        //   customDialog.dismiss();
        // dataList.remove(customDialog.getTag());
        //   adapter.notifyDataSetChanged();
        MessageItemSource source = ((ChatItem) customDialog.getTag()).source;
        String id = source.getId();
        MessageDBManager.getManager().deleteBySender(id);
        showNewMsgLable();
    }

    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
        customDialog.setTag(newList.get(index));
        customDialog.show();
        return true;
    }

    public void onNetworkChanged(NetworkInfo info) {
        if (info != null) {
            findViewById(R.id.networkWarnPanel).setVisibility(View.GONE);
            return;
        }
        findViewById(R.id.networkWarnPanel).setVisibility(View.VISIBLE);
    }


    public void onDetach() {
        //super.onDetach();getActivity().unregisterReceiver(chatBroadcastReceiver);
    }

    public void onResume() {
        super.onResume();
        showNewMsgLable();
        getActivity().stopService(new Intent(getActivity(), MessageNotifyService.class));
    }

    @Override
    public Map getRequestParams(String code) {
        Map map = new HashMap();
        map.put("channel_id", 2);


        return map;
    }

    @Override
    public void onFailed(Exception p1) {
        Toast.makeText(getContext(), p1.getMessage(), Toast.LENGTH_LONG);
    }

    @Override
    public void onRequest() {

    }

    private void loadAds(JSONObject json) {
        JSONArray list = json.getJSONArray("data");
        this.ads.clear();
        for (Object obj : list
                ) {
            ADInfo info = new ADInfo();
            JSONObject object = (JSONObject) obj;
            info.setImage((String) object.get("Logo1"));
            info.setLink((String) object.get("Link"));
            info.setTitle((String) object.get("Title"));
            ads.add(info);
        }
        this.imageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(Object data, List list, Page page, String code, String url) {
        JSONObject json = (JSONObject) data;
        hideProgressDialog();
        if (json!=null && json.getBoolean("success")!=null && json.getBoolean("success")) {
            if (ADLOADER.equals(code)) {//广告图片加载
                loadAds(json);
            } else {
                loadNews(json);
            }

        } else {
            String str = json.getString("message");
            Toast.makeText(this.getContext(), str, Toast.LENGTH_LONG);
        }
    }

    public void loadNews(JSONObject json) {
        JSONArray list = json.getJSONArray("data");
        // this.newList.clear();
        for (Object obj : list
                ) {
            LiveInfo info = new LiveInfo();
            JSONObject object = (JSONObject) obj;
            info.setTeam1((String) object.get("Team1"));
            info.setLink((String) object.get("Link"));
            info.setTeam2((String) object.get("Team2"));
            info.setLogo1((String) object.get("Logo1"));
            info.setLogo2((String) object.get("Logo2"));

            //info.setComments((Integer) object.get("Team2"));
            //info.setImgThumbs((String)object.get("imgThumbs"));
            info.setTime((String) object.get("Time"));
            newList.add(info);
        }
        this.newsAdapter.notifyDataSetChanged();
        //newsListView.showMoreComplete((Boolean)json.get("hasMore"));
        newsListView.showMoreComplete(true);
    }

    private int currentPage = 0;

    @Override
    public void onShowNextPage() {
        currentPage = (currentPage + 1);
        requester.execute(null, new TypeReference<DashBoardFragment>() {

        }.getType(), URLConstant.NEWS_INDEX, "newList");
    }


}
