package com.mzhou.merchant.activity;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.dao.IBack.IBackInfo;
import com.mzhou.merchant.dao.biz.NewsManager;
import com.mzhou.merchant.model.BackBean;
import com.mzhou.merchant.utlis.WebIsConnectUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class XinwenCommentFabuActivity extends Activity {
	private ImageView backButton;
	private Button publish;
	private EditText display_news_comment_name;
	private EditText display_news_comment_content;
	private String product_id;
	private String uid;
	private NewsManager newsManager;
	private String commenter;
	private SharedPreferences sp;
	private boolean isEnterprise;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xianshi_xinwen_comment);
		sp = getSharedPreferences("phonemerchant", 1);
		newsManager = new NewsManager();
		Intent intent = getIntent();
		product_id = intent.getStringExtra("product_id");
		uid = intent.getStringExtra("uid");

		isEnterprise = sp.getBoolean("isEnterprise", false);
		loadButton();
		listenerButton();

	}

	private void listenerButton() {
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = display_news_comment_name.getText().toString();
				String content = display_news_comment_content.getText()
						.toString();

				if (WebIsConnectUtil.showNetState(XinwenCommentFabuActivity.this)) {
					if (isEnterprise) {
						newsManager.PublishNewsComment(
								XinwenCommentFabuActivity.this, "1", content, uid,
								product_id, name);
					} else {
						newsManager.PublishNewsComment(
								XinwenCommentFabuActivity.this, "0", content, uid,
								product_id, name);
					}

					newsManager.getBackInfo(new IBackInfo() {

						@Override
						public void getBackAttactInfo(BackBean backBean) {
							if (backBean != null) {
								if (backBean.getStatus().equals("true")) {
									finish();
								}
								Toast.makeText(XinwenCommentFabuActivity.this,
										backBean.getMsg(), Toast.LENGTH_SHORT)
										.show();
							}
						}
					});
				}
			}
		});
	}

	private void loadButton() {
		backButton = (ImageView) findViewById(R.id.title_bar_showleft);
		publish = (Button) findViewById(R.id.publish);
		display_news_comment_name = (EditText) findViewById(R.id.display_news_comment_name);
		display_news_comment_content = (EditText) findViewById(R.id.display_news_comment_content);
		if (isEnterprise) {
			commenter = sp.getString("nickname_enterprise", "");
			display_news_comment_name.setText(commenter);
		} else {
			commenter = sp.getString("nickname", "");
			display_news_comment_name.setText(commenter);
		}

	}

}
