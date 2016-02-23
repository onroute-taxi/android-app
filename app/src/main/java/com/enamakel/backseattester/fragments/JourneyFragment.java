package com.enamakel.backseattester.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.data.models.TabletModel;
import com.enamakel.backseattester.data.resources.JourneyResource;
import com.enamakel.backseattester.websocket.Websocket;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_journey)
public class JourneyFragment extends Fragment {
    static JourneyFragment instance;

    Icon circleIcon;
    @ViewById Button journeyStop;
    @ViewById Button journeyStart;
    @ViewById MapView mapboxMapView;

    @Inject JourneyResource journeyResource;
    @Inject Websocket websocket;


    @AfterViews
    void afterViewsInjection() {
        mapboxMapView.setStyleUrl(Style.MAPBOX_STREETS);
        mapboxMapView.setCenterCoordinate(new LatLng(40.73581, -73.99155));
        mapboxMapView.setZoomLevel(16);
        mapboxMapView.onCreate(getArguments());
    }


    public static void addMarker(double lat, double lon) {
        if (instance != null) {
            LatLng point = new LatLng(lat, lon);

            instance.mapboxMapView.addMarker(new MarkerOptions()
                            .position(point)
                            .icon(instance.circleIcon)
            );
            instance.mapboxMapView.setCenterCoordinate(point);
        }
    }


    @AfterViews
    void afterViews() {
        instance = this;

        IconFactory mIconFactory = IconFactory.getInstance(this.getActivity());
        Drawable mIconDrawable = ContextCompat.getDrawable(this.getActivity(), R.drawable.location_marker);
        circleIcon = mIconFactory.fromDrawable(mIconDrawable);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Click
    void journeyStopClicked() {
        TabletModel.TabletStatus status = TabletModel.getInstance().getStatus();
        mapboxMapView.addMarker(new MarkerOptions()
                .position(new LatLng(status.getLatitude(), status.getLongitude())));
        journeyResource.endJourney();
    }


    @Click
    void journeyStartClicked() {
        mapboxMapView.removeAllAnnotations();

        TabletModel.TabletStatus status = TabletModel.getInstance().getStatus();
        mapboxMapView.addMarker(new MarkerOptions()
                .position(new LatLng(status.getLatitude(), status.getLongitude())));
        journeyResource.beginJourney();
    }
}