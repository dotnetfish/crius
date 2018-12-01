
package cn.cloudartisan.crius.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.bean.OSSImage;
import cn.cloudartisan.crius.util.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ProgressbarPhotoView extends RelativeLayout implements PhotoViewAttacher.OnPhotoTapListener {
    public Context _context;
    ProgressbarPhotoView.OnPhotoViewClickListener onPhotoViewClickListener;
    private DisplayImageOptions options;
    WebPhotoView photoView;
    ProgressBar progressbar;
    boolean isDisplayed = false;
    
    public ProgressbarPhotoView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        _context = paramContext;
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.cacheInMemory(false);
        builder.cacheOnDisk(true);
        builder.bitmapConfig(Bitmap.Config.RGB_565);
        options = builder.build();
    }
    
    public void setOnPhotoViewClickListener(ProgressbarPhotoView.OnPhotoViewClickListener onPhotoViewClickListener) {
        this.onPhotoViewClickListener = onPhotoViewClickListener;
    }
    
    public void onFinishInflate() {
        photoView = (WebPhotoView)findViewById(R.id.image);
        progressbar = (ProgressBar)findViewById(R.id.progress);
        photoView.setOnPhotoTapListener(this);
        super.onFinishInflate();
    }
    
    public void display(OSSImage image) {
        if(isDisplayed) {
            return;
        }
        if((image.thumbnail != null) && (!image.thumbnail.equals(image.image))) {
            ImageLoader.getInstance().displayImage(StringUtils.getOSSFileURI(image.bucket, image.thumbnail), photoView);
        }
        ImageLoader.getInstance().loadImage(StringUtils.getOSSFileURI(image.bucket, image.image), options, new SimpleImageLoadingListener() {
            

            
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                isDisplayed = true;
                progressbar.setVisibility(View.GONE);
                if(loadedImage != null) {
                    photoView.setImageBitmap(loadedImage);
                }
            }
            
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressbar.setVisibility(View.GONE);
            }
        });
    }
    
    public void display(String imageUrl) {
        ImageLoader.getInstance().displayImage(imageUrl, photoView, options,
                new SimpleImageLoadingListener() {

            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressbar.setVisibility(View.GONE);
            }
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressbar.setVisibility(View.GONE);
            }
        });
    }
    
    public void onPhotoTap(View view, float arg1, float arg2) {
        onPhotoViewClickListener.onPhotoViewClicked(view);
    }
    public  interface OnPhotoViewClickListener
    {
        void onPhotoViewClicked(View paramView);
    }
}
