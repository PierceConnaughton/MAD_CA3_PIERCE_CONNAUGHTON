package edu.pconnaughton.ca3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnOne, btnTwo, btnThree, btnFour;
    Handler handlerUI = new Handler();
    int[] sequence;

    int userScore,userRound;

    TextView tvUserScore,tvRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUserScore = findViewById(R.id.tvScore);
        tvRound = findViewById(R.id.tvRound);

        btnOne = findViewById(R.id.btnFirstColor);
        btnTwo = findViewById(R.id.btnSecondColor);
        btnThree = findViewById(R.id.btnThirdColor);
        btnFour = findViewById(R.id.btnFourthColor);

        userScore = 0;
        userRound = 0;

        userScore = getIntent().getIntExtra("userScore",0);

        userRound = getIntent().getIntExtra("userRound",1);

        tvUserScore.setText("Your Score was " + userScore);
        tvRound.setText("Round " + userRound);
    }

    public void doPlay(View view) throws InterruptedException {

        //array of 20
        sequence = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

        int z = 0;
        int nextFourNum = 0;

        int x;

        if (userScore >= 4) {

            Intent intent = getIntent();

            sequence = intent.getIntArrayExtra("sequence");

            for (int y : sequence) {

                if (y == 0) {

                    for (int m = z; m < z + 4; m++) {
                        x = getRandomNumber();

                        sequence[m] = x;

                        System.out.println(sequence[m]);

                        switch (x) {
                            case 1:
                                setBtnClicked(btnOne);

                                break;

                            case 2:
                                setBtnClicked(btnFour);

                                break;

                            case 3:
                                setBtnClicked(btnThree);

                                break;

                            case 4:
                                setBtnClicked(btnFour);

                                break;
                        }


                    }

                    Intent gameStartActivity = new Intent(view.getContext(), GameStartActivity.class);

                    gameStartActivity.putExtra("sequence", sequence);
                    gameStartActivity.putExtra("userScore", userScore);
                    gameStartActivity.putExtra("userRound", userRound);

                    startActivity(gameStartActivity);

                    finish();

                    return;


                }
                z++;
            }



        } else {
            for (int i = 0; i < 4; i++) {

                x = getRandomNumber();

                sequence[i] = x;

                System.out.println(sequence[i]);

                switch (x) {
                    case 1:
                        setBtnClicked(btnOne);

                        break;

                    case 2:
                        setBtnClicked(btnFour);

                        break;

                    case 3:
                        setBtnClicked(btnThree);

                        break;

                    case 4:
                        setBtnClicked(btnFour);

                        break;
                }
            }


            final Intent gameStartActivity = new Intent(view.getContext(), GameStartActivity.class);

            gameStartActivity.putExtra("sequence", sequence);
            gameStartActivity.putExtra("userRound", userRound);

            handlerUI.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(gameStartActivity);
                }
            }, 2000);
        }
    }



    public int getRandomNumber() {
        int rndNum = (int) ( Math.random() * 4 + 1);
        return rndNum;
    }

    public void setBtnClicked(final Button btn) {
        btn.setPressed(true);

        handlerUI.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn.setPressed(false);
            }
        }, 1000);
    }


}