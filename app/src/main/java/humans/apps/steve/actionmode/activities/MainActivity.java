package humans.apps.steve.actionmode.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import humans.apps.steve.actionmode.R;
import humans.apps.steve.actionmode.adapters.ViewPagerAdapter;
import humans.apps.steve.actionmode.fragments.BooksFragment;

public class MainActivity extends AppCompatActivity {

    private static ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);//Set up View Pager


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);//setting tab over viewpager

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new BooksFragment(), getString(R.string.books_title_name));
        viewPager.setAdapter(adapter);
    }

    //Return current fragment on basis of Position
    public Fragment getFragment(int pos) {
        return adapter.getItem(pos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        //If current fragment is recycler view fragment
        Fragment recyclerFragment = new MainActivity().getFragment(0);//Get recycler view fragment
        SparseBooleanArray selectedIds = new SparseBooleanArray();
        if (recyclerFragment != null){
            //If recycler fragment not null
            //((BooksFragment) recyclerFragment).deleteRows();//delete selected rows
            selectedIds= ((BooksFragment) recyclerFragment).getSelectedIds();
        }
        savedInstanceState.putInt(SELECTED_IDS, selectedIds.size());
        super.onSaveInstanceState(savedInstanceState);
    }*/



}
