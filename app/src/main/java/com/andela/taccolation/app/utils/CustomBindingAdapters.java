package com.andela.taccolation.app.utils;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BindingAdapter;

import com.andela.taccolation.R;
import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.model.TaskItem;
import com.andela.taccolation.presentation.model.Teacher;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;
import java.util.Locale;

public class CustomBindingAdapters {

    @BindingAdapter(value = {"imageSrc"})
    public static void setImageSource(ImageView imageView, String url) {
        final Context imageViewContext = imageView.getContext();
        if (url != null) Glide.with(imageViewContext).load(url).into(imageView);
        else
            Glide.with(imageViewContext).load(ResourcesCompat.getDrawable(imageViewContext.getResources(), R.drawable.teacher, null)).into(imageView);
    }

    @BindingAdapter(value = {"image_resource"})
    public static void setImageFromRes(ImageView imageView, int iconId) {
        imageView.setImageResource(iconId);
    }

    @BindingAdapter(value = {"card_background_color"})
    public static void setBackgroundColor(MaterialCardView cardView, int colorResId) {
        cardView.setCardBackgroundColor(cardView.getContext().getResources().getColor(colorResId));
    }

    @BindingAdapter(value = {"title_resource"})
    public static void setTitleFromResource(MaterialTextView textView, int resId) {
        textView.setText(resId);
    }

    @BindingAdapter(value = {"teacher_name"})
    public static void setTeacherName(MaterialTextView textView, Teacher teacher) {
        if (teacher != null) {
            textView.setText(String.format("%s. %s %s", teacher.getDesignation(), teacher.getFirstName(), teacher.getLastName()));
            textView.setVisibility(View.VISIBLE);
        } else textView.setVisibility(View.INVISIBLE);
    }

    @BindingAdapter(value = {"course_codes"})
    public static void setCourseCodes(MaterialTextView textView, Teacher teacher) {
        if (teacher != null) {
            StringBuilder formattedCourseCode = new StringBuilder();
            final List<String> courseCodeList = teacher.getCourseCodeList();
            for (String courseCode : courseCodeList) {
                formattedCourseCode.append(courseCode);
                if (!courseCode.equals(courseCodeList.get(courseCodeList.size() - 1)))
                    formattedCourseCode.append(", ");
            }
            textView.setText(formattedCourseCode);
        }
    }

    @BindingAdapter(value = {"student_name"})
    public static void setStudentName(MaterialTextView textView, Student student) {
        if (student != null)
            textView.setText(String.format("%s %s", student.getFirstName(), student.getLastName()));

    }

    @BindingAdapter(value = {"toolbar_title"})
    public static void setToolBarTitle(Toolbar toolbar, Student student) {
        if (student != null)
            toolbar.setTitle(String.format("%s %s %s", student.getFirstName(), student.getLastName(), " Stats"));

    }

    @BindingAdapter(value = {"spinner_adapter"})
    public static void setSpinnerAdapter(Spinner spinner, String[] resId) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(spinner.getContext(), android.R.layout.simple_spinner_item, resId);
        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(arrayAdapter);
    }

    @BindingAdapter(value = {"profile_name"})
    public static void setProfileName(MaterialTextView textView, Student student) {
        if (student != null)
            textView.setText(String.format("%s %s", student.getFirstName(), student.getLastName()));
    }

    @BindingAdapter(value = {"task_item_text"})
    public static void setTaskItemText(MaterialButton button, TaskItem taskItem) {
        button.setText(String.format(Locale.getDefault(), "%d\t\t%s", taskItem.getPosition(), taskItem.getTitle()));
    }

    @BindingAdapter(value = {"sex_image_selection"})
    public static void setSexImageSelection(ImageView imageView, String sex) {
        if (sex.toLowerCase().startsWith("m"))
            imageView.setImageResource(R.drawable.pro1);
        else imageView.setImageResource(R.drawable.pro2);
    }

}
