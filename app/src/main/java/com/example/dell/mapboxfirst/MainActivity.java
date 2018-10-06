package com.example.dell.mapboxfirst;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.MarkerViewOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.cluster.MarkerManager;
import com.mapbox.mapboxsdk.plugins.cluster.clustering.Cluster;
import com.mapbox.mapboxsdk.plugins.cluster.clustering.ClusterItem;
import com.mapbox.mapboxsdk.plugins.cluster.clustering.ClusterManagerPlugin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;
    private MapboxMap mapbox;
    private ClusterManagerPlugin clusterManagerPlugin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                // One way to add a marker view
                //https://www.mapbox.com/android-docs/maps/overview/annotations/#markers
                // Create an Icon object for the marker to use
                mapbox = mapboxMap;
                IconFactory iconFactory = IconFactory.getInstance(MainActivity.this);
                Icon icon = iconFactory.fromResource(R.drawable.toys);
                Icon icon1 = iconFactory.fromResource(R.drawable.balloons);
                Icon icon2 = iconFactory.fromResource(R.drawable.apartment);
                Icon icon3 = iconFactory.fromResource(R.drawable.daycare);
                Icon icon4 = iconFactory.fromResource(R.drawable.townhouse);
                Icon icon5 = iconFactory.fromResource(R.drawable.nursery);
                Icon icon6 = iconFactory.fromResource(R.drawable.retirement_home);
                Icon icon7 = iconFactory.fromResource(R.drawable.house);
                mapboxMap.addMarker(new MarkerViewOptions()
                        .position(new LatLng(40.885, -87.09))
                        .title("Place1 : 40.885 , -87.09")
                        .icon(icon)
                );
                mapboxMap.addMarker(new MarkerViewOptions()
                        .position(new LatLng(42.885, -87.679))
                        .title("Place2 : 42.885 , -87.679")
                        .icon(icon1)
                );
                mapboxMap.addMarker(new MarkerViewOptions()
                        .position(new LatLng(41.885, -86.679))
                        .title("Place3 : 41.885 , -86.679")
                        .icon(icon2)
                );
                mapboxMap.addMarker(new MarkerViewOptions()
                        .position(new LatLng(40.885, -86.679))
                        .title("Place4 : 40.885 , -86.679")
                        .icon(icon3)
                );mapboxMap.addMarker(new MarkerViewOptions()
                        .position(new LatLng(42.885, -88.679))
                        .title("Place5 : 42.885 , -88.679")
                        .icon(icon4)
                );mapboxMap.addMarker(new MarkerViewOptions()
                        .position(new LatLng(41.885, -89.679))
                        .title("Place6 : 41.885 , -89.679")
                        .icon(icon5)
                );
                mapboxMap.addMarker(new MarkerViewOptions()
                        .position(new LatLng(44.885, -87.679))
                        .title("Place7 : 44.885 , -87.679")
                        .icon(icon6)
                );
                mapboxMap.addMarker(new MarkerViewOptions()
                        .position(new LatLng(43.885, -85.679))
                        .title("Place8 : 43.885 , -85.679")
                        .icon(icon7)
                );

                MarkerManager markerManager = new MarkerManager(mapboxMap);
                clusterManagerPlugin = new ClusterManagerPlugin(MainActivity.this,mapboxMap,markerManager);
//                initCameraListener();
                mapboxMap.setOnMarkerClickListener(clusterManagerPlugin);
//                mapboxMap.setOnCameraChangeListener((MapboxMap.OnCameraChangeListener) clusterManagerPlugin);

                clusterManagerPlugin.setOnClusterClickListener(new ClusterManagerPlugin.OnClusterClickListener() {
                    @Override
                    public boolean onClusterClick(Cluster cluster) {
                        Log.v("yess","cluster clicked");
                        return true;
                    }
                });
                clusterManagerPlugin.setOnClusterItemClickListener(new ClusterManagerPlugin.OnClusterItemClickListener() {
                    @Override
                    public boolean onClusterItemClick(ClusterItem item) {
                        Log.v("yess","item clicked");
                        return true;
                    }
                });

               /* mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        Toast.makeText(MainActivity.this,marker.getTitle()+" : "+marker.getSnippet(),
                                Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
                */
                mapboxMap.getMarkerViewManager().setOnMarkerViewClickListener(new MapboxMap.OnMarkerViewClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker, @NonNull View view, @NonNull MapboxMap.MarkerViewAdapter adapter) {

                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(MapboxMap mapboxMap) {
                                LatLng zoomLocation = marker.getPosition();
                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(zoomLocation)
                                        .zoom(12)
                                        .build();

                                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                                        1000);
                            }
                        });
                        return false;
                    }
                });
            }

        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /*
    protected void initCameraListener(){
            mapbox.addOnCameraIdleListener(clusterManagerPlugin);
            try{
                addItemsToClusterPlugin(R.drawable.daycare);
            }
            catch (Exception e){
                Log.v("exception",e.getMessage());
            }
    }
    private void addItemsToClusterPlugin(int resource) throws Exception {
        InputStream inputStream = getResources().openRawResource(resource);
        List<MyItem> list = new MyItemReader().read(inputStream);
        clusterManagerPlugin.addItem((ClusterItem) list);
    }

    public static class MyItem implements ClusterItem{
        private final LatLng position;
        private String title;
        private String snippet;

        public MyItem(double lat, double lng) {
            position = new LatLng(lat, lng);
            title = null;
            snippet = null;
        }

        public MyItem(double lat, double lng, String title, String snippet) {
            position = new LatLng(lat, lng);
            this.title = title;
            this.snippet = snippet;
        }

        @Override
        public LatLng getPosition() {
            return position;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getSnippet() {
            return snippet;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSnippet(String snippet) {
            this.snippet = snippet;
        }
    }

    public static class MyItemReader{
        private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

        public List<MyItem> read(InputStream inputStream) throws Exception {
            List<MyItem> items = new ArrayList<MyItem>();
            String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                String title = null;
                String snippet = null;
                JSONObject object = array.getJSONObject(i);
                double lat = object.getDouble("latitude");
                double lng = object.getDouble("longitude");
                if (!object.isNull("name")) {
                    title = object.getString("name");
                }
                if (!object.isNull("address")) {
                    snippet = object.getString("address");
                }
                items.add(new MyItem(lat, lng, title, snippet));
            }
            return items;
        }
    }
    */

}
