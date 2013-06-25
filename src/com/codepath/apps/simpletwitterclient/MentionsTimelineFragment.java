package com.codepath.apps.simpletwitterclient;

import org.json.JSONArray;

import android.os.Bundle;

import com.codepath.apps.simpletwitterclient.SimpleTwitterApp;
import com.codepath.apps.simpletwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SimpleTwitterApp.getRestClient().getMentions(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsonTweets) {
						setTweets(Tweet.fromJson(jsonTweets));
					}
				});
	}
}