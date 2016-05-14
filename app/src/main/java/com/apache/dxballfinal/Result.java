package com.apache.dxballfinal;

/**
 * Created by apache on 12-31-2015.
 */
public class Result {

    String win = "";
    String lost = "";
    String result = "";

    public void Result() {
        win = "You Won! Stage Completed! \n";
        lost = "You Lost! Try Again \n";
        result = "Point:";
    }

    public String showMsgWin() {

        return win;
    }

    public String showMsgLost() {

        return lost;
    }

    public String showMsgResult() {
        return result;
    }
}
