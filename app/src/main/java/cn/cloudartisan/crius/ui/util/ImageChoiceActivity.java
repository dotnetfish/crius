package cn.cloudartisan.crius.ui.util;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.adapter.ImageChooseViewAdapter;
import cn.cloudartisan.crius.bean.AlbumItem;
import cn.cloudartisan.crius.component.WebImageView;
import cn.cloudartisan.crius.ui.base.BaseActivity;

import java.io.File;
import java.util.*;

public class ImageChoiceActivity extends BaseActivity implements AdapterView.OnItemClickListener, ImageChooseViewAdapter.OnChechedListener {
    public static String KEY_MAX_COUNT = "KEY_MAX_COUNT";
    public ImageChooseViewAdapter adapter;
    public List<AlbumItem> allFileList = new ArrayList<>();
    private Button button;
    File currentFolder = null;
    public float density;
    public Map<String, Integer> fileCountMap = new HashMap<>();
    public List<AlbumItem> fileList = new ArrayList<>();
    public GridView fileListView;
    ImageChoiceActivity.FolderAdapter folderAdapter;
    public List<File> folderList = new ArrayList<>();
    public Map<String, File> folderMap = new TreeMap<>();
    TextView folderSelectText;
    ListView listView;
    //MediaScannerConnection mConnection;
    int maxCount;
    int selectedFolderIndex = 0;
    public Map<String, String> thumbnailMap = new TreeMap<>();
    
