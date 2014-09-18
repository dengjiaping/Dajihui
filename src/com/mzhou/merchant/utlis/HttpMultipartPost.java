package com.mzhou.merchant.utlis;

import java.util.LinkedList;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.dao.IBack.IUploadBackInfo;
import com.mzhou.merchant.utlis.CustomMultiPartEntity.ProgressListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class HttpMultipartPost extends AsyncTask<HttpResponse, Integer, String> {
	ProgressDialog pd;
	long totalSize;
	Context context;
	String[] picPaths;
	String url;
	Map<String, String> paramters;

	public HttpMultipartPost(Context context, String url, String[] picPaths,
			Map<String, String> paramters) {
		this.context = context;
		this.picPaths = picPaths;
		this.url = url;
		this.paramters = paramters;
	}

	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage(context.getResources().getString(R.string.upload));
		pd.setCancelable(false);
		pd.show();
	}

	@Override
	protected String doInBackground(HttpResponse... arg0)  {
		LinkedList<String> image_str = PictureUtil.bitmapToString(picPaths);
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(url);

		CustomMultiPartEntity multipartContent = new CustomMultiPartEntity(
				new ProgressListener() {
					@Override
					public void transferred(long num) {
						publishProgress((int) ((num / (float) totalSize) * 100));
					}
				});
		try {

			for (int i = 0; i < image_str.size(); i++) {
				multipartContent.addPart("image[" + i + "]", new StringBody(
						image_str.get(i)));
			}
			if (paramters != null && !paramters.isEmpty()) {
				for (Map.Entry<String, String> entry : paramters.entrySet()) {
					multipartContent.addPart(entry.getKey(), new StringBody(
							entry.getValue()));
				}
			}
			totalSize = multipartContent.getContentLength();
			httpPost.setEntity(multipartContent);
			HttpResponse response = httpClient.execute(httpPost, httpContext);
			String serverResponse = EntityUtils.toString(response.getEntity());
			return serverResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return "[{}]";
		}
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		pd.setProgress((int) (progress[0]));
	}

	@Override
	protected void onPostExecute(String ui) {
		 System.out.println(ui);
		backInfo.getBackAttactInfo(ui);
		pd.dismiss();
	}

	private IUploadBackInfo backInfo;

	public void getBackInfoIml(IUploadBackInfo info) {
		backInfo = info;
	}
}