package com.egormoroz.schooly.ui.profile.settingscomponents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.egormoroz.schooly.MainActivity;
import com.egormoroz.schooly.R;
import com.egormoroz.schooly.ui.profile.SettingsFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NontificationsFragment extends Fragment {
    public static NontificationsFragment newInstance() {
        return new NontificationsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nontificationssettings, container, false);
        BottomNavigationView bnv = getActivity().findViewById(R.id.bottomNavigationView);
        bnv.setVisibility(bnv.GONE);
//        AppBarLayout abl=getActivity().findViewById(R.id.AppBarLayout);
//        abl.setVisibility(abl.GONE);
        return root;
    }

    @Override
    public void onViewCreated(@Nullable View view,@NonNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        ImageView backtosettingsnonts =view.findViewById(R.id.backtosettingsnonts);
        backtosettingsnonts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setCurrentFragment(SettingsFragment.newInstance());
            }
        });

        LinearLayout linearLayoutlichnye=view.findViewById(R.id.lichnye);
        linearLayoutlichnye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutlichnye.setBackground(getResources().getDrawable(R.drawable.cornersnontsred));
            }
        });

        LinearLayout linearLayoutgroups=view.findViewById(R.id.groupsnontifications);
        linearLayoutgroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutgroups.setBackground(getResources().getDrawable(R.drawable.cornersnontsred));
            }
        });

        LinearLayout linearLayoutdowithprofile=view.findViewById(R.id.dowithprofilenonts);
        linearLayoutdowithprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutdowithprofile.setBackground(getResources().getDrawable(R.drawable.cornersnontsred));
            }
        });

//        LinearLayout linearLayoutlichnye1=view.findViewById(R.id.lichnye);
//        linearLayoutlichnye1.OnClickListener(){
//            int counter=1;
//            switch (counter){
//                case 1:
//                    linearLayoutlichnye.setBackground(getResources().getDrawable(R.drawable.cornersnontsred));
//                    counter++;
//                    break;
//                case 2:
//                    linearLayoutlichnye.setBackground(getResources().getDrawable(R.drawable.cornersnontifications));
//                    counter=1;
//                    break;
//            }
//        }

//        LinearLayout linearLayout=view.findViewById(R.id.lichnye);
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int counter=1;
//            switch (counter){
//                case 1:
//                    linearLayout.setBackground(getResources().getDrawable(R.drawable.cornersnontsred));
//                    counter++;
//                    break;
//                case 2:
//                    linearLayout.setBackground(getResources().getDrawable(R.drawable.cornersnontifications));
//                    counter=1;
//                    break;
//            }
//            }
//        });
        }


    }

