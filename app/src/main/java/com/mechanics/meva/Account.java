package com.mechanics.meva;

/**
 * Created by Justas on 29/05/2017.
 */

public class Account {
    public String ID;
    public String firstName;
    public String lastName;
    public String school;
    public String employer;
    public boolean isBanned;
    public String banMessage;

    public Account(){
        isBanned = false;
        banMessage = null;
    }
}
