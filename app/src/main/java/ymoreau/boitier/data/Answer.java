package ymoreau.boitier.data;
import androidx.annotation.NonNull;

/**
 * Holds an answer attributes.
 * @author Yoann Moreau
 */
public final class Answer
{
    private boolean A;
    private boolean B;
    private boolean C;
    private boolean D;
    private boolean isCorrect;

    public Answer()
    {
        A = false;
        B = false;
        C = false;
        D = false;
        isCorrect = true;
    }

    public void setA()
    { A = true; }

    public void setB()
    { B = true; }

    public void setC()
    { C = true; }

    public void setD()
    { D = true; }

    public boolean A()
    { return A; }

    public boolean B()
    { return B; }

    public boolean C()
    { return C; }

    public boolean D()
    { return D; }

    public void clear()
    {
        A = false;
        B = false;
        C = false;
        D = false;
    }

    public boolean isEmpty()
    {
        return !A && !B && !C && !D;
    }

    public void setCorrect(boolean correct)
    { isCorrect = correct; }

    public boolean isCorrect()
    { return isCorrect; }

    @Override @NonNull
    public String toString()
    {
        StringBuilder s = new StringBuilder();

        if(A) s.append("A ");
        if(B) s.append("B ");
        if(C) s.append("C ");
        if(D) s.append("D");

        return s.toString().trim();
    }
}
