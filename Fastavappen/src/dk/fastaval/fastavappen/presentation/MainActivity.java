package dk.fastaval.fastavappen.presentation;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import dk.fastaval.fastavappen.R;
import dk.fastaval.fastavappen.fragments.MainFragment;
import dk.fastaval.fastavappen.fragments.ProgramFragment;

public class MainActivity extends SherlockFragmentActivity {

	private FragAdapter mAdapter;
    private ViewPager mViewPager;

    private static Context mContext;
    
	private ArrayList<Fragment> mFragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);

		mContext = this;
		
		mFragments = new ArrayList<Fragment>();
		mFragments.add(new MainFragment());
		mFragments.add(new ProgramFragment());
		
		mAdapter = new FragAdapter(getSupportFragmentManager(), mFragments);
		 
		setContentView(R.layout.main_layout);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAdapter);
		
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	}
	
	public static class FragAdapter extends FragmentPagerAdapter {
		
		private ArrayList<Fragment> mFragments;
		
        public FragAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }
 
        @Override
        public int getCount() {
            return 2;
        }
 
        @Override
        public CharSequence getPageTitle(int position) {
        	if (getCount() == 2) {
        		switch (position) {
				case 0:
					return MainActivity.mContext.getText(R.string.title_main_page);
				case 1:
					return MainActivity.mContext.getText(R.string.title_fastaval_program);
				default:
					return null;
				}
        	} else if (getCount() == 3) {
        		switch (position) {
				case 0:
					return MainActivity.mContext.getText(R.string.title_user_page);
				case 1:
					return MainActivity.mContext.getText(R.string.title_main_page);
				case 2:
					return MainActivity.mContext.getText(R.string.title_fastaval_program);
				default:
					return null;
				}
        	}
			return null;
        }

        @Override
        public Fragment getItem(int position) {
        	return mFragments.get(position);
        }
    }
}