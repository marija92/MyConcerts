package mk.ukim.finki.myconcerts;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity {
	
	   private GoogleMap googleMap;
		
		static double lat;
		static double lng;
		
		
		private static final LatLng PROSVETNO_DELO = new LatLng(41.99687, 21.42834);
		private static final LatLng MATICA = new LatLng(41.99735, 21.42800);
		private static final LatLng KULTURA = new LatLng(41.98855, 21.42871);
		
		static LatLng current;
		
			
		private void addMarkers() {
	        if (googleMap != null) {   	         
	        	         
	          // marker with custom color
	          googleMap.addMarker(new MarkerOptions().position(PROSVETNO_DELO).title("Просветно Дело")
	        		  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));        
	            
	          googleMap.addMarker(new MarkerOptions().position(MATICA).title("Матица")
	        		  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));	     	         
	        
	          googleMap.addMarker(new MarkerOptions().position(KULTURA).title("Култура")
	        		  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));	        	         
	         
	          googleMap.addMarker(new MarkerOptions().position(current).title("Your location")
	        		  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));	
	          googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 12));
	     
	        }
	      }
		    
	    // GPSTracker class
	    GPSTracker gps;
	     
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.mapfragment);         
	      
	                gps = new GPSTracker(MapActivity.this);
	 
	                // check if GPS enabled     
	                if(gps.canGetLocation()){
	                     
	                    lat = gps.getLatitude();
	                    lng = gps.getLongitude();
	                     
	                    // \n is for new line
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
	        			{
	        				googleMap=((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	        			}
	        			googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
	        				addMarkers();
	        		}
	        		catch(Exception e)
	        		{
	        			e.printStackTrace();
	        		}
	                
	            }
	       

}
