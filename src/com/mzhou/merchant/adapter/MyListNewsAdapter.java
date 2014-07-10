package com.mzhou.merchant.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.model.NewsBean;
import com.mzhou.merchant.utlis.MyUtlis;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class MyListNewsAdapter extends BaseAdapter {
	private Context context;
	private LinkedList<NewsBean> mnewsList;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader;

	private class ViewHolder {
		public ImageView imageView;
		public TextView content;
		public TextView number;
		public TextView time;
	}

	/**
	 * 所有新闻列表适配器
	 * 
	 * @author user
	 * 
	 */
	public MyListNewsAdapter(Context context, LinkedList<NewsBean> mnewsList) {
		this.context = context;
		this.mnewsList = mnewsList;
		imageLoader = ImageLoader.getInstance();
		 options = new DisplayImageOptions.Builder()
			.showStubImage(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_stub)
			.showImageOnFail(R.drawable.ic_stub)
			.delayBeforeLoading(0)
			.cacheOnDisc()
			.displayer(new FadeInBitmapDisplayer(200))
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build(); 
	}

	private int clickTemp = -1;

	// 标识选择的Item
	public void setSeclection(int position) {
		clickTemp = position;
	}

	@Override
	public int getCount() {
		return mnewsList.size();
	}

	@Override
	public Object getItem(int position) {
		return mnewsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.xianshi_xinwen_item, null);
			holder = new ViewHolder();
			holder.content = (TextView) view.findViewById(R.id.xinwen_title);
			holder.number = (TextView) view.findViewById(R.id.xinwen_number);
			holder.time = (TextView) view.findViewById(R.id.xinwen_time);
			holder.imageView = (ImageView) view.findViewById(R.id.xinwen_image);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.content.setText(mnewsList.get(position).getTitle());
		holder.number.setText(context.getResources().getString(R.string.from)
				+ mnewsList.get(position).getSource());
		holder.time.setText(context.getResources().getString(R.string.time)
				+ MyUtlis.TimeStamp2DateList(
						mnewsList.get(position).getCtime(), context));
		String url = MyUtlis.getHeadPic(mnewsList.get(position).getPic(),
				context);
		imageLoader.displayImage(url, holder.imageView, options);
		if (clickTemp == position) {
			view.setBackgroundResource(R.drawable.grid_item_border);
		} else {
			view.setBackgroundColor(Color.TRANSPARENT);
		}
		return view;
	}

}
