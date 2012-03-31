package org.courses.core.command;

import org.courses.core.domain.Person;

/**
 * Created by IntelliJ IDEA.
 * User: stvad
 * Date: 20.03.12
 * Time: 21:42
 * To change this template use File | Settings | File Templates.
 */
public class PersonByIdCommand implements ICommand
{
    int Index;
    Person byId;

    public PersonByIdCommand(int index, Person byId)
    {
        Index = index;
        this.byId = byId;
    }

    public int getIndex()
    {
        return Index;
    }

    public void setIndex(int index)
    {
        Index = index;
    }

    public Person getPerson()
    {
        return byId;
    }

    public void setPerson(Person byId)
    {
        this.byId = byId;
    }

    public int getType()
    {
        return Command.PERSON_BY_ID;
    }
}
