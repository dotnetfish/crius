package cn.cloudartisan.crius.ui.trend;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.beyondbit.sao.client.service.bua.access.UserService;
import com.karics.library.zxing.android.CaptureActivity;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.app.Module;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.*;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.db.ConfigDBManager;
import cn.cloudartisan.crius.db.MessageDBManager;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.service.MessageNotifyService;
import cn.cloudartisan.crius.service.adapter.Adapter;
import cn.cloudartisan.crius.service.adapter.ServiceAdapterFactory;
import cn.cloudartisan.crius.ui.base.CIMMonitorFragment;
import cn.cloudartisan.crius.ui.dashboard.DashBoardFragment;
import cn.cloudartisan.crius.util.AppTools;
import cn.cloudartisan.crius.util.IntentFactory;
import cn.cloudartisan.crius.util.MessageUtil;
import cn.cloudartisan.crius.widget.CustomDialog;

//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * Created by kenqu on 2016/1/30.
 */
public class TrendDashboard extends CIMMonitorFragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,
        CustomDialog.OnOperationListener, HttpAPIResponser {
        public static String[] ignoredTypes = new String[]{"2", "101", "103", "104", "106", "107"};

    List<Module> modules = new ArrayList<>();
    CustomDialog customDialog;
    List<View> lineViews = new ArrayList<>();
    List<View> gridItems = new ArrayList<>();
    List<Module> funs = new ArrayList<>();
    HttpAPIRequester request;
    User self;
    private IconGridAdapter gridViewAdapter;
    private IconFunGridAdapter funGridAdapter;
    HttpAPIRequester requester;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // request = new HttpAPIRequester(this);
        self = Global.getCurrentUser();
        this.requester=new HttpAPIRequester(this);
        this.gridViewAdapter = new IconGridAdapter(getContext());
        this.funGridAdapter = new IconFunGridAdapter(getContext());
        Module module = new Module();
        module.setCode("quotation");
        module.setIcon("http://www.cloudartisan.cn/images/templates/category/32.png");
        module.setLink("http://fengdengjie.com/price/index.php");
        //module.setLink(URLConstant.API_URL);
        module.setShowType("WebView");
        module.setDisplayName("市场行情");
        module.setSort("0");
        module.setvAlign("0");
        modules.add(module);
        Module product = new Module();
        product.setCode("quotation");
        product.setIcon("");
        product.setLink(ProductListActivity.class.getName());
        //module.setLink(URLConstant.API_URL);
        product.setShowType("intent");
        product.setDisplayName("库存管理");
        product.setSort("0");
        product.setvAlign("0");
        modules.add(module);
        modules.add(product);

        //showProgressDialog("processing");
        requester.execute(new TypeReference<TrendDashboard>() {}.getType(),
                null,
                URLConstant.APP_GETMODULES,"MODULE","GET");


     }




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_trend_dashboard, container, false);
        GridView funGridView = (GridView) view.findViewById(R.id.fun_grid);
        funGridView.setAdapter(this.funGridAdapter);

        funGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Module module = funs.get(position);
              try{
                        Class clazz = IntentFactory.getIntentClass(module);
                        Intent intent = new Intent(getContext(), clazz);
                        intent.putExtra("module", module.getCode());
                        intent.putExtra("title", module.getDisplayName());
                        intent.putExtra("url",module.getLink());
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


            }
        });
        GridView gridView = (GridView) view.findViewById(R.id.gv_icon);
        gridView.setAdapter(this.gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Module module = modules.get(position);

                try{
                    Class clazz = IntentFactory.getIntentClass(module);
                    Intent intent = new Intent(getContext(), clazz);
                    intent.putExtra("module", module.getCode());
                    intent.putExtra("title", module.getDisplayName());
                    intent.putExtra("url",module.getLink());
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (!AppTools.netWorkAvailable(getActivity())) {
            findViewById(R.id.networkWarnPanel).setVisibility(View.VISIBLE);
        }
        //  getActivity().setContentView(root);
        // loadMessage();
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
        //customDialog.setTag(dataList.get(index));
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
        Map map=new HashMap();
        map.put("siteId",1);
        return map;
    }

    @Override
    public void onFailed(Exception p1) {

    }

    @Override
    public void onRequest() {

    }

    @Override
    public void onSuccess(Object data, List list, Page page, String p4, String code) {
        JSONObject json = (JSONObject) data;
        hideProgressDialog();
        if (json!=null && json.getBoolean("success")) {
            Adapter<Module> adapter=ServiceAdapterFactory.getModuleAdapter();
            JSONArray header = (JSONArray) json.get("data");
            for (Object obj : header) {
                JSONObject targetObj = (JSONObject) obj;
                Module module = adapter.fromJson(targetObj);
                this.funs.add(module);

            }
            this.funGridAdapter.notifyDataSetChanged();
            JSONArray grids = (JSONArray) json.get("data");
            for (Object obj : grids) {
                JSONObject object = (JSONObject) obj;
                Module module = adapter.fromJson(object);

                this.modules.add(module);

            }
            this.gridViewAdapter.notifyDataSetChanged();
        } else {
            String str = json.getString("message");
            Toast.makeText(getContext(), str, Toast.LENGTH_LONG);
        }
    }

    public class IconGridAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater inflater;

        private class GirdTemp {
            ImageView phone_function_pic;
            TextView phone_function_name;
        }

        public IconGridAdapter(Context c) {
            mContext = c;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return modules.size();
        }

        @Override
        public Object getItem(int position) {
            return modules.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                Module module = modules.get(position);
                convertView = inflater.inflate(R.layout.layout_grid_item, null);
                WebImageView image = (WebImageView) convertView.findViewById(R.id.icon);
                //Log.e("module_icon")
                image.load(URLConstant.DOMAIN+module.getIcon());

                TextView text = (TextView) convertView.findViewById(R.id.label_contacts_new_friend);
                text.setText(module.getDisplayName());
                TextView countLable = (TextView) convertView.findViewById(R.id.new_msg_count_label);
                if(module.getMsgCount()<=0){
                    countLable.setVisibility(View.GONE);
                }else{
                    countLable.setText(module.getMsgCount());
                }
            }
            return convertView;
        }

    }

    public class IconFunGridAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater inflater;

        private class GirdTemp {
            ImageView phone_function_pic;
            TextView phone_function_name;
        }

        public IconFunGridAdapter(Context c) {
            mContext = c;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return funs.size();
        }

        @Override
        public Object getItem(int position) {
            return funs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                Module module = funs.get(position);
                convertView = inflater.inflate(R.layout.layout_fun_grid, null);
                WebImageView image = (WebImageView) convertView.findViewById(R.id.icon);
                image.load(URLConstant.DOMAIN+module.getIcon());
                TextView text = (TextView) convertView.findViewById(R.id.label_contacts_new_friend);
                text.setText(module.getDisplayName());
                TextView countLable = (TextView) convertView.findViewById(R.id.new_msg_count_label);
                if(module.getMsgCount()<=0){
                    countLable.setVisibility(View.GONE);
                }else{
                    countLable.setText(module.getMsgCount());
                }


            }
            return convertView;
        }

    }
}
