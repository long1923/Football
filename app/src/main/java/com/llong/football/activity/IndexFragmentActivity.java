package com.llong.football.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.llong.football.R;
import com.llong.football.databinding.ActivityIndexBinding;

/**
 * tab页面框架
 * @author long
 *
 */
public class IndexFragmentActivity extends BaseActivity {

	/**
	 * TabHost管理器对象
	 */
	protected TabHostManager hostManager;
	

	ActivityIndexBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);

		binding.tabHostLayout.setup();
		hostManager=new TabHostManager(this, binding.tabHostLayout, android.R.id.tabcontent);
		
	}

	/**
	 * 向布局中填充内容
	 * @param fragments		所要填充的之类Fragment	list集合
	 * @param titles		所要填充的标题View		list集合
	 * @param tabs
	 */
	protected void setPageAndTitle(List<Class> fragments,List<View> titles,List<String> tabs){
		int size=(fragments.size()>titles.size())?titles.size():fragments.size();
		for(int i=0;i<size;i++){
			hostManager.addTab(binding.tabHostLayout.newTabSpec(tabs.get(i)).setIndicator(titles.get(i)), fragments.get(i), null);
		}
		
	}
	/**
	 * 设置页面的背景颜色		默认为白色
	 * @param resid		资源id
	 */
	protected void setPageBackgroundResource(int resid){
		binding.pageLayout.setBackgroundResource(resid);
	}
	
	/**
	 * 设置标题栏分割线颜色	默认为灰色	#CCCCCC
	 * @param resid		资源id
	 */
	protected void setTitlebarLineColorResource(int resid){
		binding.titlebarLine.setBackgroundResource(resid);
	}
	
	/**
	 * 设置标题栏背景颜色		默认为白色
	 * @param resid		资源id
	 */
	public void setTitlebarColorResource(int resid){
		binding.titlebarLayout.setBackgroundResource(resid);
	}
	
	
	protected static class TabHostManager implements TabHost.OnTabChangeListener{
		private final IndexFragmentActivity mActivity;
		private final TabHost mTabHost;
		private final int mContainerId;
		private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
		private TabInfo mLastTab;

		static final class TabInfo{
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;
			private Fragment fragment;

			TabInfo(String _tag, Class<?> _class, Bundle _args){
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory{
			private final Context mContext;

			public DummyTabFactory(Context context){
				mContext = context;
			}

			@Override
			public View createTabContent(String tag){
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabHostManager(IndexFragmentActivity activity, TabHost tabHost, int containerId){
			mActivity = activity;
			mTabHost = tabHost;
			mContainerId = containerId;
			mTabHost.setOnTabChangedListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args){
			tabSpec.setContent(new DummyTabFactory(mActivity));
			String tag = tabSpec.getTag();
			TabInfo info = new TabInfo(tag, clss, args);
			info.fragment = mActivity.getSupportFragmentManager().findFragmentByTag(tag);
			if (info.fragment != null && !info.fragment.isDetached()){
				FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
				info.fragment.onResume();
				ft.detach(info.fragment);
				ft.commit();
			}
			mTabs.put(tag, info);
			mTabHost.addTab(tabSpec);
		}

		@Override
		public void onTabChanged(String tabId){
			TabInfo newTab = mTabs.get(tabId);
			if("index".equals(tabId)){
				mActivity.setTitlebarColorResource(R.color.new_index_bg);
				mActivity.setTitlebarLineColorResource(R.color.new_index_bg);
			}else if("special".equals(tabId)){
				mActivity.setTitlebarColorResource(R.color.white);
				mActivity.setTitlebarLineColorResource(R.color.new_title_line_bg);
			}else if("me".equals(tabId)){
				mActivity.setTitlebarColorResource(R.color.white);
				mActivity.setTitlebarLineColorResource(R.color.new_title_line_bg);
			}
			if (mLastTab != newTab){
				FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
				if (mLastTab != null){
					if (mLastTab.fragment != null){
						ft.hide(mLastTab.fragment);
					}
				}
				if (newTab != null){
					if (newTab.fragment == null){
						newTab.fragment = Fragment.instantiate(mActivity, newTab.clss.getName(), newTab.args);
						ft.add(mContainerId, newTab.fragment, newTab.tag);
					} else{
						ft.show(newTab.fragment);
						newTab.fragment.onResume();
					}
				}
				mLastTab = newTab;
				ft.commit();
				mActivity.getSupportFragmentManager().executePendingTransactions();
			}
		}
	}
	
}
