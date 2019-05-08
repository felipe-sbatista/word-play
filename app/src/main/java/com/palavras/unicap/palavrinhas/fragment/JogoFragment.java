package com.palavras.unicap.palavrinhas.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.activity.JogoActivity;
import com.palavras.unicap.palavrinhas.entity.Palavra;
import com.palavras.unicap.palavrinhas.exception.EndgameException;
import com.palavras.unicap.palavrinhas.util.Constantes;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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

    @BindView(R.id.palavra_escrita)
    TextView palavraEmTela;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private Palavra palavraAtual = new Palavra();
    private TextToSpeech textToSpeech;
    private MediaPlayer player;
    List<Palavra> palavras = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private JogoActivity jogoActivity;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public JogoFragment() {
    }

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

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jogo, container, false);

        player = MediaPlayer.create(getActivity(), R.raw.success);


        new Thread(() -> fetchData()).start();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                textToSpeech = new TextToSpeech(getActivity(), status -> {
                    if (status != TextToSpeech.ERROR) {
                        textToSpeech.setLanguage(new Locale("pt", "POR"));
                        textToSpeech.setSpeechRate((float) 0.5);
                    }
                });
                return null;
            }
        }.execute();


        ButterKnife.bind(this, view);
        this.jogoActivity = ((JogoActivity) getActivity());

        return view;
    }

    private void fetchData() {
        //start uma instancia do firebase
        this.database = FirebaseDatabase.getInstance();

        //Seleciona o grupo dos dados pela referencia
        this.databaseReference = database.getReference().child(Constantes.PALAVRAS_REFERENCE);
        Query query = databaseReference.orderByChild(Constantes.PALAVRAS_REFERENCE);
        this.progressBar.setVisibility(View.VISIBLE);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iteração dos dados ja buscados
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Palavra palavra = singleSnapshot.getValue(Palavra.class);
                    palavras.add(palavra);
                }
                ((JogoActivity) getActivity()).setPalavras(palavras);
                try {
                    Palavra p = selectPalavra();
                    setImagem(p);
                } catch (EndgameException e) {
                    e.printStackTrace();
                } finally {
                    progressBar.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String letra);
    }

    @OnClick(R.id.botao_confirmar)
    public void confirmarWord() {
        if (this.palavraAtual.getTexto() == null || this.palavraAtual.getTexto().isEmpty()) {
            return;
        }
        if (!this.palavraAtual.getTexto().toUpperCase().equals(jogoActivity.getPalavraUsuario().toUpperCase())) {
            FragmentManager manager = getFragmentManager();
            LifeFragment lifeFragment = (LifeFragment) manager.findFragmentById(R.id.life_g);
            lifeFragment.reduzir();
            if (lifeFragment.isFinished()) {
                this.jogoActivity.startSegundaChance();
            }
        } else {
            this.jogoActivity.incrementarPontos();
            //tocar som de acerto
            player.start();
            Palavra palavraEscolhida;
            try {
                palavraEscolhida = selectPalavra();
                setImagem(palavraEscolhida);
            } catch (EndgameException e) {
                Log.w("Fim de jogo", e.getMessage());
                ((JogoActivity) getActivity()).encerrarPartida("Continue tentando!");
            }
        }
        limparPalavra();
    }

    @OnClick(R.id.botao_play)
    public void playWord() {
        textToSpeech.speak(palavraAtual.getTexto(), TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @OnClick(R.id.botao_limpar)
    public void limparPalavra() {
        palavraEmTela.setText("");
        ((JogoActivity) getActivity()).limparPalavra();
    }


    private Palavra selectPalavra() throws EndgameException {
        if (palavras.size() == 0) {
            throw new EndgameException("Sem mais palavras");
        } else {
            Random random = new Random();
            int posicao = random.nextInt(palavras.size());
            Palavra palavra = palavras.get(posicao);
            palavras.remove(posicao);
            palavraAtual = palavra;
            setImagem(palavra);
            this.jogoActivity.setPalavraAtual(palavraAtual);
            return palavra;
        }
    }

    private void setImagem(Palavra palavra) {
        StorageReference reference = FirebaseStorage.getInstance().getReference();
        StorageReference realImage = reference.child(palavra.getNomeArquivo());
        realImage.getBytes(1024 * 1024).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ImageView imageView = getView().findViewById(R.id.imagemPalavra);
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        });
    }


    @Override
    public void onDestroyView() {
        this.textToSpeech.shutdown();
        super.onDestroyView();
    }
}
