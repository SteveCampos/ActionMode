package humans.apps.steve.actionmode.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import humans.apps.steve.actionmode.R;
import humans.apps.steve.actionmode.adapters.BooksAdapter;
import humans.apps.steve.actionmode.entities.Book;
import humans.apps.steve.actionmode.interfaces.RecyclerClick_Listener;
import humans.apps.steve.actionmode.listeners.RecyclerTouchListener;
import humans.apps.steve.actionmode.callbacks.Toolbar_ActionMode_Callback;
import humans.apps.steve.actionmode.entities.SparseBooleanArrayParcelable;


public class BooksFragment extends Fragment {


    private static final String SELECTED_IDS = "SELECTED_IDS";
    private static final String TAG = BooksFragment.class.getSimpleName();
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<Book> item_books;
    private BooksAdapter adapter;
    private ActionMode mActionMode;
    private SparseBooleanArrayParcelable mSelectedIds;


    public BooksFragment() {
        Log.d(TAG, "empty public constructor");
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            mSelectedIds = savedInstanceState.getParcelable(SELECTED_IDS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_books, container, false);
        Log.d(TAG, "onCreateView");
        populateRecyclerView();
        implementRecyclerViewClickListeners();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        Log.d(TAG, "SIZE SAVED: " + adapter.getSelectedIds().size());
        //outState.putParcelable(SELECTED_IDS, adapter.getSelectedIds());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    //Populate ListView with dummy data
    private void populateRecyclerView() {
        Log.d(TAG, "populateRecyclerView");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        item_books = new ArrayList<>();
        for (int i = 1; i <= 40; i++)
            item_books.add(new Book("Title " + i, "Author " + i));

        adapter = new BooksAdapter(getActivity(), item_books);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //Implement item click and long click over recycler view
    private void implementRecyclerViewClickListeners() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerClick_Listener() {
            @Override
            public void onClick(View view, int position) {
                //If ActionMode not null select item
                if (mActionMode != null)
                    onListItemSelect(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                //Select item on long click
                onListItemSelect(position);
            }
        }));
    }

    //List item select method
    private void onListItemSelect(int position) {
        adapter.toggleSelection(position);//Toggle the selection
        setActionMode(adapter.getSelectedCount());
    }

    private void setActionMode(int countSelected) {
        boolean hasCheckedItems = countSelected > 0;//Check if any items are already selected or not

        if (hasCheckedItems && mActionMode == null)
            // there are some selected items, start the actionMode
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new Toolbar_ActionMode_Callback(getActivity(), adapter, item_books));
        else if (!hasCheckedItems && mActionMode != null)
            // there no selected items, finish the actionMode
            mActionMode.finish();
        if (mActionMode != null)
            //set action mode title on item selection
            mActionMode.setTitle(String.valueOf(countSelected) + " selected");
    }

    //Set action mode null after use
    public void setNullToActionMode() {
        if (mActionMode != null)
            mActionMode = null;
    }

    //Delete selected rows
    public void deleteBooks() {

        SparseBooleanArray selected =
                adapter.getSelectedIds();//Get selected ids

        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                int position = selected.keyAt(i);
                item_books.remove(position);
                adapter.notifyItemRemoved(position);//notify adapter
            }
        }
        Snackbar.make(recyclerView, selected.size() + " Items Deleted", Snackbar.LENGTH_SHORT).show();//Show Toast
        if (mActionMode != null)
            mActionMode.finish();//Finish action mode after use
    }

    //Delete selected rows
    public void loveBooks() {

        SparseBooleanArray selected =
                adapter.getSelectedIds();//Get selected ids

        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                int position = selected.keyAt(i);
                adapter.notifyItemChanged(position);//notify adapter
            }
        }
        Snackbar.make(recyclerView, selected.size() + " Items Loved", Snackbar.LENGTH_SHORT).show();//Show
        if (mActionMode != null)
            mActionMode.finish();//Finish action mode after use
    }

    //Delete selected rows
    public void copyBooks() {

        SparseBooleanArray selected =
                adapter.getSelectedIds();//Get selected ids

        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                //If current id is selected remove the item via key
                int position = selected.keyAt(i);
                //item_books.remove(position);
                adapter.notifyItemChanged(position);//notify adapter
            }
        }
        Snackbar.make(recyclerView, selected.size() + " Items Copied.", Snackbar.LENGTH_SHORT).show();//Show Toast
        if (mActionMode != null)
            mActionMode.finish();//Finish action mode after use
        else {
            mActionMode = null;
        }
    }



    public SparseBooleanArray getSelectedIds() {
        return adapter.getSelectedIds();
    }

}
