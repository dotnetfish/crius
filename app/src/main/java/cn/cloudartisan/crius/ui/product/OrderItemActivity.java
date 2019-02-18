package cn.cloudartisan.crius.ui.product;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.adapter.CustomCommentListAdapter;
import cn.cloudartisan.crius.bean.Page;
import cn.cloudartisan.crius.bean.ProductInfo;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.ui.base.CIMMonitorActivity;
import cn.cloudartisan.crius.widget.CustomDialog;

public class OrderItemActivity extends CIMMonitorActivity implements CustomDialog.OnOperationListener, HttpAPIResponser {

    ProductInfo productInfo;
    double buy_price;
    @Override
    public void initComponents() {
        productInfo = (ProductInfo) getIntent().getSerializableExtra("product");
        buy_price=(double)getIntent().getSerializableExtra("price");
        fillForm();
        TextView btn_buy=(TextView)findViewById(R.id.btn_submit);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
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
        CustomDialog customDialog = new CustomDialog(this);
        customDialog.setOperationListener(new CustomDialog.OnOperationListener() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick() {
                final Runnable payRunnable = new Runnable() {

                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(OrderItemActivity.this);
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Log.i("msp", result.toString());

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();

            }
        });
        customDialog.setTitle(R.string.label_go_pay);
        DecimalFormat df=new DecimalFormat("#.00");
        customDialog.setMessage(getString(R.string.label_pay_desc));
        customDialog.setButtonsText(getString(R.string.label_micropay), getString(R.string.label_alipay));
        customDialog.show();
    }

    private void requestAddressForm(){

    }
    @Override
    public Map getRequestParams(String code) {
        return null;
    }

    @Override
    public void onFailed(Exception p1) {

    }

    @Override
    public void onRequest() {

    }

    @Override
    public void onSuccess(Object p1, List p2, Page p3, String p4, String p5) {

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
