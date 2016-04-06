package com.onroute.android.data.listeners;


import java.util.HashMap;
import java.util.Map;


public abstract class BaseListener<L, D> {
    Map<String, L> listeners = new HashMap<>();


    public void addListener(L toAdd, String name) {
        if (!listeners.containsKey(name)) listeners.put(name, toAdd);
    }


    public void removeListener(String name) {
        if (listeners.containsKey(name)) listeners.remove(name);
    }


    public void notifyListeners(D data) {
        for (String key : listeners.keySet()) onListenerNotify(listeners.get(key), data);
    }


    abstract void onListenerNotify(L listener, D data);
}