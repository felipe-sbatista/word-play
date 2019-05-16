package com.palavras.unicap.palavrinhas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.entity.Usuario;
import com.palavras.unicap.palavrinhas.firebase.UsuarioViewModel;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.botao_jogar)
    Button botaoJogar;

    @BindView(R.id.botao_recordes)
    Button botaoRecorde;

    @BindView(R.id.botao_instrucoes)
    Button botaoInstrucoes;

    List<Usuario> users = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(getApplicationContext());

//        UsuarioViewModel viewModel = ViewModelProviders.of(this).get(UsuarioViewModel.class);
////        LiveData<DataSnapshot> liveData = viewModel.getDataSnapShotLiveData();
//        LiveData<DataSnapshot> liveData = viewModel.getDataSnapShotLiveData();
//        liveData.observe(this, dataSnapshot -> {
//            users.clear();
//            if (dataSnapshot != null) {
//                for(DataSnapshot data: dataSnapshot.getChildren()){
//                    users.add(data.getValue(Usuario.class));
//                }
//
//            }
//        });

    }

    @OnClick(R.id.botao_jogar)
    public void jogar() {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @OnClick(R.id.botao_recordes)
    public void recordes() {
        Intent intent = new Intent(MainActivity.this, RecordesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @OnClick(R.id.botao_instrucoes)
    public void instrucoes() {
        Intent intent = new Intent(MainActivity.this, InstrucoesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
