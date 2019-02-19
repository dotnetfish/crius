package cn.cloudartisan.crius.ui.product;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.TypeReference;
import com.alipay.sdk.app.PayTask;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.Page;
import cn.cloudartisan.crius.bean.ProductInfo;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.ui.base.CIMMonitorActivity;
import cn.cloudartisan.crius.widget.CustomDialog;

public class OrderItemActivity extends CIMMonitorActivity implements CustomDialog.OnOperationListener, HttpAPIResponser {

    ProductInfo productInfo;
    private final static int SDK_PAY_FLAG_ALI=1000709878;
    private final static int SDK_PAY_FLAG_MICROMSG=1000909872;
    private final static String ADDRESS_REQUEST="adress_request";
    private final static String ALIPAY_AUTH_REQUEST="alipay_auth_request";
    private final static String MICRO_AUTH_REQUEST="micro_msg_auth_request";


    double buy_price;
    private String orderId;
     HttpAPIRequester apiRequester;
    @SuppressLint("HandlerLeak")
    private Handler mAlipayHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {

            if(msg.what==SDK_PAY_FLAG_ALI) {
                @SuppressWarnings("unchecked")
                AliPayResult payResult = new AliPayResult((Map<String, String>) msg.obj);
                /**
                 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    // showAlert(AlipayActivity.this, getString(R.string.pay_success) + payResult);
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    //showAlert(AlipayActivity.this, getString(R.string.pay_failed) + payResult);
                }

            }
        };
    };
    @Override
    public void initComponents() {
        productInfo = (ProductInfo) getIntent().getSerializableExtra("product");
        buy_price=(double)getIntent().getSerializableExtra("price");
        orderId=getIntent().getStringExtra("order_id");
        fillForm();
        TextView btn_buy=(TextView)findViewById(R.id.btn_submit);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
        apiRequester=new HttpAPIRequester(this);
        requestAddressForm();
        /*setDisplayHomeAsUpEnabled(true);

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
        });*/

    }

    private  void fillForm(){
        TextView productNumView=(TextView)findViewById(R.id.productNumView);
        productNumView.setText("1");
        TextView priceView=(TextView)findViewById(R.id.productPriceView);
        DecimalFormat df=new DecimalFormat("#.00");
        priceView.setText("¥"+df.format(this.buy_price));
        TextView priceSumView=(TextView)findViewById(R.id.productPriceSumView);
        priceSumView.setText("¥"+df.format(this.buy_price));
    }

    private void submitForm(){
        String order_submit=URLConstant.parseUrl("order","submit");
        apiRequester.execute(new TypeReference<OrderItemActivity>(){}.getType(),null,order_submit,
                "order_submit",HttpAPIRequester.METHOD_GET);


    }

    private void requestAddressForm(){
        String address_url=URLConstant.parseUrl("delivery","address");
        apiRequester.execute(new TypeReference<OrderItemActivity>(){}.getType(),null,address_url,ADDRESS_REQUEST,HttpAPIRequester.METHOD_GET);
    }
    @Override
    public Map getRequestParams(String code) {
        HashMap map = new HashMap();

        switch(code){
            case ADDRESS_REQUEST:
                map.put("uid","test");
                break;
            case ALIPAY_AUTH_REQUEST:
                map.put("uid","test");
                map.put("orderId",orderId);

            case "order_submit":
                map.put("order_id",orderId);
                map.put("address","sdfas");
                map.put("name","dfa sdf");
                map.put("phone","12314234");
                break;
                default:
                    break;

        }
        return map;
    }

    @Override
    public void onFailed(Exception p1) {

        hideProgressDialog();
        final CustomDialog customDialog = new CustomDialog(this);
        customDialog.setOperationListener(new CustomDialog.OnOperationListener() {
            @Override
            public void onLeftClick() {
                customDialog.hide();
            }

            @Override
            public void onRightClick() {

                customDialog.hide();
            }
        });
        customDialog.setTitle(R.string.label_go_pay);

        customDialog.setMessage(p1.getMessage());
        customDialog.setButtonsText(getString(R.string.label_micropay), getString(R.string.label_alipay));
        customDialog.show();

    }

    @Override
    public void onRequest() {
        showProgressDialog("加载中...");
    }

    @Override
    public void onSuccess(Object data, List p2, Page p3, String code, String url) {
        hideProgressDialog();
        switch (code){
            case "alipay":
                    alipay_pay("");
                break;
            case "micro_msg":
                microMsg_pay("");
            case "order_submit" :
                showPayWay(data);
                default:
                    break;
        }



    }
    private void showPayWay(Object data){
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setOperationListener(new CustomDialog.OnOperationListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                String url_pay_auth= URLConstant.getUrl("payment","alipay/sign");

                apiRequester.execute(new TypeReference<OrderItemActivity>(){}.getType(),null,
                        url_pay_auth,ALIPAY_AUTH_REQUEST,HttpAPIRequester.METHOD_GET);

            }
        });
        customDialog.setTitle(R.string.label_go_pay);
        DecimalFormat df=new DecimalFormat("#.00");
        customDialog.setMessage(String.format(getString(R.string.label_pay_desc),orderId));
        customDialog.setButtonsText(getString(R.string.label_micropay), getString(R.string.label_alipay));
        customDialog.show();
    }

    private void microMsg_pay(final String orderInfo){

    }

    private void alipay_pay(final String orderInfo){
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(OrderItemActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG_ALI;
                msg.obj = result;
                mAlipayHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public int getActionBarTitle() {
        return 0;
    }

    @Override
    public int getConentLayout() {
        return R.layout.activity_order_item;
    }



    @Override
    public void onClick(View view) {

    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }
}
