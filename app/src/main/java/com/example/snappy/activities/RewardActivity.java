package com.example.snappy.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.snappy.R;
import com.example.snappy.databinding.ActivityRewardBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RewardActivity extends AppCompatActivity {

    ActivityRewardBinding binding;
    private RewardedAd mRewardedAd;
    FirebaseDatabase database;
    String currentUid;
    int coins = 0;
    ProgressBar progressBar;  // Declare ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRewardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance();
        currentUid = FirebaseAuth.getInstance().getUid();

        // Initialize ProgressBar
        progressBar = findViewById(R.id.progressBar);

        // Load Rewarded Ad
        loadAd();

        // Fetch user's coins from Firebase
        database.getReference().child("profiles")
                .child(currentUid)
                .child("coins")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        coins = snapshot.getValue(Integer.class);
                        binding.coins.setText(String.valueOf(coins));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error if needed
                    }
                });

        // C1 reward click (200 coins)
        binding.c1.setOnClickListener(view -> {
            showRewardAd(200, binding.video1Icon);
        });

        // C2 reward click (300 coins)
        binding.c2.setOnClickListener(view -> {
            showRewardAd(300, binding.video2Icon);
        });

        // C3 reward click (400 coins)
        binding.c3.setOnClickListener(view -> {
            showRewardAd(400, binding.video3Icon);
        });

        // C4 reward click (500 coins)
        binding.c4.setOnClickListener(view -> {
            showRewardAd(500, binding.video4Icon);
        });

        // C5 reward click (1000 coins)
        binding.c5.setOnClickListener(view -> {
            showRewardAd(1000, binding.video5Icon);
        });
    }

    // Method to show the rewarded ad and update the coins
    private void showRewardAd(int rewardAmount, ImageView icon) {
        if (mRewardedAd != null) {
            progressBar.setVisibility(View.VISIBLE);  // Show ProgressBar
            Activity activityContext = RewardActivity.this;
            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    loadAd();
                    coins += rewardAmount;
                    database.getReference().child("profiles")
                            .child(currentUid)
                            .child("coins")
                            .setValue(coins);
                    icon.setImageResource(R.drawable.check); // Change icon to show reward

                    // Show message
                    binding.earnedMessage.setText("You earned " + rewardAmount + " coins!");
                    binding.earnedMessage.setVisibility(View.VISIBLE);

                    // Hide message after a few seconds
                    new Handler().postDelayed(() -> binding.earnedMessage.setVisibility(View.GONE), 2000);

                    progressBar.setVisibility(View.GONE);  // Hide ProgressBar
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);  // Hide ProgressBar
            Toast.makeText(RewardActivity.this, "Ads not available at this moment", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to load a rewarded ad
    void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, "ca-app-pub-5164700654034749/3566158185",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mRewardedAd = null;
                        progressBar.setVisibility(View.GONE);  // Hide ProgressBar
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });
    }
}
