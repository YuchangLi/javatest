package com.jvm.demo;

public enum State {
    One {
        @Override
        String user() {
            return null;
        }
    };

    abstract String user();
}
