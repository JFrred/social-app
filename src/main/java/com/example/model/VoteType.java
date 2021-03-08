package com.example.model;

public enum VoteType {
    DOWNVOTE(-1),
    UPVOTE(1);

    private final int direction;

    VoteType(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

}