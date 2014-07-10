package com.mingyuanchen.viewpager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity implements OnPageChangeListener {
	//ͼƬ��Դ
	private int[] imageResIds = {
			R.drawable.a,
			R.drawable.b,
			R.drawable.c,
			R.drawable.d,
			R.drawable.e
	};
	//ͼƬ����
	private String []imageDescriptions ={
			"���������ף��ҾͲ��ܵ���",
			"�����ֻ��������ٳ������ϸ������˴�ϳ�",
			"���ر�����Ӱ�������",
			"������TV�������",
			"��Ѫ��˿�ķ�ɱ"
			};
	
	private List<ImageView> imageViewList = new ArrayList<ImageView>();
	private ViewPager mViewPager;
	private TextView tvImageDescription;
	private boolean isStop = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		// ����һ�����߳�, ÿ��һ�����л�һ��ҳ��
        new Thread(new Runnable() {
        	@Override
        	public void run() {
        		while(true) {
        			SystemClock.sleep(2000); // ˯һ��
        			
        			if(isStop) {
        				break;
        			}
        			
        			// ���յ�runnable�����е�run����, ��Ҫ���������߳���.
        			runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// �õ�һ���µ�item������
							int newCurrentItem = mViewPager.getCurrentItem() +1;
							mViewPager.setCurrentItem(newCurrentItem);
						}
        			});
        		}
        	}
        }).start();
	}

	/**
	 * 1.�ҵ�ViewPager
	 * 2.��ViewPager����װ��ImageView������
	 * 3.׼��ͼƬ�л��õ�С��
	 * 4.����page�仯��ļ���
	 */
	private void init() {
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		tvImageDescription = (TextView) findViewById(R.id.tv_image_description);
		llPointGroup = (LinearLayout) findViewById(R.id.ll_point);
		
		ImageView imageView ;
		for (int i = 0; i < imageResIds.length; i++) {
			imageView = new ImageView(this);
			imageView.setBackgroundResource(imageResIds[i]);
			
			imageViewList.add(imageView);
			//���������·�����С���״̬
			View view = new View(this);
			LayoutParams params = new LayoutParams(5, 5);
			view.setLayoutParams(params);
			params.leftMargin = 10;
			view.setBackgroundResource(R.drawable.point_bg);
			view.setEnabled(false);
			
			llPointGroup.addView(view);
		}
		
		tvImageDescription.setText(imageDescriptions[0]);
		PagerAdapter adapter = new NumberAdapter();
		mViewPager.setAdapter(adapter);
		//�÷�������������֮��
		int currentItem = Integer.MAX_VALUE/2;
		currentItem -= currentItem%imageViewList.size();
		mViewPager.setCurrentItem(currentItem);
		

		mViewPager.setOnPageChangeListener(this);
	}


	private class NumberAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			int newPosition = position%imageViewList.size();
			container.removeView(imageViewList.get(newPosition));
//			07-07 14:18:07.130: E/AndroidRuntime(1468): java.lang.UnsupportedOperationException: Required method destroyItem was not overridden
//			super.destroyItem(container, position, object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			int newPosition = position % imageViewList.size();
			System.out.println("position:"+position+",newPosition:"+newPosition);
			mViewPager.addView(imageViewList.get(newPosition));
			return imageViewList.get(newPosition);
		}
		
	}


	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	private int previousPointPosition = 0;
	private LinearLayout llPointGroup;
	@Override
	public void onPageSelected(int position) {

		int newPosition = position % imageViewList.size();
		tvImageDescription.setText(imageDescriptions[newPosition]);
		llPointGroup.getChildAt(previousPointPosition).setEnabled(false);
		llPointGroup.getChildAt(newPosition).setEnabled(true);
		
		previousPointPosition = newPosition;
	}

	@Override
	protected void onDestroy() {
		isStop = true;
		super.onDestroy();
	}
}
