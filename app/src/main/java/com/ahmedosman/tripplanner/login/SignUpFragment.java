package com.ahmedosman.tripplanner.login;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ahmedosman.tripplanner.R;
import com.ahmedosman.tripplanner.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.ahmedosman.tripplanner.login.SignInFragment.SIGNIN;
import static com.ahmedosman.tripplanner.login.SignInFragment.SIGNUP;

public class SignUpFragment extends Fragment {
    private Button btnSignUp;
    private Button btnSignIn;
    private Validation validator;
    private SignInFragment signInFragment;
    private TextInputLayout emailWrapper;
    private TextInputLayout passwordWrapper;
    private TextInputLayout confirmPasswordWrapper;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        validator = new Validation();

        btnSignUp = (Button) view.findViewById(R.id.btn);
        btnSignIn = view.findViewById(R.id.btnGoToSignIn);

        emailWrapper = (TextInputLayout) view.findViewById(R.id.emailSignUpWrapper);
        passwordWrapper = (TextInputLayout) view.findViewById(R.id.passwordSignUpWrapper);
        confirmPasswordWrapper = (TextInputLayout) view.findViewById(R.id.confirmPasswordSignUpWrapper);
        signInFragment = (SignInFragment) getFragmentManager().findFragmentByTag(SIGNIN);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signInFragment == null)
                    signInFragment = new SignInFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.login_fragment, signInFragment, SIGNIN)
                        .commit();
            }
        });


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailWrapper.getEditText().getText().toString();
                String confirmPassword = confirmPasswordWrapper.getEditText().getText().toString();
                String password = passwordWrapper.getEditText().getText().toString();

                //Validation
                boolean emailValid = validator.validateEmail(email);
                boolean passwordLengthValid = validator.validatePasswordLength(password);
                boolean passwordConfirmationValid = validator.validatePasswordConfirmation(password, confirmPassword);

                if (emailValid && passwordConfirmationValid && passwordLengthValid) {
                    User user = new User(email, password);
                    createAccount(user);
                } else {
                    if (passwordLengthValid)
                        passwordWrapper.setError("");
                    else
                        passwordWrapper.setError("Your password must be at least 6 characters long");

                    if (passwordConfirmationValid)
                        confirmPasswordWrapper.setError("");
                    else {
                        confirmPasswordWrapper.setError("Your password doesn't match the confirmation");
                        Log.i("pass", passwordWrapper.getEditText().getText().toString());
                        Log.i("pass", confirmPasswordWrapper.getEditText().getText().toString());
                    }

                    if (emailValid)
                        emailWrapper.setError("");
                    else
                        emailWrapper.setError("Invalid Email");
                }
            }
        });
        return view;
    }


    public void createAccount(final User user) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.i("UNSUCESS", "TRUE");
                            TestConnectionAsyncTask myTest = new TestConnectionAsyncTask(getContext());
                            //No Internet Connection
                            try {
                                if (myTest.execute().get() == false)
                                    Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                                else
                                    emailWrapper.setError("Duplicated Email Address");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            //Signed up successfully
                            signInFragment.signIn(user);
                        }
                    }
                });
    }
}
