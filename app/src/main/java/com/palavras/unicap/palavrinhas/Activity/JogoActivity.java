package com.palavras.unicap.palavrinhas.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.palavras.unicap.palavrinhas.Entity.Palavra;
import com.palavras.unicap.palavrinhas.Fragment.JogoFragment;
import com.palavras.unicap.palavrinhas.Fragment.LifeFragment;
import com.palavras.unicap.palavrinhas.Fragment.TecladoAlfabeticoFragment;
import com.palavras.unicap.palavrinhas.Fragment.TecladoVogalFragment;
import com.palavras.unicap.palavrinhas.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JogoActivity extends AppCompatActivity
        implements TecladoAlfabeticoFragment.OnFragmentInteractionListener,
        TecladoVogalFragment.OnFragmentInteractionListener,
        JogoFragment.OnFragmentJogoInteraction, LifeFragment.OnFragmentInteractionListener {

    @BindView(R.id.botao_voltar)
    ImageView botaoVoltar;

    @BindView(R.id.switch_teclado)
    Switch botaoSwitch;

    private TextView textUsuario;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment tecladoFragment;
    private Fragment jogoFragment;

    private String palavraUsuario = "";
    private List<Palavra> palavras = new ArrayList<>();
    private int pontuacaoAtual = 0;

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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
