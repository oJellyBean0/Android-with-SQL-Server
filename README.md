# Android-with-SQL-Server

![alt text](https://github.com/oJellyBean0/Android-with-SQL-Server/blob/master/Screenshots/List%20of%20People%20once%20connected%20to%20the%20database.png)

## Objectives:

```
 Database connectivity
 Custom controls
```

By default, Android does not provide you with functionality to connect to a SQL-Server database.
There are some OpenSource solutions to do so though. Read the tutorials at:

```
http://www.parallelcodes.com/connect-android-to-ms-sql-database-2/
```
```
http://www.parallelcodes.com/connect-android-to-ms-sql-database/
```
Write a small Android application to connect to the WRAP database used in lecture 8 of WRAP301 
and allow a user to manipulate the _person_ table.

A user should be able to:

```
 connect to the database by clicking a connect button. If the connection could be established,
then the list view is refreshed and the delete, add and close buttons are enabled (initially
they are disabled);
```
```
 view all the records in the person table, displayed in a list view. The list view can be
refreshed by clicking on a refresh button;
```
```
 a record can be deleted by selecting it in the list view and pressing a delete button. A pop-up
should appear requesting confirmation from the user before deleting it;
```
```
 a new record can be added by allowing the user to specify a surname, name, phone number
and cell number and clicking an add button; and
```
```
 finally, the connection with the database can be terminated by clicking on a close button. At
which point, the other buttons no longer function.
```

Screenshots

Adding new person
![alt text](https://github.com/oJellyBean0/Android-with-SQL-Server/blob/master/Screenshots/Adding%20new%20person.png)

Result of adding the above contact
![alt test](https://github.com/oJellyBean0/Android-with-SQL-Server/blob/master/Screenshots/Result%20after%20adding%20new%20person.png)
