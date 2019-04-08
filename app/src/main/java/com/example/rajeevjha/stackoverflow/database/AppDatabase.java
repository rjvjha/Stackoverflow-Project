package com.example.rajeevjha.stackoverflow.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.rajeevjha.stackoverflow.models.Question;

@Database(entities = {Question.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    // Singleton
    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {


        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "question_database")
                            .build();
                }
            }

        }
        return INSTANCE;
    }

    public abstract QuestionDao questionDao();

}
