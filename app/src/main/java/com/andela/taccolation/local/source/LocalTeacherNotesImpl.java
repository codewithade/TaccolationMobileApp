package com.andela.taccolation.local.source;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.data.localdata.LocalTeacherNotesDataSource;
import com.andela.taccolation.local.database.NotesDao;
import com.andela.taccolation.local.entities.Notes;
import com.andela.taccolation.presentation.model.Teacher;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

public class LocalTeacherNotesImpl implements LocalTeacherNotesDataSource {

    private final NotesDao mNotesDao;
    private final Executor mExecutor;
    private static final String TAG = Constants.LOG.getConstant();

    @Inject
    public LocalTeacherNotesImpl(NotesDao notesDao, Executor executor) {
        mNotesDao = notesDao;
        mExecutor = executor;
    }

    @Override
    public LiveData<TaskStatus> uploadTeacherFiles(Teacher teacher, List<String> pathList, List<String> fileNames, String courseCode) {
        return null;
    }

    @Override
    public LiveData<List<Notes>> getAllNotes() {
        return mNotesDao.getAllNotes();
    }

    public LiveData<List<Notes>> loadAllByIds(int[] userIds) {
        return mNotesDao.loadAllByIds(userIds);
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<Notes> getNoteByTitle(String title, String courseCode) {

        return mNotesDao.getNoteByTitle(title, courseCode);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    @Override
    public void insertAllNotes(Notes... teacherNotes) {
        mExecutor.execute(() -> mNotesDao.insertAllNotes(teacherNotes));
    }

    @Override
    public void deleteNote(Notes teacherNotes) {
        mExecutor.execute(() -> mNotesDao.delete(teacherNotes));
    }
}
