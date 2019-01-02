package com.palavras.unicap.palavrinhas.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.palavras.unicap.palavrinhas.Entity.Pontuacao;
import com.palavras.unicap.palavrinhas.Entity.Usuario;
import com.palavras.unicap.palavrinhas.Persistence.AppDatabase;
import com.palavras.unicap.palavrinhas.R;

public class RegistrarActivity extends AppCompatActivity {

    private AppDatabase database;
    private Pontuacao pontuacao;
    private String mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        this.database = (AppDatabase) getIntent().getSerializableExtra("DbAccess");
        this.pontuacao = (Pontuacao)getIntent().getSerializableExtra("Pontuacao");
        this.mensagem = getIntent().getStringExtra("Mensagem");
        TextView msgFinal = findViewById(R.id.mensagemFinal);
        msgFinal.setText(mensagem + "\n" + pontuacao + "PONTOS");
        Usuario usuario = new Usuario();

        Button botaoSalvar = findViewById(R.id.salvar);
        botaoSalvar.setOnClickListener(v -> {
            TextView textView = findViewById(R.id.nome);
            usuario.setNome(String.valueOf(textView.getText()));
            new Runnable(){
                @Override
                public void run() {
                    pontuacao.setUsuarioId((int) database.usuarioDAO().insertUsuario(usuario));
                    database.pontuacaoDAO().insertPontuacao(pontuacao);
                }
            };
            Intent intent = new Intent(RegistrarActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
    }
}
