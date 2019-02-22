package com.palavras.unicap.palavrinhas.Service;

import com.palavras.unicap.palavrinhas.Entity.Usuario;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsuarioService {

    @GET("cep/find/{cep}/json")
    Call<Usuario> buscarCEP(@Path("cep") String cep);
}
