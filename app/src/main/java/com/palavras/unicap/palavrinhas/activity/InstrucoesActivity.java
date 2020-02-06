package com.palavras.unicap.palavrinhas.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.palavras.unicap.palavrinhas.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InstrucoesActivity extends AppCompatActivity {

    @BindView(R.id.botao_voltar_image)
    ImageView botaoVoltar;

    @BindView(R.id.botao_next_image)
    ImageView botaoProximo;

    @BindView(R.id.image_tutorial)
    ImageView image;


    private List<Drawable> images = new ArrayList<>();
    int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucoes);
        ButterKnife.bind(this);
        setImages();
//        this.botaoVoltar.setVisibility(View.GONE);

        updateImage();
    }


    @OnClick(R.id.botao_voltar_image)
    public void backImage() {
        if (position == 0) {
            finish();
            return;
        } else if (position + 1 == images.size()) {
            botaoProximo.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.ic_media_rew));
        }
        position--;
        updateImage();

    }

    @OnClick(R.id.botao_next_image)
    public void nextImage() {
        position++;
        if (position + 1 == images.size()) {
            botaoProximo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.play));
        } else if (position + 1 > images.size()) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        updateImage();
    }

    private void updateImage() {
        this.image.setImageDrawable(images.get(position));
    }

    private void setImages() {
        this.images.add(ContextCompat.getDrawable(this, R.drawable.image_instruction));
        this.images.add(ContextCompat.getDrawable(this, R.drawable.sound_instruction));
        this.images.add(ContextCompat.getDrawable(this, R.drawable.teclado_instruction));
    }
}
