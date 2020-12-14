package edu.pconnaughton.ca3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class GameStartActivity extends AppCompatActivity {

    Button btnOne, btnTwo, btnThree, btnFour;
    int userNum,sequenceNum,userScore,lastNum;

    int[] usequence = new int[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        Intent intent = getIntent();

       usequence = intent.getIntArrayExtra("sequence");

        btnOne = findViewById(R.id.btnFirstColor);
        btnTwo = findViewById(R.id.btnSecondColor);
        btnThree = findViewById(R.id.btnThirdColor);
        btnFour = findViewById(R.id.btnFourthColor);

        userNum = 0;
        userScore = 0;
        sequenceNum = 0;
        lastNum = 0;

        userScore = getIntent().getIntExtra("userScore",0);


        System.out.println("========");

        for (int x: usequence) {

            System.out.println(x);

            if(x != 0){
                lastNum++;
            }
        }
    }

    public void doBtnClick(View view) {


        switch (view.getId()){
            case R.id.btnFirstColor:
                userNum = 1;
                break;

            case R.id.btnSecondColor:
                userNum = 2;
                break;

            case R.id.btnThirdColor:
                userNum = 3;
                break;

            case R.id.btnFourthColor:
                userNum = 4;
                break;
        }


        if(userNum == usequence[sequenceNum]){
            Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
            userScore++;
        }
        else{

            Toast.makeText(this,"Wrong",Toast.LENGTH_SHORT).show();

            Intent gameOverStartActivity = new Intent(view.getContext(), GameOverActivity.class);

            gameOverStartActivity.putExtra("userScore", userScore);

        startActivity(gameOverStartActivity);

            userScore = 0;
        finish();
        }

        sequenceNum++;

        if(sequenceNum == lastNum && userNum == usequence[lastNum - 1]){

            Intent mainActivity = new Intent(view.getContext(), MainActivity.class);

            mainActivity.putExtra("userScore", userScore);

            mainActivity.putExtra("sequence", usequence);

            startActivity(mainActivity);

            Toast.makeText(this,"Got All 4 Correct",Toast.LENGTH_SHORT).show();
            finish();
        }


    }
}