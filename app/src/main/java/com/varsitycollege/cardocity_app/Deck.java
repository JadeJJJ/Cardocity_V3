package com.varsitycollege.cardocity_app;

public class Deck {


    private Integer deckID;
    private String deckName;
    private String chooseCollection; //TODO: change to Collection name
    private Integer totalNumCars;
    private String userID;
    //TODO Need to add a deckID so that they can be linked

    public Deck() {
    }

    public Deck(Integer deckID, String deckName, String chooseCollection, Integer totalNumCars, String userID) {
        this.deckID = deckID;
        this.deckName = deckName;
        this.chooseCollection = chooseCollection;
        this.totalNumCars = totalNumCars;
        this.userID = userID;
    }

    public Integer getDeckID() {return deckID; }

    public void setDeckID(Integer deckID) { this.deckID = deckID; }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public String getChooseCollection() {
        return chooseCollection;
    }

    public void setChooseCollection(String chooseCollection) {
        this.chooseCollection = chooseCollection;
    }

    public Integer getTotalNumCars() {
        return totalNumCars;
    }

    public void setTotalNumCars(Integer totalNumCars) {
        this.totalNumCars = totalNumCars;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
