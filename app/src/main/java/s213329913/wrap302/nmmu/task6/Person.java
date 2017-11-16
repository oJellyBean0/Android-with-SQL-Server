package s213329913.wrap302.nmmu.task6;

import java.io.Serializable;

/**
 * Created by s2133 on 2017/09/12.
 */

public class Person implements Serializable {
    int id;
    String name, surname, cellphone, telephone;

    public Person(int id,String name, String surname, String cell, String phone){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.cellphone = cell;
        this.telephone = phone;
    }
}
