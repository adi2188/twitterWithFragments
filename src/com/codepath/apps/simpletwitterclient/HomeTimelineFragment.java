package com.codepath.apps.simpletwitterclient;

import org.json.JSONArray;

import com.codepath.apps.simpletwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;

public class HomeTimelineFragment extends TweetsListFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		SimpleTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
    		@Override
    		public void onSuccess(int arg0, JSONArray jsonTweets) {
    			setTweets(Tweet.fromJson(jsonTweets));
    		}
    	});
	}

}
