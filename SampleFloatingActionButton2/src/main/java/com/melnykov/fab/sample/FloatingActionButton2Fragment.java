package com.melnykov.fab.sample;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import com.melnykov.fab.ScrollDirectionListener;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by x1210x on 2015-05-15.
 */
public class FloatingActionButton2Fragment extends Fragment implements MaterialTabListener {
	private MaterialTabHost tabHost;
	private ViewPager pager;
	private PagerAdapter adapter;

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_fab2, container, false);

		tabHost = (MaterialTabHost) view.findViewById(R.id.tabHost);
		pager = (ViewPager) view.findViewById(R.id.viewPager);

		adapter = new FragmentPagerAdapter(getFragmentManager()) {
			@Override
			public int getCount() {
				return 3;
			}

			@Override
			public Fragment getItem(int position) {
				if (position == 0) {
					return new ListViewFragment();
				} else if (position == 1) {
					return new RecyclerViewFragment();
				} else if (position == 2) {
					return new ScrollViewFragment();
				}
				return null;
			}

			@Override
			public String getPageTitle(int i) {
				if (i == 0) {
					return "ListView";
				} else if (i == 1) {
					return "RecyclerView";
				} else if (i == 2) {
					return "ScrollView";
				}
				return null;
			}
		};
		pager.setAdapter(adapter);

		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				tabHost.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		// insert all tabs from pagerAdapter data
		for (int i = 0; i < adapter.getCount(); i++) {
			tabHost.addTab(
					tabHost.newTab()
							.setText(adapter.getPageTitle(i))
							.setTabListener(this)
			);

		}
		return view;
	}

	@Override
	public void onTabSelected(MaterialTab materialTab) {
		pager.setCurrentItem(materialTab.getPosition());
	}

	@Override
	public void onTabReselected(MaterialTab materialTab) {

	}

	@Override
	public void onTabUnselected(MaterialTab materialTab) {

	}

	public static class ListViewFragment extends Fragment {

		@SuppressLint("InflateParams")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_listview, container, false);

			ListView list = (ListView) root.findViewById(android.R.id.list);
			ListViewAdapter listAdapter = new ListViewAdapter(getActivity(),
					getResources().getStringArray(R.array.countries));
			list.setAdapter(listAdapter);

			FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
			fab.attachToListView(list, new ScrollDirectionListener() {
				@Override
				public void onScrollDown() {
					Log.d("ListViewFragment", "onScrollDown()");
				}

				@Override
				public void onScrollUp() {
					Log.d("ListViewFragment", "onScrollUp()");
				}
			}, new AbsListView.OnScrollListener() {
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					Log.d("ListViewFragment", "onScrollStateChanged()");
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
					Log.d("ListViewFragment", "onScroll()");
				}
			});

			return root;
		}
	}

	public static class RecyclerViewFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_recyclerview, container, false);

			RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
			recyclerView.setHasFixedSize(true);
			recyclerView.setItemAnimator(new DefaultItemAnimator());
			recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
			recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

			RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), getResources()
					.getStringArray(R.array.countries));
			recyclerView.setAdapter(adapter);

			FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
			fab.attachToRecyclerView(recyclerView);

			return root;
		}
	}

	public static class ScrollViewFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.fragment_scrollview, container, false);

			ObservableScrollView scrollView = (ObservableScrollView) root.findViewById(R.id.scroll_view);
			LinearLayout list = (LinearLayout) root.findViewById(R.id.list);

			String[] countries = getResources().getStringArray(R.array.countries);
			for (String country : countries) {
				TextView textView = (TextView) inflater.inflate(R.layout.list_item, container, false);
				String[] values = country.split(",");
				String countryName = values[0];
				int flagResId = getResources().getIdentifier(values[1], "drawable", getActivity().getPackageName());
				textView.setText(countryName);
				textView.setCompoundDrawablesWithIntrinsicBounds(flagResId, 0, 0, 0);

				list.addView(textView);
			}

			FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
			fab.attachToScrollView(scrollView);

			return root;
		}
	}
}
