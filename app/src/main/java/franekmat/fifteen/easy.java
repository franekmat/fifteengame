package franekmat.fifteen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.Chronometer;
import android.widget.TextView;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class easy extends AppCompatActivity
{
    private int size = 9;
    private int[] tab = new int[size];
    private Button[] b = new Button[size];
    private Button n, m;
    private int ruchy = 0;
    private Chronometer czas;
    private Boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.easy);

        while (!inversions()) reset();
    }

    private static void shuffleArray(int[] ar)
    {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private void moves (int ile)
    {
        TextView scoreView = findViewById(R.id.moves);
        scoreView.setText("Moves: " + String.valueOf(ile));
    }

    private void reset()
    {
        finished = false;
        czas = findViewById(R.id.chronometer);
        czas.stop();
        czas.setBase(SystemClock.elapsedRealtime());

        for (int i = 0; i < size; i++)
        {
            tab[i] = i;
        }
        shuffleArray(tab);

        b[0] = findViewById(R.id.b0);
        b[1] = findViewById(R.id.b1);
        b[2] = findViewById(R.id.b2);
        b[3] = findViewById(R.id.b3);
        b[4] = findViewById(R.id.b4);
        b[5] = findViewById(R.id.b5);
        b[6] = findViewById(R.id.b6);
        b[7] = findViewById(R.id.b7);
        b[8] = findViewById(R.id.b8);

        for (int i = 0; i < size; i ++)
        {
            b[i].setVisibility(View.VISIBLE);
        }


        for (int i = 0; i < size; i ++)
        {
            b[i].setText(String.valueOf(tab[i]));
            if (tab[i] == 0)
            {
                b[i].setVisibility(View.INVISIBLE);
            }
        }

        ruchy = 0;
        moves(0);

        czas = findViewById(R.id.chronometer);
        czas.start();
    }

    private void win()
    {
        long time = SystemClock.elapsedRealtime() - czas.getBase();
        int minutes = (int) time / 60000;
        int seconds = (int) (time - minutes * 60000) / 1000;
        Boolean newBest = false;

        SharedPreferences bestScore = getSharedPreferences("highscores", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = bestScore.edit();
        int currentTime = bestScore.getInt("easyTime", 0);
        if (minutes * 60 + seconds < currentTime || currentTime == 0)
        {
            editor.putInt("easyTime", minutes * 60 + seconds);
            editor.commit();
            newBest = true;
        }
        int currentMoves = bestScore.getInt("easyMoves", 0);
        if (ruchy < currentMoves || currentMoves == 0)
        {
            editor.putInt("easyMoves", ruchy);
            editor.commit();
            newBest = true;
        }

        AlertDialog alertDialog = new AlertDialog.Builder(easy.this).create();
        alertDialog.setTitle("Congratulations!");

        if (!newBest)
        {
            alertDialog.setMessage("You have solved puzzle in " + String.valueOf(ruchy) + " moves!" +
                    "\n" + "Time: " + String.valueOf(minutes) + "m " + String.valueOf(seconds) + "s");
        }
        else
        {
            alertDialog.setMessage("You have solved puzzle in " + String.valueOf(ruchy) + " moves!" +
                    "\n" + "Time: " + String.valueOf(minutes) + "m " + String.valueOf(seconds) + "s" +
                    "\n" + "New high score!");
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

        czas = findViewById(R.id.chronometer);
        czas.stop();

        finished = true;
    }

    private Boolean inversions()
    {
        int w = 0;

        for (int i = 0; i < size; i++)
        {
            for (int j = i + 1; j < size; j++)
            {
                if (tab[j] != 0 && tab[i] > tab[j]) w++;
            }
        }

        if (w % 2 == 0) return true;
        else return false;
    }

    private Boolean check()
    {
        Boolean solved = true;

        //empty top left
        for (int i = 1; i < size; i++)
        {
            if (tab[i] != i)
            {
                solved = false;
                break;
            }
        }
        if (solved) return true;

        //empty bottom right
        for (int i = 0; i < size - 1; i++)
        {
            if (tab[i] != i + 1) return false;
        }
        return true;
    }

    private void zamien (Button p1, Button p2) //p1 - normalny kawałek, p2 - pusty
    {
        String temp = p1.getText().toString();
        p1.setText(p2.getText().toString());
        p2.setText(temp);

        p1.setVisibility(View.INVISIBLE);
        p2.setVisibility(View.VISIBLE);
    }

    private void slide (int poz)
    {
        if (tab[poz] != 0)
        {
            if (poz % 3  < 2  && tab[poz + 1] == 0)
            {
                int tmp = tab[poz];
                tab[poz] = tab[poz + 1];
                tab[poz + 1] = tmp;

                n = b[poz];
                m = b[poz + 1];

                zamien(n, m);

                ruchy++;
                moves(ruchy);
            }
            else if (poz % 3  > 0  && tab[poz - 1] == 0)
            {
                int tmp = tab[poz];
                tab[poz] = tab[poz - 1];
                tab[poz - 1] = tmp;

                n = b[poz];
                m = b[poz - 1];

                zamien(n, m);

                ruchy++;
                moves(ruchy);
            }
            else if (poz > 2  && tab[poz - 3] == 0)
            {
                int tmp = tab[poz];
                tab[poz] = tab[poz - 3];
                tab[poz - 3] = tmp;

                n = b[poz];
                m = b[poz - 3];

                zamien(n, m);

                ruchy++;
                moves(ruchy);
            }
            else if (poz < 6  && tab[poz + 3] == 0)
            {
                int tmp = tab[poz];
                tab[poz] = tab[poz + 3];
                tab[poz + 3] = tmp;

                n = b[poz];
                m = b[poz + 3];

                zamien(n, m);

                ruchy++;
                moves(ruchy);
            }
        }
    }

    public void brand(View view)
    {
        reset();
        while (!inversions()) reset();
    }

    public void click0 (View view)
    {
        if (finished) return;
        slide(0);
        if (check()) win();
    }
    public void click1 (View view)
    {
        if (finished) return;
        slide(1);
        if (check()) win();
    }
    public void click2 (View view)
    {
        if (finished) return;
        slide(2);
        if (check()) win();
    }
    public void click3 (View view)
    {
        if (finished) return;
        slide(3);
        if (check()) win();
    }
    public void click4 (View view)
    {
        if (finished) return;
        slide(4);
        if (check()) win();
    }
    public void click5 (View view)
    {
        if (finished) return;
        slide(5);
        if (check()) win();
    }
    public void click6 (View view)
    {
        if (finished) return;
        slide(6);
        if (check()) win();
    }
    public void click7 (View view)
    {
        if (finished) return;
        slide(7);
        if (check()) win();
    }
    public void click8 (View view)
    {
        if (finished) return;
        slide(8);
        if (check()) win();
    }
}

