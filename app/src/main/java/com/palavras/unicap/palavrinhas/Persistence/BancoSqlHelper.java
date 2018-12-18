package com.palavras.unicap.palavrinhas.Persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoSqlHelper extends SQLiteOpenHelper {
    private static final String nomeDB = "educacao.db";

    public BancoSqlHelper(Context context){
        super(context, nomeDB ,null, 0);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
