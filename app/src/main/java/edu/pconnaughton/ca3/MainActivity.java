package edu.pconnaughton.ca3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnOne, btnTwo, btnThree, btnFour;
    Handler handlerUI = new Handler();
    int[] sequence = new int[20];

    private final int BLUE = 1;
    private final int RED = 2;
    private final int YELLOW = 3;
    private final int GREEN = 4;


    int sequenceCount = 4, n = 0;

    private Object mutex = new Object();
    int arrayIndex = 0;

    View doPlayView;

    int userScore,userRound;

    TextView tvUserScore,tvRound;

    CountDownTimer ct = new CountDownTimer(6000,  1250) {

        public void onTick(long millisUntilFinished) {
            //mTextField.setText("seconds remaining: " + millisUntilFinished / 1500);
            oneButton();
            //here you can have your logic to set text to edittext
        }

        public void onFinish() {
            //mTextField.setText("done!");
            // we now have the game sequence

            for (int i = 0; i < arrayIndex; i++) {

                System.out.println(sequence[i]);

            }

            System.out.println("====");

            Activity mainActivity = new MainActivity();

            Intent gameStartActivity = new Intent(doPlayView.getContext(), GameStartActivity.class);

            gameStartActivity.putExtra("sequence", sequence);
            gameStartActivity.putExtra("userScore", userScore);
            gameStartActivity.putExtra("arrayIndex", arrayIndex);
            gameStartActivity.putExtra("userRound", userRound);


            startActivity(gameStartActivity);

        };
    };

    CountDownTimer ct2 = new CountDownTimer(6000,  2500) {

        public void onTick(long millisUntilFinished) {
            //mTextField.setText("seconds remaining: " + millisUntilFinished / 1500);
            oneButton();
            //here you can have your logic to set text to edittext
        }

        public void onFinish() {
            //mTextField.setText("done!");
            // we now have the game sequence

            for (int i = 0; i < arrayIndex; i++) {

                System.out.println(sequence[i]);
            }

            System.out.println("====");

            Activity mainActivity = new MainActivity();

            Intent gameStartActivity = new Intent(doPlayView.getContext(), GameStartActivity.class);

            gameStartActivity.putExtra("sequence", sequence);
            gameStartActivity.putExtra("userScore", userScore);
            gameStartActivity.putExtra("arrayIndex", arrayIndex);
            gameStartActivity.putExtra("userRound", userRound);


            startActivity(gameStartActivity);
        };
    };

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

        userScore = getIntent().getIntExtra("userScore", 0);

        userRound = getIntent().getIntExtra("userRound", 1);

        arrayIndex = getIntent().getIntExtra("arrayIndex", 0);

        sequence = getIntent().getIntArrayExtra("sequence");

        tvUserScore.setText("Your Score was " + userScore);
        tvRound.setText("Round " + userRound);



    }

    public void doPlay(View view) throws InterruptedException {
        doPlayView = view;
        if(arrayIndex <= 3){
            sequence = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            ct.start();
        }
        else{
            ct2.start();
        }
    }

    public void oneButton(){
        n = getRandomNumber();

        Toast.makeText(this, "Number = " + n, Toast.LENGTH_SHORT).show();

        switch (n) {
            case 1:
                setBtnClicked(btnOne);
                sequence[arrayIndex++] = BLUE;
                break;
            case 2:
                setBtnClicked(btnTwo);
                sequence[arrayIndex++] = RED;
                break;
            case 3:
                setBtnClicked(btnThree);
                sequence[arrayIndex++] = YELLOW;
                break;
            case 4:
                setBtnClicked(btnFour);
                sequence[arrayIndex++] = GREEN;
                break;
            default:
                break;
        }   // end switch
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