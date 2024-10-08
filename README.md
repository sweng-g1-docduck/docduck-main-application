# DocDuck Application
The DocDuck Engineering Maintenance Application

This repository contains the full application for our engineering maintenance software.

## What is DocDuck
Our product is intended to provide businesses with an application that increases the efficiency of the maintenance engineering team by providing an easy and efficient way for engineers to access, edit, track and create documentation as well as provide easy and clear communication between admins engineers and operators. The application, DocDuck, is an all in one application for the documentation side of engineering, from tracking diagnostic and calibration dates to having clear and easy access to maintenance history and number of parts in stock all in one convenient place. The three main pillars for DocDuck are affordability, efficiency and usability.

## Our Intentions
- To provide an affordable, versatile product for upcoming businesses as well as set up longstanding local businesses.
- To increase efficiency of the maintenance engineering sector by saving documentation time.
- To provide a long lasting, ever evolving product.
- To be the most customer friendly product of its genre on the market.
- To become versatile enough for multiple sectors of engineering and multiple business types.

## Getting Started
To get started with our application, download the latest release from the releases tab
Upon starting the application you will be greeted with two options, one to load the official DocDuck Application, and the second to load our customised application. The customised application requires you to have spoken with us in advance to request a custom layout and functionality.

When loading the official application, you will be greeted with a login page. New users must be created by admins via the control panel (you must contact your supervisor/administrators within your company to set up your new user account), however if you are an existing user you can log in with your username and password.


# Technical Details

## Building from Source
### Required Tools
For building the DocDuck application from source, you will require:
    - [Apache Maven](https://https://maven.apache.org/), version 3.9.x.
    - [Java JDK 11](https://jdk.java.net/archive/)

### Build Command
Once the tools have been installed, and the source code downloaded, navigate to the root directory of the application within your preferred terminal.
Make sure Maven and Java 11 are configured correctly:
    - `java --version`
    - `mvn --version`
Now run the command to build the application:
    - `mvn clean install`
This will build the application and produce a jar within the `./target` folder. And the jar will be called docduckapplication-{version}.jar

### Running the jar
To run the application, please run the jar with the following command:
    - `java -jar docduckapplication-{version}.jar`