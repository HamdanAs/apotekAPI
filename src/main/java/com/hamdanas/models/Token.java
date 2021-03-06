package com.hamdanas.models;

import com.google.gson.annotations.Expose;

@SuppressWarnings("serial")
public class Token {
    @Expose
    private String username;

    @Expose
    private long timestamp;

    public Token(){}

    public Token(String username, long timestamp){
        super();
        this.username = username;
        this.timestamp = timestamp;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public long getTimetamp(){
        return timestamp;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }
}
