package com.example.martinalocationdemo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.martinalocationdemo.R;
import com.example.martinalocationdemo.adapter.LocationListAdapter;
import com.example.martinalocationdemo.database.DatabaseClient;
import com.example.martinalocationdemo.database.model.Locations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton flbtnShowpath;
    RecyclerView rvlocations;
    AppCompatTextView tvNodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flbtnShowpath = findViewById(R.id.flbtnShowPath);
        tvNodata = findViewById(R.id.tvNodata);
        flbtnShowpath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ShowPathActivity.class));
            }
        });
        setList();
    }

    public void setList(){
        rvlocations = findViewById(R.id.rvLocations);
        rvlocations.setLayoutManager(new LinearLayoutManager(this));

        class GetLocations extends AsyncTask<Void, Void, List<Locations>> {

            @Override
            protected List<Locations> doInBackground(Void... voids) {
                List<Locations> locationsList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .locationDao()
                        .getAll();
                return locationsList;
            }

            @Override
            protected void onPostExecute(List<Locations> locations) {
                super.onPostExecute(locations);

                if(locations.size() != 0 ){
                    rvlocations.setVisibility(View.VISIBLE);
                    LocationListAdapter adapter = new LocationListAdapter(MainActivity.this, locations);
                    rvlocations.setAdapter(adapter);
                }
                else{
                    tvNodata.setVisibility(View.VISIBLE);
                }
            }
        }

        GetLocations gt = new GetLocations();
        gt.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnAdd:
                startActivity(new Intent(this,AddLocationActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

