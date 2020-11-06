package com.andela.taccolation.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.andela.taccolation.local.entities.Notes;

@Database(entities = {Notes.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NotesDao notesDao();

    /*private static volatile AppDatabase INSTANCE;

    synchronized static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null)
            INSTANCE = create(context);
        return INSTANCE;
    }

    static AppDatabase create(Context context) {
        RoomDatabase.Builder<AppDatabase> db = Room.databaseBuilder(context, AppDatabase.class, Constants.DATABASE_NAME.getConstant());
        return db.build();
    }*/
}
