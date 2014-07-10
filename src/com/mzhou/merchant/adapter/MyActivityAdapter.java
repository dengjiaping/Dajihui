package com.mzhou.merchant.adapter;

import java.util.LinkedList;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.model.ActivityBean;
import com.mzhou.merchant.myview.CustomTextView;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.ToolsUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyActivityAdapter extends BaseAdapter {

	private LinkedList<ActivityBean> mList;
	private Context context;

	private class ViewHolder {
		private CustomTextView huodong_content;
		private TextView huodong_title;
		private TextView huodong_stime;
		private TextView huodong_name;
	}

	public MyActivityAdapter(Context context, LinkedList<ActivityBean> mList) {
		this.context = context;
		this.mList = mList;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
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
					R.layout.xianshi_huodong_item, null);
			holder = new ViewHolder();
			holder.huodong_title = (TextView) view
					.findViewById(R.id.huodong_title);
			holder.huodong_content = (CustomTextView) view
					.findViewById(R.id.huodong_content);
			holder.huodong_stime = (TextView) view
					.findViewById(R.id.huodong_stime);
			holder.huodong_name = (TextView) view
					.findViewById(R.id.huodong_name);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.huodong_title.setText(mList.get(position).getTitle());
		holder.huodong_content.setText(MyUtlis.ToDBC(context.getResources().getString(R.string.title_bar_huodong_content)+mList.get(position).getContent()));
		holder.huodong_name.setText(context.getResources().getString(R.string.activity_name)+mList.get(position).getContact());
		holder.huodong_stime.setText(context.getResources().getString(
				R.string.activity_stime)
				+ mList.get(position).getLasttime());
		return view;
	}

}
