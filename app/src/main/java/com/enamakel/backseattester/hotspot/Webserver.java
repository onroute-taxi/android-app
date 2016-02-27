package com.enamakel.backseattester.hotspot;


import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;


public class Webserver extends NanoHTTPD {
    static Webserver instance;


    public Webserver() {
        super("0.0.0.0", 8080);
        CaptivePortal.start("192.168.43.1:8080");
    }


    public static void startServer() throws IOException {
        if (instance == null) instance = new Webserver();
        instance.start();
    }


    public static void stopServer() {
        if (instance != null) instance.stop();
        CaptivePortal.stop();
    }


    @Override
    public Response serve(IHTTPSession session) {
        String msg = "<html><body><h1>Hello server</h1>\n";
        Map<String, String> parms = session.getParms();
        if (parms.get("username") == null) {
            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
        } else {
            CaptivePortal.stop();
            msg += "<p>Hello, " + parms.get("username") + "!</p>";
        }
        return new Response(msg + "</body></html>\n");
    }
}