package io.github.sharelison.jsontojava.exception;

public class JsonToJavaException extends RuntimeException{

    public JsonToJavaException(String msg){
        super(msg);
    }
    public JsonToJavaException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
