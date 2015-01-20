package mk.ukim.finki.myconcerts;

import java.util.Random;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity {
	
	   private GoogleMap googleMap;
		
		static double lat;
		static double lng;
		
		static LatLng current;
		
			
		private void addMarkers(double[] lats, double longs[], String[] titles) {
			Random rand=new Random();
			//rand.
	        if (googleMap != null) {   	         
	        	         
	        	for(int i=0;i<lats.length;i++){	        		
	        		LatLng point = new LatLng(lats[i]+rand.nextDouble()/100000, longs[i]+rand.nextDouble()/100000);	        		
	        		//LatLng point=new LatLng(lats[i], longs[i]);
	        		googleMap.addMarker(new MarkerOptions().position(point).title(titles[i]));
	        	}
	        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 5));        	
	          
	        }
	      }
		    
	    // GPSTracker class
	    GPSTracker gps;
	     
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mapfragment);   
	        
	        Bundle extras = getIntent().getExtras();
	        double[] lats = extras.getDoubleArray("latitudes");
	        double[] longs= extras.getDoubleArray("longitudes");
	        String[] titles=extras.getStringArray("names");
	                gps = new GPSTracker(MapActivity.this);
	 
	                // check if GPS enabled     
	                if(gps.canGetLocation()){
	                     
	                    lat = gps.getLatitude();
	                    lng = gps.getLongitude();
	                     
	                    current=new LatLng(lat, lng);
	                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + lat + "\nLong: " + lng, Toast.LENGTH_LONG).show();    
	                }else{
	                    // can't get location
	                    // GPS or Network is not enabled
	                    // Ask user to enable GPS/network in settings
	                    gps.showSettingsAlert();
	                }
	            
	                try
	        		{
	        			if(googleMap == null)
	        				 googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	        			
	        			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	        			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

	        			addMarkers(lats,longs,titles);
	        			
	        		}
	        		catch(Exception e)
	        		{
	        			e.printStackTrace();
	        		}
	                
	            }
	       

}
