
package cn.cloudartisan.crius.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.TypeReference;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.cloudartisan.crius.app.CriusApplication;
import cn.cloudartisan.crius.client.constant.CIMConstant;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.Constant;
import cn.cloudartisan.crius.app.Global;
import cn.cloudartisan.crius.bean.Page;
import cn.cloudartisan.crius.bean.User;
import cn.cloudartisan.crius.network.FileUploader;
import cn.cloudartisan.crius.network.HttpAPIRequester;
import cn.cloudartisan.crius.network.HttpAPIResponser;
import cn.cloudartisan.crius.ui.base.BaseActivity;
import cn.cloudartisan.crius.ui.util.ImageChoiceActivity;
import cn.cloudartisan.crius.util.AppTools;
import cn.cloudartisan.crius.util.StringUtils;
import cn.cloudartisan.crius.widget.GenderChooseDialog;

import java.io.File;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends BaseActivity implements HttpAPIResponser, View.OnClickListener, GenderChooseDialog.OnChooseListener {
    EditText account;
    File file;
    GenderChooseDialog genderDialog;
    ImageView icon;
    EditText name;
    User newUser;
    EditText password;
    HttpAPIRequester requester;
    
    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
        account = (EditText)findViewById(R.id.account);
        name = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        icon = (ImageView)findViewById(R.id.icon);
        findViewById(R.id.gender_item).setOnClickListener(this);
        findViewById(R.id.iconSwicth).setOnClickListener(this);
        genderDialog = new GenderChooseDialog(this, this);
        requester = new HttpAPIRequester(this);
        newUser=new User();
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
            {
                doRegister();
                return;
            }
            case R.id.gender_item:
            {
                genderDialog.show();
                return;
            }
            case R.id.iconSwicth:
            {
                Intent intentFromGallery = new Intent();
                intentFromGallery.setClass(this, ImageChoiceActivity.class);
                startActivityForResult(intentFromGallery, 0x9);
                break;
            }
        }
    }
    
    private void doRegister() {
        newUser.setAccount(account.getText().toString());
        if(StringUtils.isEmpty(name.getText())) {
            showToast(R.string.tip_required_name);
            return;
        }
        if(StringUtils.isEmpty(((TextView)findViewById(R.id.gender)).getText())) {
            showToast(R.string.tip_required_gender);
            return;
        }
        if(StringUtils.isEmpty(account.getText())) {
            showToast(R.string.tip_required_account);
            return;
        }
        newUser.setName(name.getText().toString());//name = ;
        if(StringUtils.isEmpty(password.getText())) {
            showToast(R.string.tip_required_password);
            return;
        }
        if(file == null) {
            showToast(R.string.tip_required_icon);
            return;
        }
        newUser.setPassword(password.getText().toString()); //= ;
        requester.execute(new TypeReference<RegisterActivity>() {
          }.getType(), null, "http://developer.yljr888.com/user_register.api");
    }
private  Uri outputUri=null;
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == -1 && resultCode == 9) {
           outputUri= Uri.fromFile(new File(Constant.CACHE_DIR_IMAGE, StringUtils.getUUID() + ".jpg"));
            AppTools.startPhotoZoom(this, data.getData());
        }

        if(resultCode == -1 && requestCode == 11 && data != null) {
            try {
               // this.file = new File(data.getData().getPath());
                this.file=new File(outputUri.getPath());
                this.icon.setImageBitmap(ImageLoader.getInstance().loadImageSync(Uri.fromFile(this.file).toString()));
               // ImageLoader.getInstance().displayImage(Uri.fromFile(this.file).toString(), this.icon);
            } catch (Exception var4) {
                var4.printStackTrace();
                return;
            }
        }
    }
    
    public void onSuccess(Object data, List datalist, Page page, String resultCode, String url) {
        hideProgressDialog();
        if("101".equals(resultCode)) {
            showToast(getString(R.string.tip_account_not_unique, new Object[] {newUser.getAccount()}));
        }
        if(CIMConstant.ReturnCode.CODE_200.equals(resultCode)) {
            showToast(R.string.tip_register_complete);
            Global.setCurrentUser((User)data);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            CriusApplication.finishTarget(WelcomeActivity.class);
            CriusApplication.finishTarget(LoginActivity.class);
            finish();
            if(file != null) {
                FileUploader.asyncUpload("lvxin-user-icon", newUser.getAccount(), file,
                        new FileUploader.OnUploadCallBack() {
                    

                    public void complete(String key, File file) {
                        try {
                          //  FileUtils.moveFile(file, ("/".append(new Md5FileNameGenerator().generate(FileURLBuilder.getUserIconUrl(newUser.account))).toString()));
                            return;
                        } catch(Exception localException1) {
                        }
                    }
                    
                    public void failed(Exception e, String key) {
                    }
                    
                    public void onProgress(String key, float progress) {
                    }
                });
            }
        }
    }
    
    public void onFailed(Exception e) {
        hideProgressDialog();
    }
    
    public Map getRequestParams(String code) {
        apiParams.put("name", newUser.getName());
        apiParams.put("password", newUser.getPassword());
        apiParams.put("account", newUser.getAccount());
        return apiParams;
    }
    
    public void onRequest() {
        showProgressDialog(getString(R.string.tip_loading, new Object[] {getString(R.string.label_register)}));
    }
    
    public int getConentLayout() {
        return R.layout.activity_register;
    }
    
    public int getActionBarTitle() {
        return R.string.label_register;
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.single_button, menu);
        Button button = (Button)menu.findItem(R.id.menu_button).getActionView().findViewById(R.id.button);
        button.setOnClickListener(this);
        button.setText(R.string.label_register);
        return super.onCreateOptionsMenu(menu);
    }
    
    public void onGenderChecked(String gender) {
        if("1".equals(gender)) {
            apiParams.put("gender", gender);
            ((TextView)findViewById(R.id.gender)).setText(R.string.common_man);
        }
        if("0".equals(gender)) {
            apiParams.put("gender", gender);
            ((TextView)findViewById(R.id.gender)).setText(R.string.common_female);
        }
    }
}
