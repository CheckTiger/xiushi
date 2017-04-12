package com.sxh.olddriver.application;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
/**
 * Application����Ҫ��Menifest.xml�����ļ�������
 * ��<application
 * 	name="����.MyApplication"
 * @author user
 *
 */
public class MyApplication extends Application {
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initImageLoder(this);
	}
	public void run(){

	}
	/**
	 * ��ʼ��ImageLoder�ķ���
	 * @param context
	 */
	private void initImageLoder(Context context) {
//		���ñ���ͼƬ��sd��·��
		File cacheDir=StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache");
		ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(context)
//				���õ�ǰ�̵߳����ȼ�
				.threadPriority(Thread.MIN_PRIORITY-2)
//				������ʾ��ͬ��С��ͬһ��ͼƬ
				.denyCacheImageMultipleSizesInMemory()
//				�������ʱ���URI������MD5����
//				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				
//				50 Mb sd�������أ���������ֵ
				.diskCacheSize(50*1024*1024)
//				sd������
				.diskCache(new UnlimitedDiscCache(cacheDir))
//				�ڴ滺��
				.memoryCache(new WeakMemoryCache())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		/*
		 * getInstance����ģʽ�����ڴ���ֻ�ܴ���һ������
		 * ���췽��˽��
		 */
		ImageLoader.getInstance().init(config);
	}
}
