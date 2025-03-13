Introduction

This is a Selenium-based Test Automation Framework designed for automating the OrangeHRM web application. It follows the Page Object Model (POM) to enhance test maintainability and uses Cucumber BDD for writing feature files in a human-readable format.



Tech Stack

Programming Language: Java

Test Automation: Selenium WebDriver

Behavior-Driven Development (BDD): Cucumber

Build Tool: Maven

Test Runner: JUnit

Version Control: Git



Prerequisites

Java 17 or later

Maven

Google Chrome & Chromedriver

IDE (IntelliJ IDEA / Eclipse)

Git (For version control)


Installing Dependencies
Run the following command to install required dependencies:
  
mvn clean install


Running the Tests

mvn test


Alternatively, you can run specific feature files:

mvn test -Dcucumber.options="src/test/resources/features/UserManagement.feature"

  
Project Structure

HRM-Automation/

│── src/

│   ├── main/

│   │   ├── java/pages/         
# Page Object Model (POM) classes

│   │   ├── java/utils/     
# Helper utilities

│   ├── test/

│   │   ├── java/stepdefinitions 
# Cucumber step definitions

│   │   ├── java/runner/    
# Test runners

│   │   ├── resources/

│   │   │   ├── features/   
# Cucumber feature files

│   │   │   ├── config/       
# Configuration files

│── pom.xml              
# dependencies

│── README.md              
# Project documentation





