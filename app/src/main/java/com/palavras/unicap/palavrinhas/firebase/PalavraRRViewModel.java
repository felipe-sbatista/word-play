package com.palavras.unicap.palavrinhas.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PalavraRRViewModel extends ViewModel {
    private static final DatabaseReference REFERENCE = FirebaseDatabase.getInstance().getReference("palavras").child("RR");
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(REFERENCE);

    @NonNull
    public LiveData<DataSnapshot> getDataSnapShotLiveData(){
        return liveData;
    }
}
