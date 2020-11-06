package com.andela.taccolation.local.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.andela.taccolation.local.entities.Notes;

import java.util.List;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    LiveData<List<Notes>> getAll();

    @Query("SELECT * FROM notes WHERE uid IN (:userIds)")
    LiveData<List<Notes>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM notes WHERE title LIKE :title AND course_code LIKE :courseCode LIMIT 1")
    LiveData<Notes> getNoteByTitle(String title, String courseCode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Notes... teacherNotes);

    @Delete
    void delete(Notes teacherNotes);
}
