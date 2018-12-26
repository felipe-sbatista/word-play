package com.palavras.unicap.palavrinhas.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.palavras.unicap.palavrinhas.Persistence.AppDatabase;
import com.palavras.unicap.palavrinhas.Persistence.DatabaseCopier;
import com.palavras.unicap.palavrinhas.R;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private Button botaoJogar, botaoRecorde;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Copiar o asset do banco de dados para o Room Library
        final DatabaseCopier copier = DatabaseCopier.getInstance(getApplicationContext());
        final AppDatabase database = copier.getRoomDatabase();
        //setar os botoes de acesso ao aplicativo
        botaoJogar = findViewById(R.id.jogar);
        botaoRecorde = findViewById(R.id.recordes);

        botaoJogar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, JogoActivity.class);
            intent.putExtra("DbAccess", database);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });
        botaoRecorde.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecordesActivity.class);
            intent.putExtra("DbAccess", database);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        });


    }
}
