package org.courses.core.command;

import java.util.Collection;

import org.courses.core.domain.Person;


/**
 * Created by IntelliJ IDEA.
 * User: stvad
 * Date: 20.03.12
 * Time: 21:46
 * To change this template use File | Settings | File Templates.
 */
public class AllPersonsCommand implements ICommand
{
    Collection<Person> Persons;

    public AllPersonsCommand(Collection<Person> persons)
    {
        Persons = persons;
    }

    public Collection<Person> getPersons()
    {
        return Persons;
    }

    public void setPersons(Collection<Person> persons)
    {
        Persons = persons;
    }

    public int getType()
    {
        return Command.ALL_PERSONS;
    }
}
