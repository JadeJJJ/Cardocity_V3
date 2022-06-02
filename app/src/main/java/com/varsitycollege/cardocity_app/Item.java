package com.varsitycollege.cardocity_app;

import android.graphics.Bitmap;
import android.media.Image;

public class Item {
    private String serialNumber;
    private String cardName;
    private String cardType;
    private Integer numberOfCards;
    //private Image cardImage;
    private String userID;
    private Bitmap cardImage;

    public Item(String serialNumber, String cardName, String cardType, Integer numberOfCards, Bitmap cardImage, String userID) {
        this.serialNumber = serialNumber;
        this.cardName = cardName;
        this.cardType = cardType;
        this.numberOfCards = numberOfCards;
        this.cardImage = cardImage;
        this.userID = userID;
    }

    public Item() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(Integer numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    public Bitmap getCardImage() {
        return cardImage;
    }

    public void setCardImage(Bitmap cardImage) {
        this.cardImage = cardImage;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
