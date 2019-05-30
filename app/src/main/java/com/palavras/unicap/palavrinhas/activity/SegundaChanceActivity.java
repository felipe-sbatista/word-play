package com.palavras.unicap.palavrinhas.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.entity.Palavra;
import com.palavras.unicap.palavrinhas.util.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SegundaChanceActivity extends AppCompatActivity {


    @BindView(R.id.botao_confirmar_segunda_chance)
    Button botaoConfirmar;

    @BindView(R.id.palavra_usuario_segunda_chance)
    TextView textViewUsuario;

    @BindView(R.id.botao_limpar_segunda_chance)
    Button botaoLimpar;

    private String resposta = "";
    private String palavraUsuario = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_chance);
        ButterKnife.bind(this);
        resposta = new Intent().getStringExtra(Constantes.RESPOSTA_SEGUNDA_CHANCE);
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
    }
}
