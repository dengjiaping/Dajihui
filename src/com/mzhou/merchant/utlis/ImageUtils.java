package com.mzhou.merchant.utlis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;

public class ImageUtils {
	/**
	 * �õ�ǰʱ���ȡ�õ�ͼƬ����
	 * 
	 */
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(date);
	}

 
	
	
	public static Bitmap getimage(String srcPath) {  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//��ʱ����bmΪ��  
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //���������ֻ�Ƚ϶���1280f*720f�ֱ��ʣ����ԸߺͿ���������Ϊ  
        float hh = 1280f;//�������ø߶�Ϊ1280f  
        float ww = 720f;//�������ÿ��Ϊ720f  
        //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ����ݽ��м��㼴��  
        int be = 1;//be=1��ʾ������  
        if (w > h && w > ww) {//����ȴ�Ļ���ݿ�ȹ̶���С����  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//���߶ȸߵĻ���ݿ�ȹ̶���С����  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//�������ű���  
        //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        return compressImage(bitmap);//ѹ���ñ����С���ٽ�������ѹ��  
    } 
	public static Bitmap getimageWidthHeight(String srcPath) {  
		BitmapFactory.Options newOpts = new BitmapFactory.Options();  
		//��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
		newOpts.inJustDecodeBounds = true;  
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//��ʱ����bmΪ��  
		
		newOpts.inJustDecodeBounds = false;  
		int w = newOpts.outWidth;  
		int h = newOpts.outHeight;  
		//���������ֻ�Ƚ϶���1280f*720f�ֱ��ʣ����ԸߺͿ���������Ϊ  
		float hh = 480f;//�������ø߶�Ϊ1280f  
		float ww = 480f;//�������ÿ��Ϊ720f  
		//���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ����ݽ��м��㼴��  
		int be = 1;//be=1��ʾ������  
		if (w > h && w > ww) {//����ȴ�Ļ���ݿ�ȹ̶���С����  
			be = (int) (newOpts.outWidth / ww);  
		} else if (w < h && h > hh) {//���߶ȸߵĻ���ݿ�ȹ̶���С����  
			be = (int) (newOpts.outHeight / hh);  
		}  
		if (be <= 0)  
			be = 1;  
		newOpts.inSampleSize = be;//�������ű���  
		//���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
		return compressImageWidthHeight(bitmap);//ѹ���ñ����С���ٽ�������ѹ��  
	} 
	
	public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {  
		  
        // load the origial Bitmap  
        Bitmap BitmapOrg = bitmap;  
  
        int width = BitmapOrg.getWidth();  
        int height = BitmapOrg.getHeight();  
        int newWidth = w;  
        int newHeight = h;  
  
        // calculate the scale  
        float scaleWidth = ((float) newWidth) / width;  
        float scaleHeight = ((float) newHeight) / height;  
  
        // create a matrix for the manipulation  
        Matrix matrix = new Matrix();  
        // resize the Bitmap  
        matrix.postScale(scaleWidth, scaleHeight);  
        // if you want to rotate the Bitmap  
        // matrix.postRotate(45);  
  
        // recreate the new Bitmap  
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,  
                height, matrix, true);  
  
        // make a Drawable from Bitmap to allow to set the Bitmap  
        // to the ImageView, ImageButton or what ever  
        return resizedBitmap;  
  
    }  
	public static Bitmap compressImageWidthHeight(Bitmap image) {  
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ�������ݴ�ŵ�baos��  
		int options = 100;  
		while ( baos.toByteArray().length / 30>1024) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��         
			baos.reset();//����baos�����baos  
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ�������ݴ�ŵ�baos��  
			options -= 10;//ÿ�ζ�����10  
		}  
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ��������baos��ŵ�ByteArrayInputStream��  
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream������ͼƬ  
		return bitmap;  
	} 
	public static Bitmap compressImage(Bitmap image) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ�������ݴ�ŵ�baos��  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>2*1024) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��         
            baos.reset();//����baos�����baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ�������ݴ�ŵ�baos��  
            options -= 10;//ÿ�ζ�����10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ��������baos��ŵ�ByteArrayInputStream��  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream������ͼƬ  
        return bitmap;  
    } 
	/**
	 * Save image to the SD card
	 * 
	 * @param photoBitmap
	 * @param photoName
	 * @param path
	 */
	public static String savePhotoToSDCard(Bitmap photoBitmap, String path, String photoName) {
		if (checkSDCardAvailable()) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File photoFile = new File(path, photoName + ".jpg");
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					Bitmap newBitmap = compressImage(photoBitmap);
					if (newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
						fileOutputStream.flush();
						// fileOutputStream.close();
					}
					newBitmap.recycle();
					photoBitmap.recycle();
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return path + photoName + ".jpg";
	}

	/**
	 * Check the SD card
	 * 
	 * @return
	 */
	public static boolean checkSDCardAvailable() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
   /**
    * ��ȡ����ͼƬ
    * @param pathString
    * @return
    */
    public static  Bitmap getDiskBitmap(String pathString)  
    {  
        Bitmap bitmap = null;  
        try  
        {  
            File file = new File(pathString);  
            if(file.exists())  
            {  
                bitmap = BitmapFactory.decodeFile(pathString);  
            }  
        } catch (Exception e)  
        {  
           return null;
        }  
        return bitmap;  
    }  
	
	/*
     * �������ϻ�ȡͼƬ�����ͼƬ�ڱ��ش��ڵĻ���ֱ���ã���������ȥ������������ͼƬ
     * �����path��ͼƬ�ĵ�ַ
     */
    public static Uri getImageURI(String path, File cache) throws Exception {
        String name = path.substring(path.lastIndexOf("/"));
        File file = new File(cache, name);
        // ���ͼƬ���ڱ��ػ���Ŀ¼����ȥ����������
        if (file.exists()) {
            return Uri.fromFile(file);//Uri.fromFile(path)��������ܵõ��ļ���URI
        } else {
            // �������ϻ�ȡͼƬ
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            if (conn.getResponseCode() == 200) {
 
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
                // ����һ��URI����
                return Uri.fromFile(file);
            }
        }
        return null;
    }

}
