package mk.ukim.finki.myconcerts;

import com.androidquery.AQuery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
	
	public void init(){
		aquery=new AQuery(this);
		image=(ImageView)findViewById(R.id.image);
		name=(TextView) findViewById(R.id.eventName1);		
		artists=(TextView) findViewById(R.id.eventArtist1);
		venue=(TextView) findViewById(R.id.eventVenueName1);
		venueStreet=(TextView) findViewById(R.id.eventVenueStreet1);
		venueCity=(TextView) findViewById(R.id.eventVenueCity1);
		venueCountry=(TextView) findViewById(R.id.eventVenueCountry1);
		startDate=(TextView) findViewById(R.id.eventStartDate1);	
		addToCal=(Button) findViewById(R.id.addToCal);
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);  
        init();
        
        Bundle extras = getIntent().getExtras();
		aquery.id(image).image(extras.getString("image"),false,false);
        name.setText(extras.getString("name"));
        artists.setText(extras.getString("artists"));
        venue.setText(extras.getString("venueName"));
        venueStreet.setText(extras.getString("venueStreet"));
        venueCity.setText(extras.getString("venueCity"));
        venueCountry.setText(extras.getString("venueCountry"));
        startDate.setText(extras.getString("startDate"));
        
        addToCal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//dodadi u kalendar :)
			}
		});
        
        
	}

}
