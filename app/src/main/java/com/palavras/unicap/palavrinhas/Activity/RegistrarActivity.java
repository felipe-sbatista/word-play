package com.palavras.unicap.palavrinhas.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.palavras.unicap.palavrinhas.Entity.Pontuacao;
import com.palavras.unicap.palavrinhas.Entity.Usuario;
import com.palavras.unicap.palavrinhas.Persistence.AppDatabase;
import com.palavras.unicap.palavrinhas.Persistence.DatabaseCopier;
import com.palavras.unicap.palavrinhas.R;

public class RegistrarActivity extends AppCompatActivity {

    private AppDatabase database;
    private Pontuacao pontuacao;
    private String mensagem="", nome="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        final DatabaseCopier copier = DatabaseCopier.getInstance(getApplicationContext());
        database = copier.getRoomDatabase();
        this.pontuacao = (Pontuacao)getIntent().getSerializableExtra("Pontuacao");
        this.mensagem = getIntent().getStringExtra("Mensagem");
        TextView msgFinal = findViewById(R.id.mensagemFinal);
        msgFinal.setText(mensagem + "\n" + pontuacao.getPontos() + " - PONTOS");
        Usuario usuario = new Usuario();

        Button botaoSalvar = findViewById(R.id.salvar);
        botaoSalvar.setOnClickListener(v -> {
            TextView textView = findViewById(R.id.nome);
            usuario.setNome(String.valueOf(textView.getText()));
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    int id = (int) database.usuarioDAO().insertUsuario(usuario);
                    pontuacao.setUsuarioId(id);
                    database.pontuacaoDAO().insertPontuacao(pontuacao);
                    return null;
                };
            }.execute();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
    }

    public void getClick(View view) {
        Button botao = findViewById(view.getId());
        String letra = botao.getText().toString();
        nome = nome + letra ;
        TextView textView = findViewById(R.id.nome);
        textView.setText(nome);
    }
}
