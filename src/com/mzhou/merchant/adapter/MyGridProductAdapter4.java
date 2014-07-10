package com.mzhou.merchant.adapter;

import java.io.File;
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
import com.mzhou.merchant.model.ProductsBean;
import com.mzhou.merchant.utlis.MyUtlis;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 所有产品适配器
 * 
 * @author user
 * 
 */
public class MyGridProductAdapter4 extends BaseAdapter {
	private class ViewHolder {
		TextView text;
		ImageView imageView;
	}

	// 定义Context
	private Context mContext;
	private LinkedList<ProductsBean> mList;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader;

	public MyGridProductAdapter4(Context c, LinkedList<ProductsBean> mList,ImageLoader imageLoader,DisplayImageOptions options) {
		mContext = c;
		this.mList = mList;
		
		 this.imageLoader = imageLoader ;
		this.options = options; 
		
	}

	// 获取图片的个数
	public int getCount() {
		return mList.size();
	}

	// 获取图片在库中的位置
	public Object getItem(int position) {
		return mList.get(position);
	}

	// 获取图片ID
	public long getItemId(int position) {
		return position;
	}

	private int clickTemp = -1;

	// 标识选择的Item
	public void setSeclection(int position) {
		clickTemp = position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;
		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.item_grid4,
					null);
			holder = new ViewHolder();
			holder.text = (TextView) view.findViewById(R.id.text);
			holder.imageView = (ImageView) view.findViewById(R.id.icon);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		String url = MyUtlis.getHeadPic(mList.get(position).getPic(), mContext);
		imageLoader.displayImage(url, holder.imageView, options);

		String timeString = MyUtlis.TimeStamp2DateGrid(mList.get(position)
				.getCtime());
		holder.text.setText(mList.get(position).getBrand() + File.separator
				+ timeString);
		if (clickTemp == position) {
			view.setBackgroundResource(R.drawable.grid_item_border);
		} else {
			view.setBackgroundColor(Color.TRANSPARENT);
		}

		return view;
	}

}
