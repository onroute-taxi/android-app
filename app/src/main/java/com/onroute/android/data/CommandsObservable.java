package com.onroute.android.data;


import java.util.Observable;


public class CommandsObservable extends Observable {
    private String name = "First time i have this Text";


    /**
     * @return the value
     */
    public String getValue() {
        return name;
    }


    public void setValue(String name) {
        this.name = name;
        setChanged();
        notifyObservers();
    }
}
