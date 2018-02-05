package com.example.harrold.harrold_subbook2;

/**
 * Created by Harrold on 2018-02-05.
 */

public class Sub {

    private String subName;
    private String subDate;
    private float subCharge;
    private String subComment;

    Sub(String subName, String subDate, float subCharge, String subComment){

        this.subName = subName;
        this.subDate = subDate;
        this.subCharge = subCharge;
        this.subComment = subComment;
    }

    public String toString() {
        return (subName + "\t\t$" + subCharge + "\n"
                + subDate + "\n" + subComment);
    }

    public void setName(String newName) {
        this.subName = newName;
    }

    public void setDate(String newDate) {
        this.subDate = newDate;
    }

    public void setCharge(float newCharge) { this.subCharge = newCharge; }

    public void setSubComment(String newComment) {
        this.subComment = newComment;
    }

    public String getSubName() { return subName; }
    public String getSubDate() { return subDate; }
    public float getSubCharge() { return subCharge; }
    public String getSubComment() { return subComment; }
}
