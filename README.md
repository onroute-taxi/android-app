Onroute Tablet App
==================

This repository contains code for the android app which will be installed in the tablets for Onroute.

The Onroute app is written as a home launcher app to create a completely sealed appearance. The app also attempts to communicate with the [Onroute server-side](https://github.com/onroute-taxi/server-database) by using a websocket connection. 


Requirements
------------
 - IDE, Android Studio, Ecplise or just gradle
 - If using Android Studio, install the lombok plugin


How it works
------------
The app contains a built-in websocket client that contains code for communicating with the server-side.

Every single action that happens in the tablet, sends a message to the server via the websocket connection.

The tablet gets responses from the server and executes various tasks based on it. The tablet also at regular 5 second intervals sends a ```heartbeat``` to the server sharing it's location and current status (See DatabaseService.java). 

Also to manage advertisements, the app and the server keep track of advertisement slots. Advertisement slots are places within the tablet where advertisements can be shown (before a movie, within a movie tile etc). At every heartbeat, the tablet recieves advertisments recommended by the server to be placed on each of these slots. When an advertisement is served, the tablet informs the server.


Todo
----
 - Completely integrate all parts of the tablet (the persona selection screen, media tiles etc..) to the server. Right now content is hard-coded.
 - Grab advertisements from the server.
 - Implement a basic system of advertisement slots.
