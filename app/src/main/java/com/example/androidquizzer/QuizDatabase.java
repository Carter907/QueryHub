package com.example.androidquizzer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Database(entities = {Quiz.class}, version = 1, exportSchema = false)
@TypeConverters({QuestionListConverter.class, AnswerClassConverter.class})
public abstract class QuizDatabase extends RoomDatabase {

    abstract QuizDao getDao();

    public static final ExecutorService service = Executors.newFixedThreadPool(4);


    public enum Vendor {

        INSTANCE;

        private QuizDatabase db;

        private final RoomDatabase.Callback onCreate = new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

            }
        };

        public QuizDatabase getDb(Context context) {
            if (db == null) {
                db = Room.databaseBuilder(context.getApplicationContext(), QuizDatabase.class, "quiz_table")
                        .addCallback(onCreate)
                        .build();
            }
            return db;
        }
    }
}
