
package cn.cloudartisan.crius.network;

import java.io.File;

//import com.alibaba.sdk.android.oss.storage.OSSFile;
//import com.alibaba.sdk.android.oss.storage.OSSBucket;
//import com.alibaba.sdk.android.oss.callback.SaveCallback;


public class FileUploader {
    
    public static void asyncUpload(String objectKey, File file, FileUploader.OnUploadCallBack callback) {
        asyncUpload("lvxin-files", objectKey, file, callback);
    }
    
    public static void asyncUpload(String bucket, String objectKey, File file) {
        asyncUpload(bucket, objectKey, file, null);
    }
    
    public static void asyncUpload(String bucket, String objectKey, File file, FileUploader.OnUploadCallBack callback) {
       /* try {
            ossFile.setUploadFilePath(file.getAbsolutePath(), "text/plain");
            ossFile.enableUploadCheckMd5sum();
            ossFile.uploadInBackground(new SaveCallback() {
                

                
                public void onSuccess(String key) {
                    if(callback != null) {
                        callback.complete(key, file);
                    }
                    Intent intent = new Intent("com.farsunset.lvxin.UPLOAD_PROGRESS");
                    intent.putExtra("objectKey", key);
                    intent.putExtra("progress", 100.0f);
                    LvxinApplication.getInstance().sendBroadcast(intent);
                }
                
                public void onProgress(String key, int byteCount, int totalSize) {
                    if(callback != null) {
                        callback.onProgress(key, (((float)byteCount / (float)totalSize) * 100.0f));
                    }
                    Intent intent = new Intent("com.farsunset.lvxin.UPLOAD_PROGRESS");
                    intent.putExtra("objectKey", key);
                    intent.putExtra("progress", (((float)byteCount / (float)totalSize) * 100.0f));
                    LvxinApplication.getInstance().sendBroadcast(intent);
                }
                
                public void onFailure(String key, OSSException ossException) {
                    if(callback != null) {
                        callback.failed(ossException, key);
                    }
                    ossException.printStackTrace();
                    Intent intent = new Intent("com.farsunset.lvxin.UPLOAD_PROGRESS");
                    intent.putExtra("objectKey", key);
                    intent.putExtra("progress", -1.0f);
                    LvxinApplication.getInstance().sendBroadcast(intent);
                }
            });
            return;
        } catch(Exception e) {
            e.printStackTrace();
        }*/
    }
    public interface OnUploadCallBack
    {
        void complete(String paramString, File paramFile);

        void failed(Exception paramException, String paramString);

         void onProgress(String paramString, float paramFloat);
    }
}
