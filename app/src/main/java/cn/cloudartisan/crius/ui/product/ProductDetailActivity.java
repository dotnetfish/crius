package cn.cloudartisan.crius.ui.product;

import android.content.Intent;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.danikula.videocache.HttpProxyCacheServer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.adapter.CustomCommentListAdapter;
import cn.cloudartisan.crius.app.CriusApplication;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.Comment;
import cn.cloudartisan.crius.bean.Page;
import cn.cloudartisan.crius.bean.ProductInfo;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.client.constant.CIMConstant;
import cn.cloudartisan.crius.component.GroupListPanel;
import cn.cloudartisan.crius.component.SimpleInputPanelView;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.ui.base.CIMMonitorActivity;
import cn.cloudartisan.crius.ui.trend.ArticleDetailedActivity;
import cn.cloudartisan.crius.widget.CustomDialog;

public class ProductDetailActivity  extends CIMMonitorActivity implements CustomDialog.OnOperationListener,   HttpAPIResponser {
    protected CustomCommentListAdapter adapter;
    ProductInfo productInfo;
    private List<Comment> commentList;
    HttpAPIRequester commentRequester;
    CustomDialog customDialog;
    SimpleInputPanelView inputPanelView;
    ListView listView;
    User self;
    Timer timer;
    TextView currentPriceView;
    double currentPrice;
    private List<User> userList;
    private WebView detailView;
    private double buyPrice;

    protected GroupListPanel groupListPanel;

    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
        productInfo = (ProductInfo) getIntent().getSerializableExtra("product");
        commentList = new ArrayList<>();//article.getCommentList();
        adapter = new CustomCommentListAdapter(this, commentList);
        playVideo();
        displayProductInfo();
        startSchedule();
        findViewById(R.id.product_detail_bar).setOnClickListener(this);
        findViewById(R.id.product_detail_bar).setSelected(true);
        findViewById(R.id.join_user_bar).setOnClickListener(this);
       detailView=(WebView) findViewById(R.id.detail_view);
      // Log.e("product","conent:"+productInfo.getContent());
       detailView.loadData(productInfo.getContent(),"text/html","UTF-8");

