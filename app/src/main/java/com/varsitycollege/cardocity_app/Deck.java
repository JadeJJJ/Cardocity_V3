package com.varsitycollege.cardocity_app;

public class Deck {
    private String deckName;
    private String chooseCollection;
    private Integer totalNumCars;

    public Deck() {
    }

    public Deck(String deckName, String chooseCollection, Integer totalNumCars) {
        this.deckName = deckName;
        this.chooseCollection = chooseCollection;
        this.totalNumCars = totalNumCars;
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

}
