package com.cdroid.timedroid;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

public class DisplayResults extends Activity {

	private final static String TAG = "DisplayResultsLog";
	private static final String TAG_PROJ_TIME = "proj_time";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_results);

		ListView listview = (ListView) findViewById(R.id.list_view_id);

		listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(DisplayResults.this, "" + position,
						Toast.LENGTH_SHORT).show();
			}

		});

		ArrayList<String> result_display_list = new ArrayList<String>();

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, result_display_list);
		listview.setAdapter(arrayAdapter);

		Intent intent = getIntent();
		String jsonArray = intent.getStringExtra(TAG_PROJ_TIME);

		try {
			JSONArray projTimeObj = new JSONArray(jsonArray);
			JSONObject proj_time;

			String s = new String();
			projTimeObj.length();
			for(int row = 0; row < projTimeObj.length(); row++){
				proj_time = projTimeObj.getJSONObject(row);
				s = String.format("%s: %s minutes notes: %s", 
						proj_time.get("description").toString(),
						proj_time.get("minutes").toString(),
						proj_time.get("notes").toString());
				result_display_list.add(s);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		Log.i(TAG, "done : onCreate");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_results, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}

}
