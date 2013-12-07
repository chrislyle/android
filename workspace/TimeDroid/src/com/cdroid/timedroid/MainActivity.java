package com.cdroid.timedroid;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemSelectedListener {

	EditText editLoginName;
	Spinner spinYear;
	Spinner spinWeek;
	Spinner spinDay;
	String login_name;
	String day_no;
	String week_no;
	String year_no;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	// single product url
	private static final String url_get_times = "http://10.0.3.3/get_weeks_time1.php";
	// http://localhost/get_weeks_time1.php?login_name=clyle&day_no=0&week_no=2&year_no=2012

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_PROJ_TIME = "proj_time";
	private static final String TAG_LOGIN_NAME = "login_name";
	private static final String TAG_DAY_NO = "day_no";
	private static final String TAG_WEEK_NO = "week_no";
	private static final String TAG_YEAR_NO = "year_no";

	ArrayList<String> spinWeekNoArray = new ArrayList<String>();

	private final static String TAG = "MainActivityLog";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editLoginName = (EditText) findViewById(R.id.edit_login_id);

		// Year
		spinYear = (Spinner) findViewById(R.id.spin_year_id);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
				this, R.array.spin_year_str_array,
				android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinYear.setAdapter(adapter1);
		spinYear.setOnItemSelectedListener(this);

		// Week
		for (int i = 0; i < 52; i++) {
			spinWeekNoArray.add(new String(String.format(Locale.ENGLISH,
					"%02d", i + 1)));
		}

		spinWeek = (Spinner) findViewById(R.id.spin_week_id);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinWeekNoArray);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinWeek.setAdapter(adapter2);
		spinWeek.setOnItemSelectedListener(this);

		// DOW
		spinDay = (Spinner) findViewById(R.id.spin_dow_id);
		ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
				this, R.array.spin_dow_str_array,
				android.R.layout.simple_spinner_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinDay.setAdapter(adapter3);
		spinDay.setOnItemSelectedListener(this);

		Intent i = getIntent();
		login_name = i.getStringExtra(TAG_LOGIN_NAME);
		day_no = i.getStringExtra(TAG_DAY_NO);
		week_no = i.getStringExtra(TAG_WEEK_NO);
		year_no = i.getStringExtra(TAG_YEAR_NO);

		Log.w(TAG, "finished on create");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		Log.w(TAG, "onCreateOptionsMenu");
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {

		switch (parent.getId()) {
		case R.id.spin_year_id:
			year_no = parent.getItemAtPosition(pos).toString();
			Log.i(TAG, year_no);
			break;
		case R.id.spin_dow_id:
			day_no = String.format(Locale.ENGLISH, "%d", pos);
			Log.i(TAG, day_no);
			break;
		case R.id.spin_week_id:
			week_no = String.format(Locale.ENGLISH, "%d", pos + 1);
			Log.i(TAG, week_no);
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		Log.w(TAG, "onNothingSelected");
	}

	public void onButGetTimes(View view) {
		login_name = editLoginName.getText().toString();
		new GetTimeSheetDetails().execute();
	}

	/*
	 * Background Asynchronous Task to Get time sheet entries
	 */
	class GetTimeSheetDetails extends AsyncTask<String, String, String> {

		/*
		 * Before starting background thread Show Progress Dialog
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Loading timesheet. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/*
		 * Getting product details in background thread
		 */
		protected String doInBackground(String... args) {

			runOnUiThread(new Runnable() {
				public void run() {
					
					// Building Parameters
					List<NameValuePair> params = new ArrayList<NameValuePair>();

					params.add(new BasicNameValuePair(TAG_LOGIN_NAME,
							"clyle"));
					params.add(new BasicNameValuePair(TAG_DAY_NO, day_no));
					params.add(new BasicNameValuePair(TAG_WEEK_NO, week_no));
					params.add(new BasicNameValuePair(TAG_YEAR_NO, year_no));

					JSONObject json = jsonParser.makeHttpRequest(url_get_times,
							"GET", params);

					if (json == null) {
						Toast.makeText(MainActivity.this,
								"ERROR: makeHttpRequest returned null",
								Toast.LENGTH_LONG).show();
						Log.e(TAG, "ERROR: makeHttpRequest returned null");
					} else {

						try {
							int success = json.getInt(TAG_SUCCESS);

							if (success == 1) {

								Intent intent = new Intent(MainActivity.this,
										DisplayResults.class);

								intent.putExtra(TAG_PROJ_TIME,json.getJSONArray(TAG_PROJ_TIME).toString());

								startActivity(intent);

							} else {
								Toast.makeText(
										MainActivity.this,
										"WARNING: "
												+ json.getString(TAG_MESSAGE),
										Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							Toast.makeText(MainActivity.this,
									"ERROR: " + e.getMessage(),
									Toast.LENGTH_LONG).show();
						}
					}

				}
			});

			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
			pDialog.dismiss();
		}
	}

}
