package humans.apps.steve.actionmode.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import humans.apps.steve.actionmode.R;
import humans.apps.steve.actionmode.entities.Book;
import humans.apps.steve.actionmode.entities.SparseBooleanArrayParcelable;
import humans.apps.steve.actionmode.holders.BooksViewHolder;

/**
 * Created by Steve on 2/08/2016.
 */

public class BooksAdapter extends
        RecyclerView.Adapter<BooksViewHolder> {

    private ArrayList<Book> arrayList;
    private Context context;
    private SparseBooleanArrayParcelable mSelectedItemsIds;

    public BooksAdapter(Context context, ArrayList<Book> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        mSelectedItemsIds = new SparseBooleanArrayParcelable();
    }

    @Override
    public BooksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_books, parent, false);
        return new BooksViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(BooksViewHolder holder, int position) {
        //Setting text over text view
        holder.title.setText(arrayList.get(position).getTitle());
        holder.author.setText(arrayList.get(position).getAuthor());

        int colorAccent = ContextCompat.getColor(context, R.color.colorAccent);
        /** Change background color of the selected items  **/
        holder.itemView
                .setBackgroundColor(mSelectedItemsIds.get(position, false) ? colorAccent
                        : Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    /***
     * Methods required for do selections, remove selections, etc.
     */

    //Toggle selection methods
    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }


    //Remove selected selections
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArrayParcelable();
        notifyDataSetChanged();
    }


    //Put or delete selected position into SparseBooleanArray
    private void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, true);
        else
            mSelectedItemsIds.delete(position);

        notifyItemChanged(position);
    }

    //Get total selected count
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    //Return all selected ids
    public SparseBooleanArrayParcelable getSelectedIds() {
        return mSelectedItemsIds;
    }
}
