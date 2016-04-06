package com.onroute.android.data.resources;


import com.onroute.android.data.models.PassengerModel;
import com.onroute.android.data.models.SessionModel;
import com.onroute.android.network.websocket.Request;
import com.onroute.android.network.websocket.Websocket;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class PassengerResource extends BaseResource {
    @Inject SessionModel session;
    @Inject Websocket websocket;
    PassengerModel passenger;


    public void checkin(String macAddress) {
        passenger = new PassengerModel();
        passenger.erase(macAddress);
        session.setPassenger(passenger);

        Request request = new Request("passenger", "checkin", macAddress);
        websocket.send(request);
    }


    @Override
    public void onServerResponse(SessionModel session) {
        passenger = session.getPassenger();
    }


    public void save(PassengerModel passenger) {
        session.setPassenger(passenger);

        Request request = new Request("passenger", "save");
        websocket.send(request);
    }


    public void skipOTP(String number) {
        PassengerModel passenger = new PassengerModel();
        passenger.setPhoneNumber(number);

        session.setPassenger(passenger);

        Request request = new Request("passenger", "skip_otp");
        websocket.send(request);
    }


    public void sendOTP(String number) {
        PassengerModel passenger = new PassengerModel();
        passenger.setPhoneNumber(number);

        session.setPassenger(passenger);

        Request request = new Request("passenger", "ask_otp");
        websocket.send(request);
    }
}