# Runtime Analysis
For each method of the tasks give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented new methods not listed you must add these as well, e.g. any helper methods. You need to show how you analyzed any methods used by the methods listed below.**

The runtime should be expressed using these three parameters:
   * `n` - number of words in the list allWords
   * `m` - number of words in the list possibleWords
   * `k` - number of letters in the wordleWords


## Task 1 - matchWord
* `WordleAnswer::matchWord`: O(k) (O(1))
    * *Insert description of why the method has the given runtime*
    In the method we use a hashmap, and all the operations of the hashmap we use have an expected runtime of O(1).
    We also use for-loops, which are not nested, and all run through k, and therefore we get an expected runtime of O(k).
    However, k = 5, and thus we can argument for that this method has an expected runtime of O(1).

## Task 2 - EliminateStrategy
* `WordleWordList::eliminateWords`: O(m*k) (O(m))
    * *Insert description of why the method has the given runtime*
    The method has a for-loop that runs through all the possible answers (m). In the for-loop we call the method isPossibleWord(answer, feedback),
    which in turn calls the method we made in task 1, matchWord O(k). Thus, we get an expected runtime of O(m*k). Here we can again argument that 
    the runtime is only O(m), since k = 5.


## Task 3 - FrequencyStrategy
* `FrequencyStrategy::makeGuess`: O(m*k) (O(m))
    * *Insert description of why the method has the given runtime*
    In makeGuess we have two methods, eliminateWords(WordleWord feedback) and bestStartGuess().
    We know that eliminateWords has a runtime of O(m*k), so we just have to find the runtime of bestStartGuess().
    In bestStartGuess() we call the method frequencyPos() which has a double for-loop which first goes through 
    m and then k. The rest of the method uses operations on a hashmap which all have an expected runtime of O(1).
    Thus, frequencyPos() has a runtime of O(m*k). Other than this bestStartGuess() goes through a double for-loop, 
    the first for-loop goes over m, and the seconds goes over k, which makes the total runtime of bestStartGuess() O(m*k).
    Therefore, since makeGuess calls both bestStartGuess() and eliminateWords(WordleWord feedback), which both have an 
    expected runtime of O(m*k), the expected runtime of makeGuess is O(m*k), which again can be argumentet to be O(m).
    

# Task 4 - Make your own (better) AI
For this task you do not need to give a runtime analysis. 
Instead, you must explain your code. What was your idea for getting a better result? What is your strategy?

*Write your answer here*
