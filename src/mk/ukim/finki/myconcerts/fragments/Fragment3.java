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

import mk.ukim.finki.myconcerts.DetailsActivity;
import mk.ukim.finki.myconcerts.GPSTracker;
import mk.ukim.finki.myconcerts.MapActivity;
import mk.ukim.finki.myconcerts.R;
import mk.ukim.finki.myconcerts.adapters.EventItemAdapter;
import mk.ukim.finki.myconcerts.model.Event;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment3 extends Fragment{
	
	private SeekBar distanceBar;
	private TextView radius;
	View rootView;
	public ArrayList<Event> listOfEvents;
	EventItemAdapter adapter;
	ListView eventListView;
	TextView noNear;
	String radiusVal;
	
	Button showMap;
	GPSTracker gps;
	double  myLat;
	double  myLong;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment3, container, false);
		
		listOfEvents=new ArrayList<Event>();
		eventListView=(ListView) rootView.findViewById(R.id.nearestEvents);
		eventListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				showDetails(position);
				
			}
		});
		noNear=(TextView) rootView.findViewById(R.id.noNear);
		adapter=new EventItemAdapter(getActivity().getBaseContext());
		
		distanceBar=(SeekBar) rootView.findViewById(R.id.seekBar);
		radius=(TextView) rootView.findViewById(R.id.radius);
		
		radius.setText("Radius distance: "+distanceBar.getProgress());		

		gps=new GPSTracker(getActivity());
		
        
        if(gps.canGetLocation()){
        	myLat=gps.getLatitude();
        	myLong=gps.getLongitude();
        	System.out.println(myLat+","+myLong);
        }
        else
        	gps.showSettingsAlert();
        
        
        distanceBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
  		  int progress = 0;
  		  
  		  @Override
  		  public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
  			  progress = progresValue;
  			 // Toast.makeText(getActivity(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
  		  }
  		
  		  @Override
  		  public void onStartTrackingTouch(SeekBar seekBar) {
  			 // Toast.makeText(getActivity(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
  		  }
  		
  		  @Override
  		  public void onStopTrackingTouch(SeekBar seekBar) {
  			  radius.setText("Radius distance: " + progress);
  			  radiusVal=String.valueOf(progress);
  			  showEventsByPlace(rootView);
  			 // Toast.makeText(getActivity(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
  		  }
  	   });
        
       
        
        showMap=(Button) rootView.findViewById(R.id.map);
        showMap.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),MapActivity.class);
				intent.putExtra("latitudes", getLats(listOfEvents));
				intent.putExtra("longitudes", getLongs(listOfEvents));
				intent.putExtra("names",getNames(listOfEvents));
				startActivity(intent);
			}
		});
         
        return rootView;
    }
	
	public String[] getNames(ArrayList<Event> evs){
		if (evs==null)
			return null;
		String [] name=new String[evs.size()];
		for(int i=0;i<evs.size();i++){
			name[i]=evs.get(i).getName();
		}
		return name;
	}
	
	public double[] getLats(ArrayList<Event> evs){
		if (evs==null)
			return null;
		double [] lats=new double[evs.size()];
		for(int i=0;i<evs.size();i++){
			lats[i]=evs.get(i).getLatitude();
		}
		return lats;
	}
	public double[] getLongs(ArrayList<Event> evs){
		if (evs==null)
			return null;
		double [] longs=new double[evs.size()];
		for(int i=0;i<evs.size();i++){
			longs[i]=evs.get(i).getLongitude();
		}
		return longs;
	}
	
	public void showDetails(int position){
		
		Intent intent=new Intent(getActivity(),DetailsActivity.class);
		
		intent.putExtra("name", listOfEvents.get(position).getName());
		intent.putExtra("artists",listOfEvents.get(position).artistsToString());
		intent.putExtra("venueName",listOfEvents.get(position).getVenueName());
		intent.putExtra("venueStreet",listOfEvents.get(position).getVenueStreet());
		intent.putExtra("venueCity",listOfEvents.get(position).getVenueCity());
		intent.putExtra("venueCountry",listOfEvents.get(position).getVenueCountry());
		intent.putExtra("startDate",listOfEvents.get(position).getStartDate());
		intent.putExtra("image",listOfEvents.get(position).getImageBig());
		startActivity(intent);		
	}
	
	public void showEventsByPlace(View view) {
		String lat=String.valueOf(myLat);
		String lon=String.valueOf(myLong);
		String serverURL = getString(R.string.lastFmUrl);
		String apiKey = getString(R.string.APIkey);
		String url = String.format(
				"%s?method=geo.getevents&lat=%s&long=%s&distance=%s&limit=5000&api_key=%s&format=json",
				serverURL, lat,lon,radiusVal, apiKey);
		System.out.println(url);
		System.out.println(url);
		new EventDownloader().execute(url);
	}
	
	
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
				noNear.setText("There are no upcoming concerts near you");
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
