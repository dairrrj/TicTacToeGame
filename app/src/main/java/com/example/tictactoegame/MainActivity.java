package com.example.tictactoegame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    // Represents the internal state of the game
    private TicTacToeGame mGame;
    // Buttons making up the board
    private Button mBoardButtons[];
    // Various text displayed
    private TextView mInfoTextView;
    // Restart Button
    private Button startButton;
    // Game Over
    Boolean mGameOver;
    // Display result information
    private TextView mUserWin;
    private TextView mAndroidWin;
    private TextView mTies;
    int tie_games = 0;
    int you_win = 0;
    int android_win =0;
    private Button mClearButton;
    private Boolean mWhoFirst;
    public int mLevel = 0;
    public int round1 = 9;
    public int round2 = 9;
    public int round3 = 9;
    private MediaPlayer mediaPlayer;
    private ImageView imageanimation;
    public AnimationDrawable someAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGame = new TicTacToeGame();
        mBoardButtons = new Button[mGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.button0);
        mBoardButtons[1] = (Button) findViewById(R.id.button1);
        mBoardButtons[2] = (Button) findViewById(R.id.button2);
        mBoardButtons[3] = (Button) findViewById(R.id.button3);
        mBoardButtons[4] = (Button) findViewById(R.id.button4);
        mBoardButtons[5] = (Button) findViewById(R.id.button5);
        mBoardButtons[6] = (Button) findViewById(R.id.button6);
        mBoardButtons[7] = (Button) findViewById(R.id.button7);
        mBoardButtons[8] = (Button) findViewById(R.id.button8);
        mInfoTextView = (TextView) findViewById(R.id.information);
        mGame = new TicTacToeGame();
        mUserWin = (TextView) findViewById(R.id.userwin);
        mAndroidWin =(TextView) findViewById(R.id.androidwin);
        mTies =(TextView) findViewById((R.id.ties));
        mClearButton = (Button) findViewById(R.id.button_clear);
        imageanimation = (ImageView)findViewById(R.id.animation_iv);
        startNewGame();
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        switch(id){
            case R.id.level1:
                imageanimation.setBackgroundResource(0);
                startNewGame();
                mLevel = 0;
                Toast.makeText(this,getResources().getString(R.string.easy),
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.level2:
                imageanimation.setBackgroundResource(0);
                startNewGameHarder();
                mLevel = 1;
                Toast.makeText(this,getResources().getString(R.string.harder),
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.level3:
                imageanimation.setBackgroundResource(0);
                startNewGameExpert();
                mLevel = 2;
                Toast.makeText(this,getResources().getString(R.string.expert),
                        Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_exit:
                //finish();
                dialog();
                return true;
            case R.id.menu_msg:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://xkcd.com/832/"));
                startActivity(intent);
                return true;
        }
        //return super.onOptionsItemSelected(item);
        return false;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dialog();
        }
        return false;
    }

    protected  void  dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.sure);
        builder.setTitle(R.string.confirm);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                //MainActivity.this.finish;
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    //--- Set up the game board.
     private void startNewGame() {
         mGameOver = false;
         mGame.clearBoard();
         //---Reset all buttons
         for (int i = 0; i < mBoardButtons.length; i++) {
             mBoardButtons[i].setText("");
             mBoardButtons[i].setEnabled(true);
             mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
         }
         mClearButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 tie_games = 0;
                 you_win = 0;
                 android_win =0;
                 mTies.setTextColor(Color.rgb(0, 0, 200));
                 mTies.setText(getResources().getString(R.string.tie2) + "\n" + tie_games);
                 mUserWin.setTextColor(Color.rgb(0, 200, 0));
                 mUserWin.setText(getResources().getString(R.string.you_win) + "\n" + you_win);
                 mAndroidWin.setTextColor(Color.rgb(200, 0, 0));
                 mAndroidWin.setText(getResources().getString(R.string.android_win) + "\n" + android_win);
             }
         });
         //---Human goes first
         mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
         mInfoTextView.setText(R.string.first);
         mTies.setTextColor(Color.rgb(0, 0, 200));
         mTies.setText(getResources().getString(R.string.tie2) + "\n" + tie_games);
         mUserWin.setTextColor(Color.rgb(0, 200, 0));
         mUserWin.setText(getResources().getString(R.string.you_win) + "\n" + you_win);
         mAndroidWin.setTextColor(Color.rgb(200, 0, 0));
         mAndroidWin.setText(getResources().getString(R.string.android_win) + "\n" + android_win);
     }
     // ---set up harder game
    private void startNewGameHarder(){
        mGameOver = false;
        mGame.clearBoard();
        mWhoFirst = randomBoolean();
        //---Reset all buttons
        if (mWhoFirst == true){
            //human go first
            mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
            mInfoTextView.setText(R.string.first);
            for (int i = 0; i < mBoardButtons.length; i++) {
                mBoardButtons[i].setText("");
                mBoardButtons[i].setEnabled(true);
                mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
            }
        }else{
            //---Android goes first
            mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
            mInfoTextView.setText(R.string.first2);

            for (int i = 0; i < mBoardButtons.length; i++) {
                mBoardButtons[i].setText("");
                mBoardButtons[i].setEnabled(true);
                mBoardButtons[i].setOnClickListener(new ButtonClickListenerHarder(i));
            }
            int move = mGame.getComputerMove();
            setMove(TicTacToeGame.COMPUTER_PLAYER, move);
        }


        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tie_games = 0;
                you_win = 0;
                android_win =0;
                mTies.setTextColor(Color.rgb(0, 0, 200));
                mTies.setText(getResources().getString(R.string.tie2) + "\n" + tie_games);
                mUserWin.setTextColor(Color.rgb(0, 200, 0));
                mUserWin.setText(getResources().getString(R.string.you_win) + "\n" + you_win);
                mAndroidWin.setTextColor(Color.rgb(200, 0, 0));
                mAndroidWin.setText(getResources().getString(R.string.android_win) + "\n" + android_win);
            }
        });
        mTies.setTextColor(Color.rgb(0, 0, 200));
        mTies.setText(getResources().getString(R.string.tie2) + "\n" + tie_games);
        mUserWin.setTextColor(Color.rgb(0, 200, 0));
        mUserWin.setText(getResources().getString(R.string.you_win) + "\n" + you_win);
        mAndroidWin.setTextColor(Color.rgb(200, 0, 0));
        mAndroidWin.setText(getResources().getString(R.string.android_win) + "\n" + android_win);
    }

    // expert
    private void startNewGameExpert(){
        int userlocation;
        int round1;
        int round2;
        int round3;

        Map<String, Integer> map = new HashMap<String, Integer>();
        mGameOver = false;
        mGame.clearBoard();
        //---Reset all buttons
        mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
        mInfoTextView.setText(R.string.first2);

        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListenerExpert(i));
        }
        userlocation = 0;
        round1 = 9;
        round2 = 9;
        round3 = 9;
        map = mGame.getComputerMoveExpert(userlocation,round1,round2,round3);
        int move = map.get("move");
        setMove(TicTacToeGame.COMPUTER_PLAYER, move);

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tie_games = 0;
                you_win = 0;
                android_win =0;
                mTies.setTextColor(Color.rgb(0, 0, 200));
                mTies.setText(getResources().getString(R.string.tie2) + "\n" + tie_games);
                mUserWin.setTextColor(Color.rgb(0, 200, 0));
                mUserWin.setText(getResources().getString(R.string.you_win) + "\n" + you_win);
                mAndroidWin.setTextColor(Color.rgb(200, 0, 0));
                mAndroidWin.setText(getResources().getString(R.string.android_win) + "\n" + android_win);
            }
        });
        mTies.setTextColor(Color.rgb(0, 0, 200));
        mTies.setText(getResources().getString(R.string.tie2) + "\n" + tie_games);
        mUserWin.setTextColor(Color.rgb(0, 200, 0));
        mUserWin.setText(getResources().getString(R.string.you_win) + "\n" + you_win);
        mAndroidWin.setTextColor(Color.rgb(200, 0, 0));
        mAndroidWin.setText(getResources().getString(R.string.android_win) + "\n" + android_win);
    }

    //---Handles clicks on the game board buttons
    private class ButtonClickListener implements View.OnClickListener {
        int location;
        public ButtonClickListener(int location) {
            this.location = location;
        }

        @Override
        public void onClick(View v) {
            if (mGameOver == false) {
                if (mBoardButtons[location].isEnabled()) {
                    setMove(TicTacToeGame.HUMAN_PLAYER, location);
                    Uri uri = Uri.parse("android.resource://"
                            + getPackageName() + "/" + R.raw.gamebutton);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                    mediaPlayer.start();
//--- If no winner yet, let the computer make a move
                    int winner = mGame.checkForWinner();
                    if (winner == 0) {
                        mInfoTextView.setText(R.string.android_turn);
                        int move = mGame.getComputerMove();
                        setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }
                    if (winner == 0) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
                        mInfoTextView.setText(R.string.your_turn);
                    } else if (winner == 1) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 200));
                        mInfoTextView.setText(R.string.tie);
                        tie_games+=1;
                        mTies.setTextColor(Color.rgb(0, 0, 200));
                        mTies.setText(getResources().getString(R.string.tie2) + "\n" + tie_games);
                        imageanimation.setImageResource(R.drawable.finnbmo);
                        mGameOver = true;
                    } else if (winner == 2) {
                        mInfoTextView.setTextColor(Color.rgb(0, 200, 0));
                        mInfoTextView.setText(R.string.win);
                        imageanimation.setBackgroundResource(R.drawable.finn);
                        someAnimation = (AnimationDrawable) imageanimation.getBackground();
                        someAnimation .start();
                        you_win+=1;
                        mUserWin.setTextColor(Color.rgb(0, 200, 0));
                        mUserWin.setText(getResources().getString(R.string.you_win) + "\n" + you_win);
                        mGameOver = true;
                    } else {
                        mInfoTextView.setTextColor(Color.rgb(200, 0, 0));
                        mInfoTextView.setText(R.string.lose);
                        imageanimation.setBackgroundResource(R.drawable.bmo);
                        someAnimation = (AnimationDrawable) imageanimation.getBackground();
                        someAnimation .start();
                        android_win+=1;
                        mAndroidWin.setTextColor(Color.rgb(200, 0, 0));
                        mAndroidWin.setText(getResources().getString(R.string.android_win) + "\n" + android_win);
                        mGameOver = true;
                    }
                }
            }
        }
    }
    private class ButtonClickListenerHarder implements View.OnClickListener {
        int location;
        public ButtonClickListenerHarder (int location) {
            this.location = location;
        }
        @Override
        public void onClick(View v) {
            if (mGameOver == false) {
                if (mBoardButtons[location].isEnabled()) {
                    //int move = mGame.getComputerMove();
                    //setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    setMove(TicTacToeGame.HUMAN_PLAYER, location);
                    Uri uri = Uri.parse("android.resource://"
                            + getPackageName() + "/" + R.raw.gamebutton);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                    mediaPlayer.start();
//--- If no winner yet, let the computer make a move
                    int winner = mGame.checkForWinner();
                    if (winner == 0) {
                        mInfoTextView.setText(R.string.android_turn);
                        int move = mGame.getComputerMove();
                        setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }
                    if (winner == 0) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
                        mInfoTextView.setText(R.string.your_turn);
                    //if (winner == 0) {
                    //    mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
                    //    mInfoTextView.setText(R.string.your_turn);
                    //    setMove(TicTacToeGame.HUMAN_PLAYER, location);
                    //    winner = mGame.checkForWinner();
                    //}
                    //if (winner == 0) {
                    //    mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
                    //    mInfoTextView.setText(R.string.android_turn);
                    } else if (winner == 1) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 200));
                        mInfoTextView.setText(R.string.tie);
                        imageanimation.setImageResource(R.drawable.finnbmo);
                        tie_games+=1;
                        mTies.setTextColor(Color.rgb(0, 0, 200));
                        mTies.setText(getResources().getString(R.string.tie2) + "\n" + tie_games);
                        mGameOver = true;
                    } else if (winner == 2) {
                        mInfoTextView.setTextColor(Color.rgb(0, 200, 0));
                        mInfoTextView.setText(R.string.win);
                        imageanimation.setBackgroundResource(R.drawable.finn);
                        someAnimation = (AnimationDrawable) imageanimation.getBackground();
                        someAnimation .start();
                        you_win+=1;
                        mUserWin.setTextColor(Color.rgb(0, 200, 0));
                        mUserWin.setText(getResources().getString(R.string.you_win) + "\n" + you_win);
                        mGameOver = true;
                    } else {
                        mInfoTextView.setTextColor(Color.rgb(200, 0, 0));
                        mInfoTextView.setText(R.string.lose);
                        imageanimation.setBackgroundResource(R.drawable.bmo);
                        someAnimation = (AnimationDrawable) imageanimation.getBackground();
                        someAnimation .start();
                        android_win+=1;
                        mAndroidWin.setTextColor(Color.rgb(200, 0, 0));
                        mAndroidWin.setText(getResources().getString(R.string.android_win) + "\n" + android_win);
                        mGameOver = true;
                    }
                }
            }
        }
    }

    private class ButtonClickListenerExpert implements View.OnClickListener {
        int location;
        Map<String, Integer> map = new HashMap<String, Integer>();
        public ButtonClickListenerExpert (int location) {
            this.location = location;
        }
        @Override
        public void onClick(View v) {
            if (mGameOver == false) {
                if (mBoardButtons[location].isEnabled()) {
                    //setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    setMove(TicTacToeGame.HUMAN_PLAYER, location);
                    Uri uri = Uri.parse("android.resource://"
                            + getPackageName() + "/" + R.raw.gamebutton);
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                    mediaPlayer.start();
//--- If no winner yet, let the computer make a move
                    int winner = mGame.checkForWinner();
                    if (winner == 0) {
                        mInfoTextView.setText(R.string.android_turn);
                        map = mGame.getComputerMoveExpert(location,round1,round2,round3);
                        int move = map.get("move");
                        round1 = map.get("round1");
                        round2 = map.get("round2");
                        round3 = map.get("round3");
                        //int move = mGame.getComputerMove();
                        setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }
                    if (winner == 0) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 0));
                        mInfoTextView.setText(R.string.your_turn);
                    } else if (winner == 1) {
                        mInfoTextView.setTextColor(Color.rgb(0, 0, 200));
                        mInfoTextView.setText(R.string.tie);
                        imageanimation.setImageResource(R.drawable.finnbmo);
                        tie_games+=1;
                        mTies.setTextColor(Color.rgb(0, 0, 200));
                        mTies.setText(getResources().getString(R.string.tie2) + "\n" + tie_games);
                        mGameOver = true;
                    } else if (winner == 2) {
                        mInfoTextView.setTextColor(Color.rgb(0, 200, 0));
                        mInfoTextView.setText(R.string.win);
                        imageanimation.setBackgroundResource(R.drawable.finn);
                        someAnimation = (AnimationDrawable) imageanimation.getBackground();
                        someAnimation .start();
                        you_win+=1;
                        mUserWin.setTextColor(Color.rgb(0, 200, 0));
                        mUserWin.setText(getResources().getString(R.string.you_win) + "\n" + you_win);
                        mGameOver = true;
                    } else {
                        mInfoTextView.setTextColor(Color.rgb(200, 0, 0));
                        mInfoTextView.setText(R.string.lose);
                        imageanimation.setBackgroundResource(R.drawable.bmo);
                        someAnimation = (AnimationDrawable) imageanimation.getBackground();
                        someAnimation .start();
                        android_win+=1;
                        mAndroidWin.setTextColor(Color.rgb(200, 0, 0));
                        mAndroidWin.setText(getResources().getString(R.string.android_win) + "\n" + android_win);
                        mGameOver = true;
                    }
                }
            }
        }
    }

    public boolean randomBoolean(){
        return Math.random() < 0.5;
    }

    private void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == TicTacToeGame.HUMAN_PLAYER)
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        else
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
    }
    //--- OnClickListener for Restart a New Game Button
    public void newGame(View v) {
        if (mLevel==0){

            imageanimation.setBackgroundResource(0);
            startNewGame();
        }else if (mLevel==1){

            imageanimation.setBackgroundResource(0);
            startNewGameHarder();
        }else if (mLevel==2){

            imageanimation.setBackgroundResource(0);
            startNewGameExpert();
        }
    }
}
