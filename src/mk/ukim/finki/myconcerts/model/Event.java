package mk.ukim.finki.myconcerts.model;

public class Event {
	
	private String lastFmID;
	private String name;
	private String artist;
	private String venueName;
	private String venueCity;
	private String venueCountry;
	private String venueStreet;
	private double latitude;
	private double longitude;
	private String startDate;
	private String imageSmall;
	private String ticketUrl;
	
	public Event(){
		
	}	
	
	public Event(String lastFmID, String name, String artist, String venueName,
			String venueCity, String venueCountry, String venueStreet,
			double latitude, double longitude, String startDate,
			String imageSmall, String ticketUrl) {
		super();
		this.lastFmID = lastFmID;
		this.name = name;
		this.artist = artist;
		this.venueName = venueName;
		this.venueCity = venueCity;
		this.venueCountry = venueCountry;
		this.venueStreet = venueStreet;
		this.latitude = latitude;
		this.longitude = longitude;
		this.startDate = startDate;
		this.imageSmall = imageSmall;
		this.ticketUrl = ticketUrl;
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
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
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
	public String getTicketUrl() {
		return ticketUrl;
	}
	public void setTicketUrl(String ticketUrl) {
		this.ticketUrl = ticketUrl;
	}
	
	
	
}
