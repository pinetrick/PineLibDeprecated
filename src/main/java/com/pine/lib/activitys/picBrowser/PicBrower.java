package com.pine.lib.activitys.picBrowser;

import java.net.URLEncoder;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Base64;
import android.widget.TextView;

import com.pine.lib.R;
import com.pine.lib.base.activity.PineFragmentActivity;
import com.pine.lib.func.debug.G;
import com.pine.lib.view.UIInject.UiInject;
import com.pine.lib.view.UIInject.interfaces.InjectView;


public class PicBrower extends PineFragmentActivity
{
	private static G g = new G(PicBrower.class);
	public static String baseUrl = "http://bbs.skykiwi.com/forum.php?mod=attachment&noupdate=yes&aid=";

	public HackyViewPager mPager;

	// @InjectView(R.id.img)
	// public ImageView img; 

	public int picId;
	public List<String> imgUrlList = null;
	

	private TextView indicator;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.activity_pic_brower);
		super.onCreate(savedInstanceState);
		
		mPager = (HackyViewPager)findViewById(R.id.picViewpage);

		Intent intent = getIntent();
		picId = intent.getIntExtra("picId", 0);
		String[] urls = intent.getStringArrayExtra("imgUrlList");
		
		
		
		ImagePagerAdapter mAdapter = new ImagePagerAdapter(
				getSupportFragmentManager(), urls);
		mPager.setAdapter(mAdapter);
		indicator = (TextView) findViewById(R.id.indicator);

		CharSequence text = getString(R.string.viewpager_indicator, picId + 1, mPager
				.getAdapter().getCount());
		indicator.setText(text);
		// 更新下标
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				CharSequence text = getString(R.string.viewpager_indicator,
						arg0 + 1, mPager.getAdapter().getCount());
				indicator.setText(text);
			}

		});
//		if (savedInstanceState != null) {
//			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
//		}

		mPager.setCurrentItem(picId);
		
		
		//PicServer.i().setImgX(picUrl, img);
	}



	private class ImagePagerAdapter extends FragmentStatePagerAdapter
	{

		public String[] fileList;



		public ImagePagerAdapter(FragmentManager fm, String[] fileList)
		{
			super(fm);
			this.fileList = fileList;
		}


		@Override
		public int getCount()
		{
			return fileList == null ? 0 : fileList.length;
		}


		@Override
		public Fragment getItem(int position)
		{
			String url = fileList[position];
			return ImageDetailFragment.newInstance(url);
		}

	}





}
