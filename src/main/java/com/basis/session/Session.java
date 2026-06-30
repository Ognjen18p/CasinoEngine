package com.basis.session;

import com.basis.person.Person;

import java.sql.Date;

public class Session {
    private int personId;
    private int sessionId;
    private long createdAt;
    private long expiresAt;
    private String token;
    private State state;

    public enum State {
        ACTIVE,
        EXPIRED
    }

    public Session() {
    }

    public Session(int personId, long createdAt, long expiresAt, String token, State state) {
        this.personId = personId;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.token = token;
        this.state = state;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}