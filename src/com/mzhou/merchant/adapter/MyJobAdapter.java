package com.mzhou.merchant.adapter;

import java.util.LinkedList;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.model.JobBean;
import com.mzhou.merchant.utlis.MyUtlis;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyJobAdapter extends BaseAdapter {

	private Context context;
	private LinkedList<JobBean> mList;

	private class ViewHolder {
		public TextView zhaoping_content;
		public TextView zhaoping_name;
		public TextView zhaoping_number;
		public TextView zhaoping_title;
	}

	public MyJobAdapter(Context context, LinkedList<JobBean> mlist) {
		this.context = context;
		this.mList = mlist;
	}

	private int clickTemp = -1;

	// 标识选择的Item
	public void setSeclection(int position) {
		clickTemp = position;
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
					R.layout.xianshi_zhaoping_item, null);
			holder = new ViewHolder();
			holder.zhaoping_content = (TextView) view
					.findViewById(R.id.zhaoping_content);
			holder.zhaoping_name = (TextView) view
					.findViewById(R.id.zhaoping_name);
			holder.zhaoping_number = (TextView) view
					.findViewById(R.id.zhaoping_number);
			holder.zhaoping_title = (TextView) view
					.findViewById(R.id.zhaoping_title);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.zhaoping_content.setText(MyUtlis.ToDBC(context.getResources().getString(
				R.string.display_job_require)
				+ mList.get(position).getContent()));
		holder.zhaoping_name.setText(context.getResources().getString(
				R.string.user_manager_name)
				+ mList.get(position).getContact());
		holder.zhaoping_number.setText(context.getResources().getString(
				R.string.time)
				+ MyUtlis.TimeStamp2DateList(mList.get(position).getCtime(),
						context));
		holder.zhaoping_title.setText(MyUtlis.ToDBC(mList.get(position).getPosition()));
		if (clickTemp == position) {
			view.setBackgroundResource(R.drawable.grid_item_border);
		} else {
			view.setBackgroundColor(Color.TRANSPARENT);
		}
		return view;
	}

}
