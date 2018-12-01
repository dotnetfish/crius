
package cn.cloudartisan.crius.component;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.bean.Message;
import cn.cloudartisan.crius.listener.OnUploadProgressListener;
import cn.cloudartisan.crius.ui.chat.FileViewerActivity;
import cn.cloudartisan.crius.util.FileTypeIconBuilder;
import cn.cloudartisan.crius.util.FileUtil;

public class ChatFileView extends RelativeLayout implements View.OnClickListener,
        OnUploadProgressListener {
    Context _context;
    TextView fileName;
    TextView fileSize;
    ImageView icon;
    Message message;
    ProgressBar progressBar;
    
    public ChatFileView(Context context, AttributeSet attrs) {
        super(context, attrs, 0x0);
        _context = context;
    }
    
    public void onFinishInflate() {
        super.onFinishInflate();
        fileName = (TextView)findViewById(R.id.fileName);
        fileSize = (TextView)findViewById(R.id.fileSize);
        progressBar = (ProgressBar)findViewById(R.id.fileProgressBar);
        icon = (ImageView)findViewById(R.id.fileIcon);
    }
    
    public void initView(Message message) {
        this.message = message;
        JSONObject json = JSON.parseObject(message.content);
        fileSize.setText(_context.getString(R.string.label_file_size, new Object[] {FileUtil.getSizeName(json.getLong("size").longValue())}));
        fileName.setText(json.getString("name"));
        icon.setImageResource(FileTypeIconBuilder.create(_context).getFileIcon(json.getString("name"), false));
    }
    
    public void onClick(View v) {
        if(!message.status.equals("-2")) {
            if(message.status.equals("-3")) {
                return;
            }
            Intent intent = new Intent(_context, FileViewerActivity.class);
            intent.putExtra("message", message);
            _context.startActivity(intent);
        }
    }
    
    public void onProgress(float progress) {
        progressBar.setVisibility(VISIBLE);
        progressBar.setProgress((int)progress);
        if(progress >= 100.0f) {
            progressBar.setVisibility(View.GONE);
        }
    }
}
