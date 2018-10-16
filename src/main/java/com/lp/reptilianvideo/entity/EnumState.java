package com.lp.reptilianvideo.entity;

public enum EnumState
{
    INIT("init."),
    DOWNLOAD("download"),
    FAILED("failed"),
    SUCCESS("success");

    private String state;

    EnumState(String state)
    {
        this.state = state;
    }

    @Override
    public String toString()
    {
        return this.state;
    }
}
