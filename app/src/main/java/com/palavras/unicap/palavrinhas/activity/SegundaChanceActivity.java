package com.palavras.unicap.palavrinhas.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.entity.Palavra;
import com.palavras.unicap.palavrinhas.fragment.TecladoAlfabeticoFragment;
import com.palavras.unicap.palavrinhas.util.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SegundaChanceActivity extends AppCompatActivity
        implements TecladoAlfabeticoFragment.OnFragmentInteractionListener {

    @BindView(R.id.palavra_usuario_segunda_chance)
    TextView textViewUsuario;

    @BindView(R.id.palavra_correta)
    TextView textViewResposta;

    @BindView(R.id.botao_confirmar_segunda_chance)
    Button botaoConfirmar;


    @BindView(R.id.botao_limpar_segunda_chance)
    Button botaoLimpar;

    @BindView(R.id.botao_desistir)
    Button botaoDesistir;

    private String resposta = "";
    private String palavraUsuario = "";
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment tecladoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_chance);
        ButterKnife.bind(this);
        //set Teclado Fragment
        tecladoFragment = new TecladoAlfabeticoFragment();

        // start fragments
        androidx.fragment.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.teclado_jogo, tecladoFragment);
        transaction.commitNow();
        resposta = getIntent().getStringExtra(Constantes.RESPOSTA_SEGUNDA_CHANCE).toUpperCase();
        textViewResposta.setText(resposta);
    }


    @OnClick(R.id.botao_confirmar_segunda_chance)
    public void confirmar(){
        Intent intent = new Intent();
        if (palavraUsuario.equals(this.resposta)) {
            intent.putExtra(Constantes.RETRY, true);
        } else {
            intent.putExtra(Constantes.RETRY, false);
        }
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @OnClick(R.id.botao_limpar_segunda_chance)
    public void limparPalavra(){
        this.palavraUsuario = "";
        textViewUsuario.setText("");

    }

    @OnClick(R.id.botao_desistir)
    public void desistir(){
        Intent intent = new Intent();
        intent.putExtra(Constantes.RETRY, false);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void getClick(View view) {
        Button botao = findViewById(view.getId());
        String letra = botao.getText().toString();
        palavraUsuario = palavraUsuario + letra;
        this.textViewUsuario.setText(palavraUsuario);
    }
}
