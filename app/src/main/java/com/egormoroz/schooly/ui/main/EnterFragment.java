package com.egormoroz.schooly.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.egormoroz.schooly.MainActivity;
import com.egormoroz.schooly.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

import static com.egormoroz.schooly.ui.main.Authorization.EnterThrowGoogle;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public class EnterFragment extends Fragment {
    FirebaseAuth AuthBase;
    public static EnterFragment newInstance(){return new EnterFragment();}
    EditText phoneEditText;
    EditText passwordEditText;
    GoogleSignInOptions gso;
    GoogleSignInClient signInClient;
    RelativeLayout GoogleEnter;
    FirebaseAuth AuthenticationBase;
    TextView continueButton;
    int RC_SIGN_IN = 175;
    int GOOGLE_SIGN_IN = 101;
    private static final String TAG = "###########";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_enter, container, false);
        BottomNavigationView bnv = getActivity().findViewById(R.id.bottomNavigationView);
        bnv.setVisibility(bnv.GONE);
//        AppBarLayout abl = getActivity().findViewById(R.id.AppBarLayout);
//        abl.setVisibility(abl.GONE);
        initElements(root);
        ////////////////Init network references
        initFirebase();
        ///////////////Authorization throw google
        GoogleAuthorizationClick();
        /////////////Simple authorization
        PasswordPhoneAuthorizationClick();
        return root;
    }

    public void setCurrentFragment(Fragment fragment) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.commit();
    }
    public void AuthorizationThrowGoogle(){
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        AuthenticationBase.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = AuthenticationBase.getCurrentUser();
                            setCurrentFragment(MainFragment.newInstance());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }
    @Override
    public void onViewCreated(@Nullable View view,@NonNull Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView gotostartreg = view.findViewById(R.id.arrowtostartreg);
        gotostartreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setCurrentFragment(RegisrtationstartFragment.newInstance());
            }
        });
    }
    public void initElements(View root){
        GoogleEnter = root.findViewById(R.id.GoogleEnter);
        phoneEditText = root.findViewById(R.id.egitnick);
        passwordEditText = root.findViewById(R.id.editpassworgenter);
        continueButton = root.findViewById(R.id.next);
    }
    public void initFirebase(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient(getActivity(), gso);
        AuthenticationBase = FirebaseAuth.getInstance();
    }
    public void GoogleAuthorizationClick(){
        GoogleEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthorizationThrowGoogle();
            }
        });
    }
    public void PasswordPhoneAuthorizationClick(){
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordPhoneAuthorization();
            }
        });
    }
    public void PasswordPhoneAuthorization(){
        String phone = String.valueOf(phoneEditText.getText()).trim();
        String password = String.valueOf(passwordEditText.getText()).trim();
        AuthenticationBase.signInWithEmailAndPassword(makeEmail(phone), password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = AuthenticationBase.getCurrentUser();
                            setCurrentFragment(MainFragment.newInstance());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }
    String makeEmail(String phone){
        String email = "schooly";
        for(int i = 1; i < phone.length(); i++)
            email += phone.toCharArray()[i];
        email += "@gmail.com";
        return email;
    }
}
