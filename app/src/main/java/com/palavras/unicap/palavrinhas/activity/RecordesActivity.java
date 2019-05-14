package com.palavras.unicap.palavrinhas.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.adapter.RecyclerViewAdapter;
import com.palavras.unicap.palavrinhas.entity.Usuario;
import com.palavras.unicap.palavrinhas.util.Constantes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class RecordesActivity extends AppCompatActivity {

    private List<Usuario> usuarios = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordes);
        new Thread(() -> this.fetchData()).start();

    }

    private void fetchData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference(Constantes.USUARIOS_REFERENCE);
        Query query = reference.orderByChild(Constantes.PALAVRAS_REFERENCE);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    usuarios.add(singleSnapshot.getValue(Usuario.class));
                }
                usuarios.sort(Comparator.comparing(Usuario::getPontos).reversed());
                createRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }


    private void createRecyclerView() {
        recyclerView = findViewById(R.id.lista_usuarios);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(usuarios, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
