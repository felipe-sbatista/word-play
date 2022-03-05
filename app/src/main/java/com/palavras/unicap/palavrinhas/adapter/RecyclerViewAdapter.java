package com.palavras.unicap.palavrinhas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.palavras.unicap.palavrinhas.R;
import com.palavras.unicap.palavrinhas.entity.Usuario;
import com.palavras.unicap.palavrinhas.entity.UsuarioDB;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<UsuarioDB> usuarios;
    private Context context;

    public RecyclerViewAdapter(List<UsuarioDB> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UsuarioDB usuario = usuarios.get(position);
        holder.viewNome.setText(usuario.nome);
        holder.viewPontos.setText(usuario.pontuacao+ " pontos");
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView viewNome;
        public TextView viewPontos;
        public TextView viewTempo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             viewNome = itemView.findViewById(R.id.nome_usuario);
             viewPontos = itemView.findViewById(R.id.pontos_usuario);
             viewTempo = itemView.findViewById(R.id.tempo_usuario);
        }
    }
}
