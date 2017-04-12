package com.sxh.olddriver.view;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.sxh.olddriver.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * ImageLoader������
 * 1.�����˼���ͼƬ��һЩ����
 * 2.�����Ƿ�ʹ�û���
 * @author user
 *
 */
public class ImageLoaderUtils {
	public void run(){

	}
	//	����ͼƬ
	public static void getImageByloader (String url,ImageView image){
		DisplayImageOptions options=new DisplayImageOptions.Builder()
//				����ͼƬ����ʱ����ʾ��ͼƬ
				.showImageOnLoading(R.drawable.default_anonymous_users_avatar)
//				����ͼƬUriΪ�ջ��ߴ����ʱ����ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.default_anonymous_users_avatar)
//				����ͼƬ����/������̴����ʱ����ʾ��ͼƬ
				.showImageOnFail(R.drawable.default_anonymous_users_avatar)
//				�������ص�ͼƬ�Ƿ񻺴���Sd����
				.cacheInMemory(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
//				ͼƬ����
				.bitmapConfig(Bitmap.Config.RGB_565)
//				ͼƬ���غú���Ķ���ʱ��
//				.displayer(new FadeInBitmapDisplayer(1000))
//				���ó�Բ��ͼƬ
				.displayer(new RoundedBitmapDisplayer(200))//设置为圆角图片
				.build();
//		����ͼƬ
		ImageLoader.getInstance()
				.displayImage(url,
						image,
						options,
						new ImageLoadingListener() {

							@Override
							public void onLoadingStarted(String arg0, View arg1) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onLoadingCancelled(String arg0, View arg1) {
								// TODO Auto-generated method stub

							}
						},new ImageLoadingProgressListener() {

							@Override
							public void onProgressUpdate(String arg0, View arg1, int arg2, int arg3) {
								// TODO Auto-generated method stub

							}
						});
	}
//	����ڴ滺��
//		ImageLoader.getInstance().clearMemoryCache();
//		������ػ���
//	ImageLoader.getInstance().clearDiscCache();

}
