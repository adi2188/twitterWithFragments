package com.codepath.apps.simpletwitterclient;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitterclient.models.Tweet;
import com.codepath.apps.simpletwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	TweetsListFragment fragmentTweetsList;
	TwitterClient client;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		User u = (User) getIntent().getSerializableExtra("user");
		if (u == null) {
		client = SimpleTwitterApp.getRestClient();
		client.getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				user = User.fromJson(json);
				getActionBar().setTitle("@" + user.getScreenName());
				populateProfileHeader(user);
			}
		});
		populateProfileTweets(null);
		} else {
			Log.d("DEBUG", u.getScreenName());
			getActionBar().setTitle("@" + u.getScreenName());
			populateProfileHeader(u);
			populateProfileTweets(u);
		}
	}

	private void populateProfileHeader(User u) {
		TextView tvName = (TextView) findViewById(R.id.tvFullName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowersCount() + " Followers");
		tvFollowing.setText(user.getFriendsCount() + " Following");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(),
				ivProfileImage);
	}

	private void populateProfileTweets(User u) {
		String user_screen_name = null;
		if(user != null) {
			user_screen_name = u.getScreenName();
		}
		fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment_tweets_list);

		client.getUserTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				fragmentTweetsList.setTweets(Tweet.fromJson(jsonTweets));
			}
		}, user_screen_name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
