package com.codepath.apps.simpletwitterclient;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.codepath.apps.simpletwitterclient.models.Tweet;
import com.codepath.apps.simpletwitterclient.models.User;


public class TweetsListFragment extends Fragment {
	
	ArrayList<Tweet> tweets;
	TweetsAdapter adapter;
	FragmentActivity listener;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_twitter, container, false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		tweets = new ArrayList<Tweet>();
				//Tweet.fromJson(jsonTweets);
		adapter = new TweetsAdapter(getActivity(), tweets);
		ListView lv = (ListView)getActivity().findViewById(R.id.lvTweets);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View parent, int position,
					long rowId) {
				Tweet tweet = tweets.get(position);
				User user = tweet.getUser();
				Intent i = new Intent(getActivity().getApplicationContext(), ProfileActivity.class);
				i.putExtra("user", user);
				startActivity(i);
				
			}
		});
	}
	
	public void setTweets(ArrayList<Tweet> tweets) {
		adapter.addAll(tweets);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.listener = (FragmentActivity) activity;
	}

}
