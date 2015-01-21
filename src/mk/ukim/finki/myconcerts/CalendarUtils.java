package mk.ukim.finki.myconcerts;

import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import mk.ukim.finki.myconcerts.model.Event;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;

public class CalendarUtils {
	static HashMap<String, Integer> months;
	static String[] ms = new String[] { "jan", "feb", "mar", "apr", "may",
			"jun", "jul", "aug", "sep", "oct", "nov", "dec" };

	public static long addEventToCalender(ContentResolver cr, Event e) {
		fillMonthsMap();
		Cursor cursor = cr
				.query(Uri.parse("content://com.android.calendar/calendars"),
						new String[] { Calendars._ID,
								Calendars.CALENDAR_DISPLAY_NAME }, null, null,
						null);
		cursor.moveToFirst();
		// Get calendars name
		String calendarNames[] = new String[cursor.getCount()];
		// Get calendars id
		int[] calendarId = new int[cursor.getCount()];
		for (int i = 0; i < calendarNames.length; i++) {
			calendarId[i] = cursor.getInt(0);
			calendarNames[i] = cursor.getString(1);
			cursor.moveToNext();
		}

		ContentValues contentEvent = new ContentValues();
		contentEvent.put("calendar_id", calendarId[0]);
		contentEvent.put("title", e.getName());
		contentEvent.put("eventLocation",
				e.getVenueName() + ", " + e.getVenueCity());
		contentEvent.put("eventTimezone", TimeZone.getDefault().toString());
		contentEvent.put("dtstart", getTime(e.getStartDate()));
		contentEvent.put("dtend", getTime(e.getStartDate()) + 14400000); // +4hours
		contentEvent.put("hasAlarm", 1);
//		Uri eventsUri = Uri.parse("content://com.android.calendar/events");
		Uri uri = cr.insert(Events.CONTENT_URI, contentEvent);

		long eventID = Long.parseLong(uri.getLastPathSegment());

		cursor.close();

		System.out.println(eventID);

		return eventID;
	}

	public static void deleteEventFromCalendar(ContentResolver cr, long eventID) {
	/*	Cursor cursor = cr
				.query(Uri.parse("content://com.android.calendar/calendars"),
						new String[] { Calendars._ID,
								Calendars.CALENDAR_DISPLAY_NAME }, null, null,
						null);
		cursor.moveToFirst();
		// Get calendars name
		String calendarNames[] = new String[cursor.getCount()];
		// Get calendars id
		int[] calendarId = new int[cursor.getCount()];
		for (int i = 0; i < calendarNames.length; i++) {
			calendarId[i] = cursor.getInt(0);
			calendarNames[i] = cursor.getString(1);
			cursor.moveToNext();
		}*/
		Uri deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, eventID);
		int rows = cr.delete(deleteUri, null, null);
		System.out.println("rows deleted: " + rows);
	}

	private static long getTime(String startDate) {
		startDate = startDate.substring(5);
		System.out.println(startDate);
		String[] tokens = startDate.split("\\s+");
		int day = Integer.parseInt(tokens[0]);
		int month = months.get(tokens[1].toLowerCase());
		int year = Integer.parseInt(tokens[2]);
		int hours = 0;
		int minutes = 0;
		if (tokens.length > 3) {
			String toks[] = tokens[3].split(":");
			hours = Integer.parseInt(toks[0]);
			minutes = Integer.parseInt(toks[1]);
		}
		Date date = new Date(year - 1900, month, day, hours, minutes);
		return date.getTime();

	}

	private static void fillMonthsMap() {
		months = new HashMap<String, Integer>();
		for (int i = 0; i < 12; i++) {
			months.put(ms[i], i);
		}

	}
}