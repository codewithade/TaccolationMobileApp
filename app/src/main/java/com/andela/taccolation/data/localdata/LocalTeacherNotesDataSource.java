package com.andela.taccolation.data.localdata;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.presentation.model.Teacher;

import java.util.List;

public interface LocalTeacherNotesDataSource {

    LiveData<TaskStatus> uploadTeacherFiles(Teacher teacher, List<String> pathList, List<String> fileNames, String courseCode);
}
