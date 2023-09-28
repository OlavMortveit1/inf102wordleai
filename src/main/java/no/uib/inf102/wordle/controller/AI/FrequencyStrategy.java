package no.uib.inf102.wordle.controller.AI;

import java.util.HashMap;
import java.util.List;


import no.uib.inf102.wordle.model.word.WordleWord;
import no.uib.inf102.wordle.model.word.WordleWordList;


/**
 * This strategy finds the word within the possible words which has the highest expected
 * number of green matches.
 */
public class FrequencyStrategy implements IStrategy {

    private WordleWordList guesses;

    public FrequencyStrategy() {
        reset();
    }

    @Override
    public String makeGuess(WordleWord feedback) {
        if (feedback != null){
            guesses.eliminateWords(feedback); //O(m*k)
        }
        return bestStartGuess(); //O(m*k)
        
    
    }

    @Override
    public void reset() {
        guesses = new WordleWordList();
    }

    /**
     * Finds the frequency of each letter in each position of the possible words
     * @return
     */
    private HashMap<Integer,HashMap<Character, Integer>> letterFrequencyPos(){ //O(m*k)
        HashMap<Integer,HashMap<Character, Integer>> letterOccurenceInPos = new HashMap<>();
        for (String word : guesses.possibleAnswers()){ //O(m)
            for (int i = 0; i<word.length(); i++){ //O(k)
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
     * @return
     */
    private String bestStartGuess(){ //O(m*k)
        HashMap<Integer,HashMap<Character, Integer>> letterOccurenceInPos =  letterFrequencyPos(); //O(m*k)
        List<String> possibleAnswers = guesses.possibleAnswers(); 
        String bestGuess = "";
        int bestScore = 0;
        for (String word : possibleAnswers){ //O(m)
            int score = 0;
            for (int i = 0; i < word.length(); i++){ //O(k)
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