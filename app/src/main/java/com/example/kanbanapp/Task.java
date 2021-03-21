package com.example.kanbanapp;

import java.io.Serializable;

public class Task implements Serializable {
    private long mDateTime;
    private String mTitle;
    private String mContent;
    private String mStatus;

    public Task(long mDateTime, String mTitle, String mContent, String mStatus) {
        this.mDateTime = mDateTime;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mStatus = mStatus;
    }

    public long getDateTime() {
        return mDateTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setDateTime(long mDateTime) {
        this.mDateTime = mDateTime;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }
}

