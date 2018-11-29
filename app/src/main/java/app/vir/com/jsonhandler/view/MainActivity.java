package app.vir.com.jsonhandler.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import app.vir.com.jsonhandler.R;
import app.vir.com.jsonhandler.controller.Controller;
import app.vir.com.jsonhandler.model.adapter.DisplayListAdapter;
import app.vir.com.jsonhandler.model.pojo.Album;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, Controller.CallbackListener  {

    private static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeLayout;
    private Controller mController;
    private TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        emptyView = (TextView) findViewById(R.id.empty_view);
        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        if (swipeLayout != null) {
            swipeLayout.setOnRefreshListener(this);
        }

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mController = new Controller(MainActivity.this, MainActivity.this);

        sendRequest();
    }

    public RecyclerView.LayoutManager getmLayoutManager() {
        return mLayoutManager;
    }

    @Override
    public void onRefresh() {
        dispatchRefresh(true);
    }


    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void onFetchComplete(List<Album> albums) {
       swipeLayout.setVisibility(View.VISIBLE);
       mRecyclerView.setVisibility(View.VISIBLE);
       emptyView.setVisibility(View.GONE);


       AlbumCompare albumCompare = new AlbumCompare();
       Collections.sort(albums, albumCompare);

       mAdapter = new DisplayListAdapter(getApplicationContext(), albums);
      mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onFetchFailed() {
        swipeLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    private void sendRequest() {
        mController.startFetching();
    }

    private void dispatchRefresh(boolean refresh) {
        swipeLayout.setRefreshing(refresh);
        sendRequest();
        swipeLayout.setRefreshing(!refresh);
    }

    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.refresh:
                dispatchRefresh(true);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    // Class to compare Movies by ratings
    class AlbumCompare implements Comparator<Album>
    {
        public int compare(Album m1, Album m2)
        {
            String alb1 = m1.getTitle().toUpperCase();
            String alb2 = m2.getTitle().toUpperCase();

            //ascending order
            return alb1.compareTo(alb2);
        }
    }
}
