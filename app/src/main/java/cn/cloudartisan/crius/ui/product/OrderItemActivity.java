package cn.cloudartisan.crius.ui.product;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

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

    @Override
    public void initComponents() {
        /*setDisplayHomeAsUpEnabled(true);
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
        });*/

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
