package com.palavras.unicap.palavrinhas.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.palavras.unicap.palavrinhas.entity.Usuario;
import com.palavras.unicap.palavrinhas.fragment.TecladoAlfabeticoFragment;
import com.palavras.unicap.palavrinhas.fragment.TecladoAlfabeticoFragment.OnFragmentInteractionListener;
import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.util.Constantes;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrarActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private int pontuacao = 0, segundos = 0;
    private String nome = "";
    private FirebaseDatabase database;

    @BindView(R.id.botao_salvar)
    Button botaoSalvar;

    @BindView(R.id.nome)
    TextView textViewNome;

    @BindView(R.id.pontuacao)
    TextView textViewPontuacao;

    @BindView(R.id.botao_limpar_recordes)
    Button botaoLimpar;

    @BindView(R.id.teclado_image)
    ImageView tecladoImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        ButterKnife.bind(this);

        TecladoAlfabeticoFragment fragment = new TecladoAlfabeticoFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.teclado, fragment);
        ft.commit();

        this.pontuacao = getIntent().getIntExtra("Pontuacao", 0);
        String pontos = this.pontuacao + " Pontos";
        textViewPontuacao.setText(pontos);
        if (getIntent().getBooleanExtra("isWinner", false)) {
            tecladoImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.image_final_win));
        } else {
            tecladoImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.image_final_lose));
        }
        database = FirebaseDatabase.getInstance();

    }

    @OnClick(R.id.botao_limpar_recordes)
    public void limparNome() {
        this.nome = "";
        this.textViewNome.setText(this.nome);
    }

    @OnClick(R.id.botao_salvar)
    public void salvarUsuario() {
        if (this.textViewNome == null || this.textViewNome.getText() == null || this.textViewNome.getText().toString().isEmpty()) {
            Toast.makeText(this, "DIGITE SEU NOME PARA PODER SALVAR!", Toast.LENGTH_LONG).show();
            return;
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Usuario usuario = createUsuario();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference(Constantes.USUARIOS_REFERENCE);
                databaseReference.push().setValue(usuario);
                return null;
            }
        }.execute();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private Usuario createUsuario() {
        Usuario usuario = new Usuario();
        long time = getIntent().getLongExtra("Segundos", 0);
        this.segundos = (int) time;

        TextView textView = findViewById(R.id.nome);
        usuario.setNome(String.valueOf(textView.getText()));
        usuario.setPontos(pontuacao);
        usuario.setSegundos(segundos);
        return usuario;
    }

    public void getClick(View view) {
        Button botao = findViewById(view.getId());
        String letra = botao.getText().toString();
        nome = nome + letra;
        this.textViewNome.setText(nome);
    }
}
