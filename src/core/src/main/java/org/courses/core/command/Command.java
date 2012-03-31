package org.courses.core.command;

import com.google.gson.Gson;


public class Command
{
    static public final int SAVE_OR_UPDATE_PERSON = 2;
    static public final int DELETE_PERSON = 3;
    static public final int PERSON_BY_ID = 4;
    static public final int ALL_PERSONS = 5;

    private String data;
    private int comandType;

    public Command(ICommand o)
    {
        comandType = o.getType();
        Gson gson = new Gson();
        data = gson.toJson(o);
    }

    public String getStringData()
    {
        return data;
    }

    static public Command deserialize(String s)
    {
        Gson gson = new Gson();
        Command com = gson.fromJson(s, Command.class);
        return com;
    }

    public String serialize()
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getType()
    {
        return comandType;
    }
}
