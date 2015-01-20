package mk.ukim.finki.myconcerts.adapters;

import java.util.ArrayList;
import java.util.List;

import com.androidquery.AQuery;

import mk.ukim.finki.myconcerts.R;
import mk.ukim.finki.myconcerts.model.Event;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EventItemAdapter extends BaseAdapter implements
		OnItemClickListener {

	private List<Event> items;
	private Context ctx;
	private LayoutInflater inflater;
	private AQuery aquery;

	public EventItemAdapter(Context ctx) {
		items = new ArrayList<Event>();
		this.ctx = ctx;
		aquery=new AQuery(ctx);
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public EventItemAdapter(List<Event> items, Context ctx) {
		this.items = items;
		this.ctx = ctx;
		aquery=new AQuery(ctx);
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
		public TextView eventDate;
		public ImageView image;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Event item = items.get(position);
		EventHolder holder = null;
		if (convertView == null) {
			holder = new EventHolder();
			holder.itemLayout = (RelativeLayout) inflater.inflate(R.layout.custom_list_item, null);
			holder.image=(ImageView) holder.itemLayout.findViewById(R.id.icon);
			holder.eventName = (TextView) holder.itemLayout.findViewById(R.id.eventName);
			holder.eventArtist = (TextView) holder.itemLayout.findViewById(R.id.eventArtist);
			holder.eventVenue=(TextView) holder.itemLayout.findViewById(R.id.eventVenue);
			holder.eventDate=(TextView)holder.itemLayout.findViewById(R.id.eventDate);			
			convertView = holder.itemLayout;
			convertView.setTag(holder);
		}
		
		
		holder = (EventHolder) convertView.getTag();

		
		aquery.id(holder.image).image(item.getImageSmall(),false,false);
		//Toast.makeText(aq.getContext(), "Download initiated...",Toast.LENGTH_SHORT).show();
        
		
		//holder.image.setI
		holder.eventName.setText(item.getName());
		/*String artists="";
		for(int i=0;i<item.getArtist().size();i++){
			artists+=" "+item.getArtist().get(i);
		}*/
		holder.eventArtist.setText("Artists: "+item.artistsToString());
		String venue="Venue: "+item.getVenueName()+"  "+item.getVenueCity()+"  "+item.getVenueCountry();
		holder.eventVenue.setText(venue);
		holder.eventDate.setText("Date: "+item.getStartDate());
		
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
