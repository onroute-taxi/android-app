package com.onroute.android.services;


import android.app.Service;
import android.content.Intent;

import com.onroute.android.App;


public abstract class InjectableService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        App.getApplicationGraph().inject(this);
        return super.onStartCommand(intent, flags, startId);
    }
}
