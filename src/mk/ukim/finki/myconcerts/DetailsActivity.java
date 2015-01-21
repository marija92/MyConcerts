package mk.ukim.finki.myconcerts;

import java.util.ArrayList;

import mk.ukim.finki.myconcerts.db.EventDao;
import mk.ukim.finki.myconcerts.model.Event;

import com.androidquery.AQuery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class DetailsActivity extends Activity {

	ImageView image;
	TextView name;
	TextView artists;
	TextView venue;
	TextView venueStreet;
	TextView venueCity;
	TextView venueCountry;
	TextView startDate;
	Button addToCal;
	AQuery aquery;
	Button delete;

	EventDao dao;

	String artists1;
	String lastfmID1;
	String image1;
	String venName;
	String venStreet;
	String venCity;
	String venCountry;
	String date;
	String evName;
	String imageSmall;

	String dbID;
	String eventID;
	boolean deleteFlag = false;

	public void init() {
		aquery = new AQuery(this);
		image = (ImageView) findViewById(R.id.image);
		name = (TextView) findViewById(R.id.eventName1);
		artists = (TextView) findViewById(R.id.eventArtist1);
		venue = (TextView) findViewById(R.id.eventVenueName1);
		venueStreet = (TextView) findViewById(R.id.eventVenueStreet1);
		venueCity = (TextView) findViewById(R.id.eventVenueCity1);
		venueCountry = (TextView) findViewById(R.id.eventVenueCountry1);
		startDate = (TextView) findViewById(R.id.eventStartDate1);
		addToCal = (Button) findViewById(R.id.addToCal);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_details);
		init();

		Bundle extras = getIntent().getExtras();

		
		

		// delte=new Button

		lastfmID1 = extras.getString("lastFmID");
		evName = extras.getString("name");
		artists1 = extras.getString("artists");
		image1 = extras.getString("image");
		venName = extras.getString("venueName");
		venStreet = extras.getString("venueStreet");
		venCity = extras.getString("venueCity");
		venCountry = extras.getString("venueCountry");
		date = extras.getString("startDate");
		imageSmall = extras.getString("imgSmall");

		aquery.id(image).image(extras.getString("image"), false, false);
		name.setText(extras.getString("name"));
		artists.setText(extras.getString("artists"));
		venue.setText(extras.getString("venueName"));
		venueStreet.setText(extras.getString("venueStreet"));
		venueCity.setText(extras.getString("venueCity"));
		venueCountry.setText(extras.getString("venueCountry"));
		startDate.setText(extras.getString("startDate"));

		if (extras.getLong("ID") != 0L && extras.getString("eventID") != null) {
			dbID = extras.getLong("ID") + "";
			eventID = extras.getString("eventID");
			deleteFlag = true;
			addToCal.setText("Delete from calendar");
			System.out.println("flag is true");
		} else {
			addToCal.setText("Add to calendar");
		}

		dao = new EventDao(this);
		dao.open();

		Event e = dao.existsLastFMID(lastfmID1);

		if(e != null){
			eventID = e.getEventID();
			dbID = e.getId()+"";
			deleteFlag = true;
			addToCal.setText("Delete from calendar");
			System.out.println("flag is true");
		}
		
		
		
		addToCal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (deleteFlag == false) {

					Event e = new Event(lastfmID1, evName, artists1, venName,
							venCity, venCountry, venStreet, date, imageSmall,
							image1);
					long eventid = CalendarUtils.addEventToCalender(
							getContentResolver(), e);
					System.out.println("event id:" + eventid);
					e.setEventID(eventid + "");

					System.out.println("artists " + e.artistsToString());
					
					
					dao.insert(e);
					Toast.makeText(getBaseContext(), "Event added to calendar",
							Toast.LENGTH_SHORT).show();
					eventID = eventid + "";
					dbID = e.getId() + "";
					addToCal.setText("Delete from calendar");
					deleteFlag = true;
				} else {
					Event e = new Event(lastfmID1, evName, artists1, venName,
							venCity, venCountry, venStreet, date, imageSmall,
							image1);
					e.setEventID(eventID);
					e.setId(Long.parseLong(dbID));
					CalendarUtils.deleteEventFromCalendar(getContentResolver(),
							Long.valueOf(eventID));
					dao.deleteItem(Integer.parseInt(dbID));
					Toast.makeText(getBaseContext(),
							"Event deleted from calendar", Toast.LENGTH_SHORT)
							.show();
					addToCal.setText("Add to calendar");
					deleteFlag = false;
					eventID = null;
					dbID = null;
				}
			}
		});

	}

}
