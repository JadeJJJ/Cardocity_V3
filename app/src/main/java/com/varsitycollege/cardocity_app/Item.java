package com.varsitycollege.cardocity_app;

import java.util.Date;

public class Item {
    private String serialNumber;
    private String cardName;
    private String cardType;
    private Integer numberOfCards;
    //private Image cardImage;
    private String userID;
    private String cardImageLink;
    private String collectionName;
    private String aquireDate;

    public Item(String serialNumber, String cardName, String cardType, Integer numberOfCards, String cardImageLink, String collectionName, String userID, String aquireDate) {
        this.serialNumber = serialNumber;
        this.cardName = cardName;
        this.cardType = cardType;
        this.numberOfCards = numberOfCards;
        this.cardImageLink = cardImageLink;
        this.collectionName = collectionName;
        this.userID = userID;
        this.aquireDate = aquireDate;
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

    public String getCardImageLink() {
        return cardImageLink;
    }

    public void setCardImage(String cardImageLink) {
        this.cardImageLink = cardImageLink;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getAquireDate() {
        return aquireDate;
    }

    public void setAquireDate(String aquireDate) {
        this.aquireDate = aquireDate;
    }
}
