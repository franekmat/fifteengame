package franekmat.fifteen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

public class HighScores extends AppCompatActivity
{
    TextView text;
    SharedPreferences bestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.high_scores);


        bestScore = getSharedPreferences("highscores", Context.MODE_PRIVATE);

        text = findViewById(R.id.best_easy);
        text.setText(Html.fromHtml("<b>Easy mode</b>" + "<br>" + "<br>" +
                "The least number of moves: " + String.valueOf(bestScore.getInt("easyMoves", 0)) + "<br>" +
                "The best time: " + String.valueOf(bestScore.getInt("easyTime", 0) / 60) + "m " +
                String.valueOf(bestScore.getInt("easyTime", 0) % 60) + "s "));

        text = findViewById(R.id.best_medium);
        text.setText(Html.fromHtml("<center>" + "<b>Medium mode</b>" + "<br>" + "<br>" +
                "The least number of moves: " + String.valueOf(bestScore.getInt("mediumMoves", 0)) + "<br>" +
                "The best time: " + String.valueOf(bestScore.getInt("mediumTime", 0) / 60) + "m " +
                String.valueOf(bestScore.getInt("mediumTime", 0) % 60) + "s "));

        text = findViewById(R.id.best_hard);
        text.setText(Html.fromHtml("<b>Hard mode</b>" + "<br>" + "<br>" +
                "The least number of moves: " + String.valueOf(bestScore.getInt("hardMoves", 0)) + "<br>" +
                "The best time: " + String.valueOf(bestScore.getInt("hardTime", 0) / 60) + "m " +
                String.valueOf(bestScore.getInt("hardTime", 0) % 60) + "s "));
    }
}