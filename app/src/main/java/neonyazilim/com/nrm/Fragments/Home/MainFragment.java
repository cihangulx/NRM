package neonyazilim.com.nrm.Fragments.Home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import neonyazilim.com.nrm.R;

/**
 * Created by tuzlabim on 26.10.2017.
 */

public class MainFragment extends Fragment {

    public ViewPager viewPager;
    public TabHost tabHost;
    public FragmentManager fragmentManager;
    public TabHost.TabSpec pencere1, pencere2, pencere3, pencere4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);



        tabHost = (TabHost) view.findViewById(R.id.tabhost);
        tabHost.setup();
        pencere1 = tabHost.newTabSpec("Projeler");
        pencere1.setContent(R.id.tab1);
        pencere1.setIndicator("Talepler");
        tabHost.addTab(pencere1);
        tabHost.setVisibility(View.VISIBLE);

        ///Tab 2 ;
        pencere2 = tabHost.newTabSpec("Projeler");
        pencere2.setContent(R.id.tab2);
        pencere2.setIndicator("Projeler");
        tabHost.addTab(pencere2);


        //ViewPager Tanımlamaları
        fragmentManager = getFragmentManager();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);


        viewPager.setAdapter(new Adapter(fragmentManager));
        viewPager.getAdapter().notifyDataSetChanged();

        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        TextView tv1 = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
        tv.setTextColor(getResources().getColor(R.color.White));
        tv1.setTextColor(Color.parseColor("#424242"));
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int i=tabHost.getCurrentTab();

                TextView tv = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
                TextView tv1 = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);

                viewPager.setCurrentItem(i,true);
                if (i==0){
                    tv.setTextColor(getResources().getColor(R.color.White));
                    tv1.setTextColor(Color.parseColor("#424242"));


                }else if (i==1){
                    tv.setTextColor(Color.parseColor("#424242"));
                    tv1.setTextColor(getResources().getColor(R.color.White));

                }
            }
        });

        //Sayfa Kaydırıldığı zaman olacaklar
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                tabHost.setCurrentTab(position);
            }

            public void onPageScrollStateChanged(int state) {
            }
        });


        return view;
    }
    private class Adapter extends FragmentPagerAdapter {
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new TaleplerFragment();
            } else if (position == 1) {
                fragment = new ProjelerFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
