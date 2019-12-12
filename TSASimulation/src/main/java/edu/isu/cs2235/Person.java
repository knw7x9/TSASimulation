package edu.isu.cs2235;

/**
 * Creates a
 * @author Katherine Wilsdon
 */
public class Person {

    private int inLine = -1;
    private int outOfLine = -1;
    private int personNum;

    /**
     * Creates a Person
     */
    public Person() {
    }

    /**
     * @return the number of minutes the person waited in line
     */
    public int getMinutesInLine() {
        if (outOfLine == -1 || inLine == -1)
            return 0;
        else
            return outOfLine - inLine;
    }

    /**
     * Sets when the person began waiting in line
     * @param inLine The minute the person began waiting in line
     */
    public void setInLine(int inLine) {
        this.inLine = inLine;
    }

    /**
     * Sets when the person exited the line
     * @param outOfLine The minute the person exited in line
     */
    public void setOutOfLine(int outOfLine) {
        this.outOfLine = outOfLine;
    }

    /**
     * Sets the personally identifiable number
     * @param personNum The personally identifiable number
     */
    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }
}
