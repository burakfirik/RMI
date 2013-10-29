package com.pk.bean;

import java.io.Serializable;

public class FileResponse implements Serializable {
    private boolean status;
    private String message;

    public FileResponse() {

    }

    public FileResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
