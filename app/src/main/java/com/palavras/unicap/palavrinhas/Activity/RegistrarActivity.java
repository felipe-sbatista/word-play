package com.palavras.unicap.palavrinhas.Activity;

import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.palavras.unicap.palavrinhas.Entity.Pontuacao;
import com.palavras.unicap.palavrinhas.Entity.Usuario;
import com.palavras.unicap.palavrinhas.Persistence.AppDatabase;
import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.Fragment.TecladoAlfabeticoFragment;
import com.palavras.unicap.palavrinhas.Fragment.TecladoAlfabeticoFragment.OnFragmentInteractionListener;

public class RegistrarActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private AppDatabase database;
    private Pontuacao pontuacao;
    private String mensagem="", nome="";

    @BindView(R.id.botao_salvar)
    Button botaoSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        ButterKnife.bind(this);

        TecladoAlfabeticoFragment fragment = new TecladoAlfabeticoFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.teclado, fragment);
        ft.commit();


        database = AppDatabase.getInstance(this);
        this.pontuacao = (Pontuacao)getIntent().getSerializableExtra("Pontuacao");
        this.mensagem = getIntent().getStringExtra("Mensagem");
        TextView msgFinal = findViewById(R.id.mensagemFinal);
        msgFinal.setText(mensagem + "\n" + pontuacao.getPontos() + " - PONTOS");

    }

    @OnClick(R.id.botao_salvar)
    public void salvarUsuario(){
        TextView textView = findViewById(R.id.nome);
        Usuario usuario = new Usuario();
        usuario.setNome(String.valueOf(textView.getText()));
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                int id = (int) database.usuarioDAO().insertUsuario(usuario);
                pontuacao.setUsuarioId(id);
                database.pontuacaoDAO().insertPontuacao(pontuacao);
                return null;
            }
        }.execute();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }


    @Override
    public void onFragmentInteraction(String letra) {
        nome = nome + letra;
        TextView t = findViewById(R.id.nome);
        t.setText(nome);
    }
    public void getClick(View view) {
        Button botao = findViewById(view.getId());
        String letra = botao.getText().toString();
        onFragmentInteraction(letra);
    }
}
