package com.mzhou.merchant.adapter;

import java.util.LinkedList;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.model.ActivityMemberBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyActivityMemberAdapter extends BaseAdapter {

	private LinkedList<ActivityMemberBean> mList;
	private Context context;

	private class ViewHolder {
		private TextView huodong_meber;
		private TextView huodong_number;
		 
	}

	public MyActivityMemberAdapter(Context context, LinkedList<ActivityMemberBean> mList) {
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
					R.layout.xianshi_huodong_meber_item, null);
			holder = new ViewHolder();
			holder.huodong_meber = (TextView) view.findViewById(R.id.huodong_meber);
			holder.huodong_number = (TextView) view.findViewById(R.id.huodong_number);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.huodong_meber.setText(mList.get(position).getContact());
		holder.huodong_number.setText(mList.get(position).getPhone());

		return view;
	}

}