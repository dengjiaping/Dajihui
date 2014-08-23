package com.mzhou.merchant.utlis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Base64;

public class PictureUtil {

	public static LinkedList<String> bitmapToString(String[] filePath) {
		try {
			LinkedList<String> linkedList = new LinkedList<String>();
			String[] array = clearPath(filePath);
			if (array != null) {
				for (int i = 0; i < array.length; i++) {
					String string = array[i];
					if (!string.startsWith("http")) {
						Bitmap bitmap = getimage(string);
						linkedList.addLast(Bitmap2StrByBase64(bitmap));
					}else {
						linkedList.addLast(string);
					}
				}
				return linkedList;
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	private static Bitmap getimage(String srcPath) {  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空  
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
        float hh = 800f;//这里设置高度为800f  
        float ww = 480f;//这里设置宽度为480f  
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be = 1;//be=1表示不缩放  
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置缩放比例  
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
    } 
	private static Bitmap compressImage(Bitmap image) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>1024) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    } 
public static String Bitmap2StrByBase64(Bitmap bit){  
	   ByteArrayOutputStream bos=new ByteArrayOutputStream();  
	   bit.compress(CompressFormat.JPEG, 50, bos);//参数100表示不压缩  
	   byte[] bytes=bos.toByteArray();  
	   return Base64.encodeToString(bytes, Base64.DEFAULT);  
	}
	public static String encodeBase64File(String path) throws Exception {
		File  file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int)file.length()];
		inputFile.read(buffer);
		        inputFile.close();
		        return Base64.encodeToString(buffer,Base64.DEFAULT);
		}
	private static String[] clearPath(String[] filePath) {
		List<String> list = new ArrayList<String>();
//		String string = "drawable://" + R.drawable.roominfo_add_btn_normal;
		for (int i = 0; i < filePath.length; i++) {
			if (filePath[i].startsWith("/mnt")) {
				String s1 = filePath[i].replace("/mnt/", "/");
				String s2 = s1.replace("file://", "/");
				list.add(s2);
			}else if (filePath[i].startsWith("file://")) {
				String s2 = filePath[i].replace("file://", "/");
				list.add(s2);
			}else if (filePath[i].startsWith("http")) {
				
			}else {
				list.add(filePath[i]);
			}
		}
		/*if (list.contains(string)) {
			list.remove(string);
		}*/
		String[] array = (String[]) list.toArray(new String[0]);
		list.clear();
		return array;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public static Bitmap getSmallBitmap(String filePath) throws Exception{
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		Bitmap bitmap2 = resize(bitmap, bitmap.getWidth() / 2,
				bitmap.getHeight() / 3);
		bitmap.recycle();
		Canvas canvas = new Canvas(bitmap2);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		return bitmap2;
	}

 

	private static Bitmap resize(Bitmap picture, int targetWidth,
			int targetHeight) {
		if (picture == null || picture.getHeight() < 0
				|| picture.getWidth() < 0) {
			return null;
		}

		int pictureWidth = picture.getWidth();
		int pictureHeight = picture.getHeight();

		float scale = Math.min((float) targetWidth / pictureWidth,
				(float) targetHeight / pictureHeight); // (1)

		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);

		return Bitmap.createBitmap(picture, 0, 0, pictureWidth, pictureHeight,
				matrix, true);
	}
}
