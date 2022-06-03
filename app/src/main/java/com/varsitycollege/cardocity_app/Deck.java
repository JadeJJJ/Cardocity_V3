package com.varsitycollege.cardocity_app;

public class Deck {
    private String deckName;
    private String chooseCollection;
    private Integer totalNumCars;
    private String userID;

    public Deck() {
    }

    public Deck(String deckName, String chooseCollection, Integer totalNumCars, String userID) {
        this.deckName = deckName;
        this.chooseCollection = chooseCollection;
        this.totalNumCars = totalNumCars;
        this.userID = userID;
    }

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
