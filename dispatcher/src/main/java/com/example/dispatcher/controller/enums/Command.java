package com.example.dispatcher.controller.enums;

public enum Command {
    HELP("/help"),
    LIST("/list"),
    ADDBOT("/addBot"),
    LANGUAGE("/language"),
    START("/start"),
    STOP("/stop");
    private final String value;

    Command(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Command fromValue(String v){
        for(Command c: Command.values()){
            if(c.value.equals(v)){
                return c;
            }
        }
        return null;
    }
}
