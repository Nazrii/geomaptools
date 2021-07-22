# Geo Map Tools

### Retrieve distance
With database containing all UK postcodes, the system are able to calculate the distance based on the provided postcodes

* Request: POST with JSON Request parameters: from_postcode and to_postcode
* Response: JSON Response parameters: distance, its unit and both postcodes details

### Manage postcode coordinates
The system allows user to manage the postcodes name and its coordinates

* View postcode details
* Change postcode name
* Change postcode coordinates

Further details on rest method api are available in swagger ui:
http://localhost:8080/swagger-ui/

---
### Technology
* Java 1.8
* Maven 3.3.9
* MySQL

---
### Run
Go to system directory and run the following command:
> `D:\workspace\geodistance> mvn clean spring-boot:run`

---
### Database
Database that is being used for this system is MySQL.
Below are the steps to add the postcode table and its data

* Using mysql command line, create a database named geomaptools
> `mysql> create database geomaptools;`

> `mysql> use geomaptools;`
* Run the table creation script as below:
> `mysql> source D:\mysql_script\ukpostcodes_tablemysql.sql`

The script for table creation can be downloaded from
[here](https://www.freemaptools.com/download/full-postcodes/ukpostcodes_tablemysql.sql)
* Run the data insertion script as below:
> `mysql> source D:\mysql_script\ukpostcodesmysql.sql`

The script for table data insertion can be downloaded from
[here](https://www.freemaptools.com/download/full-postcodes/ukpostcodesmysql.zip)

Note that the data is huge and contains almost 1.8 million of rows.  