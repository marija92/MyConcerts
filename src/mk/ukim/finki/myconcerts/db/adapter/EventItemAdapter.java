package mk.ukim.finki.myconcerts.db.adapter;
import java.util.ArrayList;

import mk.ukim.finki.myconcerts.db.DatabaseItemsAdapter;
import mk.ukim.finki.myconcerts.db.EventDbOpenHelper;
import mk.ukim.finki.myconcerts.model.Event;
import android.content.ContentValues;
import android.database.Cursor;

public class EventItemAdapter implements DatabaseItemsAdapter<Event> {
	
	@Override
	public ContentValues itemToContentValues(Event item) {
		ContentValues values = new ContentValues();
		if (item.getId() != null) {
			values.put(EventDbOpenHelper.COLUMN_ID, item.getId());
		}
		values.put(EventDbOpenHelper.COLUMN_LASTFM_ID, item.getLastFmID());
		values.put(EventDbOpenHelper.COLUMN_EVENT_ID, item.getEventID());
		values.put(EventDbOpenHelper.COLUMN_NAME, item.getName());
		String artists = "";
		for(String artist :item.getArtist()){
			artists += artist +";";
		}
		values.put(EventDbOpenHelper.COLUMN_ARTISTS, artists);
		
		values.put(EventDbOpenHelper.COLUMN_VENUE_NAME, item.getVenueName());
		values.put(EventDbOpenHelper.COLUMN_VENUE_CITY, item.getVenueCity());
		values.put(EventDbOpenHelper.COLUMN_VENUE_COUNTRY, item.getVenueCountry());
		values.put(EventDbOpenHelper.COLUMN_VENUE_STREET, item.getVenueStreet());
		values.put(EventDbOpenHelper.COLUMN_LATITUDE, item.getLatitude());
		values.put(EventDbOpenHelper.COLUMN_LONGITUDE, item.getLongitude());
		values.put(EventDbOpenHelper.COLUMN_START_DATE, item.getStartDate());
		values.put(EventDbOpenHelper.COLUMN_IMAGE_SMALL, item.getImageSmall());
		values.put(EventDbOpenHelper.COLUMN_IMAGE_BIG, item.getImageBig());
		values.put(EventDbOpenHelper.COLUMN_TICKET_URL, item.getTicketUrl());
	
		return values;
	}

	@Override
	public Event cursorToItem(Cursor cursor) {
		Event item = new Event();
		item.setId(cursor.getLong(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_ID)));

		item.setLastFmID(cursor.getString(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_LASTFM_ID)));
		item.setEventID(cursor.getString(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_EVENT_ID)));
		item.setName(cursor.getString(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_NAME)));
		item.setArtists(getArtists(cursor));
		
		item.setVenueName(cursor.getString(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_VENUE_NAME)));
		item.setVenueCity(cursor.getString(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_VENUE_CITY)));
		item.setVenueCountry(cursor.getString(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_VENUE_COUNTRY)));
		item.setVenueStreet(cursor.getString(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_VENUE_STREET)));
		
		item.setLatitude(cursor.getDouble(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_LATITUDE)));
		item.setLongitude(cursor.getDouble(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_LONGITUDE)));
		
		item.setStartDate(cursor.getString(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_START_DATE)));
		
		item.setImageSmall(cursor.getString(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_IMAGE_SMALL)));
		item.setImageBig(cursor.getString(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_IMAGE_BIG)));
		item.setTicketUrl(cursor.getString(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_TICKET_URL)));

		return item;
	}

	private ArrayList<String> getArtists(Cursor cursor) {
		ArrayList<String> artists = new ArrayList<String>();
		String[] dbartists = cursor.getString(cursor.getColumnIndex(EventDbOpenHelper.COLUMN_VENUE_NAME)).split(";");

		for(String artist: dbartists){
			artists.add(artist);
		}
		return artists;
	}

}