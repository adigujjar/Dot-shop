package com.developers.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developers.shop.Adapters.MainMenuListViewAdapter;
import com.developers.shop.Fragments.HelpFragment;
import com.developers.shop.Fragments.ProfileFragment;
import com.developers.shop.Fragments.SettingsFragment;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ListView mainMenuList;
    MainMenuListViewAdapter mainMenuListViewAdapter;
    ImageButton cartButton;
    View mainMenu;
    FrameLayout frame;
    FloatingActionButton fab;

    String[] menuCategories = new String[] {
        "Atta & Flour",
        "Dal & Pulses",
        "Rice",
        "Edible Oil & Ghee",
        "Spices & Masala",
        "Salt & Sugar",
        "Tae & Coffee",
        "Biscuit & Cookies",
        "Snacks & Namkeens",
        "Kids Food",
        "Breakfast Cereal",
        "Noodle & Pasta",
        "Ready to Cook Food",
        "Pickle, Sauce & Spreads",
        "Misc, Food Items & Additives",
        "Juices & Drinks",
        "Chocolates & Candies",
        "Dry Fruits",
        "Plastic & Disposales"
    };

    int[] img = new int[] {
            R.drawable.atta,
            R.drawable.dhal_pulses,
            R.drawable.rice,
            R.drawable.sunflower_oil,
            R.drawable.masala,
            R.drawable.salt,
            R.drawable.coffee,
            R.drawable.cookies,
            R.drawable.snacks,
            R.drawable.kidsfood,
            R.drawable.breakfast,
            R.drawable.pasta,
            R.drawable.ready_to_cook,
            R.drawable.sauces_pickles_spreads_dips,
            R.drawable.miscfood,
            R.drawable.drinks,
            R.drawable.candies,
            R.drawable.dryfruit,
            R.drawable.plastic,
    };

    SharedPreferences sharedPreferences;

    CircleImageView profileImage;
    TextView nametxt, emailtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainMenu = findViewById(R.id.main_menu_frame);
        frame = findViewById(R.id.frame);
        mainMenuListViewAdapter = new MainMenuListViewAdapter(this, menuCategories, img);

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        cartButton = findViewById(R.id.cart_btn);
        cartButton.setOnClickListener(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mainMenuList = findViewById(R.id.main_menu);
        mainMenuList.setAdapter(mainMenuListViewAdapter);

        mainMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = mainMenuList.getItemAtPosition(position);
                String str = (String) o;//As you are using Default String Adapter
                BufferedReader bufferedReader = new BufferedReader(new StringReader(str));
                try {
                    Intent intent = new Intent(CustomerActivity.this, SubMenuActivity.class);
                    intent.putExtra("main_menu", bufferedReader.readLine());
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        nametxt = findViewById(R.id.profile_name);
        emailtxt = findViewById(R.id.profile_email);
        profileImage = findViewById(R.id.cus_profile_image);

//        Toast.makeText(this, emailtxt.getText().toString(), Toast.LENGTH_LONG).show();

//        emailtxt.setText(sharedPreferences.getString("email", ""));
//        nametxt.setText(sharedPreferences.getString("name", ""));
//        profileImage.setImageBitmap(decodeImage(sharedPreferences.getString("picture", "")));
    }

    public Bitmap decodeImage(String img) {
        byte[] decode = Base64.decode(img, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
        return bitmap;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            frame.setVisibility(View.GONE);
            mainMenu.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
        }
        if (id == R.id.nav_profile) {
            ProfileFragment frag = new ProfileFragment();
            loadFragment(frag);
        }
        if (id == R.id.nav_cart) {
            startActivity(new Intent(this, CartActivity.class));
        }
        if (id == R.id.nav_logout) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();
            editor.apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("ResourceType")
    public void loadFragment(Fragment fragment){
        frame.setVisibility(View.VISIBLE);
        mainMenu.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.addToBackStack(null);

        ft.replace(R.id.frame, fragment,"Fragment");
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.fab:
                startActivity(new Intent(this, GetShopLocationMapActivity.class));
            break;
            case R.id.cart_btn:
                startActivity(new Intent(this, CartActivity.class));
            break;

            default:
            break;
        }

    }
}
