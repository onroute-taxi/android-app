package com.enamakel.backseattester.fragments;


import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.enamakel.backseattester.R;
import com.enamakel.backseattester.data.resources.TabletResource;
import com.enamakel.backseattester.network.websocket.Websocket;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;


@EFragment(R.layout.fragment_session)
public class SessionFragment extends BaseFragment {
    @ViewById TextView sessionStatusText;
    @ViewById EditText sessionSocketIPtext;
    @ViewById Button sessionRestartWebserver;
    @ViewById Button sessionCheckin;
    @ViewById TextView sessionLogContainer;

    @Inject TabletResource tabletResource;
    @Inject Websocket websocket;


    @AfterViews
    void afterViewInjection() {
        sessionSocketIPtext.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        sessionLogContainer.setMovementMethod(new ScrollingMovementMethod());

        sessionSocketIPtext.setText("192.168.1.120");
        sessionStatusText.setText("activity started");
    }


    @UiThread
    public void logMessage(final String message) {
        if (sessionStatusText != null) sessionStatusText.setText(message);
        if (sessionLogContainer != null)
            sessionLogContainer.setText(sessionLogContainer.getText() + "\n" + message);
    }


    @Click
    void sessionRestartWebserverClicked() {
        try {
            websocket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Click
    void sessionCheckinClicked() {
        tabletResource.checkin();
    }
}