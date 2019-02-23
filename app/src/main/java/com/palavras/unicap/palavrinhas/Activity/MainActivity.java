package com.palavras.unicap.palavrinhas.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

<<<<<<< Updated upstream
=======
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.palavras.unicap.palavrinhas.Entity.Usuario;
import com.palavras.unicap.palavrinhas.Persistence.AppDatabase;
import com.palavras.unicap.palavrinhas.Persistence.DatabaseCopier;
>>>>>>> Stashed changes
import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.Viewmodel.MainViewModel;

<<<<<<< Updated upstream
import androidx.appcompat.app.AppCompatActivity;
=======
import java.util.HashMap;
import java.util.Map;

>>>>>>> Stashed changes
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.botao_jogar) Button botaoJogar;
    @BindView(R.id.botao_recordes) Button botaoRecorde;

    private Boolean firstTime = null;

    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
<<<<<<< Updated upstream
        viewModel = new MainViewModel(getApplication());
        if(isFirstTime()){
            viewModel.copyDatabase();
        }

=======
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> u = new HashMap<>();
        u.put("nome", "felipe");
        u.put("idade", 5);
        db.collection("usuarios").add(u).addOnSuccessListener(documentReference -> System.out.println(documentReference.getId()));
>>>>>>> Stashed changes
    }


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


    @OnClick(R.id.botao_jogar)
    public void jogar(){
        Intent intent = new Intent(MainActivity.this, JogoActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @OnClick(R.id.botao_recordes)
    public void recordes(){
        Intent intent = new Intent(MainActivity.this, RecordesActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}
