package com.egormoroz.schooly.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.egormoroz.schooly.R;
import com.egormoroz.schooly.ui.profile.settingscomponents.SupportFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

public interface Authorization {
    ///// Changing Fragment function
    //Parameters: 1) New Fragment
    //            2) FragmentManager
    static void setCurrentFragment(Fragment fragment, FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.commit();
    }
    //// Function for authorization throw Google,
    //Parameters: 1) User id --- unique for each user
    //            2) Authorization database
    //            3) Current Activity --- I don't know for but it necessary
    //            4) Fragment manger --- for fragments changing
    static void EnterThrowGoogle(String accountId, FirebaseAuth AuthBase, Activity act, FragmentManager fm){
        AuthCredential credential = GoogleAuthProvider.getCredential(accountId, null);
        AuthBase.signInWithCredential(credential)
                .addOnCompleteListener(act, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("#####", "signInWithCredential:success");
                            FirebaseUser user = AuthBase.getCurrentUser();
                            setCurrentFragment(MainFragment.newInstance(), fm);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("####", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }
    default boolean isPasswordCorrect(String password){
        boolean digits = false, characters = true;
        for(char c : password.toCharArray()){
            if(isDigit(c))
                digits = true;
            else if(isLetter(c))
                characters = true;
            else
                return false;
        }
        return digits && characters && password.length() > 6;
    }
}
