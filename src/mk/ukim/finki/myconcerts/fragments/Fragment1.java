package mk.ukim.finki.myconcerts.fragments;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import mk.ukim.finki.myconcerts.CalendarUtils;
import mk.ukim.finki.myconcerts.DetailsActivity;
import mk.ukim.finki.myconcerts.R;
import mk.ukim.finki.myconcerts.adapters.EventItemAdapter;
import mk.ukim.finki.myconcerts.db.EventDao;
import mk.ukim.finki.myconcerts.model.Event;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Fragment1 extends Fragment {
	EventDao dao;
	ListView eventListView;
	EventItemAdapter adapter;

	
	@Override
	public void onStart() {
		super.onStart();
		setAdapterState();
		adapter.notifyDataSetChanged();
//		eventListView.invalidate();
		System.out.println("onstart");
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment1, container, false);
		eventListView = (ListView) rootView.findViewById(R.id.upcomingEvents);

		dao = new EventDao(getActivity());
		dao.open();
		setAdapterState();
		eventListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Event e = (Event) parent.getAdapter().getItem(position);
				showDetails(e);
			}
		});
		return rootView;
	}

	private void setAdapterState() {
		adapter = new EventItemAdapter(getActivity());
		ArrayList<Event> allEvents = (ArrayList<Event>) dao.getAllItems();
		adapter.addAll(allEvents);
		eventListView.setAdapter(adapter);
	}

	public void showDetails(Event e) {

		Intent intent = new Intent(getActivity(), DetailsActivity.class);
		intent.putExtra("eventID", e.getEventID());
		intent.putExtra("ID", e.getId());
		intent.putExtra("name", e.getName());
		intent.putExtra("artists", e.artistsToString());
		intent.putExtra("venueName", e.getVenueName());
		intent.putExtra("venueStreet", e.getVenueStreet());
		intent.putExtra("venueCity", e.getVenueCity());
		intent.putExtra("venueCountry", e.getVenueCountry());
		intent.putExtra("startDate", e.getStartDate());
		intent.putExtra("image", e.getImageBig());
		intent.putExtra("lastFmID", e.getLastFmID());
		intent.putExtra("imgSmall", e.getImageSmall());
		startActivity(intent);
	}
}
