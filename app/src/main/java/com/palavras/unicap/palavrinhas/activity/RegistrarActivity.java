package com.palavras.unicap.palavrinhas.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.FirebaseDatabase;
import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.fragment.TecladoAlfabeticoFragment;
import com.palavras.unicap.palavrinhas.fragment.TecladoAlfabeticoFragment.OnFragmentInteractionListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrarActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private int pontuacao = 0;
    @BindView(R.id.botao_finalizar)
    Button botaoSalvar;

    @BindView(R.id.pontuacao)
    TextView textViewPontuacao;

    @BindView(R.id.teclado_image)
    ImageView tecladoImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        ButterKnife.bind(this);

        long time = getIntent().getLongExtra("Segundos", 0);
        // todo: set time and fix firebase

        this.pontuacao = getIntent().getIntExtra("Pontuacao", 0);
        String pontos = this.pontuacao + " Pontos";
        textViewPontuacao.setText(pontos);
        if (getIntent().getBooleanExtra("isWinner", false)) {
            tecladoImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.image_final_win));
        } else {
            tecladoImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.image_final_lose));
        }
    }


    @OnClick(R.id.botao_finalizar)
    public void salvarUsuario() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }


    public void getClick(View view) {

    }
}
