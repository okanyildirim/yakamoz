package com.hof.yakomoz.content;


public class Content {

    private Long id;
    private String name;
    private State state;

    public Content() {
        this.state = State.IN_PROGRESS;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
