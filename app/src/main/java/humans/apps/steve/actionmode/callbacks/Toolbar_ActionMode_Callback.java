package humans.apps.steve.actionmode.callbacks;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import humans.apps.steve.actionmode.R;
import humans.apps.steve.actionmode.activities.MainActivity;
import humans.apps.steve.actionmode.adapters.BooksAdapter;
import humans.apps.steve.actionmode.entities.Book;
import humans.apps.steve.actionmode.fragments.BooksFragment;

/**
 * Created by Steve on 2/08/2016.
 */

public class Toolbar_ActionMode_Callback implements ActionMode.Callback {
    private Context context;
    private BooksAdapter recyclerView_adapter;
    private ArrayList<Book> message_models;


    public Toolbar_ActionMode_Callback(Context context, BooksAdapter recyclerView_adapter, ArrayList<Book> message_models) {
        this.context = context;
        this.recyclerView_adapter = recyclerView_adapter;
        this.message_models = message_models;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_mode, menu);//Inflate the menu over action mode
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

        //Sometimes the meu will not be visible so for that we need to set their visibility manually in this method
        //So here show action menu according to SDK Levels
        if (Build.VERSION.SDK_INT < 11) {
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_love), MenuItemCompat.SHOW_AS_ACTION_NEVER);
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_delete), MenuItemCompat.SHOW_AS_ACTION_NEVER);
            MenuItemCompat.setShowAsAction(menu.findItem(R.id.action_copy), MenuItemCompat.SHOW_AS_ACTION_NEVER);
        } else {
            menu.findItem(R.id.action_love).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.findItem(R.id.action_delete).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.findItem(R.id.action_copy).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        //If current fragment is recycler view fragment
        Fragment recyclerFragment = new MainActivity().getFragment(0);//Get recycler view fragment
        switch (item.getItemId()) {
            case R.id.action_delete:

                    if (recyclerFragment != null)
                        //If recycler fragment not null
                        ((BooksFragment) recyclerFragment).deleteBooks();//delete selected rows

                break;
            case R.id.action_copy:

                if (recyclerFragment != null)
                    //If recycler fragment not null
                    ((BooksFragment) recyclerFragment).copyBooks();//delete selected rows
                break;
            case R.id.action_love:
                if (recyclerFragment != null)
                    //If recycler fragment not null
                    ((BooksFragment) recyclerFragment).loveBooks();//delete selected rows
                break;

        }
        return true;
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {

        //When action mode destroyed remove selected selections and set action mode to null
        //First check current fragment action mode

            recyclerView_adapter.removeSelection(true);  // remove selection
            Fragment recyclerFragment = new MainActivity().getFragment(0);//Get recycler fragment
            if (recyclerFragment != null){
                ((BooksFragment) recyclerFragment).setNullToActionMode();//Set action mode null
            }

    }
}
