package com.mzhou.merchant.utlis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.util.Log;

public class HttpRequest {

	/**
	 * post ���󽫷��ص�������ת����byte���� ���飬Ȼ��ת����String�ַ���
	 * 
	 * @param path
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String sendPostRequest(Map<String, String> params, String path)  {
		String str = null;
		 
			try {
				StringBuilder sb = new StringBuilder();
				if (params != null && !params.isEmpty()) {

					Log.i("print", String.valueOf(params));

					for (Map.Entry<String, String> entry : params.entrySet()) {
						sb.append(entry.getKey()).append('=')
								.append(URLEncoder.encode(entry.getValue(), "utf-8")).append('&');
					}
					sb.deleteCharAt(sb.length() - 1);
				}
				byte [] entitydata = sb.toString().getBytes();// �õ�ʵ��Ķ���������
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setConnectTimeout(5 * 1000);
				conn.setDoOutput(true);// ���ͨ��post�ύ���ݣ�����������������������
				// Content-Type: application/x-www-form-urlencoded
				// Content-Length: 38
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Length", String.valueOf(entitydata.length));
				OutputStream outStream = conn.getOutputStream();
				outStream.write(entitydata);
				outStream.flush();
				outStream.close();
				if (conn.getResponseCode() == 200) {
					byte [] result = readStream(conn.getInputStream());
					str = new String(result);
					return str;
				} 
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "[]";
		 
	}

	/**
	 * ��ȡ��
	 * 
	 * @param inStream
	 * @return �ֽ�����
	 * @throws Exception
	 */
	private static byte [] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte [] buffer = new byte [1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}

}
