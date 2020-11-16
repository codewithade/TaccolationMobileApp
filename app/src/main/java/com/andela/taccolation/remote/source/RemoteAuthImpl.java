package com.andela.taccolation.remote.source;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.andela.taccolation.app.utils.AuthenticationState;
import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.data.remotedata.RemoteAuthDataSource;
import com.andela.taccolation.presentation.model.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class RemoteAuthImpl implements RemoteAuthDataSource {

    private static final String TAG = Constants.LOG.getConstant();
    private final FirebaseAuth mFirebaseAuth;
    private final FirebaseFirestore mFirestore;
    private final MutableLiveData<AuthenticationState> userAuthState = new MutableLiveData<>();
    private final MutableLiveData<TaskStatus> status = new MutableLiveData<>();
    private final MutableLiveData<Teacher> mTeacher = new MutableLiveData<>();

    @Inject
    public RemoteAuthImpl(FirebaseAuth firebaseAuth, FirebaseFirestore firestore) {
        mFirebaseAuth = firebaseAuth;
        mFirestore = firestore;
    }

    @Override
    public LiveData<AuthenticationState> signUpTeacher(Teacher teacher) {
        if (mFirebaseAuth.getCurrentUser() == null) {
            mFirebaseAuth.createUserWithEmailAndPassword(teacher.getEmail(), teacher.getPassword())
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            userAuthState.setValue(AuthenticationState.FAILED);
                            Log.i(TAG, "signUpTeacher: Failed. Error: " + task.getException());
                        } else {
                            userAuthState.setValue(AuthenticationState.PENDING);
                            Log.i(TAG, "signUpTeacher: Successful");
                            createUser(teacher);
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            if (user != null) sendConfirmationEmail(user);
                        }
                    });
        }
        return userAuthState;
    }

    private void createUser(Teacher teacher) {
        if (teacher != null) {
            String autoId = mFirestore.collection(Constants.TEACHER.getConstant()).document().getId();
            Log.i(TAG, "createUser: autoID: " + autoId);
            teacher.setId(autoId);
            mFirestore.collection(Constants.TEACHER.getConstant()).document(autoId).set(teacher)
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful())
                            Log.i(TAG, "createUser: failed. Error: " + task.getException());
                        else
                            Log.i(TAG, "createUser: Successful.");
                    });
        }
    }

    private void sendConfirmationEmail(FirebaseUser user) {
        if (!user.isEmailVerified()) {
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "sendConfirmationEmail: successful");
                            userAuthState.setValue(AuthenticationState.EMAIL_UNCONFIRMED);
                        } else {
                            Log.i(TAG, "sendConfirmationEmail: failed. Error: " + task.getException());
                            userAuthState.setValue(AuthenticationState.FAILED);
                        }
                    });
        }
    }

    @Override
    public LiveData<AuthenticationState> signInTeacher(String email, String password) {
        userAuthState.setValue(AuthenticationState.PENDING);
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            user.reload();
            if (!user.isEmailVerified())
                userAuthState.setValue(AuthenticationState.EMAIL_UNCONFIRMED);
            else signInWithEmailAndPassword(email, password);
        } else signInWithEmailAndPassword(email, password);
        return userAuthState;
    }

    @Override
    public LiveData<AuthenticationState> getTeacherAuthState() {
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.reload();
            Log.i(TAG, "getTeacherAuthState: user details: " + firebaseUser.getUid());
            if (firebaseUser.isEmailVerified())
                userAuthState.setValue(AuthenticationState.AUTHENTICATED);
            else userAuthState.setValue(AuthenticationState.EMAIL_UNCONFIRMED);
        } else userAuthState.setValue(AuthenticationState.UNAUTHENTICATED);
        return userAuthState;
    }

    @Override
    public LiveData<AuthenticationState> sendVerificationEmail() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) sendConfirmationEmail(user);
        return userAuthState;
    }

    @Override
    public LiveData<TaskStatus> sendPasswordResetLink(String email) {
        mFirebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        status.setValue(TaskStatus.SUCCESS);
                        Log.i(TAG, "sendPasswordResetLink: successful");
                    } else {
                        status.setValue(TaskStatus.FAILED);
                        Log.i(TAG, "sendPasswordResetLink: failed. Error: " + Objects.requireNonNull(task.getException()).getMessage());
                    }

                });
        return status;
    }

    @Override
    public LiveData<Teacher> getTeacherDetails() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            Log.i(TAG, "getTeacherDetails: User email: " + user.getEmail());
            mFirestore.collection(Constants.TEACHER.getConstant()).whereEqualTo("email", user.getEmail()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Teacher> teacherList = Objects.requireNonNull(task.getResult()).toObjects(Teacher.class);
                            mTeacher.setValue(teacherList.get(0));
                            Log.i(TAG, "getTeacherDetails: Teacher details: " + mTeacher.toString());
                        } else
                            Log.i(TAG, "getTeacherDetails: failed. Error: " + task.getException());
                    });
        }
        return mTeacher;
    }

    private void signInWithEmailAndPassword(String email, String password) {
        userAuthState.setValue(AuthenticationState.PENDING);
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        String errorMessage = task.getException().getMessage();
                        Log.i(TAG, "signInTeacher: failed. Error: " + errorMessage);
                        if (errorMessage.contains("invalid"))
                            userAuthState.setValue(AuthenticationState.INVALID_CREDENTIALS);
                        else if (errorMessage.contains("network"))
                            userAuthState.setValue(AuthenticationState.NETWORK_ERROR);
                        else if (errorMessage.contains("no user record"))
                            userAuthState.setValue(AuthenticationState.NO_USER_RECORD);
                        else if (errorMessage.contains("already in use"))
                            userAuthState.setValue(AuthenticationState.EMAIL_ALREADY_IN_USE);
                        else if (errorMessage.contains("least 6 characters"))
                            userAuthState.setValue(AuthenticationState.PASSWORD_TOO_SHORT);
                        else userAuthState.setValue(AuthenticationState.FAILED);
                    } else {
                        Log.i(TAG, "signInTeacher: successful");
                        userAuthState.setValue(AuthenticationState.AUTHENTICATED);
                    }
                });
    }
}
