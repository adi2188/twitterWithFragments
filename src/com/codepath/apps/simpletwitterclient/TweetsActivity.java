package com.codepath.apps.simpletwitterclient;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.simpletwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TweetsActivity extends FragmentActivity implements TabListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweets);
		setupNavigationTabs();

		SimpleTwitterApp.getRestClient().getMyInfo(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject json) {
						User user = User.fromJson(json);
						getActionBar().setTitle(
								user.getName().split("\\s+")[0] + "'s Tweets");
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tweets, menu);
		return true;
	}

	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar.newTab().setText("Home").setTabListener(this)
				.setTag("HomeTimelineFragment").setIcon(R.drawable.ic_launcher);

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar.newTab().setText("Mentions").setTabListener(this)
				.setTag("MentionsTimelineFragment")
				.setIcon(R.drawable.ic_launcher);

		actionBar.addTab(tab2);
	}

	public void onProfileView(MenuItem mi) {
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Class<? extends Fragment> fragmentClass = HomeTimelineFragment.class;
		if (tab.getTag() == "MentionsTimelineFragment") {
			fragmentClass = MentionsTimelineFragment.class;
		}
		android.support.v4.app.FragmentTransaction fts = getSupportFragmentManager()
				.beginTransaction();
		Fragment fragment = Fragment.instantiate(this, fragmentClass.getName());
		fts.replace(R.id.frame_container, fragment);
		fts.commit();
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

}