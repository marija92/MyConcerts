package mk.ukim.finki.myconcerts.db;

import mk.ukim.finki.myconcerts.model.DatabaseItem;
import android.content.ContentValues;
import android.database.Cursor;

public interface DatabaseItemsAdapter<T extends DatabaseItem> {

	public ContentValues itemToContentValues(T item);

	public T cursorToItem(Cursor c);
}
