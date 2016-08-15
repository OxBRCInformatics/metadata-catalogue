# metadata-catalogue
Oxford Biomedical Research Centre Metadata Catalogue

This core component contains the database layer, and HTTP REST API.

### System requirements

- Tomcat 8 (untested on other versions)
- Postgres 9.4.4.1 (untested on other versions)

To run the application, you need to create a database, and an appropriate database user, build the war file, and deploy it to Tomcat with the correct configuration.

## Create a database

From the postgres command line, run the following commands:

```
CREATE USER metadatacatalogue WITH SUPERUSER PASSWORD 'password';
CREATE DATABASE metadatacatalogue OWNER metadatacatalogue;
```

replacing ```password``` with the password of your choice.

## To build
Either download the latest .war file from jenkins, or build it by checking out this repository, running

```mvn verify```

and taking the .war file from the `target` directory

## To deploy

Stop tomcat if it is already running.  Unzip the .war file into a directory ```webapps``` directory (e.g.) ```webapps/metadatacatalogue```.

Update the ```META-INF/persistence.properties``` file - it should have contents something like this:

```
hibernate.connection.username=metadatacatalogue
hibernate.connection.password=password
hibernate.connection.url=jdbc:postgresql://localhost:5432/metadatacatalogue
```
where ```password``` is the value you chose in step 1.

Start tomcat, and you should be able to navigate to the test end point at:

```
http://localhost:8080/metadatacatalogue/api/test
```
and get a friendly message in return.

## Authentication

The database installation scripts include the creation of a default API user to bootstrap further user creation.  The username is ```admin@metadatacatalogue.com``` and the password is ```password```.  

You can test successful login by visiting 

```
http://localhost:8080/metadatacatalogue/api/testSecured
```
and getting the same friendly message.

## Swagger

Swagger is used to document the API.  You can view the human-readable documentation by browsing to:

```
http://localhost:8080/metadatacatalogue/swagger
```

*However, the interface is very slow, because some of the datastructures are recursive.  You may just want to view the JSON.*


If you want to just view the JSON, then the description of the API is found here:

```
http://localhost:8080/metadatacatalogue/api/swagger.json
```