package com.ahmedosman.tripplanner.login;


import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedosman.tripplanner.home.Home;
import com.ahmedosman.tripplanner.R;
import com.ahmedosman.tripplanner.models.User;
import com.ahmedosman.tripplanner.sqllite.TripsTable;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInFragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btnSignUp;
    private Button btnSignIn;
    private TextView invalidEmailOrPassword;
    private SignInButton btnSignInGoogle;
    private GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 1;
    private String googleClientId;
    private Validation validator;
    private Intent intent;
    private String userName;
    public static final String USER_NAME = "username";
    private static final String SHARED = "shared";
    private static final String PASSWORD = "password";
    public static final String SIGNUP = "signup";
    public static final String SIGNIN = "signin";

    public SignInFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        validator = new Validation();
        final TextInputLayout emailWrapper = (TextInputLayout) view.findViewById(R.id.emailSignInWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) view.findViewById(R.id.passwordSignInWrapper);
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        btnSignIn = (Button) view.findViewById(R.id.btnSignIn);
        btnSignInGoogle = (SignInButton) view.findViewById(R.id.btnSignInGoogle);
        invalidEmailOrPassword = view.findViewById(R.id.invalid_email_or_password);

        googleClientId = "832170904658-e9qavrugrim2l0ipcj3al0rg9lbq9vtj.apps.googleusercontent.com";
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userName = user.getEmail();
                    intent = new Intent(SignInFragment.this.getContext(), Home.class);
                    userName = user.getEmail();
                    TripsTable.setUserName(userName);
                    startActivity(intent);
                    Log.d("signingin", "onAuthStateChanged:signed_in:" + user.getEmail());
                } else {
                    // User is signed out
                    Log.d("signingout", "onAuthStateChanged:signed_out");
                }
            }
        };
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                invalidEmailOrPassword.setVisibility(View.INVISIBLE);
                String email = emailWrapper.getEditText().getText().toString();
                String password = passwordWrapper.getEditText().getText().toString();

                //Validation
                boolean emailValid = validator.validateEmail(email);
                boolean passwordLengthValid = validator.validatePasswordLength(password);

                if (passwordLengthValid)
                    passwordWrapper.setError("");
                else
                    passwordWrapper.setError("Short password");

                if (emailValid)
                    emailWrapper.setError("");
                else
                    emailWrapper.setError("Invalid Email");

                if (emailValid && passwordLengthValid) {
                    User user = new User(email, password);
                    signIn(user);
                }
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidEmailOrPassword.setVisibility(View.INVISIBLE);
                SignUpFragment signUpFragment = new SignUpFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.login_fragment, signUpFragment, SIGNUP)
                        .addToBackStack(null)
                        .commit();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(googleClientId).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(view.getContext(), gso);
        btnSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnSignInGoogle:
                        signInUsingGoogle();
                        break;
                }
            }
        });
        return view;
    }

    private void signInUsingGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());
        final String email = acct.getEmail();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), email, Toast.LENGTH_SHORT).show();
                            userName = email;
                            TripsTable.setUserName(userName);
                            intent = new Intent(SignInFragment.this.getContext(), Home.class);
                            startActivity(intent);
                        } else {
                            Log.i("UNSUCESS", "TRUE");
                            TestConnectionAsyncTask myTest = new TestConnectionAsyncTask(getContext());
                            //No Internet Connection
                            try {
                                if (myTest.execute().get() == false)
                                    Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getContext(), "Duplicated", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public void signIn(final User user) {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            TestConnectionAsyncTask myTest = new TestConnectionAsyncTask(getContext());
                            //No Internet Connection
                            try {
                                if (myTest.execute().get() == false)
                                    Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                                else
                                    invalidEmailOrPassword.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            userName = user.getEmail();
                            TripsTable.setUserName(userName);
                            intent = new Intent(SignInFragment.this.getContext(), Home.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}
