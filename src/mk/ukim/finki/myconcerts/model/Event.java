package mk.ukim.finki.myconcerts.model;

import java.util.ArrayList;

public class Event extends DatabaseItem {

	private String lastFmID;
	private String eventID;
	private String name;
	private ArrayList<String> artists;
	private String venueName;
	private String venueCity;
	private String venueCountry;
	private String venueStreet;
	private double latitude;
	private double longitude;
	private String startDate;
	private String imageSmall;
	private String imageBig;
	private String ticketUrl;

	public Event() {
		artists = new ArrayList<String>();

	}

	public Event(String lastFmID, String name, ArrayList<String> artist,
			String venueName, String venueCity, String venueCountry,
			String venueStreet, double latitude, double longitude,
			String startDate, String imageSmall, String imageBig,
			String ticketUrl) {
		super();
		this.lastFmID = lastFmID;
		this.name = name;
		this.artists = artist;
		this.venueName = venueName;
		this.venueCity = venueCity;
		this.venueCountry = venueCountry;
		this.venueStreet = venueStreet;
		this.latitude = latitude;
		this.longitude = longitude;
		this.startDate = startDate;
		this.imageSmall = imageSmall;
		this.imageBig = imageBig;
		this.ticketUrl = ticketUrl;
	}

	public Event(String lastFmID, String name, String artist, String venueName,
			String venueCity, String venueCountry, String venueStreet,
			String startDate, String imageSmall, String imageBig) {
		super();
		this.lastFmID = lastFmID;
		this.name = name;
		this.artists = parseArtists(artist);
		this.venueName = venueName;
		this.venueCity = venueCity;
		this.venueCountry = venueCountry;
		this.venueStreet = venueStreet;
		this.imageSmall = imageSmall;
		this.startDate = startDate;
		this.imageBig = imageBig;
	}

	public ArrayList<String> parseArtists(String art) {
		ArrayList<String> s = new ArrayList<String>();
		String[] str = art.split(", ");
		for (String st : str) {
			s.add(st);
		}
		return s;
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getLastFmID() {
		return lastFmID;
	}

	public void setLastFmID(String lastFmID) {
		this.lastFmID = lastFmID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<String> getArtist() {
		return artists;
	}

	public void setArtists(ArrayList<String> artist) {
		this.artists = artist;
	}

	public void addArtists(String artist) {
		this.artists.add(artist);
	}

	public String artistsToString() {
		String s = "";
		for (String a : artists) {
			s += a + ", ";
		}
		return s.substring(0, s.length() - 2);
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public String getVenueCity() {
		return venueCity;
	}

	public void setVenueCity(String venueCity) {
		this.venueCity = venueCity;
	}

	public String getVenueCountry() {
		return venueCountry;
	}

	public void setVenueCountry(String venueCountry) {
		this.venueCountry = venueCountry;
	}

	public String getVenueStreet() {
		return venueStreet;
	}

	public void setVenueStreet(String venueStreet) {
		this.venueStreet = venueStreet;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getImageSmall() {
		return imageSmall;
	}

	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}

	public String getImageBig() {
		return imageBig;
	}

	public void setImageBig(String imageBig) {
		this.imageBig = imageBig;
	}

	public String getTicketUrl() {
		return ticketUrl;
	}

	public void setTicketUrl(String ticketUrl) {
		this.ticketUrl = ticketUrl;
	}

}
