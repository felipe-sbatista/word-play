package com.palavras.unicap.palavrinhas.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.activity.JogoActivity;
import com.palavras.unicap.palavrinhas.entity.Palavra;
import com.palavras.unicap.palavrinhas.exception.EndgameException;
import com.palavras.unicap.palavrinhas.util.Constantes;

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
    private List<Palavra> palavras = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private JogoActivity jogoActivity;
    private String typeParam;

    public JogoFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.jogoActivity = ((JogoActivity) getActivity());
        if (getArguments() != null) {
            typeParam = getArguments().getString("type");
        }
    }

    @Override
    public void onDestroyView() {
        this.textToSpeech.shutdown();
        super.onDestroyView();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jogo, container, false);

        player = MediaPlayer.create(getActivity(), R.raw.success);

        new Thread(() -> JogoFragment.this.fetchData(typeParam)).start();

        textToSpeech = new TextToSpeech(getActivity(), status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(new Locale("pt", "POR"));
                textToSpeech.setSpeechRate((float) 0.5);
            }
        });

        ButterKnife.bind(this, view);

        return view;
    }


    private void fetchData(String url) {
        //start uma instancia do firebase
        this.database = FirebaseDatabase.getInstance();

        //Seleciona o grupo dos dados pela referencia
        this.databaseReference = database.getReference(Constantes.PALAVRAS_REFERENCE).child(url);
        Query query = databaseReference.orderByChild(url);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iteração dos dados ja buscados
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Palavra palavra = singleSnapshot.getValue(Palavra.class);
                    palavras.add(palavra);
                }
                ((JogoActivity) getActivity()).setPalavras(palavras);
                setPalavra();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void setPalavra() {
        this.progressBar.setVisibility(View.VISIBLE);
        try {
            Palavra palavra = selectPalavra();
            setImagem(palavra);
        } catch (EndgameException e) {
            ((JogoActivity) getActivity()).encerrarPartida(true);
        } finally {
            this.progressBar.setVisibility(View.GONE);
        }
    }

    public interface OnFragmentInteractionListener {
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
            setPalavra();
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
            this.jogoActivity.setPalavraAtual(palavraAtual);
            return palavra;
        }
    }

    private void setImagem(Palavra palavra) {
        Log.i("", "Palavra: " + palavra.getTexto());
        StorageReference reference = FirebaseStorage.getInstance().getReference();
        StorageReference realImage = reference.child(palavra.getNomeArquivo());
        realImage.getBytes(1024 * 1024).addOnSuccessListener(bytes -> {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            ImageView imageView = JogoFragment.this.getView().findViewById(R.id.imagemPalavra);
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
            bitmap = null;
            System.gc();
        }).addOnFailureListener(e -> {
            Log.e("", "Erro na busca da imagem para palavra:" + palavra.getTexto());
            setPalavra();
        });

    }

    public void setLetra(String letra) {
        String palavra = String.valueOf(this.palavraEmTela.getText());
        palavra = palavra + letra;
        this.palavraEmTela.setText(palavra);
    }
}
