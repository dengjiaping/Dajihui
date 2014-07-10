package com.mzhou.merchant.utlis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;

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
					if (!string.contains("http")) {
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
        //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//��ʱ����bmΪ��  
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ  
        float hh = 800f;//�������ø߶�Ϊ800f  
        float ww = 480f;//�������ÿ��Ϊ480f  
        //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��  
        int be = 1;//be=1��ʾ������  
        if (w > h && w > ww) {//�����ȴ�Ļ����ݿ�ȹ̶���С����  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//����߶ȸߵĻ����ݿ�ȹ̶���С����  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//�������ű���  
        //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        return compressImage(bitmap);//ѹ���ñ�����С���ٽ�������ѹ��  
    } 
	private static Bitmap compressImage(Bitmap image) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>1024) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��         
            baos.reset();//����baos�����baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
            options -= 10;//ÿ�ζ�����10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream��������ͼƬ  
        return bitmap;  
    } 
public static String Bitmap2StrByBase64(Bitmap bit){  
	   ByteArrayOutputStream bos=new ByteArrayOutputStream();  
	   bit.compress(CompressFormat.JPEG, 50, bos);//����100��ʾ��ѹ��  
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
		LinkedList<String> list = new LinkedList<String>();
//		String string = "drawable://" + R.drawable.roominfo_add_btn_normal;
		for (int i = 0; i < filePath.length; i++) {
			String s1 = filePath[i].replace("/mnt/", "/");
			String s2 = s1.replace("file://", "/");
			list.add(s2);
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
