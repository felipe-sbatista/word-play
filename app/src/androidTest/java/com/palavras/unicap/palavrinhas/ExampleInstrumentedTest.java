package com.palavras.unicap.palavrinhas;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.palavras.unicap.palavrinhas.Persistence.AppDatabase;
import com.palavras.unicap.palavrinhas.Persistence.DatabaseCopier;
import com.palavras.unicap.palavrinhas.Persistence.PalavraDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @RunWith(AndroidJUnit4.class)
    public static class ClasseTest {
        private PalavraDAO dao;
        private AppDatabase appDatabase;
        private DatabaseCopier databaseCopier;

        private Context contexto;
        @Before
        public void criarDB(){
            databaseCopier = DatabaseCopier.getInstance(InstrumentationRegistry.getContext());
            appDatabase = databaseCopier.getRoomDatabase();
            dao = appDatabase.palavraDAO();
        }

        @After
        public void close(){
            appDatabase.close();
        }

        @Test
        public void listar(){
            List palavras = dao.loadAllPalavras();
            assertEquals(palavras.size(), 5);
        }
    }
}
