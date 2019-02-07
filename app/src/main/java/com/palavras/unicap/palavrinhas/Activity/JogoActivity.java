package com.palavras.unicap.palavrinhas.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.palavras.unicap.palavrinhas.Entity.Palavra;
import com.palavras.unicap.palavrinhas.Entity.Pontuacao;
import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.Viewmodel.JogoViewModel;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class JogoActivity extends AppCompatActivity{

    private JogoViewModel viewModel;

    private Button botaoConfirmar, botaoLimpar;
    private TextView palavraEmTela, textUsuario;
    private TextToSpeech textToSpeech;
    private ImageView botaoPlay, botaoVoltar;
    private MediaPlayer player;
//    private AppDatabase database;

    private String palavraUsuario = "";
    private Palavra palavraAtual;
    private List<Palavra> palavras;
    private Pontuacao pontuacaoAtual;
    private List<String> vidas = new ArrayList(Arrays.asList("vida1","vida2", "vida3"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        viewModel = ViewModelProviders.of(this).get(JogoViewModel.class);
        palavras = viewModel.getPalavras().getValue();
        this.viewModel.getPalavras().observe(this, allPalavras -> {
            if(JogoActivity.this.palavras == null){
                this.palavras = allPalavras;
            }
        });


        this.pontuacaoAtual = new Pontuacao();
        this.pontuacaoAtual.setPontos(0);

        palavraEmTela = findViewById(R.id.palavra);


        //configurar os botoes para pegar o onClick do confirmar e limpar
        setBotoes();

        //escolher a primeira palavra
        selectPalavra();

        //carregar o som de acerto da palavra
        player = MediaPlayer.create(this, R.raw.success);
    }


    // <Operacoes de jogabilidade>


    private void limparPalavra(){
        palavraUsuario = "";
        palavraEmTela.setText(palavraUsuario);
    }

    private void selectPalavra(){
        if(palavras.size() == 0){ // fim de jogo
            encerrarPartida("Parabéns!");
        }
        Random random = new Random();
        int posicao = random.nextInt(palavras.size());
        Palavra palavra = palavras.get(posicao);
        palavras.remove(posicao);
        palavraAtual = palavra;
        setImagem(palavra);
    }

    private void encerrarPartida(String mensagem) {
        Intent intent = new Intent(JogoActivity.this, RegistrarActivity.class);
        intent.putExtra("Pontuacao", pontuacaoAtual);
        intent.putExtra("Mensagem", mensagem);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    // </Operacoes de jogabilidade>




    // <Operacoes de configuracao de tela>

    public void setBotoes(){
        botaoConfirmar = findViewById(R.id.botaoConfirmar);
        botaoConfirmar.setOnClickListener(v -> {
            if(!this.palavraAtual.getTexto().toUpperCase().equals(this.palavraUsuario.toUpperCase())){
                Toast toast = Toast.makeText(JogoActivity.this, "Palavra errada!", Toast.LENGTH_SHORT);
                toast.show();
                reduzirVida(vidas.get(vidas.size()-1));
                vidas.remove(vidas.size()-1);
                if(vidas.size() == 0 ){
                    encerrarPartida("Muito bem! Continue Tentando!");
                }
            }else{
                this.pontuacaoAtual.setPontos(pontuacaoAtual.getPontos()+1);
                //tocar som de acerto
                player.start();
                selectPalavra();
            }
            limparPalavra();
        });
        botaoLimpar = findViewById(R.id.limpar);
        botaoLimpar.setOnClickListener(v -> limparPalavra());

        textToSpeech = new TextToSpeech(getApplicationContext(), status -> {
            if(status != TextToSpeech.ERROR){
                textToSpeech.setLanguage(new Locale("pt", "POR"));
                textToSpeech.setSpeechRate((float) 0.5);
            }
        });

        botaoPlay = findViewById(R.id.botaoPlay);
        botaoPlay.setOnClickListener(v ->
                textToSpeech.speak(palavraAtual.getTexto(),TextToSpeech.QUEUE_FLUSH,null, null));

        botaoVoltar = findViewById(R.id.botaoVoltar);
        botaoVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(JogoActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
    }

    public void getClick(View view) {
        Button botao = findViewById(view.getId());
        String letra = botao.getText().toString();
        palavraUsuario = palavraUsuario + letra ;
        textUsuario = findViewById(R.id.palavra);
        textUsuario.setText(palavraUsuario);
    }

    private void setImagem(Palavra palavra){
        //faz a leitura da imagem da palavra carregada do banco
        ByteArrayInputStream inputStream = new ByteArrayInputStream(palavra.getImagem());
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ImageView image = findViewById(R.id.imagemPalavra);
        //carrega a imagem na tela
        image.setImageBitmap(bitmap);
    }

    private void reduzirVida(String imagemVida){
        int id = getResources().getIdentifier(imagemVida, "id", getPackageName());
        ImageView imageView = findViewById(id);
        imageView.setVisibility(View.GONE);
    }
    // </Operacoes de configuracao de tela>
}
