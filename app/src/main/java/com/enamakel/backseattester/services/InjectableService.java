package com.enamakel.backseattester.services;


import android.app.Service;
import android.content.Intent;

import com.enamakel.backseattester.App;


public abstract class InjectableService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        App.getApplicationGraph().inject(this);
        return super.onStartCommand(intent, flags, startId);
    }
}
