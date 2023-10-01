package no.uib.inf102.wordle.controller.AI;

import java.util.HashMap;
import java.util.List;
import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;

public class MyAI implements IStrategy{
    private WordleWordList guesses;

    public MyAI() {
        reset();
    }

    @Override
    public String makeGuess(WordleWord feedback) {
        if (feedback != null){
            guesses.eliminateWords(feedback);
        }
        
        return bestStartGuess();
        
    }

    @Override
    public void reset() {
        guesses = new WordleWordList();
    }
    
     /**
     * Finds the frequency of each letter in each position of the possible words
     * @return a hashmap with the frequency of each letter in each position
     */
    private HashMap<Integer,HashMap<Character, Integer>> letterFrequencyPos(){ 
        HashMap<Integer,HashMap<Character, Integer>> letterOccurenceInPos = new HashMap<>();
        for (String word : guesses.possibleAnswers()){
            for (int i = 0; i<word.length(); i++){
                char letter = word.charAt(i);
                if (!letterOccurenceInPos.containsKey(i)){
                    letterOccurenceInPos.put(i, new HashMap<Character, Integer>());
                }
                if (!letterOccurenceInPos.get(i).containsKey(letter)){
                    letterOccurenceInPos.get(i).put(letter, 1);
                }
                else {
                    letterOccurenceInPos.get(i).put(letter, letterOccurenceInPos.get(i).get(letter)+1);
                }
            }
        }
        return letterOccurenceInPos;

    }
    
    /**
     * Finds the word within the possible words which has the highest expected
     * number of green matches.
     * @return the word with the highest expected number of green matches
     */
    private String bestStartGuess(){ 
        HashMap<Integer,HashMap<Character, Integer>> letterOccurenceInPos =  letterFrequencyPos();
        List<String> possibleAnswers = guesses.possibleAnswers();
        String bestGuess = "";
        int bestScore = 0;
        for (String word : possibleAnswers){ 
            int score = 0;
            for (int i = 0; i < word.length(); i++){
                char letter = word.charAt(i);
                if(letterOccurenceInPos.containsKey(i) && letterOccurenceInPos.get(i).containsKey(letter)){
                    score += letterOccurenceInPos.get(i).get(letter);
                }
                
            }
            if (score > bestScore){
                bestScore = score;
                bestGuess = word;
            }
        }
        return bestGuess;

    }

    
}
   

