package mk.ukim.finki.myconcerts;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import mk.ukim.finki.myconcerts.adapters.TabsPagerAdapter;
import mk.ukim.finki.myconcerts.model.Event;
import android.app.ActionBar.Tab;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Upcoming", "Artists", "Nearest" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	public void showEventsByArtist(View view) {
		EditText artistEdit = (EditText) findViewById(R.id.editText);
		String artistName = artistEdit.getText().toString();
		if (artistName.contains(" ")) {
			artistName = artistName.replace(" ", "%20");
		}
		String serverURL = getString(R.string.lastFmUrl);
		String apiKey = getString(R.string.APIkey);
		String url = String.format(
				"%s?method=artist.getevents&artist=%s&api_key=%s&format=json",
				serverURL, artistName, apiKey);
		System.out.println(url);
		new EventDownloader().execute(url);
	}

	
	
	
	private class EventDownloader extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... urls) {
			String response = "";
			String url = urls[0];

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			try {
				HttpResponse execute = client.execute(httpGet);
				InputStream content = execute.getEntity().getContent();

				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					response += s;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			System.out.println(result);
			// vraka arraylist od event
			getEvents(result);
		}
	}

	public Event createEvent(JSONObject obj) {
		Event event = new Event();

		event.setLastFmID(obj.get("id").toString());
		event.setName(obj.get("title").toString());
		event.setStartDate(obj.get("startDate").toString());

		ArrayList<JSONObject> images = (ArrayList<JSONObject>) obj.get("image");
		event.setImageSmall(images.get(1).get("#text").toString());
		event.setImageBig(images.get(3).get("#text").toString());

		JSONObject artists = (JSONObject) obj.get("artists");
		Object art = artists.get("artist");
		if (art instanceof JSONArray) {
			ArrayList<JSONObject> artist = (ArrayList<JSONObject>) artists.get("artist");
			for (Object a : artist) {
				event.addArtists(a.toString());
			}
		} else {
			event.addArtists(art.toString());
		}

		JSONObject venue = (JSONObject) obj.get("venue");
		event.setVenueName(venue.get("name").toString());
		JSONObject venueDetails = (JSONObject) venue.get("location");
		event.setVenueCity(venueDetails.get("city").toString());
		event.setVenueCountry(venueDetails.get("country").toString());
		event.setVenueStreet(venueDetails.get("street").toString());
		JSONObject geo = (JSONObject) venueDetails.get("geo:point");
		if (geo.get("geo:lat") != "") {
			event.setLatitude(Double.parseDouble(geo.get("geo:lat").toString()));
		}
		if (geo.get("geo:long") != "") {
			event.setLongitude(Double.parseDouble(geo.get("geo:long")
					.toString()));
		}

		return event;
	}

	public ArrayList<Event> getEvents(String result) {

		ArrayList<Event> eventList = new ArrayList<Event>();

		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("GRESKAAAAAAAAA");
		}

		JSONObject event = (JSONObject) jsonObject.get("events");
		JSONArray ev = (JSONArray) event.get("event");
		if (ev == null) {
			return null;
		} else {
			Iterator<JSONObject> iterator = ev.iterator();
			while (iterator.hasNext()) {
				JSONObject tmpObj = iterator.next();
				Event e = createEvent(tmpObj);
				eventList.add(e);
			}
		}

		return eventList;
	}

}