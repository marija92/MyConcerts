package mk.ukim.finki.myconcerts.fragments;

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

import mk.ukim.finki.myconcerts.R;
import mk.ukim.finki.myconcerts.adapters.EventItemAdapter;
import mk.ukim.finki.myconcerts.model.Event;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment2 extends Fragment{
	
	ListView eventListView;
	View rootView;
	EventItemAdapter adapter;
	public ArrayList<Event> listOfEvents;
	Button search;
	TextView noEvents;
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment2, container, false);      
		
		eventListView=(ListView) rootView.findViewById(R.id.listArtists);	
		adapter = new EventItemAdapter(getActivity().getBaseContext());
		listOfEvents= new ArrayList<Event>();
		noEvents=(TextView) rootView.findViewById(R.id.noEvents);
		search=(Button) rootView.findViewById(R.id.btnSearch);
				
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showEventsByArtist(v);
				//adapter.notifyDataSetChanged();
			}
		});

		eventListView.setAdapter(adapter);
		eventListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View parent,
					int position, long id) {
				Toast.makeText(getActivity().getBaseContext(), "Item long click",
						Toast.LENGTH_LONG).show();
				return true;
			}
		}); 
         super.onCreateView(inflater,container,savedInstanceState);		
           
         return rootView;      
    }
	
	public void showEventsByArtist(View view) {
		EditText artistEdit = (EditText) rootView.findViewById(R.id.editText);
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

	
	/***Async Task***/
	
	private class EventDownloader extends AsyncTask<String, Void, String> {
		
		private ProgressDialog loadingDialog;

		private void createDialog() {
			loadingDialog = new ProgressDialog(getActivity());
			loadingDialog.setTitle("Downloading");
			loadingDialog
					.setMessage("Please wait while we are downloading the events.");
			loadingDialog.setIndeterminate(true);
			loadingDialog.setCancelable(false);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			createDialog();
			loadingDialog.show();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

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
			// vraka arraylist od event
			listOfEvents = getEvents(result);
			if (loadingDialog != null && loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}
			if(listOfEvents!=null){
			adapter.clear();
			adapter.addAll(listOfEvents);
			eventListView.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			}
			else{
				adapter.clear();
				noEvents.setText("There are no upcoming concerts for this artist");
			}
		}
	}

	public Event createEvent(JSONObject obj) {
		Event event = new Event();

		event.setLastFmID(obj.get("id").toString());
		event.setName(obj.get("title").toString());
		event.setStartDate(obj.get("startDate").toString());

		ArrayList<JSONObject> images = (ArrayList<JSONObject>) obj.get("image");
		event.setImageSmall(images.get(2).get("#text").toString());
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
		if(event==null)
			return null;
		JSONArray ev = (JSONArray) event.get("event");
		if (ev == null)
			return null;
		else {
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
