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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        // Here you can uncomment lines to enable redirection according to clicks from navigation drawer
        // and delete return false to make selection in navigation drawer visible

        if (itemId == R.id.nav_explore) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NEWS_EXPLORE_FRAGMENT_CLASS_NAME()).commit();
            return false;
        } else if (itemId == R.id.nav_add_plan) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TripPlannerFragment()).commit();
        } else if (itemId == R.id.nav_travelling) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccommodationFragment()).commit();
        } else if (itemId == R.id.nav_on_location) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ON_LOCATION_FRAGMENT_CLASS_NAME()).commit();
            return false;
        } else if (itemId == R.id.nav_account) {
            if (LoggedUser.getIsLoggedIn()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserInterfaceFragment()).commit();
            } else {
                Toast.makeText(this, "You are currently not logged in.", Toast.LENGTH_LONG).show();
                return false;
            }
        } else if (itemId == R.id.nav_settings) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SETTINGS_FRAGMENT_CLASS_NAME()).commit();
            return false;
        } else if (itemId == R.id.nav_share) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SHARE_FRAGMENT_CLASS_NAME()).commit();
        } else if (itemId == R.id.nav_contact_support) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SUPPORT_FRAGMENT_CLASS_NAME()).commit();
        }

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