package no.uib.inf102.wordle.controller.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
            guesses.eliminateWords(feedback); 
        }
        return bestStartGuess(); 
        
    
    }

    @Override
    public void reset() {
        guesses = new WordleWordList();
    }

    /**
     * Finds the frequency of each letter in the possible words
     * @return a hashmap with the frequency of each letter
     */
    private HashMap<Character, Integer> letterFrequency(){
        HashMap<Character, Integer> letterOccurence = new HashMap<>();
        for (String word : guesses.possibleAnswers()){ 
            for (char letter : word.toCharArray()){ 
                if (!letterOccurence.containsKey(letter)){ 
                    letterOccurence.put(letter, 1); 
                }
                else {
                    letterOccurence.put(letter, letterOccurence.get(letter)+1); 
                }
            }
        }
        return letterOccurence;

    }

    /**
     * Finds the words within the possible words which has the highest expected
     * number of green matches.
     * @return one of the words with the highest expected number of green matches.
     */
    private String bestStartGuess(){ 
        HashMap<Character, Integer> letterOccurence = letterFrequency(); 
        List<String> possibleAnswers = guesses.possibleAnswers(); 
        int bestScore = 0;

        HashMap<Integer, HashSet<Character>> frequency = new HashMap<>();
        for (char letter : letterOccurence.keySet()){ 
            int freq = letterOccurence.get(letter);
            if (frequency.containsKey(freq)){
                frequency.get(freq).add(letter);
            }
            else {
                HashSet<Character> letters = new HashSet<>();
                letters.add(letter);
                frequency.put(freq, letters);
            }

        }
        List<String> startWords = new ArrayList<>();
        List<Integer> frequencyList = new ArrayList<>(frequency.keySet());
        Collections.sort(frequencyList); 
        Collections.reverse(frequencyList); 
        List<Character> letters = new ArrayList<>();
        for (int freq : frequencyList){ 
            letters.addAll(frequency.get(freq)); 
            if(letters.size() == 8){
                break;
            }
        }
        for (String word : possibleAnswers){ 
            List<Character> lettersInWord = checkLettersInWord(word); 
            int score = 0;
            for(int i = 0; i < word.length(); i++){ 
                char letter = lettersInWord.get(i);
                if (letters.contains(letter)){
                    score++;
                }
            }
            if (score == bestScore){
                startWords.add(word);
            }

            else if (score > bestScore){
                startWords.clear();
                startWords.add(word);
                bestScore = score;
            }

        }
        return startWords.get(0);
   
    }

    /**
     * Checks which letters are in the word
     * @param word the word to check
     * @return a list of the letters in the word
     */
    private List<Character> checkLettersInWord(String word) {
        List<Character> letters = new ArrayList<>();
        for (int i = 0; i < word.length(); i++){ 
            char letter = word.charAt(i);
            letters.add(letter);
            
        }
        return letters;
    }
    
}