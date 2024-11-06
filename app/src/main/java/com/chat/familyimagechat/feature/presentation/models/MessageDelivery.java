package com.chat.familyimagechat.feature.presentation.models;

public enum MessageDelivery {
    NOT_DELIVERED,
    SENDING,
    SENT,
    DELIVERED,
    READ,
    FAILED,
    RETRYING;

    public String getDescription() {
        switch (this) {
            case NOT_DELIVERED:
                return "Message not delivered";
            case SENDING:
                return "Sending...";
            case SENT:
                return "Message sent";
            case DELIVERED:
                return "Message delivered";
            case READ:
                return "Message read";
            case FAILED:
                return "Delivery failed";
            case RETRYING:
                return "Retrying...";
            default:
                return "";
        }
    }
}