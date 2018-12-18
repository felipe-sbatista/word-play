package com.palavras.unicap.palavrinhas.Persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String NOME_DATABASE = "educacao.db";
    private static final int VERSAO_DATABASE = 1;

    public DatabaseHelper(Context context) {
        super(context, NOME_DATABASE, null, VERSAO_DATABASE);
    }
}
