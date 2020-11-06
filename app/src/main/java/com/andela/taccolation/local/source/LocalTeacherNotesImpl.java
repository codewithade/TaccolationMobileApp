package com.andela.taccolation.local.source;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.data.localdata.LocalTeacherNotesDataSource;
import com.andela.taccolation.local.database.NotesDao;
import com.andela.taccolation.local.entities.Notes;
import com.andela.taccolation.presentation.model.Teacher;

import java.util.List;

import javax.inject.Inject;

public class LocalTeacherNotesImpl implements LocalTeacherNotesDataSource {

    private final NotesDao mNotesDao;

    @Inject
    public LocalTeacherNotesImpl(NotesDao notesDao) {
        mNotesDao = notesDao;
    }

    @Override
    public LiveData<TaskStatus> uploadTeacherFiles(Teacher teacher, List<String> pathList, List<String> fileNames, String courseCode) {
        return null;
    }

    public LiveData<List<Notes>> getAll() {
        return mNotesDao.getAll();
    }

    public LiveData<List<Notes>> loadAllByIds(int[] userIds) {
        return mNotesDao.loadAllByIds(userIds);
    }

    public LiveData<Notes> getNoteByTitle(String title, String courseCode) {
        return mNotesDao.getNoteByTitle(title, courseCode);
    }

    public void insertAll(Notes... teacherNotes) {
        mNotesDao.insertAll(teacherNotes);
    }

    public void delete(Notes teacherNotes) {
        mNotesDao.delete(teacherNotes);
    }
}
