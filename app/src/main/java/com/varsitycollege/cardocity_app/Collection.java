package com.varsitycollege.cardocity_app;

public class Collection {
    private String collectionID;
    private String collectionName;
    private Integer goalItems;
    private String userID;

    public Collection() {
    }

    public Collection(String collectionID, String collectionName, Integer goalItems, String userID) {
        this.collectionID = collectionID;
        this.collectionName = collectionName;
        this.goalItems = goalItems;
        this.userID = userID;
    }

    public String getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(String collectionID) {
        this.collectionID = collectionID;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public Integer getGoalItems() {
        return goalItems;
    }

    public void setGoalItems(Integer goalItems) {
        this.goalItems = goalItems;
    }

    public String StringOut()
    {
        return "ID:\t"+getCollectionID() + "\tName:\t" + getCollectionName() + "\tGoal:\t" + getGoalItems();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
