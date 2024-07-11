package com.example.bmicalculatorapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;

    private TextView userName, userEmail;
    private CircleImageView profilePhoto;

    private ImageView logout, male, female;
    private RelativeLayout maleBG, femaleBG;

    private TextView userHeight;
    private SeekBar seekBar;

    private TextView userWeight;
    private ImageView decrementWeight, incrementWeight;

    private TextView userAge;
    private ImageView decrementAge, incrementAge;

    private Button calculateBMI;


    int weight = 60;
    int age = 50;
    int currerntprogress;
    String height = "170";
    String gender = "0";
    String newWeight = "177";
    String newAge = "60";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI elements
        userName = (TextView) findViewById(R.id.textView_userName);
        userEmail = (TextView) findViewById(R.id.textView_userEmail);
        profilePhoto = (CircleImageView) findViewById(R.id.user_profile_image);
        logout = (ImageView) findViewById(R.id.logout);

        male = (ImageView) findViewById(R.id.maleIcon);
        female = (ImageView) findViewById(R.id.femaleIcon);

        maleBG = (RelativeLayout) findViewById(R.id.maleBG);
        femaleBG = (RelativeLayout) findViewById(R.id.femaleBG);

        userHeight = (TextView) findViewById(R.id.userHeight);
        seekBar = (SeekBar) findViewById(R.id.seekbarForHeight);

        userWeight = (TextView) findViewById(R.id.userWeight);
        decrementWeight = (ImageView) findViewById(R.id.weightDecrement);
        incrementWeight = (ImageView) findViewById(R.id.weightIncrement);

        userAge = (TextView) findViewById(R.id.userAge);
        decrementAge = (ImageView) findViewById(R.id.ageDecrement);
        incrementAge = (ImageView) findViewById(R.id.ageIncrement);

        calculateBMI = (Button) findViewById(R.id.calculateBMI);

        // Retrieve the last signed-in account details if available
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!= null)
        {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Glide.with(this).load(acct.getPhotoUrl()).into(profilePhoto);

            userName.setText(personName);
            userEmail.setText(personEmail);
        }

        // Configure Google Sign-In
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                    signOut(); // Sign out user if confirmed
                });
                builder.setNegativeButton("No", (dialogInterface, i) -> {
                    // do nothing if user cancels
                });

                builder.show();
            }
        });

        // Male icon click listener
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update UI for male selection
                maleBG.setBackgroundColor(Color.parseColor("#D8FAF7"));
                femaleBG.setBackgroundColor(Color.parseColor("#FFFFFF"));
                gender = "Male";
            }
        });

        // Female icon click listener
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update UI for female selection
                femaleBG.setBackgroundColor(Color.parseColor("#D8FAF7"));
                maleBG.setBackgroundColor(Color.parseColor("#FFFFFF"));
                gender = "Female";
            }
        });

        // SeekBar for height change listener
        seekBar.setMax(300);
        seekBar.setProgress(170);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // Update height value and UI text
                currerntprogress = i;
                height = String.valueOf(currerntprogress);
                userHeight.setText(height);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // do nothing when tracking starts
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // do nothing when tracking stops
            }
        });


        // Weight decrement button click listener
        decrementWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Decrease weight and update UI text
                weight = weight - 1 ;
                newWeight = String.valueOf(weight);
                userWeight.setText(newWeight);
            }
        });

        // Weight increment button click listener
        incrementWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase weight and update UI text
                weight = weight + 1 ;
                newWeight = String.valueOf(weight);
                userWeight.setText(newWeight);
            }
        });

        // Age decrement button click listener
        decrementAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Decrease age and update UI text
                age = age - 1 ;
                newAge = String.valueOf(age);
                userAge.setText(newAge);
            }
        });

        // Age increment button click listener
        incrementAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase age and update UI text
                age = age + 1 ;
                newAge = String.valueOf(age);
                userAge.setText(newAge);
            }
        });

        // Calculate BMI button click listener
        calculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // Validate inputs and navigate to result activity
                if(gender.equals("0"))
                {
                    Toast.makeText(HomeActivity.this, "Select Your Gender", Toast.LENGTH_SHORT).show();
                }
                else if(height.equals("0"))
                {
                    Toast.makeText(HomeActivity.this, "Select Your Height", Toast.LENGTH_SHORT).show();
                }
                else if(weight <= 0)
                {
                    Toast.makeText(HomeActivity.this, "Weight should be greater than 0", Toast.LENGTH_SHORT).show();
                }
                else if(age <= 0)
                {
                    Toast.makeText(HomeActivity.this, "Age should be greater than 0", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Start result activity with BMI calculation inputs
                    Intent intent = new Intent(HomeActivity.this, ResultActivity.class);
                    intent.putExtra("gender", gender);
                    intent.putExtra("height", height);
                    intent.putExtra("weight", newWeight);
                    intent.putExtra("age", newAge);
                    startActivity(intent);
                }

            }
        });

    }// end on create

    // Method to sign out the user from Google Sign-In
    private void signOut() {
        gsc.signOut().addOnCompleteListener(this, task -> {
            Toast.makeText(HomeActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
            // Sign out successful, navigate to login activity
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // Close the home activity
        });
    }

}// end public class

