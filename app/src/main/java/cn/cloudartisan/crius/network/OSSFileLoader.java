
package cn.cloudartisan.crius.network;

import java.io.File;

import android.content.Context;
//import com.alibaba.sdk.android.oss.storage.OSSData;
//import com.alibaba.sdk.android.oss.storage.OSSBucket;
//import com.alibaba.sdk.android.oss.callback.GetBytesCallback;
import cn.cloudartisan.crius.app.Constant;

public class OSSFileLoader {
    Context context;
    private static OSSFileLoader loader;

    private OSSFileLoader() {
    }

    public static synchronized OSSFileLoader getFileLoader(Context c) {
        if (loader == null) {
            loader = new OSSFileLoader();
            loader.context = c;
        }
        return loader;
    }

    public void loadImage(String bucket, String key, OSSFileLoader.FileLoadedCallback fileCallback) {
        beginLoad(bucket, key, Constant.CACHE_DIR_IMAGE, fileCallback);
    }

    public void loadVoiceFile(String bucket, String key, OSSFileLoader.FileLoadedCallback fileCallback) {
        beginLoad(bucket, key, Constant.CACHE_DIR_VOICE, fileCallback);
    }

    private void beginLoad(String bucket, String key, String dir, OSSFileLoader.FileLoadedCallback fileCallback) {
        File targetFile = new File(dir, key);
        if (targetFile.exists()) {
            if (fileCallback != null) {
                fileCallback.fileLoaded(targetFile, key);
            }
            return;
        }
        /*ossData.getInBackground(new GetBytesCallback() {

            public void onSuccess(String objectKey, byte[] data) {
                try {
                    FileUtils.writeByteArrayToFile(targetFile, data);
                    if (fileCallback != null) {
                        fileCallback.fileLoaded(targetFile, objectKey);
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (fileCallback != null) {
                        fileCallback.fileLoaded(0x0, objectKey);
                    }

                }
            }

            public void onProgress(String objectKey, int byteWirtten, int totalSize) {
            }

            public void onFailure(String objectKey, OSSException ossException) {
                if (fileCallback != null) {
                    fileCallback.fileLoaded(0x0, objectKey);
                }
                ossException.printStackTrace();
            }
        });*/
    }

    public interface FileLoadedCallback
    {
        void fileLoaded(File paramFile, String paramString);
    }
}
