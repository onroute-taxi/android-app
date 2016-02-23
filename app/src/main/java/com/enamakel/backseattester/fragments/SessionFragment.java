package com.enamakel.backseattester.fragments;


import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.data.resources.TabletResource;
import com.enamakel.backseattester.websocket.Websocket;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;


@EFragment(R.layout.fragment_session)
public class SessionFragment extends Fragment {
    @ViewById TextView sessionStatusText;
    @ViewById EditText sessionSocketIPtext;
    @ViewById Button sessionRestartWebserver;
    @ViewById Button sessionCheckin;
    @ViewById Button sessionHeartbeat;
    @ViewById TextView sessionLogContainer;

    @Inject TabletResource tabletResource;
    @Inject Websocket websocket;


    @AfterViews
    void afterViewInjection() {
        sessionSocketIPtext.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        sessionLogContainer.setMovementMethod(new ScrollingMovementMethod());

        sessionSocketIPtext.setText("192.168.1.120");
        sessionStatusText.setText("activity started");

        sessionRestartWebserverClicked();
    }


    @UiThread
    public void logMessage(final String message) {
        if (sessionStatusText != null) sessionStatusText.setText(message);
        if (sessionLogContainer != null)
            sessionLogContainer.setText(sessionLogContainer.getText() + "\n" + message);
    }


    @Click
    void sessionRestartWebserverClicked() {
        String socket_ip = sessionSocketIPtext.getText().toString();
        String socket_url = "ws://" + socket_ip + ":1414";

        try {
            URI socket_uri = new URI(socket_url);
            websocket.connect(socket_uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Click
    void sessionCheckinClicked() {
        tabletResource.checkin();
    }


    @Click
    void sessionHeartbeatClicked() {
        tabletResource.hearbeat();
    }
}