# Cricbuzz Project Overview

## About the Project

Cricbuzz is an oracle and javafx based desktop application. It is intended to provide users with information about cricket teams and players and further provide scores of live matches and tournaments.

## Dependencies

- Java
- Javafx
- Oracle database

## Getting Started

We have used Windows 10(/11) as an operating system. If you have some different OS, please navigate to that version of the software before downloading it.

1. **Java Installation**
   - You need to have java pre-installed on your device. You can download java from [here](https://www.java.com/en/).

2. **JavaFX Libraries**
   - Since javafx is not pre-installed in jdk, you have to manually add the javafx libraries in order to run a javafx application. You can download the zip folder from [here](https://gluonhq.com/products/javafx/).

3. **Oracle Database**
   - We have used Oracle as our database. So, you need to have Oracle in your machine as well. This is the link you can download oracle from - [ORACLE - 19c](https://www.oracle.com/database/technologies/oracle-database-software-downloads.html).

4. **Oracle JDBC Driver**
   - Lastly, you will need another jar file named “ojdbc10.jar” to connect to the oracle database from the javafx application. You can download that from [here](https://www.oracle.com/database/technologies/appdev/jdbc-downloads.html).

## Setting up the database

1. **Access SQL Plus**
   - Open SQL PLUS on your machine.
   - Type in the following information:  
     - username: sys as sysdba 
     - password: password

2. **Create a New User**
   - Execute the following commands:
     ```
     alter session set "_ORACLE_SCRIPT"=true;
     create user pdb identified by pdb;
     grant create session to pdb;
     grant all privileges to pdb;
     connect username: pdb
     password: pdb
     ```

3. **Prepare the Database**
   - Now head over to your database GUI and run the queries specified in the file - “SQL_DUMP.sql”. If no errors are found, the database is ready for use!

## Setting up the environment

1. **JavaFX Libraries Setup**
   - As you run the project on editor, you may find that your IDE is not recognizing javafx files.
     - Go to the “Open Module Settings”
     - Head over to “Global Libraries” and add the javafx jar files manually as a global library from the “lib” folder of the javafx directory.
     - Lastly, add them to your project and press OK.

2. **Adding JDBC to Your Project**
   - Add JDBC to your project:
     - Again, go to the “Open Module Setting”
     - Open “Module”
     - Go to the “Dependencies” section and click add “JARs or directories”
     - Manually direct to the “ojdbc10.jar” file you installed before.

We have used Intellij IDEA for source code. If you use a different JDK IDE, you may or may not get the same type of errors. With that, we are ready to run our project!
