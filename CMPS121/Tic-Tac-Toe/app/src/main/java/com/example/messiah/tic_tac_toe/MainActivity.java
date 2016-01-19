package com.example.messiah.tic_tac_toe;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // keeps track which turn it is
    boolean player_one = true;

    // keeps track if the game is over
    boolean game_over = false;

    // keeps track of if someone wins
    boolean win = false;

    // keeps track of the scores of the users
    int player_one_score = 0;
    int player_two_score = 0;

    // keeps track of how many turns taken
    int turns_taken = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBoard(View view) {
        // checks if the game is over
        if(game_over) return;

        // changes the background image and tag
        changeImage(view);

        // after the user makes a move, checks if it is a winning move
        checkWin(view);
    }

    public void changeImage(View view) {
        // cast ImageButton ID to ImageButton to change the image it displays
        ImageButton change_image = (ImageButton) view;
        // checks if the spot the user wants to make a move on is valid
        if (change_image.getTag() != null) return;

        // checks if it is player_one or player_two's turn then takes action
        if (player_one) {
            change_image.setImageResource(R.drawable.cross);
            change_image.setTag("X");
            player_one = !player_one;
            ++turns_taken;
        } else {
            change_image.setImageResource(R.drawable.circle);
            change_image.setTag("O");
            player_one = !player_one;
            ++turns_taken;
        }
    }

    public void checkWin(View view) {
        // checks if player won
        checkPlayerWin(view);
        // checks if it is a tie
        checkTie(view);
    }

    public void checkPlayerWin(View view) {
        GridLayout grid = (GridLayout) findViewById(R.id.game_board);
        String[] grid_items = new String[9];
        for(int i = 0; i < grid.getChildCount(); ++i) {
            ImageButton child = (ImageButton) grid.getChildAt(i);
            String tag = (String) child.getTag();
            grid_items[i] = tag;
        }

        for(int i = 0; i < 7; i += 3) {
            if(grid_items[i] != null && grid_items[i+1] != null && grid_items[i+2] != null) {
                if(grid_items[i].equals(grid_items[i+1]) && grid_items[i+1].equals(grid_items[i+2])) {
                    game_over = true;
                    win = true;
                    highLightWin(i, i+1, i+2);
                }
            }
        }

        for(int i = 0; i < 3; ++i) {
            if(grid_items[i] != null && grid_items[i+3] != null && grid_items[i+6] != null) {
                if(grid_items[i].equals(grid_items[i+3]) && grid_items[i+3].equals(grid_items[i+6])) {
                    game_over = true;
                    win = true;
                    highLightWin(i, i+3, i+6);
                }
            }
        }

        for(int i = 0; i < 3; i += 2) {
            if(grid_items[i] != null && grid_items[4] != null && grid_items[8 - i] != null) {
                if(grid_items[i].equals(grid_items[4]) && grid_items[4].equals(grid_items[8 - i])) {
                    game_over = true;
                    win = true;
                    highLightWin(i, 4, 8-i);
                }
            }
        }

        if (win) {
            TextView winView = (TextView) findViewById(R.id.win_text_view);
            if (player_one) {
                winView.setText("O WON!");

                TextView playerTwo = (TextView) findViewById(R.id.player_two_score);
                playerTwo.setText(String.valueOf(++player_two_score));
            } else {
                winView.setText("X WON!");

                TextView playerOne = (TextView) findViewById(R.id.player_one_score);
                playerOne.setText(String.valueOf(++player_one_score));
            }
            winView.setVisibility(winView.VISIBLE);
        }
    }

    public void highLightWin(int one, int two, int three) {
        GridLayout grid = (GridLayout) findViewById(R.id.game_board);
        ImageButton areaOne = (ImageButton) grid.getChildAt(one);
        ImageButton areaTwo = (ImageButton) grid.getChildAt(two);
        ImageButton areaThree = (ImageButton) grid.getChildAt(three);

        if(player_one) {
            areaOne.setImageResource(R.drawable.circle_win);
            areaTwo.setImageResource(R.drawable.circle_win);
            areaThree.setImageResource(R.drawable.circle_win);
        } else {
            areaOne.setImageResource(R.drawable.cross_win);
            areaTwo.setImageResource(R.drawable.cross_win);
            areaThree.setImageResource(R.drawable.cross_win);
        }

    }

    public void checkTie(View view) {
        if(turns_taken == 9 && game_over == false) {
            game_over = true;
            TextView tie = (TextView) findViewById(R.id.tie_text_view);
            tie.setVisibility(tie.VISIBLE);
        }
    }

    public void resetGame(View view) {
        // Gets the parent grid view
        GridLayout grid = (GridLayout) findViewById(R.id.game_board);

        // Loops through each child in the parent grid layout and resets it
        for(int i = 0; i < grid.getChildCount(); ++i) {
            ImageButton child = (ImageButton) grid.getChildAt(i);
            child.setTag(null);
            child.setImageResource(0);
        }

        // sets all the winner/tie text views invisible
        TextView tie = (TextView) findViewById(R.id.tie_text_view);
        tie.setVisibility(tie.INVISIBLE);

        TextView winView = (TextView) findViewById(R.id.win_text_view);
        winView.setText(null);

        // resets the player to player one
        player_one = true;
        // resets turns taken to 0
        turns_taken = 0;
        // resets game over to be false
        game_over = false;
        // changes win to false since game reset
        win = false;
    }

    public void resetScore(View view) {
        player_one_score = 0;
        player_two_score = 0;

        TextView playerTwo = (TextView) findViewById(R.id.player_two_score);
        playerTwo.setText(String.valueOf(player_two_score));

        TextView playerOne = (TextView) findViewById(R.id.player_one_score);
        playerOne.setText(String.valueOf(player_one_score));
    }
}
