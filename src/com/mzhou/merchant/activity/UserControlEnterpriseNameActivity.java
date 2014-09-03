package com.mzhou.merchant.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mzhou.merchant.model.GroupUsers;
import com.mzhou.merchant.model.User;
import com.mzhou.merchant.utlis.MyConstants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UserControlEnterpriseNameActivity extends Activity {
	private ImageView title_bar_showleft;
	private EditText name1;
	private EditText name2;
	private EditText name3;
	private EditText name4;
	private EditText name5;
	private EditText name6;
	private EditText name7;
	private EditText name8;
	private EditText name9;
	private EditText name10;

	private EditText nub1;
	private EditText nub2;
	private EditText nub3;
	private EditText nub4;
	private EditText nub5;
	private EditText nub6;
	private EditText nub7;
	private EditText nub8;
	private EditText nub9;
	private EditText nub10;
	private Button publish;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_manager_enterprise_name);
		GroupUsers groupUsers = init();
		loadButton();
		setdata(groupUsers);
		listenerButton();
	}

	private void setdata(GroupUsers groupUsers) {
		if (groupUsers != null) {
			name1.setText(groupUsers.getUsers().get(0).getName());
			name2.setText(groupUsers.getUsers().get(1).getName());
			name3.setText(groupUsers.getUsers().get(2).getName());
			name4.setText(groupUsers.getUsers().get(3).getName());
			name5.setText(groupUsers.getUsers().get(4).getName());
			name6.setText(groupUsers.getUsers().get(5).getName());
			name7.setText(groupUsers.getUsers().get(6).getName());
			name8.setText(groupUsers.getUsers().get(7).getName());
			name9.setText(groupUsers.getUsers().get(8).getName());
			name10.setText(groupUsers.getUsers().get(9).getName());
			nub1.setText(groupUsers.getUsers().get(0).getNumber());
			nub2.setText(groupUsers.getUsers().get(1).getNumber());
			nub3.setText(groupUsers.getUsers().get(2).getNumber());
			nub4.setText(groupUsers.getUsers().get(3).getNumber());
			nub5.setText(groupUsers.getUsers().get(4).getNumber());
			nub6.setText(groupUsers.getUsers().get(5).getNumber());
			nub7.setText(groupUsers.getUsers().get(6).getNumber());
			nub8.setText(groupUsers.getUsers().get(7).getNumber());
			nub9.setText(groupUsers.getUsers().get(8).getNumber());
			nub10.setText(groupUsers.getUsers().get(9).getNumber());
		}
	}

	private String getdata() {
		GroupUsers groupUsers = new GroupUsers();
		User user1 = new User();
		user1.setId(1L);
		user1.setName(name1.getText().toString());
		user1.setNumber(nub1.getText().toString());
		User user2 = new User();
		user2.setId(2L);
		user2.setName(name2.getText().toString());
		user2.setNumber(nub2.getText().toString());
		User user3 = new User();
		user3.setId(3L);
		user3.setName(name3.getText().toString());
		user3.setNumber(nub3.getText().toString());
		User user4 = new User();
		user4.setId(4L);
		user4.setName(name4.getText().toString());
		user4.setNumber(nub4.getText().toString());
		User user5 = new User();
		user5.setId(5L);
		user5.setName(name5.getText().toString());
		user5.setNumber(nub5.getText().toString());
		User user6 = new User();
		user6.setId(6L);
		user6.setName(name6.getText().toString());
		user6.setNumber(nub6.getText().toString());
		User user7 = new User();
		user7.setId(7L);
		user7.setName(name7.getText().toString());
		user7.setNumber(nub7.getText().toString());
		User user8 = new User();
		user8.setId(8L);
		user8.setName(name8.getText().toString());
		user8.setNumber(nub8.getText().toString());
		User user9 = new User();
		user9.setId(9L);
		user9.setName(name9.getText().toString());
		user9.setNumber(nub9.getText().toString());
		User user10 = new User();
		user10.setId(9L);
		user10.setName(name10.getText().toString());
		user10.setNumber(nub10.getText().toString());

		groupUsers.getUsers().add(user1);
		groupUsers.getUsers().add(user2);
		groupUsers.getUsers().add(user3);
		groupUsers.getUsers().add(user4);
		groupUsers.getUsers().add(user5);
		groupUsers.getUsers().add(user6);
		groupUsers.getUsers().add(user7);
		groupUsers.getUsers().add(user8);
		groupUsers.getUsers().add(user9);
		groupUsers.getUsers().add(user10);
		String json_name = JSON.toJSONString(groupUsers,
				SerializerFeature.WriteNullStringAsEmpty);
		return json_name;
	}

	private void listenerButton() {
		title_bar_showleft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("name", getdata());
				setResult(MyConstants.REQUEST_NAME, intent);
				finish();
			}
		});
	}

	private void loadButton() {
		publish = (Button) findViewById(R.id.publish);
		title_bar_showleft = (ImageView) findViewById(R.id.title_bar_showleft);
		name1 = (EditText) findViewById(R.id.name1);
		name2 = (EditText) findViewById(R.id.name2);
		name3 = (EditText) findViewById(R.id.name3);
		name4 = (EditText) findViewById(R.id.name4);
		name5 = (EditText) findViewById(R.id.name5);
		name6 = (EditText) findViewById(R.id.name6);
		name7 = (EditText) findViewById(R.id.name7);
		name8 = (EditText) findViewById(R.id.name8);
		name9 = (EditText) findViewById(R.id.name9);
		name10 = (EditText) findViewById(R.id.name10);
		nub1 = (EditText) findViewById(R.id.nub1);
		nub2 = (EditText) findViewById(R.id.nub2);
		nub3 = (EditText) findViewById(R.id.nub3);
		nub4 = (EditText) findViewById(R.id.nub4);
		nub5 = (EditText) findViewById(R.id.nub5);
		nub6 = (EditText) findViewById(R.id.nub6);
		nub7 = (EditText) findViewById(R.id.nub7);
		nub8 = (EditText) findViewById(R.id.nub8);
		nub9 = (EditText) findViewById(R.id.nub9);
		nub10 = (EditText) findViewById(R.id.nub10);
	}

	private GroupUsers init() {
		Intent intent = getIntent();
		String string = intent.getStringExtra("name");

		if (string != null && !string.equals("") && !string.equals("[]")) {
			GroupUsers groupUsers = JSON.parseObject(string, GroupUsers.class);
			return groupUsers;
		}
		return null;
	}

}
