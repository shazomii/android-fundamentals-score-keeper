package com.davenet.scorekeeper;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.davenet.scorekeeper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ScoreViewModel mViewModel;
    private ActivityMainBinding mBinding;
    public static final String TAG = "onChanged";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);

        mBinding.setScoreViewModel(mViewModel);

        mBinding.setLifecycleOwner(this);

        // TODO [1] Add Game Over implementation using the observer pattern.
        mViewModel.eventScoreEqual().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean scoreEqual) {
                if (scoreEqual) {
                    Toast.makeText(MainActivity.this, "You're tied! Who will go ahead?", Toast.LENGTH_SHORT).show();
                    setColor(Color.RED, Color.RED);
                }
            }
        });

        mViewModel.eventOneAhead().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean oneAhead) {
                if (oneAhead) {
                    Toast.makeText(MainActivity.this, "Team One is ahead!", Toast.LENGTH_SHORT).show();
                    setColor(Color.GREEN, Color.GRAY);
                }
            }
        });

        mViewModel.eventTwoAhead().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean twoAhead) {
                if (twoAhead) {
                    Toast.makeText(MainActivity.this, "Team Two has taken the lead!", Toast.LENGTH_SHORT).show();
                    setColor(Color.GRAY, Color.GREEN);
                }
            }
        });

        mViewModel.teamOneWin().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hasWon) {
                if (hasWon) {
                    Toast.makeText(MainActivity.this, "Team One has won this round!", Toast.LENGTH_SHORT).show();
                    setVisible(View.INVISIBLE, View.VISIBLE);
                    setWinner(mBinding.score1, mBinding.score2);
                    setColor(Color.GREEN, Color.RED);
                }
            }
        });

        mViewModel.teamTwoWin().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean hasWon) {
                if (hasWon) {
                    Toast.makeText(MainActivity.this, "Team Two has won this round!", Toast.LENGTH_SHORT).show();
                    setVisible(View.INVISIBLE, View.VISIBLE);
                    setWinner(mBinding.score2, mBinding.score1);
                    setColor(Color.RED, Color.GREEN);
                }
            }
        });

        mViewModel.eventPlayAgain().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean playAgain) {
                if (playAgain) {
                    setVisible(View.VISIBLE, View.INVISIBLE);
                    mViewModel.onResetGameComplete();
                }
            }
        });
    }

    private void setColor(int color1, int color2) {
        mBinding.score1.setTextColor(color1);
        mBinding.score2.setTextColor(color2);
    }

    private void setWinner(TextView p, TextView p2) {
        p.setText(R.string.win);
        p2.setText(R.string.lose);
    }

    private void setVisible(int invisible, int visible) {
        mBinding.decreaseTeam1.setVisibility(invisible);
        mBinding.decreaseTeam2.setVisibility(invisible);
        mBinding.increaseTeam1.setVisibility(invisible);
        mBinding.increaseTeam2.setVisibility(invisible);
        mBinding.buttonAgain.setVisibility(visible);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // Change the menu label based on the state of the app.
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else {
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Check if the correct item was clicked
        if (item.getItemId() == R.id.night_mode) {
            // Get the night mode state of the app
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            // Set the theme mode for the restarted activity
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            // Recreate the activity for the theme changes to take effect.
            recreate();
        }
        return true;
    }
}