package ug.sparkpl.momoapi.models;

public class MomoApiError {


    private int code;
    private String message;

    public MomoApiError() {
    }

    public int status() {
        return code;
    }

    public String message() {
        return message;
    }

}
