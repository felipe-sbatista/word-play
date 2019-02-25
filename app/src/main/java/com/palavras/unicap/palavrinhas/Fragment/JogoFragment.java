package com.palavras.unicap.palavrinhas.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.palavras.unicap.palavrinhas.Activity.JogoActivity;
import com.palavras.unicap.palavrinhas.Entity.Palavra;
import com.palavras.unicap.palavrinhas.R;

import java.io.ByteArrayInputStream;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class JogoFragment extends Fragment {
    @BindView(R.id.botao_confirmar)
    Button botaoConfirmar;

    @BindView(R.id.botao_limpar)
    Button botaoLimpar;

    @BindView(R.id.botao_play)
    ImageView botaoPlay;

    @BindView(R.id.palavra)
    TextView palavraEmTela;

    private int pontos = 0;
    private Palavra palavraAtual;
    private TextToSpeech textToSpeech;
    private MediaPlayer player;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String palavraUsuario;


    public JogoFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static JogoFragment newInstance(String param1, String param2) {
        JogoFragment fragment = new JogoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jogo, container, false);

        //carregar o som de acerto da palavra
        player = MediaPlayer.create(getActivity(), R.raw.success);

        textToSpeech = new TextToSpeech(getActivity(), status -> {
            if(status != TextToSpeech.ERROR){
                textToSpeech.setLanguage(new Locale("pt", "POR"));
                textToSpeech.setSpeechRate((float) 0.5);
            }
        });
        ButterKnife.bind(this, view);
        return view;
    }



    public interface OnFragmentJogoInteraction {
        void flow();
    }

    @OnClick(R.id.botao_confirmar)
    public void confirmarWord() {
        if (!this.palavraAtual.getTexto().toUpperCase().equals(this.palavraUsuario.toUpperCase())) {
            FragmentManager manager = getFragmentManager();
            LifeFragment fragment = (LifeFragment) manager.findFragmentByTag("life_fragment");
            fragment.reduzir();
            if(fragment.isFinished()){
                ((JogoActivity)getActivity()).encerrarPartida("Muito bem, continue assim!");
            }
        }else{
            this.pontos++;
            //tocar som de acerto
            player.start();
            selectPalavra();
        }
        limparPalavra();

    }

    @OnClick(R.id.botao_play)
    public void playWord(){
        botaoPlay.setOnClickListener(v ->
                textToSpeech.speak(palavraAtual.getTexto(), TextToSpeech.QUEUE_FLUSH, null, null));
    }

    @OnClick(R.id.botao_limpar)
    public void limparPalavra () {
        palavraUsuario = "";
        palavraEmTela.setText(palavraUsuario);
    }
    private void selectPalavra(){
//        if(palavras.size() == 0){ // fim de jogo
//            encerrarPartida("Parabéns!");
//        }
//        Random random = new Random();
//        int posicao = random.nextInt(palavras.size());
//        Palavra palavra = palavras.get(posicao);
//        palavras.remove(posicao);
//        palavraAtual = palavra;
//        setImagem(palavra);
    }

    private void setImagem(Palavra palavra){
        //faz a leitura da imagem da palavra carregada do banco
        ByteArrayInputStream inputStream = new ByteArrayInputStream(palavra.getImagem());
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ImageView image = getView().findViewById(R.id.imagemPalavra);
        //carrega a imagem na tela
        image.setImageBitmap(bitmap);
    }



}
