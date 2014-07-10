package com.mzhou.merchant.activity;

import java.util.LinkedList;
import java.util.StringTokenizer;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.dao.INews.IgetNewsByIdInfo;
import com.mzhou.merchant.dao.biz.NewsManager;
import com.mzhou.merchant.model.NewsByIdBean;
import com.mzhou.merchant.utlis.MyConstants;
import com.mzhou.merchant.utlis.MyUtlis;
import com.mzhou.merchant.utlis.WebIsConnectUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class XinwenContentActivity extends Activity {
	private Gallery gallery;

	private ImageView backButton;
	private String[] listStr;
	private DisplayImageOptions options;
	protected ImageLoader imageLoader;
	private TextView display_news_title;
	private TextView xianshi_xinwen_content;
	private TextView display_news_channel;
	private TextView display_news_time;
	private NewsManager newsManager;
	private ImageAdapter imageAdapter;
	private LinkedList<String> list;
	private Button getMoreComment;
	private String newsId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xianshi_xinwen);
		init();
		loadButton();
		listenerButton();
		getdata();
	}

	private void init() {
		Intent intent = getIntent();
		newsId = intent.getStringExtra("id");
		imageAdapter = new ImageAdapter();
		list = new LinkedList<String>();
		newsManager = new NewsManager();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_stub)
				.showImageOnFail(R.drawable.ic_stub).cacheInMemory()
				.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
		imageLoader = ImageLoader.getInstance();

	}

	private void listenerButton() {
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(XinwenContentActivity.this, PicPagerActivity.class);
				intent.putExtra(
						MyConstants.Extra.IMAGES,
						listStr);
				intent.putExtra(
						MyConstants.Extra.IMAGE_POSITION,
						arg2);
				startActivity(intent);

			}
		});
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				XinwenContentActivity.this.finish();
			}
		});
		getMoreComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(XinwenContentActivity.this,
						XinwenCommentListActivity.class);
				intent.putExtra("newsId", newsId);
				startActivity(intent);

			}
		});
	}

	private void loadButton() {
		gallery = (Gallery) findViewById(R.id.xianshi_xinwen_gallery);
		backButton = (ImageView) findViewById(R.id.title_bar_showleft);
		display_news_title = (TextView) findViewById(R.id.display_news_title);
		xianshi_xinwen_content = (TextView) findViewById(R.id.xianshi_xinwen_content);
		display_news_channel = (TextView) findViewById(R.id.display_news_channel);
		display_news_time = (TextView) findViewById(R.id.display_news_time);
		getMoreComment = (Button) findViewById(R.id.getMoreComment);

	}

	private void getdata() {
		if (WebIsConnectUtil.showNetState(XinwenContentActivity.this)) {
			newsManager.GetNewsInfoById(XinwenContentActivity.this, newsId);
			newsManager.getNewsByIdInfoIml(new IgetNewsByIdInfo() {
				@Override
				public void getNewsByIdInfo(NewsByIdBean newsBean) {
					if (newsBean != null) {
						String title = newsBean.getTitle();
						String time = getResources().getString(R.string.time)
								+ MyUtlis.TimeStamp2Date(newsBean.getCtime());
						String content = newsBean.getContent();
						String from = getResources().getString(R.string.from)
								+ newsBean.getSource();
						xianshi_xinwen_content.setText("\t\t"+content);
						display_news_channel.setText(from);
						display_news_title.setText(title);
						display_news_time.setText(time);
						String picurl = newsBean.getPic();
						if (picurl != null) {
							StringTokenizer tokenizer = new StringTokenizer(
									newsBean.getPic(), getResources()
											.getString(R.string.spilt));
							while (tokenizer.hasMoreTokens()) {
								list.addLast(MyConstants.PICTURE_URL
										+ tokenizer.nextToken());
							}
							listStr = new String[list.size()];
							for (int i = 0; i < list.size(); i++) {
								listStr[i] = list.get(i);
							}
							gallery.setAdapter(imageAdapter);
						}
					} else {
						finish();
					}
				}
			});

		}

	}

	public class ImageAdapter extends BaseAdapter {

		public ImageAdapter() {
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = (ImageView) convertView;
			if (imageView == null) {
				imageView = (ImageView) getLayoutInflater().inflate(
						R.layout.item_gallery_image, parent, false);
			}
			imageLoader.displayImage(list.get(position), imageView, options);
			return imageView;

		}
	}

}
