# WOTApp

The Warehouse Order Tracking Application for the NBGardens case study designed to connect to the rest of the system constructed in the SpringWater project.

Installation Instructions:
If you experience a Maven build error the auto dependency download has failed therefore:

You will require the https://github.com/DaveWoodie/SpringWater project downloaded in order for Maven to build and then add it as a dependency.

This project is dependant on the SpringWater-Ops-Website child project in SpringWater for java entities to use the JMS.

This project will not run without the correct MongoDB and MySQL databases. In the future they will be auto-generated if not found by the project.
