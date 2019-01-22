package com.palavras.unicap.palavrinhas.Activity;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.palavras.unicap.palavrinhas.Entity.Pontuacao;
import com.palavras.unicap.palavrinhas.Entity.Usuario;
import com.palavras.unicap.palavrinhas.Persistence.AppDatabase;
import com.palavras.unicap.palavrinhas.Persistence.DatabaseCopier;
import com.palavras.unicap.palavrinhas.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;



public class RecordesActivity extends AppCompatActivity {

    private AppDatabase database;
    private List<Pontuacao> pontuacoes;
    private Map<Long, Usuario> usuarios;
    private int[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordes);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "palavras.db").build();

        ConstraintLayout layout = findViewById(R.id.layout);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                pontuacoes = database.pontuacaoDAO().listTop10();
                Set<Integer> set = getIds();
                Integer[] a = set.stream().toArray(Integer[]::new);
                ids = Arrays.stream(a).mapToInt(Integer::intValue).toArray();
                List<Usuario> list;
                list = database.usuarioDAO().findByIds(ids);
                usuarios = list.stream().collect(Collectors.toMap(Usuario::getId, item -> item));
                return null;
            }
        }.execute();
        Thread t = Thread.currentThread();
        try {
            synchronized (t){
                t.wait(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setPontos();

        adicionarPontuacoes(layout);

    }

    private void adicionarPontuacoes(ConstraintLayout layout) {
        int margem = 30;
        for(Pontuacao pontuacao: pontuacoes) {
            ConstraintSet set = new ConstraintSet();
            TextView textView = new TextView(this);
            textView.setTextSize(32);
            textView.setText(pontuacao.getUsuario().getNome() + " - " + pontuacao.getPontos());
            layout.addView(textView, 0);
            set.clone(layout);
            set.connect(textView.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, margem+=30);
            set.applyTo(layout);
        }
    }


    private void setPontos() {
        for(Pontuacao p:pontuacoes){
            p.setUsuario(usuarios.get(p.getUsuarioId()));
        }
    }

    private Set<Integer> getIds(){
        Set<Integer> set = new HashSet<>();
        for (Pontuacao p: pontuacoes){
            set.add(p.getUsuarioId());
        }
        return set;
    }



}
