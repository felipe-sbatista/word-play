package com.palavras.unicap.palavrinhas;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class PalavrinhasApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
