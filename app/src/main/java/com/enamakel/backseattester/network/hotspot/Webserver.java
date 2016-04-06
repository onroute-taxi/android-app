package com.enamakel.backseattester.network.hotspot;


import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;


/**
 * A webserver to serve content to the user. This webserver is access via the captive portal that
 * is setup by the hotspot.
 * <p/>
 * TODO: Server an entire folder with all the resources needed.
 */
public class Webserver extends NanoHTTPD {
    static Webserver instance;


    public Webserver() {
        super("0.0.0.0", 8080);

        // Start the captive portal and redirect all users to this ip and port.
        // TODO: Put this in a different place.
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