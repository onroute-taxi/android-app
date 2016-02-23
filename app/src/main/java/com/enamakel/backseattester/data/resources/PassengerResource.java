package com.enamakel.backseattester.data.resources;


import com.enamakel.backseattester.activities.TabbedActivity;
import com.enamakel.backseattester.data.models.PassengerModel;
import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.websocket.Request;
import com.enamakel.backseattester.websocket.Websocket;

import javax.inject.Inject;


public class PassengerResource extends BaseResource {
    @Inject PassengerModel passenger;
    @Inject SessionModel session;
    @Inject Websocket websocket;


    public void checkin(String macAddress) {
        Request request = new Request("passenger", "checkin", macAddress);
        websocket.send(request);
    }


    @Override
    public void onSocketResponse(SessionModel session) {
        passenger = session.getPassenger();
    }


    public void save(PassengerModel passenger) {
        session.setPassenger(passenger);

        TabbedActivity.info("saving passenger details");
        Request request = new Request("passenger", "save");
        websocket.send(request);
    }


    public void skipOTP(String number) {
        PassengerModel passenger = new PassengerModel();
        passenger.setPhone_number(number);

        session.setPassenger(passenger);

        Request request = new Request("passenger", "skip_otp");
        websocket.send(request);
    }


    public void sendOTP(String number) {
        PassengerModel passenger = new PassengerModel();
        passenger.setPhone_number(number);

        session.setPassenger(passenger);

        TabbedActivity.info("sending OTP to " + number);
        Request request = new Request("passenger", "ask_otp");
        websocket.send(request);
    }
}