package com.andela.taccolation.app.ui.home;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDeepLinkRequest;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.andela.taccolation.R;
import com.andela.taccolation.app.ui.teachernotes.NotesBroadcastReceiver;
import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.databinding.DrawerBottomLayoutBinding;
import com.andela.taccolation.presentation.viewmodel.ProfileViewModel;
import com.google.android.material.navigation.NavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private static final String TAG = Constants.LOG.getConstant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* popUpToInclusive set to TRUE removes all previous instances of the destination Fragment
         enabling this with popUpToInclusive(navigating from Worker to Dashboard) set to FALSE, prevents crashes
         disabling this with popUpToInclusive set to TRUE, prevents crashes*/
        setUpThemePreferences();
        Log.i(TAG, "onCreate: ACTIVITY");
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        if (intent.getType() != null)
            handleIntent(intent);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        /* NavigationUI uses an AppBarConfiguration object to manage the behavior of the Navigation
         button in the upper-left corner of your app's display area.
         The Navigation button’s behavior changes depending on whether the user is at a top-level destination.
         A top-level destination is the root, or highest level destination, in a set of hierarchically-related destinations.
         Top-level destinations do not display an Up button in the top app bar because there is no higher level destination.
         By default, the start destination of your app is the only top-level destination.
         When the user is at a top-level destination, the Navigation button becomes a drawer icon
         if the destination uses a DrawerLayout.
         If the destination doesn't use a DrawerLayout, the Navigation button is hidden.
         When the user is on any other destination, the Navigation button appears as an Up button .
         To configure the Navigation button using only the start destination as the top-level destination,
         create an AppBarConfiguration object, and pass in the corresponding navigation graph, as shown below:
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();*/

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        DrawerBottomLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.drawer_bottom_layout, null, false);
        profileViewModel.getTeacher().observe(this, teacher -> {
            if (teacher != null)
                binding.setTeacher(teacher);
        });
        navigationView.addHeaderView(binding.getRoot());

        // Next, connect the DrawerLayout to your navigation graph by passing it to AppBarConfiguration,
        // as shown in the following example:
        mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setOpenableLayout(mDrawerLayout)
                .build();

        /* To add navigation support to the default action bar,
         call setupActionBarWithNavController() from your main activity's onCreate() method, as shown below.
         Note that you need to declare your AppBarConfiguration outside of onCreate(),
         since you also use it when overriding onSupportNavigateUp():
         NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);*/

        /* https://stackoverflow.com/questions/55447697/navigation-drawer-dont-work-with-navigation-component
         https://developer.android.com/guide/navigation/navigation-ui#action_bar
         To create a Toolbar with NavigationUI*/
        mToolbar = findViewById(R.id.dashboard_toolbar);
        setSupportActionBar(mToolbar);
        NavigationUI.setupActionBarWithNavController(this, navController, mDrawerLayout);
        // NavigationUI.setupWithNavController(mToolbar, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        setUpNavListener();
    }

    private void handleIntent(Intent intent) {
        String type = intent.getType();
        String action = intent.getAction();
        Log.i(TAG, "handleIntent: TYPE: " + type);
        Log.i(TAG, "handleIntent: ACTION: " + action);
        Uri uri = intent.getData();
        if (uri != null)
            Log.i(TAG, "handleIntent: URI: " + uri.toString());
        else {
            final NavDeepLinkRequest request = NavDeepLinkRequest.Builder
                    .fromUri(Uri.parse(getString(R.string.uri)))
                    .setAction(Intent.ACTION_SEND)
                    .setMimeType("image/*")
                    .build();
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(request);
        }


    }

    private void registerReceiver() {
        BroadcastReceiver mReceiver = new NotesBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        // https://stackoverflow.com/questions/12901763/broadcast-receiver-action-send-does-not-show-up
        intentFilter.addAction(Intent.ACTION_SEND);
        //intentFilter.addAction(Intent.ACTION_SEND_MULTIPLE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        try {
            intentFilter.addDataType("application/pdf");
            intentFilter.addDataType("application/vnd.google.panorama360+jpg");
            intentFilter.addDataType("image/*");
            intentFilter.addDataType("application/pdf");
            intentFilter.addDataType("application/doc");
            intentFilter.addDataType("text/csv");
            intentFilter.addDataType("text/comma-separated-values");
            intentFilter.addDataType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
        }


        registerReceiver(mReceiver, intentFilter);
    }

    private void setUpThemePreferences() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) // Follow system default for Android 9 and above
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        else {
            final boolean isDarkThemeEnabled = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.theme_key), false);
            if (isDarkThemeEnabled)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mDrawerLayout) //mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    // If your menu was added via the Activity's onCreateOptionsMenu(),
    // for example, you can associate the menu items with destinations by overriding the Activity's onOptionsItemSelected()
    // to call onNavDestinationSelected(), as shown in the following example:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    /*
   NavigationUI uses OnDestinationChangedListener to make these common UI components navigation-aware. Note, however, that you can also use
   OnDestinationChangedListener on its own to make any custom UI or business logic aware of navigation events.
   As an example, you might have common UI elements that you intend to show in some areas of your app while hiding them in others.
   Using your own OnDestinationChangedListener, you can selectively show or hide these UI elements based on the target destination,
   as shown in the following example:*/
    // TODO: 01/11/2020 DrawerLayout should only appear or swipe able on DashboardFragment
    private void setUpNavListener() {
        NavController controller = Navigation.findNavController(this, R.id.nav_host_fragment);
        controller.addOnDestinationChangedListener((navController, navDestination, arguments) -> {
            final int id = navDestination.getId();
            if (id == R.id.studentProfile ||
                    (id == R.id.reward) ||
                    (id == R.id.addStudent) ||
                    (id == R.id.task) ||
                    (id == R.id.attendance) ||
                    (id == R.id.nav_home) ||
                    (id == R.id.nav_profile) ||
                    (id == R.id.nav_settings) ||
                    (id == R.id.reportSheet) ||
                    (id == R.id.teacherNotes) ||
                    (id == R.id.notification) ||
                    (id == R.id.editProfile) ||
                    (id == R.id.leaderBoard) ||
                    (id == R.id.teacherProfile) ||
                    (id == R.id.lectureAids)) {
                mToolbar.setVisibility(View.VISIBLE);
                mToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            } else if ((id == R.id.loginFragment) ||
                    (id == R.id.registerFragment) ||
                    (id == R.id.OnBoardingFragment) ||
                    (id == R.id.workerFragment) ||
                    (id == R.id.studentDetails)) {
                mToolbar.setVisibility(View.GONE);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            } else if (id == R.id.dashboardFragment) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                mToolbar.setVisibility(View.VISIBLE);
            }

        });
    }

    @Override
    protected void onDestroy() {
        // unregisterReceiver(mReceiver);
        super.onDestroy();
        Log.i(TAG, "onDestroy: ACTIVITY");
    }
}