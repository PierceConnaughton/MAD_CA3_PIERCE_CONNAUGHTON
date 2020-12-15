package edu.pconnaughton.ca3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameStartActivity extends AppCompatActivity implements SensorEventListener {

    Button btnOne, btnTwo, btnThree, btnFour;
    int userNum,sequenceNum,userScore,lastNum,userRound;

    int[] usequence = new int[20];

    TextView tvRound;

    private final double NORTH_MOVE_FORWARD = 9.0;     // upper mag limit
    private final double NORTH_MOVE_BACKWARD = 7.0;      // lower mag limit

    private final double SOUTH_MOVE_FORWARD = 7.0;     // upper mag limit
    private final double SOUTH_MOVE_BACKWARD = 9.0; // lower mag limit

    private final double NORTH_MOVE_FORWARD_MINUS = -7.0;     // upper mag limit
    private final double NORTH_MOVE_BACKWARD_MINUS = -9.0;      // lower mag limit

    private final double SOUTH_MOVE_FORWARD_MINUS = -9.0;     // upper mag limit
    private final double SOUTH_MOVE_BACKWARD_MINUS = -7.0; // lower mag limit

    boolean detectNorth = false;      // detect high limit
    boolean detectSouth = false;      // detect high limit


    private SensorManager mSensorManager;
    private Sensor mSensor;

    Handler handlerUI = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        tvRound = findViewById(R.id.tvRound);

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
        userRound = 0;

        userScore = getIntent().getIntExtra("userScore",0);
        userRound = getIntent().getIntExtra("userRound",1);

        tvRound.setText("Round " + userRound);

        System.out.println("========");

        for (int x: usequence) {

            System.out.println(x);

            if(x != 0){
                lastNum++;
            }
        }

        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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

        CheckUserValue(view);

    }

    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];



        final Button btnNorth = findViewById(R.id.btnFirstColor);
        final Button btnSouth = findViewById(R.id.btnFourthColor);
        Button btnEast = findViewById(R.id.btnThirdColor);
        Button btnWest = findViewById(R.id.btnFourthColor);
        // Can we get a north movement
        // you need to do your own mag calculating

        //screen rotated to the left
        if(x > 0){
            if ((x > NORTH_MOVE_FORWARD && z > 0) && (detectNorth == false)) {
                detectNorth = true;
            }
            if ((x < NORTH_MOVE_BACKWARD && z > 0) && (detectNorth == true)) {
                // we have a tilt to the north


                userNum = 1;

                btnNorth.setPressed(true);

                handlerUI.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnNorth.setPressed(false);
                    }
                }, 1000);

                CheckUserValue(btnNorth);

                detectNorth = false;
            }

            if ((x < SOUTH_MOVE_FORWARD && z < 0) && (detectSouth == false)) {
                detectSouth = true;
            }
            if ((x > SOUTH_MOVE_BACKWARD && z < 0) && (detectSouth == true)) {
                // we have a tilt to the north
                userNum = 4;

                btnSouth.setPressed(true);

                handlerUI.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnSouth.setPressed(false);
                    }
                }, 1000);

                CheckUserValue(btnSouth);

                detectSouth = false;
            }
        }

        //screen rotated to the right
        if( x < 0){
            if ((x > NORTH_MOVE_FORWARD_MINUS && z > 0) && (detectNorth == false)) {
                detectNorth = true;
            }
            if ((x < NORTH_MOVE_BACKWARD_MINUS && z > 0) && (detectNorth == true)) {
                // we have a tilt to the north
                userNum = 1;

                btnNorth.setPressed(true);

                handlerUI.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnNorth.setPressed(false);
                    }
                }, 1000);

                CheckUserValue(btnNorth);

                detectNorth = false;
            }

            if ((x < SOUTH_MOVE_FORWARD_MINUS && z < 0) && (detectSouth == false)) {
                detectSouth = true;
            }
            if ((x > SOUTH_MOVE_BACKWARD_MINUS && z < 0) && (detectSouth == true)) {
                // we have a tilt to the north
                userNum = 4;

                btnSouth.setPressed(true);

                handlerUI.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnSouth.setPressed(false);
                    }
                }, 1000);

                CheckUserValue(btnSouth);

                detectSouth = false;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public void CheckUserValue(View view){

        if(userNum == usequence[sequenceNum]){
            Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
            userScore++;
        }
        else{

            Toast.makeText(this,"Wrong",Toast.LENGTH_SHORT).show();

            Intent gameOverStartActivity = new Intent(view.getContext(), GameOverActivity.class);

            gameOverStartActivity.putExtra("userScore", userScore);

            gameOverStartActivity.putExtra("userRound", userRound);

            startActivity(gameOverStartActivity);

            userScore = 0;
            finish();
        }

        sequenceNum++;
        if(sequenceNum == 20 && userNum == usequence[19]) {
            Toast.makeText(this,"Got all correct",Toast.LENGTH_SHORT).show();

            Intent gameOverStartActivity = new Intent(view.getContext(), GameOverActivity.class);

            gameOverStartActivity.putExtra("userScore", userScore);

            gameOverStartActivity.putExtra("userRound", userRound);

            startActivity(gameOverStartActivity);

            userScore = 0;
            finish();
        }

        if(sequenceNum == lastNum && userNum == usequence[lastNum - 1]){

            Intent mainActivity = new Intent(view.getContext(), MainActivity.class);

            mainActivity.putExtra("userScore", userScore);

            mainActivity.putExtra("sequence", usequence);

            userRound++;

            mainActivity.putExtra("userRound", userRound);

            startActivity(mainActivity);

            Toast.makeText(this,"Round Complete",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}