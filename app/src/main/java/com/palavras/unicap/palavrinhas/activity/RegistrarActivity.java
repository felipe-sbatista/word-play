package com.palavras.unicap.palavrinhas.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.palavras.unicap.palavrinhas.entity.Usuario;
import com.palavras.unicap.palavrinhas.fragment.TecladoAlfabeticoFragment;
import com.palavras.unicap.palavrinhas.fragment.TecladoAlfabeticoFragment.OnFragmentInteractionListener;
import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.util.Constantes;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrarActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private int pontuacao = 0;
    private String mensagem="", nome="";
    private FirebaseDatabase database;

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

        this.pontuacao = getIntent().getIntExtra("Pontuacao", 0);
        this.mensagem = getIntent().getStringExtra("Mensagem");
        TextView msgFinal = findViewById(R.id.mensagemFinal);
        msgFinal.setText(mensagem + "\n" + pontuacao+ " - PONTOS");

        database = FirebaseDatabase.getInstance();

    }

    @OnClick(R.id.botao_salvar)
    public void salvarUsuario(){

        Usuario usuario = this.createUsuario();


        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference(Constantes.USUARIOS_REFERENCE);
                databaseReference.push().setValue(usuario);
                return null;
            }
        }.execute();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private Usuario createUsuario(){
        Usuario usuario = new Usuario();
        int pontuacao = getIntent().getIntExtra("Pontuacao",0);
        int tempo  = getIntent().getIntExtra("Segundos", 0);
        TextView textView = findViewById(R.id.nome);
        usuario.setNome(String.valueOf(textView.getText()));
        usuario.setPontos(pontuacao);
        usuario.setSegundos(tempo);
        return usuario;
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
