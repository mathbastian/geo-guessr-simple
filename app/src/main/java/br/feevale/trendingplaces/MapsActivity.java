package br.feevale.trendingplaces;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements View.OnClickListener, OnStreetViewPanoramaReadyCallback {

    private List<LatLng> coordinates = new ArrayList<LatLng>();
    private LatLng currentCoordinate;
    private final int MAP_RESULT = 1234;
    Button voteButton;
    private int locationIndex = 1;
    private StreetViewPanorama panorama;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.street_view);

        voteButton = findViewById(R.id.voteButton);
        voteButton.setOnClickListener(this);

        coordinates.add( new LatLng(-33.87365, 151.20689));
        coordinates.add( new LatLng(48.871265, 2.362855));
        coordinates.add( new LatLng(40.725185, -74.002998));

        currentCoordinate = coordinates.get(0);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment = (SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync( this );
    }

    @Override
    public void onClick(View view) {
        Intent mapChoice = new Intent(getBaseContext(), MapLocationChoice.class);

        Coordinates coordinates = new Coordinates();
        coordinates.setLatitute(currentCoordinate.latitude);
        coordinates.setLongitude(currentCoordinate.longitude);

        mapChoice.putExtra("coordinates", coordinates);
        startActivityForResult(mapChoice, MAP_RESULT);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
        this.panorama = panorama;
        this.panorama.setPosition(currentCoordinate);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAP_RESULT){
            currentCoordinate = coordinates.get(locationIndex);
            locationIndex += 1;
            onStreetViewPanoramaReady(this.panorama);
        }
    }
}