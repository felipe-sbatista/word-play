package com.palavras.unicap.palavrinhas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.entity.Palavra;
import com.palavras.unicap.palavrinhas.util.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SegundaChanceActivity extends AppCompatActivity {


    @BindView(R.id.palavra1)
    Button palavra1;

    @BindView(R.id.palavra2)
    Button palavra2;

    @BindView(R.id.palavra3)
    Button palavra3;

    @BindView(R.id.palavra4)
    Button palavra4;



    private String resposta = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_chance);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        List<Palavra> palavras = (List<Palavra>) intent.getSerializableExtra(Constantes.PALAVRAS_SEGUNDA_CHANCE);
        this.resposta = intent.getStringExtra(Constantes.RESPOSTA_SEGUNDA_CHANCE);
        List<Button> buttons = this.createButtons();
        initButton(palavras, buttons, resposta);
    }

    private List<Button> createButtons(){
        List<Button> buttons = new ArrayList<>();
        buttons.add(palavra1);
        buttons.add(palavra2);
        buttons.add(palavra3);
        buttons.add(palavra4);
        return buttons;

    }

    private void initButton(List<Palavra> palavras, List<Button> buttons, String resposta){

        //Define o botão que irá ficar a resposta
        Random random = new Random();
        int posicao = random.nextInt(buttons.size());
        Button button = buttons.get(posicao);
        button.setText(resposta);
        buttons.remove(posicao);


        //Define os demais botoes
        for (Button btn: buttons) {
            int limit = 0;

            //Não permite repetir uma palavra selecionada com a resposta
            do{
                posicao = random.nextInt(palavras.size());
                limit++;
            } while(palavras.get(posicao).equals(resposta) && limit < 40);

            String palavra = palavras.get(posicao).getTexto();
            btn.setText(palavra);
            palavras.remove(posicao);
        }
    }


    public void getPalavraEscolhida(View view){
        Button button = findViewById(view.getId());
        String palavra = button.getText().toString();
        Intent intent = new Intent();
        if(palavra.equals(this.resposta)){
            intent.putExtra(Constantes.RETRY, true);
        } else {
            intent.putExtra(Constantes.RETRY, false);
        }
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
