package com.enamakel.backseattester.websocket;


import com.enamakel.backseattester.data.models.SessionModel;
import com.google.gson.annotations.Expose;

import lombok.Data;


@Data
public class Response {
    @Expose String status;
    @Expose String error_message;
    @Expose String error;
    @Expose Request request;
    @Expose SessionModel session;
}
