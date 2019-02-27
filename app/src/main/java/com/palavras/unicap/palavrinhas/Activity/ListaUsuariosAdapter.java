package com.palavras.unicap.palavrinhas.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.palavras.unicap.palavrinhas.Entity.Usuario;
import com.palavras.unicap.palavrinhas.R;

import java.util.List;

public class ListaUsuariosAdapter extends RecyclerView.Adapter {


    private List<Usuario> usuarios;
    private Context context;

    public ListaUsuariosAdapter(List<Usuario> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewLayout = LayoutInflater.from(this.context)
                .inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(viewLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        TextView textView = holder.itemView.findViewById(R.id.nome_usuario);
        textView.setText(usuario.getNome());
        TextView t2= holder.itemView.findViewById(R.id.pontos_usuario);
        t2.setText(usuario.getPontos().get(0));

    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    class UsuarioViewHolder extends RecyclerView.ViewHolder{

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
