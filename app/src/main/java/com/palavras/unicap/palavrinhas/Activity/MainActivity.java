package com.palavras.unicap.palavrinhas.Activity;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.palavras.unicap.palavrinhas.Entity.Palavra;
import com.palavras.unicap.palavrinhas.Persistence.AppDatabase;
import com.palavras.unicap.palavrinhas.Persistence.DatabaseCopier;
import com.palavras.unicap.palavrinhas.R;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.botao_jogar)
    Button botaoJogar;
    @BindView(R.id.botao_recordes)
    Button botaoRecorde;
    private Boolean firstTime = null;
    private static AppDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isFirstTime()){
            //Copiar o asset do banco de dados para o Room Library
            DatabaseCopier copier = DatabaseCopier.getInstance(getApplicationContext());
            database = copier.getRoomDatabase();
        }else{
            database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "palavras.db").build();
        }

    }


    /**

     * Checks if the user is opening the app for the first time.

     * Note that this method should be placed inside an activity and it can be called multiple times.

     * @return boolean

     */

    private boolean isFirstTime() {
        if (firstTime == null) {
            SharedPreferences mPreferences = this.getSharedPreferences("first_time", Context.MODE_PRIVATE);
            firstTime = mPreferences.getBoolean("firstTime", true);
            if (firstTime) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();
            }
        }
        return firstTime;
    }

    @OnItemClick(R.id.botao_jogar)
    public void jogar(){
        Intent intent = new Intent(MainActivity.this, JogoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @OnItemClick(R.id.botao_recordes)
    public void recordes(){
        Intent intent = new Intent(MainActivity.this, RecordesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
