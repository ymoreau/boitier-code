package ymoreau.boitier.data;

import java.util.Locale;
import java.util.Vector;

/**
 * Manages the answers as a singleton object.
 * @author Yoann Moreau
 *
 */
public final class DataModel
{
    private int answersCount;
    private int currentAnswer;
    private Vector<Answer> answers;
    private boolean isInitialized = false;
    private boolean isStarted = false;

    private DataModel()
    {
        answersCount = 0;
    }

    /**
     * Initialize the data model for the given answers count.
     * @param count Number of answers to manage
     */
    public void initialize(int count)
    {
        answersCount = count;
        currentAnswer = 1;

        answers = new Vector<>(answersCount);
        for(int i=0; i<answersCount; ++i)
            answers.add(i, new Answer());

        isInitialized = true;
        isStarted = true;
    }

    public void reset()
    {
        answers = null;
        isInitialized = false;
        isStarted = false;
    }

    public void stop()
    {
        isStarted = false;
    }

    public boolean isStopped()
    {
        return isInitialized && !isStarted;
    }

    public boolean isStarted()
    {
        return isStarted;
    }

    // Data access
    public int answersCount()
    {
        return answersCount;
    }

    public int currentAnswerNumber()
    {
        return currentAnswer;
    }

    public void nextAnswer()
    {
        if(currentAnswer >= answersCount)
            return;
        ++currentAnswer;
    }

    public Answer answer(int i)
    {
        return answers.get(i);
    }

    public Answer currentAnswer()
    {
        return answers.get(currentAnswer-1);
    }

    public int errorsCount()
    {
        int count = 0;
        for(int i=0; i < answersCount; ++i)
        {
            if(!answers.get(i).isCorrect())
                ++count;
        }
        return count;
    }

    public String[] resultStrings()
    {
        String[] results = new String[answersCount];
        for(int i=0; i < answersCount; ++i)
        {
            results[i] = String.format(Locale.FRENCH, "%02d", i+1) + " : " + answers.get(i).toString();
        }
        return results;
    }

    // Singleton pattern
    private static final DataModel instance = new DataModel();
    public static DataModel ref()
    {
        return instance;
    }
}
