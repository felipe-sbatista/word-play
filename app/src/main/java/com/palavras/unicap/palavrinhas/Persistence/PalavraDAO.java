package com.palavras.unicap.palavrinhas.Persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.palavras.unicap.palavrinhas.Entity.Palavra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PalavraDAO {
    private static String COLUNA_TEXTO = "texto";
    private static String COLUNA_IMAGEM = "imagem";
    private static String COLUNA_SOM = "som";
    private static String COLUNA_ID = "id";

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static PalavraDAO instance;
    Cursor cursor = null;




    public PalavraDAO(Context context){
//        this.palavras = new ArrayList<>();
//        palavras.add("briga");
//        palavras.add("guerra");
//        palavras.add("carro");
//        palavras.add("casa");
       this.openHelper = new DatabaseHelper(context);
    }

    public static synchronized PalavraDAO getInstance(Context context){
       if(instance == null) {
           instance = new PalavraDAO(context);
       }
        return instance;
    }

    public void openConnection(){
       this.db = openHelper.getWritableDatabase();
    }

    public void closeConnection(){
       if(db!=null){
           this.db.close();
       }
    }
    public List<Palavra> listar(){
        List<Palavra> palavras = new ArrayList<>();
        cursor = db.rawQuery("select * from Palavra", new String[]{});
        try{
            Palavra palavra = new Palavra();
            if(cursor.moveToFirst()){
                do{
                    int indexTexto = cursor.getColumnIndex(COLUNA_TEXTO);
                    int indexImagem = cursor.getColumnIndex(COLUNA_IMAGEM);
                    int indexSom = cursor.getColumnIndex(COLUNA_SOM);
                    palavra.setTexto(cursor.getString(indexTexto));
                    palavra.setImagem(cursor.getBlob(indexImagem));
                    palavra.setSom(cursor.getBlob(indexSom));
                    palavras.add(palavra);
                }while (cursor.moveToNext());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return palavras;
    }

//    private List<String> palavras;
}
