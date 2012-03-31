package org.courses.core.command;

import org.courses.core.domain.Person;

/**
 * Created by IntelliJ IDEA.
 * User: stvad
 * Date: 20.03.12
 * Time: 21:36
 * To change this template use File | Settings | File Templates.
 */
public class deletePersonCommand implements ICommand
{
    Person toDel;

    public deletePersonCommand(Person toDel)
    {
        this.toDel = toDel;
    }

    public Person getPerson()
    {
        return toDel;
    }

    public void setPerson(Person toDel)
    {
        this.toDel = toDel;
    }

    public int getType()
    {
        return Command.DELETE_PERSON;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
