package mk.ukim.finki.myconcerts.db;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.myconcerts.model.Event;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class EventDao {

	// Database fields
	private SQLiteDatabase database;
	private EventDbOpenHelper dbHelper;

	private String[] allColumns = { EventDbOpenHelper.COLUMN_ID,
			EventDbOpenHelper.COLUMN_LASTFM_ID,
			EventDbOpenHelper.COLUMN_EVENT_ID, EventDbOpenHelper.COLUMN_NAME,
			EventDbOpenHelper.COLUMN_ARTISTS,
			EventDbOpenHelper.COLUMN_VENUE_NAME,
			EventDbOpenHelper.COLUMN_VENUE_CITY,
			EventDbOpenHelper.COLUMN_VENUE_COUNTRY,
			EventDbOpenHelper.COLUMN_VENUE_STREET,
			EventDbOpenHelper.COLUMN_LATITUDE,
			EventDbOpenHelper.COLUMN_LONGITUDE,
			EventDbOpenHelper.COLUMN_START_DATE,
			EventDbOpenHelper.COLUMN_IMAGE_SMALL,
			EventDbOpenHelper.COLUMN_IMAGE_BIG,
			EventDbOpenHelper.COLUMN_TICKET_URL, };

	public EventDao(Context context) {
		dbHelper = new EventDbOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
		dbHelper.close();
	}

	public boolean insert(Event item) {

		if (item.getId() != null) {
			return update(item);
		}

		long insertId = database.insert(EventDbOpenHelper.TABLE_NAME, null,
				itemToContentValues(item));

		if (insertId > 0) {
			item.setId(insertId);
			return true;
		} else {
			return false;
		}

	}

	public boolean update(Event item) {
		long numRowsAffected = database.update(EventDbOpenHelper.TABLE_NAME,
				itemToContentValues(item), EventDbOpenHelper.COLUMN_ID + " = "
						+ item.getId(), null);
		return numRowsAffected > 0;
	}

	public Event existsLastFMID(String lastfmid) {
		String Query = "Select * from " + EventDbOpenHelper.TABLE_NAME
				+ " where " + EventDbOpenHelper.COLUMN_LASTFM_ID + "="
				+ lastfmid;
		Cursor cursor = database.rawQuery(Query, null);
		if (cursor.getCount() <= 0) {
			return null;
		}
		cursor.moveToFirst();
		return cursorToItem(cursor);
	}

	public List<Event> getAllItems() {
		List<Event> items = new ArrayList<Event>();

		Cursor cursor = database.query(EventDbOpenHelper.TABLE_NAME,
				allColumns, null, null, null, null, null);

		if (cursor.moveToFirst()) {
			do {
				items.add(cursorToItem(cursor));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return items;
	}

	public Event getById(long id) {

		Cursor cursor = database.query(EventDbOpenHelper.TABLE_NAME,
				allColumns, EventDbOpenHelper.COLUMN_ID + " = " + id, null,
				null, null, null);
		try {
			if (cursor.moveToFirst()) {
				return cursorToItem(cursor);
			} else {
				// no items found
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			cursor.close();
		}

	}

	public Event cursorToItem(Cursor cursor) {
		Event item = new Event();
		item.setId(cursor.getLong(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_ID)));

		item.setLastFmID(cursor.getString(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_LASTFM_ID)));
		item.setEventID(cursor.getString(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_EVENT_ID)));
		item.setName(cursor.getString(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_NAME)));
		item.setArtists(getArtists(cursor));

		item.setVenueName(cursor.getString(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_VENUE_NAME)));
		item.setVenueCity(cursor.getString(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_VENUE_CITY)));
		item.setVenueCountry(cursor.getString(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_VENUE_COUNTRY)));
		item.setVenueStreet(cursor.getString(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_VENUE_STREET)));

		item.setLatitude(cursor.getDouble(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_LATITUDE)));
		item.setLongitude(cursor.getDouble(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_LONGITUDE)));

		item.setStartDate(cursor.getString(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_START_DATE)));

		item.setImageSmall(cursor.getString(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_IMAGE_SMALL)));
		item.setImageBig(cursor.getString(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_IMAGE_BIG)));
		item.setTicketUrl(cursor.getString(cursor
				.getColumnIndex(EventDbOpenHelper.COLUMN_TICKET_URL)));

		return item;
	}

	private ArrayList<String> getArtists(Cursor cursor) {
		ArrayList<String> artists = new ArrayList<String>();
		String[] dbartists = cursor.getString(
				cursor.getColumnIndex(EventDbOpenHelper.COLUMN_ARTISTS))
				.split(", ");

		for (String artist : dbartists) {
			artists.add(artist);
		}
		return artists;
	}

	protected ContentValues itemToContentValues(Event item) {
		ContentValues values = new ContentValues();
		if (item.getId() != null) {
			values.put(EventDbOpenHelper.COLUMN_ID, item.getId());
		}
		values.put(EventDbOpenHelper.COLUMN_LASTFM_ID, item.getLastFmID());
		values.put(EventDbOpenHelper.COLUMN_EVENT_ID, item.getEventID());
		values.put(EventDbOpenHelper.COLUMN_NAME, item.getName());
		values.put(EventDbOpenHelper.COLUMN_ARTISTS, item.artistsToString());

		values.put(EventDbOpenHelper.COLUMN_VENUE_NAME, item.getVenueName());
		values.put(EventDbOpenHelper.COLUMN_VENUE_CITY, item.getVenueCity());
		values.put(EventDbOpenHelper.COLUMN_VENUE_COUNTRY,
				item.getVenueCountry());
		values.put(EventDbOpenHelper.COLUMN_VENUE_STREET, item.getVenueStreet());
		values.put(EventDbOpenHelper.COLUMN_LATITUDE, item.getLatitude());
		values.put(EventDbOpenHelper.COLUMN_LONGITUDE, item.getLongitude());
		values.put(EventDbOpenHelper.COLUMN_START_DATE, item.getStartDate());
		values.put(EventDbOpenHelper.COLUMN_IMAGE_SMALL, item.getImageSmall());
		values.put(EventDbOpenHelper.COLUMN_IMAGE_BIG, item.getImageBig());
		values.put(EventDbOpenHelper.COLUMN_TICKET_URL, item.getTicketUrl());

		return values;
	}

	public boolean deleteItem(int id) {
		return database.delete(EventDbOpenHelper.TABLE_NAME,
				EventDbOpenHelper.COLUMN_ID + "=" + id, null) > 0;
	}
}