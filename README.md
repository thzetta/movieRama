* Summary of set up

The application has been built with:

* Spring framework 3.1.2

* Spring Tool Suite IDE 3.6.3

* Maven 3.2.3

* Apache Tomcat Maven Plugin


To build the application you need to have Maven installed. You can download Maven 3.2.3 from http://maven.apache.org/ and follow the instructions to set it up.

Once maven is configured, you can cd in the project's folder MovieRama in the command line and execute: mvn clean install tomcat7:run
The application will be available at: http://localhost:8080/movierama/

The project was developed on Spring Tool Suite on Windows: http://spring.io/tools
To view and run the application on Spring Tool Suite click File->Import and select 
'Existing Maven Projects'. Browse to the project's folder and the pom.xml will be discovered. 

To run the project in Spring Tool Suite create a new run configuration under Maven Build and set the goal to: clean install tomcat7:run.