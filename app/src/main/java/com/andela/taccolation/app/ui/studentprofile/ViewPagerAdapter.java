package com.andela.taccolation.app.ui.studentprofile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.presentation.model.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private static final String TAG = Constants.LOG.getConstant();
    private final int mTabCount;
    private final Map<String, List<Student>> mMap;

    public ViewPagerAdapter(@NonNull Fragment fragment, Map<String, List<Student>> map) {
        super(fragment);
        mMap = map;
        mTabCount = mMap.size();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String[] studentCourseCode = new String[mMap.keySet().size()];
        final String[] courseCode = mMap.keySet().toArray(studentCourseCode);
        // uses the same Fragment but with different data depending on the tab's position
        return StudentCourse.newInstance(position, Arrays.asList(courseCode), mMap.get(courseCode[position]));
    }

    @Override
    public int getItemCount() {
        return mTabCount;
    }
}
