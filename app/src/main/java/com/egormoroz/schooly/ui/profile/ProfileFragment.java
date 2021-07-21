package com.egormoroz.schooly.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.egormoroz.schooly.MainActivity;
import com.egormoroz.schooly.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileFragment extends Fragment {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
//        AppBarLayout abl=getActivity().findViewById(R.id.AppBarLayout);
//        abl.setVisibility(abl.GONE);
        BottomNavigationView bnv = getActivity().findViewById(R.id.bottomNavigationView);
        bnv.setVisibility(bnv.VISIBLE);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        TextView pub = view.findViewById(R.id.pub);
//        pub.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                pub.setBackgroundColor(getResources().getColor(R.color.purple_300));
//                pub.setTextColor(getResources().getColor(R.color.white));
//            }
//        });
//
//        TextView quote = view.findViewById(R.id.quote);
//        quote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                quote.setBackgroundColor(getResources().getColor(R.color.purple_300));
//                quote.setTextColor(getResources().getColor(R.color.white));
//            }
//        });

        ImageView imageView = view.findViewById(R.id.settingsIcon);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setCurrentFragment(SettingsFragment.newInstance());
            }
        });

//        FloatingActionButton FAB=view.findViewById(R.id.floating_action_button);
//        FAB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity)getActivity()).setCurrentFragment(GalleryFragment.newInstance());
//            }
//        });

    }

}