package com.andela.taccolation.app.ui.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.andela.taccolation.R;
import com.andela.taccolation.app.utils.AuthenticationState;
import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.databinding.FragmentLoginBinding;
import com.andela.taccolation.databinding.PasswordResetDialogBinding;
import com.andela.taccolation.presentation.viewmodel.AuthViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    private static final String TAG = Constants.LOG.getConstant();
    private AuthViewModel mAuthViewModel;
    private FragmentLoginBinding mBinding;
    private TextInputLayout mEmail, mPassword;
    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mEmail.setError("");
            mPassword.setError("");
        }
    };
    private boolean isEmpty = true;
    // watches for empty Text Input fields
    private final TextInputLayout.OnEditTextAttachedListener mTextAttachedListener = inputLayout -> {
        if (Objects.requireNonNull(inputLayout.getEditText()).getText().toString().isEmpty()) {
            inputLayout.setError(getResources().getString(R.string.empty_input_text_error));
            isEmpty = true;
        } else {
            inputLayout.setError("");
            isEmpty = false;
        }
    };
    private AlertDialog mAlertDialog;

    public LoginFragment() {
        // Required empty public constructor
    }

    private PasswordResetDialogBinding mResetDialogBinding;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: LOGIN FRAGMENT CALLED");
        mAuthViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentLoginBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            final AuthenticationState authenticationState = LoginFragmentArgs.fromBundle(getArguments()).getAuthemticationState();
            Log.i(TAG, "onViewCreated: AuthState received from Dashboard: " + authenticationState);
            processAuthState(authenticationState);
        }

        bindViews();
        addTextWatcher();

        mBinding.loginButton.setOnClickListener(v -> {
            addTextChangeListener();
            if (!isEmpty) signInTeacher();
        });

        mBinding.registerRoute.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment));
        mBinding.forgotPassword.setOnClickListener(v -> showResetPasswordDialog());
        closeAppOnBackPressed();
    }

    private void showResetPasswordDialog() {
        mAlertDialog = new MaterialAlertDialogBuilder(requireContext()).create();

        mResetDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.password_reset_dialog, null, false);
        mResetDialogBinding.cancelResetButton.setOnClickListener(view -> mAlertDialog.dismiss());
        mResetDialogBinding.sendResetLinkButton.setOnClickListener(view -> {
            String email = Objects.requireNonNull(mResetDialogBinding.emailResetTil.getEditText()).getText().toString();
            if (!TextUtils.isEmpty(email)) {
                mResetDialogBinding.progressBar.setVisibility(View.VISIBLE);
                mAuthViewModel.sendPasswordResetLink(email).observe(getViewLifecycleOwner(), this::processTaskStatus);
            }
        });

        mAlertDialog.setView(mResetDialogBinding.getRoot());
        mAlertDialog.show();
    }

    private void signInTeacher() {
        String email = Objects.requireNonNull(mEmail.getEditText()).getText().toString();
        String password = Objects.requireNonNull(mPassword.getEditText()).getText().toString();
        mProgressBar.setVisibility(View.VISIBLE);
        mBinding.loginButton.setEnabled(false);
        mAuthViewModel.signInTeacher(email, password).observe(getViewLifecycleOwner(), this::processAuthState);
    }

    private void bindViews() {
        mProgressBar = mBinding.progressBar;
        mEmail = mBinding.emailTil;
        mPassword = mBinding.passwordTil;
    }

    private void addTextWatcher() {
        Objects.requireNonNull(mEmail.getEditText()).addTextChangedListener(mTextWatcher);
        Objects.requireNonNull(mPassword.getEditText()).addTextChangedListener(mTextWatcher);
    }

    private void addTextChangeListener() {
        mEmail.addOnEditTextAttachedListener(mTextAttachedListener);
        mPassword.addOnEditTextAttachedListener(mTextAttachedListener);
    }

    private void processAuthState(AuthenticationState authenticationState) {
        switch (authenticationState) {
            case AUTHENTICATED:
                SharedPreferences.Editor editor = requireActivity().getPreferences(Context.MODE_PRIVATE).edit();
                editor.putBoolean(Constants.USER_AUTHENTICATED.getConstant(), true);
                editor.apply();
                // navigate user to dashboard and pop back stack
                // mNavController.popBackStack();
                NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_workerFragment);
                mProgressBar.setVisibility(View.GONE);
                mBinding.loginButton.setEnabled(true);
                break;
            case EMAIL_CONFIRMED:
                // inform user to login since email is confirmed
                sendSnackbar(getString(R.string.email_confirmed));
                break;
            case EMAIL_UNCONFIRMED:
                // inform user to confirm email
                Snackbar.make(requireView(), getString(R.string.resend_email), Snackbar.LENGTH_LONG).setAction("Resend", view -> mAuthViewModel.sendVerificationEmail()).show();
                mProgressBar.setVisibility(View.GONE);
                mBinding.loginButton.setEnabled(true);
                break;
            case UNAUTHENTICATED:
                mBinding.loginButton.setEnabled(true);
                // inform user to login if user is registered
                sendSnackbar(getString(R.string.unauthenticated_user));
                break;
            case NETWORK_ERROR:
                sendSnackbar(getString(R.string.internet_error));
                mProgressBar.setVisibility(View.GONE);
                mBinding.loginButton.setEnabled(true);
                break;
            case INVALID_CREDENTIALS:
                sendSnackbar(getString(R.string.invalid_credentials));
                mProgressBar.setVisibility(View.GONE);
                mBinding.loginButton.setEnabled(true);
                break;
            case FAILED:
                // update user of failure to login
                sendSnackbar(getString(R.string.failed_authentication));
                mProgressBar.setVisibility(View.GONE);
                mBinding.loginButton.setEnabled(true);
                break;
        }
    }

    private void processTaskStatus(TaskStatus status) {
        switch (status) {
            case SUCCESS:
                mAlertDialog.dismiss();
                sendSnackbar("Password reset link has been sent to your email");
                break;
            case PENDING:
                sendSnackbar("Processing...");
                break;
            case FAILED:
                sendSnackbar("An error occurred. try again");
                mResetDialogBinding.progressBar.setVisibility(View.GONE);
                break;
        }
    }

    private void closeAppOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }

    private void sendSnackbar(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: LOGIN FRAGMENT");
    }
}