package com.palavras.unicap.palavrinhas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.palavras.unicap.palavrinhas.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void selectTypeOfWords(View view){
        Button button = findViewById(view.getId());
        String type = String.valueOf(button.getText());
        Intent intent = new Intent(MenuActivity.this, JogoActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