    public void initComponents() {
        setDisplayHomeAsUpEnabled(true);
        fileListView = (GridView)findViewById(R.id.gridView);
        folderSelectText = (TextView)findViewById(R.id.folderSelectText);
        findViewById(R.id.folderSelectPanel).setOnClickListener(this);
        findViewById(R.id.folderSelect).setOnClickListener(this);
        fileListView.setOnItemClickListener(this);
        maxCount = getIntent().getIntExtra(KEY_MAX_COUNT, 1);
        adapter = new ImageChooseViewAdapter(this, fileList, maxCount);
        try {
            fileListView.setAdapter(adapter);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        adapter.notifyDataSetChanged();
        adapter.setOnChechedListener(this);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        density = mDisplayMetrics.density;
        listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        folderAdapter = new ImageChoiceActivity.FolderAdapter();
        listView.setAdapter(folderAdapter);
        searchImageList();
    }
    
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.folderSelect:
            {
                if((listView.getVisibility() == View.GONE) && (folderList.size() > 0x1)) {
                    listView.setVisibility(View.VISIBLE);
                    break;
                }
                listView.setVisibility(View.GONE);
                break;
            }
            case R.id.button:
            {
                Intent intent = new Intent();
                intent.putExtra("files", adapter.getSelectedFiles());
                setResult(-0x1, intent);
                finish();
                break;
            }
            case R.id.parentDirButton:
            {
                break;
            }
        }
    }

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
        if(paramAdapterView instanceof GridView) {
            AlbumItem var6 = this.adapter.getItem(paramInt);
            Intent var7 = new Intent();
            var7.setData(Uri.fromFile(var6.file));
            var7.putExtra("file", var6.file);
            this.setResult(-1, var7);
            this.finish();
        } else {
            this.listView.setVisibility(View.GONE);
            if(paramInt == 0 && this.currentFolder != null) {
                this.onFolderChanaged(null);
                this.currentFolder = null;
                this.selectedFolderIndex = 0;
            }

            if(paramInt > 0 && !this.folderList.get(paramInt).equals(this.currentFolder)) {
                this.currentFolder =this.folderList.get(paramInt);
                this.onFolderChanaged(this.currentFolder);
                this.selectedFolderIndex = paramInt;

            }
        }
    }
    
    public void searchImageList() {
        scanImages();
        adapter.notifyDataSetChanged();
    }
    
    public int getConentLayout() {
        return R.layout.activity_image_choice;
    }
    
    public int getActionBarTitle() {
        return R.string.common_album;
    }
    
    public boolean onCreateOptionsMenu(Menu menu) {
        if(maxCount > 0x1) {
            getMenuInflater().inflate(R.menu.single_button, menu);
            button = (Button)menu.findItem(R.id.menu_button).getActionView().findViewById(R.id.button);
            button.setOnClickListener(this);
            button.setText(R.string.common_confirm);
            button.setEnabled(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void scanImages()
    {
        this.folderList.add(null);
        if(Build.VERSION.SDK_INT > 18) {
            this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE",
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        } else {
            this.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED",
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }

        this.getThumbnail();
        Uri var1 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor var4 = this.getContentResolver().query(var1, null,
                "mime_type=? or mime_type=?",
                new String[]{"image/jpeg", "image/png"}, "date_added desc");
        if(var4 != null) {
            while(var4.moveToNext()) {
                AlbumItem var3 = new AlbumItem();
                File var2 = new File(var4.getString(var4.getColumnIndex("_data")));
                var3.file = var2;
                var3.imageId = var4.getString(var4.getColumnIndex("_id"));
                var3.thumbnail = this.thumbnailMap.get(var3.imageId);
                var3.time = var4.getLong(var4.getColumnIndex("date_added"));
                this.fileList.add(var3);
                String var5 = var2.getParentFile().getAbsolutePath();
                if(!this.folderMap.containsKey(var5)) {
                    this.folderMap.put(var5, var2);
                    this.fileCountMap.put(var5, 1);
                } else {
                    this.fileCountMap.put(var5,
                           this.fileCountMap.get(var5)+ 1);
                }

                if(!this.folderList.contains(var2.getParentFile())) {
                    this.folderList.add(var2.getParentFile());
                }
            }

            var4.close();
        }

        this.allFileList.addAll(this.fileList);
        if(this.fileList.size() > 0) {
            this.folderAdapter.notifyDataSetChanged();
        }

    }

    private void getThumbnail()
    {
        Uri var1 = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        Cursor var4 = this.getContentResolver().query(var1, null, null, null, null);
        if(var4 != null) {
            var4.moveToFirst();
            if(var4.moveToNext()) {
                String var2 = var4.getString(var4.getColumnIndex("image_id"));
                String var3 = var4.getString(var4.getColumnIndex("_data"));
                this.thumbnailMap.put(var2, var3);
            }
            var4.close();
        }


    }
    
    public void onChecked(CompoundButton compoundbutton, File file, boolean isChecked) {
        if(isChecked) {
            if(adapter.getSelectedFiles().size() > maxCount) {
                compoundbutton.setChecked(false);
                adapter.getSelectedFiles().remove(file);
            }
        } else {
            adapter.getSelectedFiles().remove(file);
        }
        if(adapter.getSelectedFiles().size() > 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)((density * 65.0f) + 0.5f), (int)((density * 35.0f) + 0.5f));
            button.setLayoutParams(params);
            button.setEnabled(true);
            button.setText(getString(R.string.label_album_selected_count, "/" + maxCount));
            return;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)((density * 50.0f) + 0.5f), (int)((density * 35.0f) + 0.5f));
        button.setLayoutParams(params);
        button.setEnabled(false);
        button.setText(R.string.common_confirm);
    }
    
    public void onFolderChanaged(File file) {
        this.fileList.clear();
        if(file == null) {
            this.folderSelectText.setText(R.string.label_ablbum_all);
            this.fileList.addAll(this.allFileList);
        } else {
            for(AlbumItem item:this.allFileList){
                if(item.file.getParentFile().equals(file)) {
                    this.fileList.add(item);
                }
            }
            this.folderSelectText.setText(file.getName());
        }

        this.adapter.notifyDataSetChanged();
        this.folderAdapter.notifyDataSetChanged();
    }
    
    public void onBackPressed() {
        if(listView.getVisibility() == View.VISIBLE) {
            listView.setVisibility(View.GONE);
            return;
        }
        allFileList = null;
        fileList = null;
        folderList = null;
        thumbnailMap = null;
        folderMap = null;
        fileCountMap = null;
        finish();
    }
    public class FolderAdapter extends BaseAdapter {
        public int getCount() {
            return ImageChoiceActivity.this.folderList.size();
        }

        public File getItem(int var1) {
            return ImageChoiceActivity.this.folderList.get(var1);
        }

        public long getItemId(int var1) {
            return 0L;
        }

        public View getView(int var1, View var2, ViewGroup var3) {
            File var4 = this.getItem(var1);
            View var9 = var2;
            if(var2 == null) {
                var9 = LayoutInflater.from(ImageChoiceActivity.this).inflate(R.layout.item_change_folder, (ViewGroup)null);
            }

            var9.setTag(var4);
            TextView var8 = (TextView)var9.findViewById(R.id.name);
            TextView var5 = (TextView)var9.findViewById(R.id.count);
            WebImageView var6 = (WebImageView)var9.findViewById(R.id.image);
            if(var4 == null) {
                var8.setText(R.string.label_ablbum_all);
                var5.setText(ImageChoiceActivity.this.getString(R.string.label_ablbum_count, new Object[]{Integer.valueOf(ImageChoiceActivity.this.allFileList.size())}));
                var6.load("file://" + (ImageChoiceActivity.this.allFileList.get(0)).file.getAbsolutePath());
            } else {
                String var7 = var4.getAbsolutePath();
                var6.load("file://" + (ImageChoiceActivity.this.folderMap.get(var7)).getAbsolutePath());
                var8.setText(var4.getName());
                var5.setText(ImageChoiceActivity.this.getString(R.string.label_ablbum_count, new Object[]{ImageChoiceActivity.this.fileCountMap.get(var7)}));
            }

            if((var4 != null || ImageChoiceActivity.this.currentFolder != null) && (var4 == null || !var4.equals(ImageChoiceActivity.this.currentFolder))) {
                var9.findViewById(R.id.mark).setVisibility(View.GONE);
                return var9;
            } else {
                var9.findViewById(R.id.mark).setVisibility(View.VISIBLE);
                return var9;
            }
        }
    }
}
