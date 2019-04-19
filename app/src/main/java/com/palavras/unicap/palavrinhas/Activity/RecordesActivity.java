package com.palavras.unicap.palavrinhas.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.palavras.unicap.palavrinhas.Entity.Usuario;
import com.palavras.unicap.palavrinhas.R;

import java.util.ArrayList;
import java.util.List;


public class RecordesActivity extends AppCompatActivity {

    private List<Usuario> usuarios = new ArrayList<>();
    private int[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordes);

//        usuarios = database.usuarioDAO().loadAllUsuarios();
        Usuario u1 = new Usuario();
        Usuario u2 = new Usuario();
        u1.getPontos().add(20);
        u2.getPontos().add(10);
        u1.setNome("felipe");
        u2.setNome("lucas");
        usuarios.add(u1);
        usuarios.add(u2);

        RecyclerView listaUsuarios = findViewById(R.id.lista_usuarios);
        listaUsuarios.setAdapter(new ListaUsuariosAdapter(usuarios, this));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listaUsuarios.setLayoutManager(layoutManager);


    }





}
