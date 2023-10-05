# Introduction 
Introduction
I found it challenging to locate a comprehensive online example that demonstrates the use of coroutines in Kotlin with R2DBC connections, 
as opposed to JDBC, in conjunction with Liquibase. As a result, I embarked on a personal project to explore and implement this combination. 
I've shared this project on my GitHub profile not only as a means of safeguarding it from being lost on some machine but 
also as a valuable resource for anyone looking to build a microservice using a similar approach.

## Learning and Collaboration
This project is primarily intended for learning purposes and encourages collaboration. 
If you find it useful or have suggestions for improvements, please feel free to fork the repository, 
adapt the codebase to suit your specific requirements, and contribute to its development. 
Together, we can create a robust solution for building microservices with coroutines, Kotlin, R2DBC, and Liquibase.

### Unit Tests with Coroutines
In addition to the primary implementation, I've also included a comprehensive set of unit tests. 
These tests not only validate the functionality of the code but also serve as a practical example of how to work with unit
tests in a coroutine-based project. You can explore these tests to understand how to test coroutine-based code effectively.
Feel free to examine the test cases, run them, and use them as a reference when writing your own unit tests for coroutine-based 
Kotlin applications. Learning how to write and run unit tests for coroutines can be a valuable skill when building robust 
and reliable microservices or applications.

**Prerequisites:**
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [Java 17](https://www.oracle.com/java/technologies/javase-downloads.html)
- [PostgreSQL](https://www.postgresql.org/download/)
- Two PostgreSQL databases installed: `master_pet_project` and `tenant_pet_project`
- Set your local profile to run the app

**Starting the App:**

1. **Clone the Repository**
2. **Open the Project in IntelliJ IDEA:**
    - Open IntelliJ IDEA.
    - Click on "File" > "Open" and select the project folder you just cloned.

3. **Configure Java 17:**
    - Make sure you have Java 17 installed on your system.
    - In IntelliJ IDEA, go to "File" > "Project Structure."
    - Under "Project," set the "Project SDK" to Java 17.

4. **Set Up Database Configuration:**
    - Locate the database configuration file in your project `application-local.yml`.
    - Configure the database connection properties (username, password, URL) to match your PostgreSQL setup for both `master_pet_project` and `tenant_pet_project` databases.

5. **Set Local Profile:**
    - Edit run configuration to set local profile and read from `application-local.yml`.

6. **Run the Application:**
    - In IntelliJ IDEA, locate the main application file (usually named `Application.kt` or similar).
    - Right-click on the main file and select "Run" to start the application.

**Running Unit Tests:**

Running unit tests is simple:

1. In IntelliJ IDEA, locate the test files (usually in the `src/test` directory).

2. Right-click on the test file or a specific test case you want to run.

3. Select "Run" to execute the tests.

That's it! You've successfully started the app and run unit tests in your Kotlin project. Make sure to explore the project further to understand its functionalities and how to use it effectively.


<blockquote>
  <p align="center">Kotlin 🚀: Where Elegance Meets Efficiency</p>
  <p align="center">A language that makes coding feel like a delightful puzzle, where every piece fits perfectly.</p>
  <p align="center">With concise syntax and powerful features, Kotlin empowers developers to craft exceptional software.</p>
  <p align="center">Whether you're a beginner or a seasoned pro, Kotlin welcomes you with open arms to a world of endless possibilities.</p>
  <p align="center">Embrace the joy of Kotlin, and let your code shine!</p>
  <p align="center">🎉 Kotlin is Awesome! 🎉</p>
</blockquote>
