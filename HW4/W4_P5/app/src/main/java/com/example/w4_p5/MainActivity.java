package com.example.w4_p5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private TableRow trSolution;
    private TextView tvChoose;
    private TableRow trPlayerLetters;

    private TextView [] tvSolutionLetters;
    private Button[] btnPlayerLetters;

    private WordGuessingGame game;
    private ArrayList<String> availableWords = new ArrayList<String>();
    private ArrayList<Character> wrongLetters = new ArrayList<Character>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate game
        generateWords();
        game = new WordGuessingGame(availableWords.get(0),wrongLetters);

        image = (ImageView) findViewById(R.id.image);
        trSolution = (TableRow) findViewById(R.id.trSolution);
        tvChoose = (TextView) findViewById(R.id.tvChoose);
        trPlayerLetters = (TableRow) findViewById(R.id.trPlayerLetters);
    }

    private void generateWords(){
        availableWords.add("university");
        availableWords.add("boolean");
        availableWords.add("shakespeare");


        Character [] list = {'h', 'j', 'k', 'l'};
        wrongLetters.addAll(Arrays.asList(list));
    }

    private void createSolutionGUI(String word){
        for (int i = 0; i < word.length(); i++){
            tvSolutionLetters[i] = new TextView(this);
            tvSolutionLetters[i].setText("_");
        }

    }
}