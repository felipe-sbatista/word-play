package com.palavras.unicap.palavrinhas.activity;

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

import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.entity.Palavra;
import com.palavras.unicap.palavrinhas.fragment.JogoFragment;
import com.palavras.unicap.palavrinhas.fragment.LifeFragment;
import com.palavras.unicap.palavrinhas.fragment.TecladoAlfabeticoFragment;
import com.palavras.unicap.palavrinhas.fragment.TecladoVogalFragment;
import com.palavras.unicap.palavrinhas.util.Constantes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JogoActivity extends AppCompatActivity implements
        TecladoAlfabeticoFragment.OnFragmentInteractionListener,
        TecladoVogalFragment.OnFragmentInteractionListener,
        JogoFragment.OnFragmentInteractionListener,
        LifeFragment.OnFragmentInteractionListener {

    @BindView(R.id.botao_voltar)
    ImageView botaoVoltar;

    @BindView(R.id.switch_teclado)
    Switch botaoSwitch;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment tecladoFragment;
    private JogoFragment jogoFragment;
    private String palavraUsuario = "";
    private List<Palavra> palavras = new ArrayList<>();
    private int pontuacaoAtual = 0;
    private Palavra palavraAtual = null;
    private long startMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        ButterKnife.bind(this);
        this.startFragments();
        this.startMillis = System.currentTimeMillis();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data.getBooleanExtra(Constantes.RETRY, false)) {
            FragmentManager manager = getSupportFragmentManager();
            LifeFragment lifeFragment = (LifeFragment) manager.findFragmentById(R.id.life_g);
            lifeFragment.restoreLife();
        } else {
            encerrarPartida("Continue assim!");
        }
    }

    public void startFragments() {
        // set parameters for jogoFragment
        jogoFragment = new JogoFragment();
        Bundle bundle = new Bundle();
        Intent intent = getIntent();
        bundle.putString("type", intent.getStringExtra("type"));
        jogoFragment.setArguments(bundle);

        //set Teclado Fragment
        tecladoFragment = new TecladoAlfabeticoFragment();

        // start fragments
        androidx.fragment.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.teclado_jogo, tecladoFragment);
        transaction.add(R.id.tela_jogo, jogoFragment);
        transaction.commitNow();
    }

    public void startSegundaChance() {
        Intent intent = new Intent(JogoActivity.this, SegundaChanceActivity.class);
        intent.putExtra(Constantes.PALAVRAS_SEGUNDA_CHANCE, (Serializable) this.palavras);
        intent.putExtra(Constantes.RESPOSTA_SEGUNDA_CHANCE, palavraAtual.getTexto());
        startActivityForResult(intent, 1);
    }

    public void encerrarPartida(String mensagem) {
        Intent intent = new Intent(JogoActivity.this, RegistrarActivity.class);
        intent.putExtra("Pontuacao", pontuacaoAtual);
        intent.putExtra("Mensagem", mensagem);
        Long time = (System.currentTimeMillis() - this.startMillis) / 1000;
        intent.putExtra("Segundos", time);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @OnClick(R.id.switch_teclado)
    public void switchTeclado() {
        new Thread(() -> {
            if (tecladoFragment instanceof TecladoAlfabeticoFragment) {
                tecladoFragment = new TecladoVogalFragment();
            } else {
                tecladoFragment = new TecladoAlfabeticoFragment();
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.teclado_jogo, tecladoFragment);
            transaction.commit();
        }).start();

    }

    @OnClick(R.id.botao_voltar)
    public void botaoVoltar() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public void getClick(View view) {
        Button botao = findViewById(view.getId());
        String letra = botao.getText().toString();
        palavraUsuario = palavraUsuario + letra;
        this.jogoFragment.setLetra(letra);
    }

    public void limparPalavra() {
        this.palavraUsuario = "";
    }

    public String getPalavraUsuario() {
        return this.palavraUsuario;
    }

    public void setPalavras(List<Palavra> palavras) {
        this.palavras.addAll(palavras);
    }

    public void incrementarPontos() {
        this.pontuacaoAtual++;
    }

    public void setPalavraAtual(Palavra palavraAtual) {
        this.palavraAtual = palavraAtual;
    }




}
