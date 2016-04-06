package com.onroute.android.fragments;


import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.onroute.android.R;
import com.onroute.android.data.listeners.ResponseManager;
import com.onroute.android.data.models.PassengerModel;
import com.onroute.android.data.models.SessionModel;
import com.onroute.android.data.resources.MediaResource;
import com.onroute.android.data.resources.PassengerResource;
import com.onroute.android.fragments.base.BaseFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;


@EFragment(R.layout.fragment_passenger)
public class PassengerFragment extends BaseFragment {
    @ViewById EditText passengerPhoneNumber;
    @ViewById EditText passengerFullName;
    @ViewById EditText passengerEmail;
    @ViewById Button passengerSendOTPbutton;
    @ViewById Button passengerSaveDetails;
    @ViewById Button passengerSkipOTPButton;
    @ViewById Button passengerVerifyOTPButton;
    @ViewById LinearLayout passengerDetailsLayout;
    @ViewById ListView passengerMovieList;
    @ViewById Button passengerShowMovies;
    @ViewById Button passengerHideMovies;
    @ViewById Button passengerRefreshMovies;

    @Inject MediaResource mediaResource;
    @Inject PassengerResource passengerResource;
    @Inject ResponseManager responseManager;


    @AfterViews
    void afterViews() {
        passengerPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // Register a listener for a change in the passenger..
        responseManager.addListener(new ResponseManager.Listener() {
            @Override
            public void onServerResponse(SessionModel session) {
                onPassengerChange(session.getPassenger());
            }
        }, getClass().getName());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        responseManager.removeListener(getClass().getName());
    }


    @UiThread
    public void onPassengerChange(final PassengerModel passenger) {
        if (passenger != null) {
            passengerDetailsLayout.setVisibility(View.VISIBLE);

            passengerPhoneNumber.setText(passenger.getPhoneNumber());
            passengerEmail.setText(passenger.getEmail());
            passengerFullName.setText(passenger.getFullName());

//            setupMovieList();
        } else {
//            passengerDetailsLayout.setVisibility(View.INVISIBLE);
        }
    }


//    @UiThread
//    public void setupMovieList() {
//        MovieListAdapter adapter = new MovieListAdapter(mediaResource.getMovies());
//        passengerMovieList.setAdapter(adapter);
//    }


    @Click
    void passengerSkipOTPButtonClicked() {
        passengerResource.skipOTP(passengerPhoneNumber.getText().toString());
    }


    @Click
    void passengerShowMoviesClicked() {
        passengerMovieList.setVisibility(View.VISIBLE);
    }


    @Click
    void passengerHideMoviesClicked() {
        passengerMovieList.setVisibility(View.INVISIBLE);
    }


    @Click
    void passengerRefreshMovies() {
        mediaResource.updateMovieList();
    }


    @Click
    void passengerSaveDetailsClicked() {
        PassengerModel passenger = new PassengerModel();
        passenger.setPhoneNumber(passengerPhoneNumber.getText().toString());
        passenger.setFullName(passengerFullName.getText().toString());
        passenger.setEmail(passengerEmail.getText().toString());

        passengerResource.save(passenger);
    }


    @Click
    void passengerSendOTPbuttonClicked() {
        passengerResource.sendOTP(passengerPhoneNumber.getText().toString());
    }
}