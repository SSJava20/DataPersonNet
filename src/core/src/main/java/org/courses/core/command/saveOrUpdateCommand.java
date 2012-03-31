package org.courses.core.command;

import org.courses.core.domain.Person;

/**
 * Created by IntelliJ IDEA.
 * User: stvad
 * Date: 20.03.12
 * Time: 21:25
 * To change this template use File | Settings | File Templates.
 */
public class saveOrUpdateCommand implements ICommand
{
    private Person toSend;

    public saveOrUpdateCommand(Person ts)
    {
        toSend = ts;
    }

    public int getType()
    {
        return Command.SAVE_OR_UPDATE_PERSON;
    }

    public Person getPerson()
    {
        return toSend;
    }

    public void setPerson(Person toSend)
    {
        this.toSend = toSend;
    }
}
