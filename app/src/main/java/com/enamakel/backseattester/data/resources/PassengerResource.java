package com.enamakel.backseattester.data.resources;


import com.enamakel.backseattester.TabbedActivity;
import com.enamakel.backseattester.data.models.PassengerModel;
import com.enamakel.backseattester.data.models.SessionModel;
import com.enamakel.backseattester.websocket.Request;
import com.enamakel.backseattester.websocket.Websocket;

import javax.inject.Inject;


public class PassengerResource extends BaseResource {
    PassengerModel passenger;
    @Inject SessionModel session;


    @Override
    public void onSocketResponse(SessionModel session) {
        passenger = session.getPassenger();
//        TabbedActivity.context.passengerFragment.onPassengerChange(passenger);
    }


    public void save(PassengerModel passenger) {
        session.setPassenger(passenger);

        TabbedActivity.info("saving passenger details");
        Request request = Request.create("passenger", "save");
        Websocket.send(request);
    }


    public void skipOTP(String number) {
        PassengerModel passenger = new PassengerModel();
        passenger.setPhone_number(number);

        session.setPassenger(passenger);

        Request request = Request.create("passenger", "skip_otp");
        Websocket.send(request);
    }


    public void sendOTP(String number) {
        PassengerModel passenger = new PassengerModel();
        passenger.setPhone_number(number);

        session.setPassenger(passenger);

        TabbedActivity.info("sending OTP to " + number);
        Request request = Request.create("passenger", "ask_otp");
        Websocket.send(request);
    }
}