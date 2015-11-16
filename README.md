# WOTApp

The Warehouse Order Tracking Application for the NBGardens case study designed to connect to the rest of the system constructed in the SpringWater project.

Installation Instructions:
If you experience a Maven build error the auto dependency download has failed therefore:

You will require the https://github.com/DaveWoodie/SpringWater project downloaded in order for Maven to build and then add it as a dependency.

This project is dependant on the SpringWater-Ops-Website child project in SpringWater for java entities to use the JMS.

This project will not run without the correct MongoDB and MySQL databases. A test database can be constructed from importing the contents of the "Database Files" folder into their respective server types. The database name for the mySQL database is "mydb" and for the mongoDB it is "nbgardens" In the future they will be auto-generated if not found by the project.
