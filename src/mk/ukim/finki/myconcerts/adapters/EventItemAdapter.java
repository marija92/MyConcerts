package mk.ukim.finki.myconcerts.adapters;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.myconcerts.R;
import mk.ukim.finki.myconcerts.model.Event;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EventItemAdapter extends BaseAdapter implements
		OnItemClickListener {

	private List<Event> items;
	private Context ctx;
	private LayoutInflater inflater;

	public EventItemAdapter(Context ctx) {
		items = new ArrayList<Event>();
		this.ctx = ctx;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public EventItemAdapter(List<Event> items, Context ctx) {
		this.items = items;
		this.ctx = ctx;
		inflater = (LayoutInflater) this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class EventHolder {
		public RelativeLayout itemLayout;
		// treba od custom listata site da se stavat
		public TextView eventName;
		public TextView eventArtist;
		public TextView eventVenue;
		public TextView venueCity;
		public TextView eventDate;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Event item = items.get(position);
		EventHolder holder = null;
		if (convertView == null) {
			holder = new EventHolder();
			holder.itemLayout = (RelativeLayout) inflater.inflate(R.layout.custom_list_item, null);
			holder.eventName = (TextView) holder.itemLayout.findViewById(R.id.eventName);
			holder.eventArtist = (TextView) holder.itemLayout.findViewById(R.id.eventArtist);
			holder.eventVenue=(TextView) holder.itemLayout.findViewById(R.id.eventVenue);
			holder.venueCity=(TextView)holder.itemLayout.findViewById(R.id.venueCity);
			holder.eventDate=(TextView)holder.itemLayout.findViewById(R.id.eventDate);
			convertView = holder.itemLayout;
			convertView.setTag(holder);
		}

		
		holder = (EventHolder) convertView.getTag();

		holder.eventName.setText(item.getName());
		for(int i=0;i<item.getArtist().size();i++){
		holder.eventArtist.setText(item.getArtist().get(i));
		}
		holder.eventVenue.setText(item.getVenueName());
		holder.venueCity.setText(item.getVenueCity());
		holder.eventDate.setText(item.getStartDate());
		return convertView;
	}

	public void add(Event item) {
		items.add(item);
		notifyDataSetChanged();
	}

	public void addAll(List<Event> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}

	public void clear() {
		items.clear();
		notifyDataSetInvalidated();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Event item = items.get(position);
       //tuka bi trebalo da se otvare nov fragment/strana
	}

}
