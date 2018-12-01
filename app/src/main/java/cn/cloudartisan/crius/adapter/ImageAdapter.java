/*
 * Copyright (C) 2011 Patrik �kerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.cloudartisan.crius.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import cn.cloudartisan.crius.R;
import cn.cloudartisan.crius.app.URLConstant;
import cn.cloudartisan.crius.bean.ADInfo;
import cn.cloudartisan.crius.component.BaseWebActivity;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

	private LayoutInflater mInflater;

	private static final int[] ids = {
			R.drawable.beside_personal_page_default_cover,
			R.drawable.circle_banner_normal,
			R.drawable.nearby_lbs_banner_bg,
			R.drawable.nearby_lbs_banner_pull_bg,
			R.drawable.beside_personal_page_default_cover,
			R.drawable.honeycomb,
			R.drawable.icecream
	};

	private static final String[] urls={
			URLConstant.DOMAIN+"/templates/sport/images/banner_1.png",
			URLConstant.DOMAIN+"/templates/sport/images/banner_3.png",
			URLConstant.DOMAIN+"/templates/sport/images/banner_2.png"
	};
	private List<ADInfo> adInfos;

	public ImageAdapter(Context context,List<ADInfo> data) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.adInfos=data;
		ADInfo info=new ADInfo();
		info.setLink(URLConstant.DOMAIN+"/news/show-141.html");
		info.setTitle("周小川：汇率已趋于稳定，股市调整已大致到位");
		info.setImage(URLConstant.DOMAIN+"/templates/sport/images/banner_2.png");
		adInfos.add(info);
	}

	@Override
	public int getCount() {
		return adInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position,  View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.image_item, null);
		}
		final View  parentView=convertView;
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
		builder.cacheInMemory(false);
		builder.cacheOnDisk(true);
		builder.bitmapConfig(Bitmap.Config.RGB_565);
		builder.build();
		ImageView imageView=(ImageView) convertView.findViewById(R.id.imgView);
		ImageLoader.getInstance().displayImage(adInfos.get(position).getImage(), imageView, new ImageLoadingListener() {
			@Override
			public void onLoadingStarted(String s, View view) {

			}

			@Override
			public void onLoadingFailed(String s, View view, FailReason failReason) {

			}

			@Override
			public void onLoadingComplete(String s, View view, Bitmap bitmap) {

			}

			@Override
			public void onLoadingCancelled(String s, View view) {

			}
		});
		//((ImageView) convertView.findViewById(R.id.imgView)).setImageResource(ids[position]);
imageView.setOnClickListener(new View.OnClickListener() {
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(parentView.getContext(), BaseWebActivity.class);
		intent.putExtra("url", adInfos.get(position).getLink());
		intent.putExtra("title",adInfos.get(position).getTitle());
		parentView.getContext().startActivity(intent);
	}
});
		return convertView;
	}

}
