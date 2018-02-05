package com.example.harrold.harrold_subbook2;

/**
 * Created by Harrold on 2018-02-05.
 *
 * Version 1.0
 *
 * This class contains information for a subscription
 *  - subName is the name of the subscription
 *  - subDate is the date the subscription was started
 *  - subCharge is the monthly charge for the subscription
 *  - subComment is any comment left by user
 */

public class Sub {

    private String subName;
    private String subDate;
    private String subCharge;
    private String subComment;

    Sub(String subName, String subDate, String subCharge, String subComment){

        this.subName = subName;
        this.subDate = subDate;
        this.subCharge = subCharge;
        this.subComment = subComment;
    }

    /**
     * This will return a specified string value of the Sub when called.
     *
     * @return, returns block of String.
     */
    public String toString() {
        return (subName + "\t\t$ " + subCharge + "\n"
                + subDate + "\n" + subComment);
    }

    /**
     * Setters for all attributes
     *
     */
    public void setName(String newName) {
        this.subName = newName;
    }

    public void setDate(String newDate) {
        this.subDate = newDate;
    }

    public void setCharge(String newCharge) { this.subCharge = newCharge; }

    public void setSubComment(String newComment) {
        this.subComment = newComment;
    }

    /**
     * Getters for all attributes.
     *
     * @return: returns respective attribute values.
     */
    public String getSubName() { return subName; }
    public String getSubDate() { return subDate; }
    public String getSubCharge() { return subCharge; }
    public String getSubComment() { return subComment; }
}
