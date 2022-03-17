package com.example.w4_p5;

import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WordGuessingGame {

    private String word;
    private HashMap<String, ArrayList<Integer>> letterCount;
    private final int GUESSES_ALLOWED = 6;
    private int wrongGuesses;
    private String solution;
    private ArrayList<Character> playerLetters;

    public WordGuessingGame(String word, ArrayList<Character> wrongLetters){
        this.word = word.toUpperCase();
        this.letterCount = initLetterCount(word);
        this.wrongGuesses = 0;
        this.solution = initSolution(word);
        this.playerLetters = scramble(word, wrongLetters);

    }

    private String initSolution(String word) {
        String s = "";
        for (char letter: word.toCharArray()){
            s += " ";
        }

        return s;
    }

    private HashMap<String, ArrayList<Integer>> initLetterCount(String word) {

        HashMap<String, ArrayList<Integer>> count = new HashMap<String, ArrayList<Integer>>();
        char [] letters = word.toLowerCase().toCharArray();

        // add the keys
        for (char letter: letters){
            count.put(Character.toString(letter), new ArrayList<Integer>());
        }

        for (int i = 0; i < word.length(); i++){

            char letter = word.charAt(i);

            // add the index of the letter to the list for that key
            count.get(Character.toString(letter)).add(i);
        }

        return count;
    }

    private ArrayList<Character> scramble(String word, ArrayList<Character> wrongLetters) {
        ArrayList<Character> charList = new ArrayList<>();

        //for each char of the word, put it in a list
        for (char c: word.toCharArray()){
            charList.add(c);
        }

        // combine the two lists
        charList.addAll(wrongLetters);

        // shuffle the list
        // https://stackoverflow.com/questions/20588736/how-can-i-shuffle-the-letters-of-a-word
        Collections.shuffle(charList);

        return charList;
    }

    public ArrayList<Character> getPlayerLetters(){
        return this.playerLetters;
    }

    public int updateLetterCount(String letter){
        // -1 indicates a wrong guess
        letter = letter.toLowerCase();
        int result = -1;
        if (!this.letterCount.containsKey(letter)){
            wrongGuesses ++;
        }
        else {
            // checking if all the indexes for this given letter are used up
            if (letterCount.get(letter).isEmpty()){
                wrongGuesses ++;
            }
            else{
                // get the first index in the list assigned to this key
                result = letterCount.get(letter).get(0);

                // remove the number in the list
                letterCount.get(letter).remove(0);
            }
        }

        return result;
    }

    public boolean isGameOver(){
        return wrongGuesses == GUESSES_ALLOWED;
    }


}
