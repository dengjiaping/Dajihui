package com.mzhou.merchant.utlis;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.mzhou.merchant.activity.R;
import com.mzhou.merchant.model.ActivityBean;
import com.mzhou.merchant.model.ActivityMemberBean;
import com.mzhou.merchant.model.AdBean;
import com.mzhou.merchant.model.AttactBean;
import com.mzhou.merchant.model.JobBean;
import com.mzhou.merchant.model.NewsBean;
import com.mzhou.merchant.model.NewsCommentBean;
import com.mzhou.merchant.model.ProductCommentBean;
import com.mzhou.merchant.model.ProductsBean;
import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class MyUtlis {
	/**
	 * 判断是否为空，如果为空那么返回true
	 * 
	 * @param string
	 * @return
	 */
	public static String getHeadPic(String url, Context context) {
		LinkedList<String> list = new LinkedList<String>();
		if (url != null) {
			StringTokenizer tokenizer = new StringTokenizer(url, context
					.getResources().getString(R.string.spilt));
			while (tokenizer.hasMoreTokens()) {
				list.addLast(MyConstants.PICTURE_URL + tokenizer.nextToken());
			}
			if (list.size() != 0) {
				return list.get(0);
				// Log.i("print", "adasdfdsf-->" + list.get(0));
			}
		}
		return MyConstants.PICTURE_URL;
	}
	public static  String getYouKuThumb(String youkuid){
		return "http://www.sj6.cn/video/thumb/" + youkuid+".html";
	}
	public static  String getYouKuVideo(String youkuid){
		return "http://www.sj6.cn/video/youku/" + youkuid+".html";
	}
	/**
	 * 将textview中的字符全角化。即将所有的数字、字母及标点全部转为全角字符，使它们与汉字同占两个字节，这样就可以避免由于占位导致的排版混乱问题了
	 * @param input
	 * @return
	 */
/*	public static String ToDBC(String input) {
		   char[] c = input.toCharArray();
		   for (int i = 0; i< c.length; i++) {
		       if (c[i] == 12288) {
		         c[i] = (char) 32;
		         continue;
		       }if (c[i]> 65280&& c[i]< 65375)
		          c[i] = (char) (c[i] - 65248);
		       }
		   return new String(c);
		}*/
	 public static String ToDBC(String input) {  
	        char[] c = input.toCharArray();  
	        for (int i = 0; i < c.length; i++) {  
	            if (c[i] == 12288) {  
	                c[i] = (char) 32;  
	                continue;  
	            }  
	            if (c[i] > 65280 && c[i] < 65375)  
	                c[i] = (char) (c[i] - 65248);  
	        }  
	        return new String(c);  
	    }  
	
	
	public static boolean isEmpity(String string) {
		if ((string != null) && (!string.equals("[]"))) {
			return false;
		} else {
			// toastInfo(context, "");
		}
		return true;

	}

	/**
	 * 将 天数转换成 秒数，比如，发布招聘 的时候调用
	 * 
	 * @param days
	 * @return
	 */
	public static long getdays(int days) {
		java.util.Date date = new java.util.Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)
				+ days);
		return calendar.getTimeInMillis() / 1000;
	}

	/**
	 * 将 s 转换成 年-月-日，比如求购，招商，新闻，招聘
	 * 
	 * @param timestampString
	 * @return
	 */
	public static String TimeStamp2Date(String timestampString) {
		if (timestampString != null) {
			Long timestamp = Long.parseLong(timestampString) * 1000;
			String date = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
					.format(new java.util.Date(timestamp));

			return date.substring(0, 10);
		}
		return "";

	}

	/**
	 * 将 s 转换成 月- 日，用于 listview 数据的时间，比如求购，招商，新闻，招聘
	 * 
	 * @param timestampString
	 * @return 返回日期 月-日
	 */
	public static String TimeStamp2DateList(String timestampString,
			Context context) {
		if (timestampString != null) {

			Long timestamp = Long.parseLong(timestampString) * 1000;

			String date = new java.text.SimpleDateFormat(context.getResources()
					.getString(R.string.time_zh)).format(new java.util.Date(
					timestamp));
			return date.substring(5, 11);
		}
		return "";
	}

	/**
	 * 将 s 转换成 月-日，比如用于产品
	 * 
	 * @param timestampString
	 * @return
	 */
	public static String TimeStamp2DateGrid(String timestampString) {
		if (timestampString != null) {

			Long timestamp = Long.parseLong(timestampString) * 1000;
			String date = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
					.format(new java.util.Date(timestamp));
			return date.substring(5, 10);

		}
		return "";
	}

	/**
	 * Toast 函数
	 * 
	 * @param string
	 */
	public static void toastInfo(Context context, String string) {
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 将list里面的数据排序
	 * 
	 * @param mList
	 */
	public static void sortLogoOrder(LinkedList<ProductsBean> mList) throws Exception{

		Collections.sort(mList, new Comparator<ProductsBean>() {
			@Override
			public int compare(ProductsBean lhs, ProductsBean rhs) {
				if (lhs.getOrder_sort() != null && rhs.getOrder_sort() != null) {

					if (Integer.parseInt(lhs.getOrder_sort()) >= Integer
							.parseInt(rhs.getOrder_sort())) {
						return 1;
					}
					return -1;

				}
				return 0;
			}
		});
	}

	/**
	 * 图片排序
	 * 
	 * @param mList
	 */
	public static void sortAdOrder(List<AdBean> mList) {
		Collections.sort(mList, new Comparator<AdBean>() {
			@Override
			public int compare(AdBean lhs, AdBean rhs) {
				if ((lhs.getOrder_sort() != null)
						&& (rhs.getOrder_sort() != null)) {
					if (Integer.parseInt(lhs.getOrder_sort()) >= Integer
							.parseInt(rhs.getOrder_sort())) {
						return 1;
					}
					return -1;
				}
				return 0;
			}
		});
	}

	public static void sortListOrder(LinkedList<ProductsBean> mList) {
		Collections.sort(mList, new Comparator<ProductsBean>() {
			@Override
			public int compare(ProductsBean lhs, ProductsBean rhs) {
				if ((lhs.getCtime() != null) && (rhs.getCtime() != null)) {

					if (Integer.parseInt(lhs.getCtime()) <= Integer
							.parseInt(rhs.getCtime())) {
						return 1;
					}
					return -1;

				}
				return 0;
			}
		});
	}
	public static void sortActivityMemberOrder(LinkedList<ActivityMemberBean> mList) {
		Collections.sort(mList, new Comparator<ActivityMemberBean>() {
			@Override
			public int compare(ActivityMemberBean lhs, ActivityMemberBean rhs) {
				if ((lhs.getCtime() != null) && (rhs.getCtime() != null)) {
					
					if (Integer.parseInt(lhs.getCtime()) <= Integer
							.parseInt(rhs.getCtime())) {
						return 1;
					}
					return -1;
					
				}
				return 0;
			}
		});
	}

	public static void sortListAttactBeanOrder(LinkedList<AttactBean> mList) {
		Collections.sort(mList, new Comparator<AttactBean>() {
			@Override
			public int compare(AttactBean lhs, AttactBean rhs) {
				if ((lhs.getCtime() != null) && (rhs.getCtime() != null)) {

					if (Integer.parseInt(lhs.getCtime()) <= Integer
							.parseInt(rhs.getCtime())) {
						return 1;
					}
					return -1;

				}
				return 0;
			}
		});
	}
	public static void sortListActivityBeanOrder(LinkedList<ActivityBean> mList) {
		Collections.sort(mList, new Comparator<ActivityBean>() {
			@Override
			public int compare(ActivityBean lhs, ActivityBean rhs) {
				if ((lhs.getCtime() != null) && (rhs.getCtime() != null)) {
					
					if (Integer.parseInt(lhs.getCtime()) <= Integer
							.parseInt(rhs.getCtime())) {
						return 1;
					}
					return -1;
					
				}
				return 0;
			}
		});
	}

	public static void sortListNewsBeanOrder(LinkedList<NewsBean> mList) {
		Collections.sort(mList, new Comparator<NewsBean>() {
			@Override
			public int compare(NewsBean lhs, NewsBean rhs) {
				if ((lhs.getCtime() != null) && (rhs.getCtime() != null)) {

					if (Integer.parseInt(lhs.getCtime()) <= Integer
							.parseInt(rhs.getCtime())) {
						return 1;
					}
					return -1;

				}
				return 0;
			}
		});
	}

	public static void sortListJobBeanOrder(LinkedList<JobBean> mList) {
		Collections.sort(mList, new Comparator<JobBean>() {
			@Override
			public int compare(JobBean lhs, JobBean rhs) {
				if ((lhs.getCtime() != null) && (rhs.getCtime() != null)) {

					if (Integer.parseInt(lhs.getCtime()) <= Integer
							.parseInt(rhs.getCtime())) {
						return 1;
					}
					return -1;

				}
				return 0;
			}
		});
	}

	public static void sortListProductCommentBeanOrder(
			LinkedList<ProductCommentBean> mList) {
		Collections.sort(mList, new Comparator<ProductCommentBean>() {
			@Override
			public int compare(ProductCommentBean lhs, ProductCommentBean rhs) {
				if ((lhs.getCtime() != null) && (rhs.getCtime() != null)) {

					if (Integer.parseInt(lhs.getCtime()) <= Integer
							.parseInt(rhs.getCtime())) {
						return 1;
					}
					return -1;

				}
				return 0;
			}
		});
	}

	public static void sortListNewsCommentBeanOrder(
			LinkedList<NewsCommentBean> mList) {
		Collections.sort(mList, new Comparator<NewsCommentBean>() {
			@Override
			public int compare(NewsCommentBean lhs, NewsCommentBean rhs) {
				if ((lhs.getCtime() != null) && (rhs.getCtime() != null)) {

					if (Integer.parseInt(lhs.getCtime()) <= Integer
							.parseInt(rhs.getCtime())) {
						return 1;
					}
					return -1;

				}
				return 0;
			}
		});
	}

	/**
	 * 取得EditText里面的值
	 * 
	 * @param editText
	 * @return
	 */
	public static String getEditString(EditText editText) {
		if (editText == null) {
			return "";
		}
		String string = editText.getText().toString();
		return string+"";

	}
 

 

 
	/**
	 * 读取product.txt里面的信息
	 * 
	 * @param filename
	 * @return
	 */
	public static String readFiles(String filename, Context context) {

		String content = null;
		try {
			FileInputStream fis = context.openFileInput(filename);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			content = baos.toString();
			fis.close();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

}