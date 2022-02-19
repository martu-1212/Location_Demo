package com.example.martinalocationdemo.ui;

import static com.example.martinalocationdemo.R.id.place_autocomplete_fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.martinalocationdemo.R;
import com.example.martinalocationdemo.database.DatabaseClient;
import com.example.martinalocationdemo.database.model.Locations;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class AddLocationActivity extends AppCompatActivity {

    private PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        String apiKey = getString(R.string.google_api_key);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Create a new Places client instance.
        placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.ADDRESS);
        autocompleteFragment.setCountry("IN");
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                addLocation(place.getName(),place.getLatLng());
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(),"Location Not Added.Try again!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void addLocation(String name, LatLng lat_long) {


        class AddLocation extends AsyncTask<Void, Void, Void> {
            String latitude = String.valueOf(lat_long.latitude);
            String longitude = String.valueOf(lat_long.longitude);

            @Override
            protected Void doInBackground(Void... voids) {
                Locations locations = new Locations();
                locations.setName(name);
                locations.setLatitude(latitude);
                locations.setLongitude(longitude);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .locationDao()
                        .insert(locations);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        AddLocation addLocation = new AddLocation();
        addLocation.execute();
    }
}
