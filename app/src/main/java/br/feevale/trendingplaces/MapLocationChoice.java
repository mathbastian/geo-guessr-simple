package br.feevale.trendingplaces;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;

public class MapLocationChoice extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap map;
    private LatLng correctLatLng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_location_choice);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.setOnMapClickListener(this);
        Coordinates correctCoordinates = (Coordinates) getIntent().getSerializableExtra("coordinates");
        correctLatLng = new LatLng(correctCoordinates.getLatitute(), correctCoordinates.getLongitude());
    }

    @Override
    public void onMapClick(LatLng chosenlatLng) {

        double distance = SphericalUtil.computeDistanceBetween(chosenlatLng, correctLatLng);
        Toast.makeText(getBaseContext(),
                "Distancia entre o lugar mostrado anteriormente e o escolhido por voce em kms: " + Double.toString(distance/1000)
                + "Volte para jogar de novo!",
                Toast.LENGTH_LONG)
                .show();

        map.addMarker( new MarkerOptions().position(correctLatLng).title("Local Correto"));
        map.addMarker( new MarkerOptions().position(chosenlatLng).title("Local que você escolheu"));

        Polyline polyline = map.addPolyline( new PolylineOptions()
                            .clickable(true)
                            .add(chosenlatLng, correctLatLng));
        polyline.setTag("Distancia");
    }
}
