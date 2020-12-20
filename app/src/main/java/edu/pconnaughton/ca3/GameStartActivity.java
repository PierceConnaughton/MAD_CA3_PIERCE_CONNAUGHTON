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

    int userNum,sequenceNum,userScore,lastNum,userRound,arrayIndex;

    int[] usequence = new int[20];

    TextView tvRound,tvScore;

    private final double NORTH_MOVE_FORWARD = 9.0;     // upper mag limit
    private final double NORTH_MOVE_BACKWARD = 7.0;      // lower mag limit

    private final double SOUTH_MOVE_FORWARD = 7.0;     // upper mag limit
    private final double SOUTH_MOVE_BACKWARD = 9.0; // lower mag limit

    private final double NORTH_MOVE_FORWARD_MINUS = -7.0;     // upper mag limit
    private final double NORTH_MOVE_BACKWARD_MINUS = -9.0;      // lower mag limit

    private final double SOUTH_MOVE_FORWARD_MINUS = -9.0;     // upper mag limit
    private final double SOUTH_MOVE_BACKWARD_MINUS = -7.0; // lower mag limit


    //look at z coordinates

    private final double WEST_MOVE_FORWARD = -1;     // upper mag limit
    private final double WEST_MOVE_BACKWARD = 0; // lower mag limit

    private final double WEST_MOVE_FORWARD_MINUS = 1;     // upper mag limit
    private final double WEST_MOVE_BACKWARD_MINUS = 0; // lower mag limit

    private final double EAST_MOVE_FORWARD = 1;     // upper mag limit
    private final double EAST_MOVE_BACKWARD = 0; // lower mag limit

    private final double EAST_MOVE_FORWARD_MINUS = -1;     // upper mag limit
    private final double EAST_MOVE_BACKWARD_MINUS = 0; // lower mag limit

    boolean detectNorth = false;      // detect high limit
    boolean detectSouth = false;      // detect high limit
    boolean detectEast = false;      // detect high limit
    boolean detectWest = false;      // detect high limit

    private SensorManager mSensorManager;
    private Sensor mSensor;

    Handler handlerUI = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        tvRound = findViewById(R.id.tvRound);
        tvScore = findViewById(R.id.tvScore);

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
        arrayIndex = getIntent().getIntExtra("arrayIndex",0);

        tvRound.setText("Round " + userRound);
        tvScore.setText("Score " + userScore);

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
        final Button btnEast = findViewById(R.id.btnSecondColor);
        final Button btnWest = findViewById(R.id.btnThirdColor);
        // Can we get a north movement
        // you need to do your own mag calculating

        tvRound.setText(String.valueOf(y));

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

            if ( y > 1.5 && detectEast == false) {
                detectEast = true;
            }
            if (y < 0.5 && detectEast == true) {
                userNum = 2;

                btnEast.setPressed(true);

                handlerUI.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnEast.setPressed(false);
                    }
                }, 1000);

                CheckUserValue(btnEast);

                detectEast = false;
            }

            if (  y < -1.5 && detectWest == false) {
                detectWest = true;
            }
            if ( y > -0.5 && detectWest == true) {
                userNum = 3;

                btnWest.setPressed(true);

                handlerUI.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       btnWest.setPressed(false);
                    }
                }, 1000);

                CheckUserValue(btnWest);

                detectWest = false;
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

            if ( y > WEST_MOVE_FORWARD_MINUS && detectWest == false) {
                detectWest = true;
            }
            if ( y < WEST_MOVE_BACKWARD_MINUS && detectWest == true) {
                userNum = 3;

                btnWest.setPressed(true);

                handlerUI.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnWest.setPressed(false);
                    }
                }, 1000);

                CheckUserValue(btnWest);

                detectWest = false;
            }

            if (y < EAST_MOVE_FORWARD_MINUS && detectEast == false) {
                detectEast = true;
            }
            if(y > EAST_MOVE_BACKWARD_MINUS && detectEast == true) {
                userNum = 4;

                btnEast.setPressed(true);

                handlerUI.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnEast.setPressed(false);
                    }
                }, 1000);

                CheckUserValue(btnEast);

                detectEast = false;
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
            //Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show();
            userScore++;
            tvScore.setText("Score " + userScore);
        }
        else{

            //Toast.makeText(this,"Wrong",Toast.LENGTH_SHORT).show();

            Intent gameOverStartActivity = new Intent(view.getContext(), GameOverActivity.class);

            gameOverStartActivity.putExtra("userScore", userScore);

            gameOverStartActivity.putExtra("userRound", userRound);

            startActivity(gameOverStartActivity);

            userScore = 0;
            finish();
        }

        sequenceNum++;
        if(sequenceNum == 40 && userNum == usequence[39]) {
            //Toast.makeText(this,"Got all correct",Toast.LENGTH_SHORT).show();

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

            mainActivity.putExtra("arrayIndex",arrayIndex);

            userRound++;

            mainActivity.putExtra("userRound", userRound);

            startActivity(mainActivity);

            //Toast.makeText(this,"Round Complete",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}