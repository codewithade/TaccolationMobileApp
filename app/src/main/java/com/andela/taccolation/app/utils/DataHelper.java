package com.andela.taccolation.app.utils;

import com.andela.taccolation.R;
import com.andela.taccolation.presentation.model.DashboardItem;
import com.andela.taccolation.presentation.model.TaskItem;

import java.util.ArrayList;
import java.util.List;

public class DataHelper {


    public static List<DashboardItem> getDashboardItems() {
        List<DashboardItem> dashboardItems = new ArrayList<>();
        dashboardItems.add(new DashboardItem(R.string.add_student, R.drawable.ic_baseline_person_add_alt_1_24, R.color.magenta, DashboardMenu.ADD_STUDENT));
        dashboardItems.add(new DashboardItem(R.string.slides, R.drawable.ic_baseline_school_24, R.color.yellow, DashboardMenu.LECTURE_AIDS));
        dashboardItems.add(new DashboardItem(R.string.assessment, R.drawable.ic_baseline_assessment_24, R.color.orange, DashboardMenu.ASSESSMENTS));
        dashboardItems.add(new DashboardItem(R.string.attendance, R.drawable.ic_baseline_analytics_24, R.color.green, DashboardMenu.ATTENDANCE));
        dashboardItems.add(new DashboardItem(R.string.student_profile, R.drawable.ic_baseline_perm_contact_calendar_24, R.color.dark_blue, DashboardMenu.STUDENT_PROFILE));
        dashboardItems.add(new DashboardItem(R.string.teacher_profile, R.drawable.ic_baseline_account_circle_24, R.color.dark_brown, DashboardMenu.TEACHER_PROFILE));
        dashboardItems.add(new DashboardItem(R.string.task_title, R.drawable.ic_task_24, R.color.torque, DashboardMenu.TASKS));
        dashboardItems.add(new DashboardItem(R.string.teacher_notes, R.drawable.ic_note, R.color.pink, DashboardMenu.TEACHER_NOTES));
        dashboardItems.add(new DashboardItem(R.string.leaderboard, R.drawable.trophy_variant, R.color.black, DashboardMenu.LEADER_BOARD));
        dashboardItems.add(new DashboardItem(R.string.report, R.drawable.ic_baseline_assignment_24, R.color.purple, DashboardMenu.REPORT_SHEET));
        return dashboardItems;
    }

    public static List<TaskItem> getTaskItems() {
        List<TaskItem> taskItems = new ArrayList<>();
        taskItems.add(new TaskItem(1, "Do Homework"));
        taskItems.add(new TaskItem(2, "Bring Class Book"));
        taskItems.add(new TaskItem(3, "Math's Problems"));
        taskItems.add(new TaskItem(4, "Physics Classwork"));
        taskItems.add(new TaskItem(5, "Plant a Tree"));
        taskItems.add(new TaskItem(6, "Go on excursion"));
        taskItems.add(new TaskItem(7, "Visit the Orphanage"));
        return taskItems;
    }
}
