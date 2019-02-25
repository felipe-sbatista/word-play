package com.palavras.unicap.palavrinhas.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.palavras.unicap.palavrinhas.Entity.Palavra;
import com.palavras.unicap.palavrinhas.Entity.Pontuacao;
import com.palavras.unicap.palavrinhas.Fragment.JogoFragment;
import com.palavras.unicap.palavrinhas.Persistence.AppDatabase;
import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.Fragment.TecladoAlfabeticoFragment;
import com.palavras.unicap.palavrinhas.Fragment.TecladoVogalFragment;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class JogoActivity extends AppCompatActivity
        implements TecladoAlfabeticoFragment.OnFragmentInteractionListener,
        TecladoVogalFragment.OnFragmentInteractionListener,
        JogoFragment.OnFragmentJogoInteraction {

    @BindView(R.id.botao_voltar)
    ImageView botaoVoltar;

    @BindView(R.id.switch_teclado)
    Switch botaoSwitch;

    private TextView textUsuario;
    private AppDatabase database;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment tecladoFragment;
    private Fragment jogoFragment;

    private String palavraUsuario = "";
    private List<Palavra> palavras = new ArrayList<>();
    private Pontuacao pontuacaoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        ButterKnife.bind(this);

        tecladoFragment = new TecladoAlfabeticoFragment();
        jogoFragment = new JogoFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.teclado_jogo, tecladoFragment);
        transaction.add(R.id.tela_jogo, jogoFragment);
        transaction.commit();

        Runnable r = () -> {
            this.pontuacaoAtual = new Pontuacao();
            this.pontuacaoAtual.setPontos(0);
            database = AppDatabase.getInstance(this);
        };
        r.run();

        try {
            palavras = new AsyncTask<Void, Void, List<Palavra>>(){
                @SuppressLint("WrongThread")
                @Override
                protected List<Palavra> doInBackground(Void... voids) {
                    return database.palavraDAO().loadAllPalavras();

                }

            }.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Runnable r2 = ()->{
//            //escolher a primeira palavra
//            selectPalavra();
//        };
//        r2.run();


    }


    public void encerrarPartida(String mensagem) {
        Intent intent = new Intent(JogoActivity.this, RegistrarActivity.class);
        intent.putExtra("Pontuacao", pontuacaoAtual);
        intent.putExtra("Mensagem", mensagem);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }


    @OnClick(R.id.switch_teclado)
    public void switchTeclado(){
        Runnable r = ()-> {
            if (tecladoFragment instanceof TecladoAlfabeticoFragment) {
                tecladoFragment = new TecladoVogalFragment();
            } else {
                tecladoFragment = new TecladoAlfabeticoFragment();
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.teclado_jogo, tecladoFragment);
            transaction.commit();
        };
        r.run();
    }

    @OnClick(R.id.botao_voltar)
    public void botaoVoltar(){
        Intent intent = new Intent(JogoActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public void getClick(View view) {
        Button botao = findViewById(view.getId());
        String letra = botao.getText().toString();
        palavraUsuario = palavraUsuario + letra ;
        textUsuario = findViewById(R.id.palavra);
        textUsuario.setText(palavraUsuario);
    }


    @Override
    public void onFragmentInteraction(String letra) {

    }

    @Override
    public void flow() {

    }
}
