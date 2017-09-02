package com.example.android.pranaycomp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
       implements NavigationView.OnNavigationItemSelectedListener {

    private String mUsername=null;
    private String mUseremail=null;
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(MainActivity.this,"Your logiin friend",Toast.LENGTH_SHORT).show();
                    mUsername=user.getDisplayName();
                    mUseremail=user.getEmail();
                    setUserdata();
                    Log.d("", "onAuthStateChanged: CCIN Email:"+mUseremail+"name: "+mUsername);

                } else {//user signed out
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()
                                            ))
                                    .build(),
                            RC_SIGN_IN);
                }

            }
        };


        Button bt = (Button) findViewById(R.id.signinButton);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, signinpage.class);
                startActivity(it);
            }
        });

        Button bt2 = (Button) findViewById(R.id.complaintButton);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itr = new Intent(MainActivity.this, complaintpage.class);
                startActivity(itr);
            }
        });


    //end of oncreate activity
    }

    private void setUserdata() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            TextView tv = (TextView) findViewById(R.id.navbar_Username);
            try {
                 tv.setText(mUsername);
            } catch (Exception e) {
                Log.d("TAG", "String username wala exception" + e+user.getDisplayName());
            }
            tv = (TextView) findViewById(R.id.navbar_usremail);
            try {
                tv.setText(mUseremail);
            } catch (Exception e) {
                Log.d("TAG", "String email wala exception" + e+user.getProviders()+user.getEmail());
            }

    }
//        ImageView iv=(ImageView)findViewById(R.id.navbar_userphoto);
//        URL newurl = null;
//        try {
//            newurl = new URL(mUserPhoto);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
//        profile_photo.setImageBitmap(mIcon_val);
//        iv.setImageURI(mUserPhoto);


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.complaint_nav_bar) {
            Intent it = new Intent(MainActivity.this, complaintpage.class);
            startActivity(it);

        } else if (id == R.id.navbar_profile) {
            Intent vv = new Intent(MainActivity.this, profilePage.class);
            startActivity(vv);
        } else if (id == R.id.nav_share) {
//             Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
//                     .setMessage(getString(R.string.invitation_message))
//                     .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
//                     .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
//                     .setCallToActionText(getString(R.string.invitation_cta))
//                     .build();
//             startActivityForResult(intent, REQUEST_INVITE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null)
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will

        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout_action_bar) {
            AuthUI.getInstance().signOut(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