       TextView btn_buy=(TextView)findViewById(R.id.btn_buy);
       btn_buy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              // Toast.makeText(ProductDetailActivity.this,"Buy item"+getCurrentPrice(),Toast.LENGTH_LONG);
               buyPrice=currentPrice;
               DecimalFormat df = new DecimalFormat("#.00");
               customDialog = new CustomDialog(ProductDetailActivity.this);
               customDialog.setOperationListener(ProductDetailActivity.this);
               customDialog.setTitle(R.string.label_congratulation_buy);
               customDialog.setMessage(String.format(getString(R.string.label_congratulation_buy_desc),productInfo.getTitle(),df.format(buyPrice)));
               customDialog.setButtonsText(getString(R.string.label_congratulation_buy_cancel), getString(R.string.label_buy_go_pay));
               customDialog.show();
           }
       });

    }

    private void startSchedule(){
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1){
                    currentPrice-=0.02;
                    changePrice();
                }
                super.handleMessage(msg);
            }
        };

         timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        };
        timer.schedule(timerTask,100,20);//延时1s，每隔500毫秒执行一次run方法
    }

    private void changePrice() {

    currentPriceView.setText(getCurrentPrice());
    }

    private String getCurrentPrice(){
        DecimalFormat df = new DecimalFormat("#.00");
       return df.format(currentPrice);
    }

    private void playVideo() {
        final VideoView videoView=(VideoView)findViewById(R.id.videoView);
        final Uri uri=    Uri.parse("https://secure.massmotionmedia.com/diorparfums/projects/diorparfums_ultrarouge_tvc_inter/videos/20180725145855_768x432_854_d784a8d6-07bc-401a-9769-b7e81b9d7cb5.mp4");
        HttpProxyCacheServer proxy = CriusApplication.getProxy(this);
        String proxyUrl = proxy.getProxyUrl("https://secure.massmotionmedia.com/diorparfums/projects/diorparfums_ultrarouge_tvc_inter/videos/20180725145855_768x432_854_d784a8d6-07bc-401a-9769-b7e81b9d7cb5.mp4");
        videoView.setVideoPath(proxyUrl);
        // videoView.setVideoURI(uri);
        //设置视频控制器
        videoView.setMediaController(new MediaController(this));

        //播放完成回调
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                //开始播放视频
                videoView.start();
            }
        });
        videoView.start();
    }

    private void displayProductInfo(){
         TextView priceView=(TextView) findViewById(R.id.p_price);
        priceView.setText(String.valueOf(productInfo.getMarket_price()));
        priceView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        TextView titleView=(TextView)findViewById(R.id.p_name);
        titleView.setText(productInfo.getTitle());
        currentPriceView=(TextView)findViewById(R.id.p_c_price);
        currentPriceView.setText(String.valueOf(productInfo.getMarket_price()));
        currentPrice=productInfo.getMarket_price();
    }


    public void onClick(View v) {
       /* if(R.id.button == v.getId()) {
            startActivity(new Intent(this, CreateGroupActivity.class));
        }*/
        if(R.id.product_detail_bar == v.getId()) {
            findViewById(R.id.join_user_bar).setSelected(false);
            findViewById(R.id.product_detail_bar).setSelected(true);
            findViewById(R.id.product_detail_container).setVisibility(View.VISIBLE);
            findViewById(R.id.join_user_list).setVisibility(View.GONE);
           // groupListPanel.toogleMyCreatedGroup();
        }
        if(R.id.join_user_bar == v.getId()) {
            findViewById(R.id.product_detail_bar).setSelected(false);
            findViewById(R.id.join_user_bar).setSelected(true);
            findViewById(R.id.product_detail_container).setVisibility(View.GONE);
            findViewById(R.id.join_user_list).setVisibility(View.VISIBLE);
          //  groupListPanel.toogleMyJoinGroup();
        }

        if(R.id.btn_buy == v.getId()){
            Toast.makeText(this,"Buy item"+getCurrentPrice(),Toast.LENGTH_LONG);
        }
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
        if (index == 0) {
            return;
        }
        Comment comment = (Comment) commentList.get((index - 0x1));
        if (comment.account.equals(self.getAccount())) {
            resetApiParams();
            return;
        }
        String name = JSON.parseObject(comment.content).getString("name");
        apiParams.put("replyAccount", comment.account);
        apiParams.put("replyName", name);
        apiParams.put("type", "1");
        apiParams.put("commentId", comment.gid);
        inputPanelView.setHint(getString(R.string.hint_comment, new Object[]{name}));
        inputPanelView.show();
    }

    public void onSendContent(String content) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", self.getName());
        map.put("content", content);
        map.put("type", apiParams.get("type").toString());
        if ("1".equals(apiParams.get("type"))) {
            map.put("commentId", String.valueOf(apiParams.get("commentId")));
            map.put("replyAccount", apiParams.get("replyAccount").toString());
            map.put("replyName", apiParams.get("replyName").toString());
        }
        apiParams.put("content", JSON.toJSONString(map));
        Comment comment = new Comment();
        comment.account = self.getAccount();
        comment.articleId = apiParams.get("articleId").toString();
        comment.content = apiParams.get("content").toString();
        comment.type = apiParams.get("type").toString();
        comment.timestamp = String.valueOf(System.currentTimeMillis());
        commentList.add(comment);
        adapter.notifyDataSetChanged();
        listView.setSelection(listView.getBottom());
        commentRequester.execute(new TypeReference<ArticleDetailedActivity>(){}.getType(), null, URLConstant.COMMENT_PUBLISH_URL);
        inputPanelView.reset();
    }

    public void onSuccess(Object data, List list, Page page, String code, String url) {
        if (CIMConstant.ReturnCode.CODE_200.equals(code)) {
            resetApiParams();
        }
    }

    public void onFailed(Exception e) {
    }

    public Map getRequestParams(String code) {
      /*  apiParams.put("articleId", article.gid);
        apiParams.put("authorAccount", article.account);
        apiParams.put("name", self.getName());
        apiParams.put("account", self.getAccount());*/
        return apiParams;
    }

    public void onRequest() {
    }

    public void resetApiParams() {
        apiParams.remove("replyAccount");
        apiParams.remove("replyName");
        apiParams.remove("commentId");
        apiParams.put("type", "0");
        inputPanelView.setContent(null);
        inputPanelView.setHint(null);
    }

    public int getConentLayout() {
        return R.layout.activity_slash_buy;
    }

    public void onResume() {
        super.onResume();
        //groupListPanel.loadGroups(findViewById(R.id.join_user_bar).isSelected() ? 0x0 : 0x1);
    }


    public int getActionBarTitle() {
        return R.string.common_detail;
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_icon: {
                customDialog.show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }



    public void onLeftClick() {
        customDialog.dismiss();
    }

    @Override
    public void onRightClick() {
       Intent intent =new Intent(this,OrderItemActivity.class);
       intent.putExtra("price",buyPrice);
       intent.putExtra("product",productInfo);
       startActivity(intent);
    }

    public void onKeyboardShow() {
    }
/*
    public void onMessageReceived(Message message) {
        if ((message.getType().equals("801")) || (message.getType().equals("802"))) {
            JSONObject json = JSON.parseObject(message.getContent());
            String articleId = json.getString("articleId");
            if (articleId.equals(article.gid)) {
                Comment comment = new Comment();
                comment.account = json.getString("account");
                comment.articleId = articleId;
                comment.content = json.getString("content");
                comment.type = json.getString("type");
                comment.timestamp = json.getString("timestamp");
                commentList.add(comment);
                adapter.notifyDataSetChanged();
                listView.setSelection(listView.getBottom());
            }
        }
    }*/
}
