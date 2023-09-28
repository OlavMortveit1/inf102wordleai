package no.uib.inf102.wordle.model.word;

import java.util.HashMap;
import java.util.Random;

import no.uib.inf102.wordle.resources.GetWords;

/**
 * This class represents an answer to a Wordle puzzle.
 * 
 * The answer must be one of the words in the LEGAL_WORDLE_LIST.
 */
public class WordleAnswer {

    private final String WORD;

    private static Random random = new Random();

    /**
     * Creates a WordleAnswer object with a given word.
     * @param answer
     */
    public WordleAnswer(String answer) {
        this.WORD = answer.toLowerCase();
    }

    /**
     * Creates a WordleAnswer object with a random word from the answer word list
     */
    public WordleAnswer() {
        this(random);
    }

    /**
     * Creates a WordleAnswer object with a random word from the answer word list
     * using a specified random object.
     * This gives us the opportunity to set a seed so that tests are repeatable.
     */
    public WordleAnswer(Random random) {
        this(getRandomWordleAnswer(random));
	}

    /**
     * Gets a random wordle answer
     * 
     * @param random
     * @return
     */
    private static String getRandomWordleAnswer(Random random) {
        int randomIndex = random.nextInt(GetWords.ANSWER_WORDS_LIST.size());
        String newWord = GetWords.ANSWER_WORDS_LIST.get(randomIndex);
        return newWord;
    }

    /**
     * Guess the Wordle answer. Checks each character of the word guess and gives
     * feedback on which that is in correct position, wrong position and which is
     * not in the answer word.
     * This is done by updating the AnswerType of each WordleCharacter of the
     * WordleWord.
     * 
     * @param wordGuess
     * @return wordleWord with updated answertype for each character.
     */
    public WordleWord makeGuess(String wordGuess) {
        if (!GetWords.isLegalGuess(wordGuess))
            throw new IllegalArgumentException("The word '" + wordGuess + "' is not a legal guess");

        WordleWord guessFeedback = matchWord(wordGuess, WORD);
        return guessFeedback;
    }

    /**
     * Generates a WordleWord showing the match between <code>guess</code> and
     * <code>answer</code>
     * 
     * @param guess
     * @param answer
     * @return
     */
    public static WordleWord matchWord(String guess, String answer) { 
        int wordLength = answer.length();
        if (guess.length() != wordLength)
            throw new IllegalArgumentException("Guess and answer must have same number of letters but guess = " + guess
                    + " and answer = " + answer);

        HashMap<Character, Integer> remainingOccurance = new HashMap<Character, Integer>();
        for (char letter : answer.toCharArray()){ 
            remainingOccurance.put(letter, remainingOccurance.getOrDefault(letter, 0) +1); 
        }

        AnswerType[] feedback = new AnswerType[5];
        for (int i=0; i<wordLength; i++) { 
            char guessLetter = guess.charAt(i);
            char answerLetter = answer.charAt(i);
            if(guessLetter == answerLetter){
                feedback[i] = AnswerType.CORRECT;
                remainingOccurance.put(guessLetter, remainingOccurance.get(guessLetter)-1);
            }}
        for (int j = 0; j<wordLength; j++) { 
            char guessLetter = guess.charAt(j);
            if(feedback[j] == null && remainingOccurance.containsKey(guessLetter) && remainingOccurance.get(guessLetter)>0){
                feedback[j] = AnswerType.WRONG_POSITION;
                remainingOccurance.put(guessLetter, remainingOccurance.get(guessLetter)-1);
        }
         else if(feedback[j] == null) {
                feedback[j] = AnswerType.WRONG;
            }     
        }        
        

        return new WordleWord(guess, feedback);
    }
}
