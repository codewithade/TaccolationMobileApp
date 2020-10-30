
package com.andela.taccolation.app.ui.auth;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.andela.taccolation.R;
import com.andela.taccolation.app.utils.AuthenticationState;
import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.databinding.FragmentRegisterBinding;
import com.andela.taccolation.presentation.model.Teacher;
import com.andela.taccolation.presentation.viewmodel.AuthViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {

    private static final String TAG = Constants.LOG.getConstant();

    private FragmentRegisterBinding mBinding;
    private AuthViewModel mAuthViewModel;
    private NavController mNavController;
    private TextInputLayout mFirstName, mLastName, mEmail, mPassword, mConfirmPassword;
    private final TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mFirstName.setError("");
            mLastName.setError("");
            mEmail.setError("");
            mPassword.setError("");
            mConfirmPassword.setError("");
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

    public RegisterFragment() {
        // Required empty public constructor
    }

    private Spinner mDesignationSpinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentRegisterBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavController = NavHostFragment.findNavController(this);
        // setUpNavGraphScopedViewModel();da
        mAuthViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews();
        setUpSpinner();
        addTextWatcher();

        mBinding.loginRoute.setOnClickListener(v -> mNavController.navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(AuthenticationState.UNAUTHENTICATED)));

        mBinding.registerButton.setOnClickListener(v -> {
            addTextChangeListener();
            if (!isEmpty) signUpNewTeacher();
        });
    }

    private void setUpSpinner() {
        mDesignationSpinner = mBinding.designationSpinner;
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.designation, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mDesignationSpinner.setAdapter(adapter);
    }

    private void addTextWatcher() {
        Objects.requireNonNull(mFirstName.getEditText()).addTextChangedListener(mTextWatcher);
        Objects.requireNonNull(mLastName.getEditText()).addTextChangedListener(mTextWatcher);
        Objects.requireNonNull(mEmail.getEditText()).addTextChangedListener(mTextWatcher);
        Objects.requireNonNull(mPassword.getEditText()).addTextChangedListener(mTextWatcher);
        Objects.requireNonNull(mConfirmPassword.getEditText()).addTextChangedListener(mTextWatcher);
    }

    private void addTextChangeListener() {
        mFirstName.addOnEditTextAttachedListener(mTextAttachedListener);
        mLastName.addOnEditTextAttachedListener(mTextAttachedListener);
        mEmail.addOnEditTextAttachedListener(mTextAttachedListener);
        mPassword.addOnEditTextAttachedListener(mTextAttachedListener);
        mConfirmPassword.addOnEditTextAttachedListener(mTextAttachedListener);
    }

    private void signUpNewTeacher() {
        String firstName = Objects.requireNonNull(mFirstName.getEditText()).getText().toString();
        String lastName = Objects.requireNonNull(mLastName.getEditText()).getText().toString();
        String email = Objects.requireNonNull(mEmail.getEditText()).getText().toString();
        String password = Objects.requireNonNull(mPassword.getEditText()).getText().toString();
        String confirmPassword = Objects.requireNonNull(mConfirmPassword.getEditText()).getText().toString();

        if (!password.equals(confirmPassword))
            Snackbar.make(requireView(), getString(R.string.password_mismatch), Snackbar.LENGTH_LONG).show();
        else {
            mProgressBar.setVisibility(View.VISIBLE);
            Teacher teacher = new Teacher(firstName, lastName, (String) mDesignationSpinner.getSelectedItem(), Arrays.asList("MAT 111", "TCS 407", "ICS 413", "ABE 263"), "", email, "https://www.imageurl.com.ng", password);
            Log.i(TAG, "signUpNewTeacher: Teacher: " + teacher.toString());
            mAuthViewModel.signUpTeacher(teacher).observe(getViewLifecycleOwner(), this::processAuthState);
        }
    }

    // fixme do i really need a live data? why not navigate to loginFragment after registration and show a toast message for user to confirm email address
    private void processAuthState(AuthenticationState authenticationState) {
        switch (authenticationState) {
            case AUTHENTICATED:
            case EMAIL_CONFIRMED:
                // navigate user to dashboard and pop back stack
                sendSnackbar(getString(R.string.snack_bar_email_confirmed_msg));
                mNavController.navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(authenticationState));
                break;
            case EMAIL_UNCONFIRMED:
                // navigate user to login screen and inform user to confirm email
                sendSnackbar(getString(R.string.reg_success));
                mNavController.navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(authenticationState));
                break;
            case UNAUTHENTICATED:
                // navigate user to login screen if user is registered
                break;
            case FAILED:
                // update user of failure to register
                sendSnackbar(getString(R.string.failed_authentication));
                break;
        }
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private void bindViews() {
        mFirstName = mBinding.firstNameTil;
        mLastName = mBinding.lastNameTil;
        mEmail = mBinding.emailTil;
        mPassword = mBinding.passwordTil;
        mConfirmPassword = mBinding.confirmPasswordTil;
        mProgressBar = mBinding.progressBar;
    }

    // Since ViewModel is scoped to the navigation graph,
    // we use the defaultViewModelProviderFactory object that is
    // available to activities and fragments that are annotated with @AndroidEntryPoint.
    /*private void setUpNavGraphScopedViewModel() {
        NavBackStackEntry backStackEntry = mNavController.getBackStackEntry(R.id.auth_graph);
        mAuthViewModel = new ViewModelProvider(backStackEntry, getDefaultViewModelProviderFactory()).get(AuthViewModel.class);
    }*/

    private void sendSnackbar(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}