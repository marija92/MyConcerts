package mk.ukim.finki.myconcerts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDbOpenHelper extends SQLiteOpenHelper {
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_LASTFM_ID = "lastfm_id";
	public static final String COLUMN_EVENT_ID = "event_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_ARTISTS = "artists";
	public static final String COLUMN_VENUE_NAME = "venue_name";
	public static final String COLUMN_VENUE_CITY = "venue_city";
	public static final String COLUMN_VENUE_COUNTRY = "venue_country";
	public static final String COLUMN_VENUE_STREET = "venue_street";
	public static final String COLUMN_LATITUDE = "latitude";
	public static final String COLUMN_LONGITUDE = "longitude";
	public static final String COLUMN_START_DATE = "start_date";
	public static final String COLUMN_IMAGE_SMALL = "image_small";
	public static final String COLUMN_IMAGE_BIG = "image_big";
	public static final String COLUMN_TICKET_URL = "ticket_url";
	
	
	public static final String TABLE_NAME = "EventItems";

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_NAME_EXPRESSION = "MyConcertEvents.db";

	private static final String DATABASE_CREATE = String
			.format("create table %s (" +
					"%s  integer primary key autoincrement, " +	//id
					"%s integer not null, " +					//lastfm id
					"%s integer not null, " +					//event id
					"%s text," +									//name
					"%s text," +									//artists
					"%s text," +									//venue name
					"%s text," +									//venue city
					"%s text," +									//venue country
					"%s text," +									//venue street
					"%s real," +									//latitude
					"%s real," +									//longitude
					"%s text," +									//start date
					"%s text," +									//image small
					"%s text," +									//image large
					"%s text);"									//ticket url
					,
					TABLE_NAME, 
					COLUMN_ID, 
					COLUMN_LASTFM_ID,
					COLUMN_EVENT_ID,
					COLUMN_NAME,
					COLUMN_ARTISTS,
					COLUMN_VENUE_NAME,
					COLUMN_VENUE_CITY,
					COLUMN_VENUE_COUNTRY,
					COLUMN_VENUE_STREET,
					COLUMN_LATITUDE,
					COLUMN_LONGITUDE,
					COLUMN_START_DATE,
					COLUMN_IMAGE_SMALL,
					COLUMN_IMAGE_BIG,
					COLUMN_TICKET_URL);

	public EventDbOpenHelper(Context context) {
		super(context, String.format(DATABASE_NAME_EXPRESSION), null,
				DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_NAME));
		onCreate(db);
	}

}