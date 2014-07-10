package com.mzhou.merchant.adapter;

import java.util.LinkedList;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.model.AttactBean;
import com.mzhou.merchant.utlis.MyUtlis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAttachAdapter extends BaseAdapter {

	private LinkedList<AttactBean> mList;
	private Context context;

	private class ViewHolder {
		private TextView content;
		private TextView contact;
		private TextView time;
		private TextView nub;
	}

	public MyAttachAdapter(Context context, LinkedList<AttactBean> mList) {
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
					R.layout.xianshi_qiugou_item, null);
			holder = new ViewHolder();
			holder.content = (TextView) view.findViewById(R.id.list_content);
			holder.contact = (TextView) view.findViewById(R.id.list_contact);
			holder.time = (TextView) view.findViewById(R.id.list_time);
			holder.nub = (TextView) view.findViewById(R.id.list_qq);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.content.setText(MyUtlis.ToDBC(mList.get(position).getContent()));
		holder.contact.setText(context.getResources().getString(
				R.string.user_manager_name)
				+ mList.get(position).getContact());
		holder.time.setText(context.getResources().getString(R.string.time)
				+ MyUtlis.TimeStamp2DateList(mList.get(position).getCtime(),
						context));
		holder.nub.setText(context.getResources().getString(
				R.string.user_manager_phonnumber3)
				+ mList.get(position).getEmail());

		return view;
	}

}
