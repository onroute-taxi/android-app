package com.enamakel.backseattester.websocket;

import com.enamakel.backseattester.data.models.SessionModel;
import com.google.gson.annotations.Expose;

import lombok.Getter;

/**
 * Created by robert on 12/29/15.
 */
public class Response {
    @Expose @Getter String status;
    @Expose @Getter String error_message;
    @Expose @Getter String error;
    @Expose @Getter Request request;
    @Expose @Getter SessionModel session;
}
