package com.palavras.unicap.palavrinhas.Activity;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.palavras.unicap.palavrinhas.Entity.Pontuacao;
import com.palavras.unicap.palavrinhas.Entity.Usuario;
import com.palavras.unicap.palavrinhas.Persistence.AppDatabase;
import com.palavras.unicap.palavrinhas.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordesActivity extends AppCompatActivity {

    private AppDatabase database;
    private List<Pontuacao> pontuacoes;
    private List<Usuario> usuarios;
    private Map<Long, Usuario> mapUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordes);
        database = (AppDatabase) getIntent().getSerializableExtra("DbAccess");

        ConstraintLayout layout = findViewById(R.id.layout);

        getResultados();

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


    private void getResultados(){
        new Runnable(){
            @Override
            public void run() {
                pontuacoes = database.pontuacaoDAO().listTop10();
                int [] ids = getIds(pontuacoes);
                usuarios = database.usuarioDAO().findByIds(ids);
                 mapUsuarios = usuarios.stream().collect(Collectors.toMap(Usuario::getId, item->item));
                setPontos(mapUsuarios, pontuacoes);
            }
        };
    }

    private void setPontos(Map<Long, Usuario> usuarios, List<Pontuacao> pontuacoes) {
        for(Pontuacao p:pontuacoes){
            p.setUsuario(usuarios.get(p.getUsuarioId()));
        }
    }


    private int [] getIds(List<Pontuacao> pontuacoes){
        int ids [] = new int [pontuacoes.size()];
        for(int i = 0; i<pontuacoes.size(); i++){
            ids[i] = pontuacoes.get(i).getUsuarioId();
        }
        return ids;
    }
}
