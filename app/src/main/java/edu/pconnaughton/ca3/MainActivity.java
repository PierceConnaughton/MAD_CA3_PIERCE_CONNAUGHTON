package edu.pconnaughton.ca3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnOne, btnTwo, btnThree, btnFour;
    Handler handlerUI = new Handler();
    int[] sequence = new int[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOne = findViewById(R.id.btnFirstColor);
        btnTwo = findViewById(R.id.btnSecondColor);
        btnThree = findViewById(R.id.btnThirdColor);
        btnFour = findViewById(R.id.btnFourthColor);
    }

    public void doPlay(View view) throws InterruptedException {

        int x;

        for (int i=0; i<4; i++) {

            x  = getRandomNumber();

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

        handlerUI.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(gameStartActivity);
            }
        }, 2000);
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