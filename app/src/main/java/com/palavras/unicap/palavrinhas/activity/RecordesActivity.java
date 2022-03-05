package com.palavras.unicap.palavrinhas.activity;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.adapter.RecyclerViewAdapter;
import com.palavras.unicap.palavrinhas.data.database.AppDatabase;
import com.palavras.unicap.palavrinhas.entity.Usuario;
import com.palavras.unicap.palavrinhas.entity.UsuarioDB;
import com.palavras.unicap.palavrinhas.util.Constantes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class RecordesActivity extends AppCompatActivity {

    List<UsuarioDB> usuarios = new ArrayList<>();

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordes);

        AsyncTask.execute(() -> {
            // Insert Data
            db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "database-name").build();
            usuarios = db.userDao().getAll();
            // Stuff that updates the UI
            runOnUiThread(this::createRecyclerView);
        });
    }

    private void createRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.lista_usuarios);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.Adapter adapter = new RecyclerViewAdapter(usuarios, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
