package com.palavras.unicap.palavrinhas.Config;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig(){
       retrofit =  new Retrofit.Builder()
               .baseUrl("localhost:8080/")
               .addConverterFactory(JacksonConverterFactory.create())
               .build();
    }
}
