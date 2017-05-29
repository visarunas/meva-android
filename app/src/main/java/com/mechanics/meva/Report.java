package com.mechanics.meva;

/**
 * Created by Justas on 29/05/2017.
 */

public class Report {
    public String ID;
    public boolean isRead;
    public String cause;
    public boolean isMarked;
    public Account account;

    public Report(String message){
        isMarked = false;
        isRead = false;
        cause = message;
        ID = "";
        account = new Account();
    }
}
