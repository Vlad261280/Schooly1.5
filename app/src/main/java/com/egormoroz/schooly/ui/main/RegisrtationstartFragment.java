package com.egormoroz.schooly.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.egormoroz.schooly.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

import static com.egormoroz.schooly.ui.main.Authorization.setCurrentFragment;

public class RegisrtationstartFragment extends Fragment {
    public static RegisrtationstartFragment newInstance(){return new RegisrtationstartFragment();}
    Button RegistrationButton;
    Button EnterButton;
    GoogleSignInOptions gso;
    GoogleSignInClient signInClient;
    FirebaseAuth AuthBase;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reg, container, false);
        BottomNavigationView bnv = getActivity().findViewById(R.id.bottomNavigationView);
        bnv.setVisibility(bnv.GONE);
//        AppBarLayout abl = getActivity().findViewById(R.id.AppBarLayout);
//        abl.setVisibility(abl.GONE);
        AuthBase = AuthBase.getInstance();
        RegistrationButton = root.findViewById(R.id.registr);
        EnterButton = root.findViewById(R.id.enter);
        ////////////Is user Logged in
        if(AuthBase.getCurrentUser() != null)
            setCurrentFragment(MainFragment.newInstance());
        //////////


        RegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCurrentFragment(RegFragment.newInstance());
            }
        });
        EnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCurrentFragment(EnterFragment.newInstance());
            }
        });


        return root;
    }
    public void setCurrentFragment(Fragment fragment) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.commit();
    }
}
