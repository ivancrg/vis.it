package com.example.visit;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        // Without toolbar
        toolbar.setVisibility(View.GONE);

        //With toolbar
        //setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.main_drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        addFragment();
    }

    // Method used for changing MainActivity's fragments inside of the R.id.fragment_container view
    // with the possibility to add removed fragments to stack
    public static void changeFragment(FragmentManager fragmentManager, Fragment fragment, boolean addToBackStack){
        if(addToBackStack)
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        else
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        // Here you can uncomment lines to enable redirection according to clicks from navigation drawer
        // and delete return false to make selection in navigation drawer visible

        if (itemId == R.id.nav_explore) {
            //changeFragment(getSupportFragmentManager(), new NEWS_EXPLORE_FRAGMENT_CLASS_NAME(), true);
            return false;
        }
        else if (itemId == R.id.nav_add_plan) {
            if (LoggedUser.getIsLoggedIn()) {
                changeFragment(getSupportFragmentManager(), new TripPlannerFragment(), true);
            } else {
                Toast.makeText(this, "You are currently not logged in.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else if (itemId == R.id.nav_travelling) {
            changeFragment(getSupportFragmentManager(), new TravellingFragment(), true);
        }
        else if (itemId == R.id.nav_on_location) {
            //changeFragment(getSupportFragmentManager(), new ON_LOCATION_FRAGMENT_CLASS_NAME(), true);
            return false;
        }
        else if (itemId == R.id.nav_account) {
            if (LoggedUser.getIsLoggedIn()) {
                changeFragment(getSupportFragmentManager(), new UserInterfaceFragment(), true);
            } else {
                Toast.makeText(this, "You are currently not logged in.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else if (itemId == R.id.nav_settings) {
            //changeFragment(getSupportFragmentManager(), new SETTINGS_FRAGMENT_CLASS_NAME(), true);
            return false;
        }
        else if (itemId == R.id.nav_share) {
            //changeFragment(getSupportFragmentManager(), new SHARE_FRAGMENT_CLASS_NAME(), true);
            return false;
        }
        else if (itemId == R.id.nav_contact_support) {
            //changeFragment(getSupportFragmentManager(), new SUPPORT_FRAGMENT_CLASS_NAME(), true);
            return false;
        }

        // Automatically hides navigation drawer after clicking a link
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    public void addFragment() {
        LoginFragment fragment = new LoginFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}