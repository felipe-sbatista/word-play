package com.palavras.unicap.palavrinhas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.util.Constantes;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void selectTypeOfWords(View view) {
        Button button = findViewById(view.getId());
        String type = String.valueOf(button.getText());
        Intent intent = new Intent(MenuActivity.this, JogoActivity.class);
        intent.putExtra("type", this.chooseType(type));
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private String chooseType(String type) {
        switch (type) {
            case Constantes.PALAVRAS_A_E:
                return Constantes.PALAVRAS_A_E_PATH;
            case Constantes.PALAVRAS_B_D:
                return Constantes.PALAVRAS_B_D_PATH;
            case Constantes.PALAVRAS_P_B:
                return Constantes.PALAVRAS_P_B_PATH;
            case Constantes.PALAVRAS_R_FINAL:
                return Constantes.PALAVRAS_R_FINAL_PATH;
            case Constantes.PALAVRAS_RR_R:
                return Constantes.PALAVRAS_RR_R_PATH;
            default:
                return "";
        }
    }
}
