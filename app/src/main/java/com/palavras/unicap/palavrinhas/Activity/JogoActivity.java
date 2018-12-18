package com.palavras.unicap.palavrinhas.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.palavras.unicap.palavrinhas.Entity.Palavra;
import com.palavras.unicap.palavrinhas.Persistence.PalavraDAO;
import com.palavras.unicap.palavrinhas.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JogoActivity extends AppCompatActivity{

    private Button botaoConfirmar , botaoLimpar, botaoTocar;
    private TextView palavraEmTela, textUsuario;
    private TextToSpeech textToSpeech;
    private ImageView botaoPlay, botaoVoltar;
    private MediaPlayer player;

    private String palavraUsuario = "";
    private String palavraAtual = "";
    private List<Palavra> palavras = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        palavraEmTela = findViewById(R.id.palavra);

        //listar palavras atraves do DAO
        buscarPalavras();
        //configurar os botoes para pegar o onClick do confirmar e limpar
        setBotoes();
        //escolher a primeira palavra
        selectPalavra();

        player = MediaPlayer.create(this, R.raw.success);
    }


    public void setBotoes(){
        botaoConfirmar = findViewById(R.id.botaoConfirmar);
        botaoConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!palavraAtual.equals(palavraUsuario)){
                    Toast toast = Toast.makeText(JogoActivity.this, "Palavra errada!", Toast.LENGTH_SHORT);
                    toast.show();
                    limparPalavra();
                }else{
                    player.start();
                    selectPalavra();
                }
            }
        });
        botaoLimpar = findViewById(R.id.limpar);
        botaoLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparPalavra();
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(new Locale("pt", "POR"));
                    textToSpeech.setSpeechRate((float) 0.7);
                }
            }
        });

        botaoPlay = findViewById(R.id.botaoPlay);
        botaoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(palavraAtual,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        botaoVoltar = findViewById(R.id.botaoVoltar);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JogoActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });
    }

    private void limparPalavra(){
        palavraUsuario = "";
        palavraEmTela.setText(palavraUsuario);
    }

    public void getClick(View view) {
        Button botao = findViewById(view.getId());
        String letra = botao.getText().toString();
        palavraUsuario = palavraUsuario + letra ;
        textUsuario = findViewById(R.id.palavra);
        textUsuario.setText(palavraUsuario);
    }

    private void selectPalavra(){
        Random random = new Random();
        int posicao = random.nextInt(palavras.size());
        Palavra palavra = palavras.get(posicao);
        palavras.remove(posicao);
        palavraAtual = palavra.getTexto();
        setImagem(palavra);
    }

    private void setImagem(Palavra palavra){
        ByteArrayInputStream inputStream = new ByteArrayInputStream(palavra.getImagem());
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ImageView image = findViewById(R.id.imagemPalavra);
        image.setImageBitmap(bitmap);
    }

    public void buscarPalavras(){
        PalavraDAO dao = PalavraDAO.getInstance(getApplicationContext());
        dao.openConnection();
        palavras = dao.listar();
        dao.closeConnection();
    }

}
