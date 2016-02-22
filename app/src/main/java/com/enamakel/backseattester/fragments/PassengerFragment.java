package com.enamakel.backseattester.fragments;


import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.adapters.MovieListAdapter;
import com.enamakel.backseattester.data.models.PassengerModel;
import com.enamakel.backseattester.data.resources.MediaResource;
import com.enamakel.backseattester.data.resources.PassengerResource;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;


@EFragment(R.layout.fragment_passenger)
public class PassengerFragment extends Fragment {
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


    @AfterViews
    void afterViewInjection() {
        passengerPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        passengerDetailsLayout.setVisibility(View.INVISIBLE);
        passengerMovieList.setVisibility(View.INVISIBLE);
    }


    @UiThread
    public void onPassengerChange(final PassengerModel passenger) {
        if (passenger != null) {
            passengerDetailsLayout.setVisibility(View.VISIBLE);

            passengerPhoneNumber.setText(passenger.getPhone_number());
            passengerEmail.setText(passenger.getEmail());
            passengerFullName.setText(passenger.getFull_name());

            setupMovieList();
        } else {
            passengerDetailsLayout.setVisibility(View.INVISIBLE);
        }
    }


    @UiThread
    public void setupMovieList() {
        MovieListAdapter adapter = new MovieListAdapter(getActivity(), mediaResource.movies);
        passengerMovieList.setAdapter(adapter);
    }


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
        passenger.setPhone_number(passengerPhoneNumber.getText().toString());
        passenger.setFull_name(passengerFullName.getText().toString());
        passenger.setEmail(passengerEmail.getText().toString());

        passengerResource.save(passenger);
    }


    @Click
    void passengerSendOTPbuttonClicked() {
        passengerResource.sendOTP(passengerPhoneNumber.getText().toString());
    }
}