package com.egormoroz.schooly;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.egormoroz.schooly.ui.chat.def.ChatActivity;
import com.egormoroz.schooly.ui.main.EnterFragment;
import com.egormoroz.schooly.ui.main.MainFragment;
import com.egormoroz.schooly.ui.main.MessengerFragment;
import com.egormoroz.schooly.ui.main.NontificationFragment;
import com.egormoroz.schooly.ui.main.RegFragment;
import com.egormoroz.schooly.ui.main.RegisrtationstartFragment;
import com.egormoroz.schooly.ui.news.NewsFragment;
import com.egormoroz.schooly.ui.people.PeopleFragment;
import com.egormoroz.schooly.ui.profile.ProfileFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        AppBarLayout appBarLayout = findViewById(R.id.AppBarLayout);
//        setSupportActionBar(toolbar);

//<<<<<<< Updated upstream
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // notifications
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Notifications", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.nav_top_messages) {
//                    Toast.makeText(getApplicationContext(), "Messages", Toast.LENGTH_LONG).show();
//                }
//                return false;
//            }
//        });
//=======
//>>>>>>> Stashed changes

        CoordinatorLayout fragmentContainer = findViewById(R.id.fragment_container);

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() { // notifications
//            @Override
//            public void onClick(View v) {
//                setCurrentFragment(NontificationFragment.newInstance());
//                CoordinatorLayout.LayoutParams coordinatorLayoutParams = (CoordinatorLayout.LayoutParams) fragmentContainer.getLayoutParams();
//                coordinatorLayoutParams.setBehavior(null);
//            }
//        });
//
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (item.getItemId() == R.id.nav_top_messages) {
//                    setCurrentFragment(MessengerFragment.newInstance());
//                    CoordinatorLayout.LayoutParams coordinatorLayoutParams = (CoordinatorLayout.LayoutParams) fragmentContainer.getLayoutParams();
//                    coordinatorLayoutParams.setBehavior(null);
//                    //   ChatActivity.open(null);
//                }
//                return false;
//            }
//        });

//        TextView toolbarTitle = findViewById(R.id.toolbar_title);

//        toolbarTitle.setText(getString(R.string.app_name));
//        toolbarTitle.setTextColor(getColor(R.color.app_color));
        ///////////Authorization block
        if(IsEntered())
            setCurrentFragment(MainFragment.newInstance());
        else
            RegistrationOrEnter();
        ///////////
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_home:
                        setCurrentFragment(MainFragment.newInstance());
//                        toolbarTitle.setText(getString(R.string.app_name));
//                        toolbarTitle.setTextColor(getColor(R.color.app_color));
//                        appBarLayout.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.bottom_nav_news:
                        setCurrentFragment(NewsFragment.newInstance());
//                        toolbarTitle.setText(getString(R.string.toolbar_news));
//                        toolbarTitle.setTextColor(getColor(R.color.black));
//                        appBarLayout.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.bottom_nav_people:
                        setCurrentFragment(PeopleFragment.newInstance());
//                        toolbarTitle.setText(getString(R.string.toolbar_people));
//                        toolbarTitle.setTextColor(getColor(R.color.black));
//                        appBarLayout.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.bottom_nav_profile:
                        setCurrentFragment(ProfileFragment.newInstance());
//                        appBarLayout.setVisibility(View.GONE);
                        CoordinatorLayout.LayoutParams coordinatorLayoutParams = (CoordinatorLayout.LayoutParams) fragmentContainer.getLayoutParams();
                        coordinatorLayoutParams.setBehavior(null);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_top_nav, menu);
        return true;
    }

    public void setCurrentFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment);
        ft.commit();
    }
    void RegistrationOrEnter(){
        setCurrentFragment(RegisrtationstartFragment.newInstance());
    }
    boolean IsEntered(){
        return false;
    }




}