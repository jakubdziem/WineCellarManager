# WineCellarManager

WineCellarManager is a web application designed to help users efficiently manage their wine collections. It offers features such as inventory tracking, wine details management, and more.

## Description

This application allows users to:

- **Add New Wines**: Input details like name, vintage, region, image url and price.
- **Rate wines**: Input details like flavor, aroma, aging time and suggested food pairings.
- **View Collection**: Browse the entire wine collection with sorting and filtering options.
- **Browse Collections** Browse collections of other users.
- **Update Entries**: Modify wine details as needed.
- **Delete Entries**: Remove wines that are no longer in the collection.

## Technologies Used

The project utilizes the following technologies:

- **Backend**:
  - [Spring Boot](https://spring.io/projects/spring-boot): Simplifies the creation of stand-alone, production-grade Spring-based applications.
  - [Spring Security](https://spring.io/projects/spring-security): Provides authentication and authorization for secure access to application features.
    - **JWT Authentication**: Used for securing API endpoints and managing user sessions. JSON Web Tokens are stored in cookies and local storage for authentication.
  - [Hibernate](https://hibernate.org/): An object-relational mapping tool for Java.
  - [Spring Data JPA](https://spring.io/projects/spring-data-jpa): Simplifies database access and operations with JPA repositories.
  - [PostgreSQL](https://www.postgresql.org/): A powerful, open-source relational database used to store and manage wine data.
  - **Spring Validation**: Provides robust validation mechanisms, including custom annotations for validating DTOs like wine and rating data.
    - **Custom Validators**:
      - `@EnumValidator`: Ensures that a field's value matches one of the values in a specified enumeration.
      - `@VintageValidator`: Validates that the vintage year is after year 0 and not in the future.
- **Frontend**:
  - [Thymeleaf](https://www.thymeleaf.org/): A server-side Java template engine for building dynamic and interactive HTML views.
  - [HTML5](https://developer.mozilla.org/en-US/docs/Web/Guide/HTML/HTML5): For structuring the web content.
  - [CSS3](https://developer.mozilla.org/en-US/docs/Web/CSS): For styling the application.
  - [JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript): For adding interactivity and sending asynchronous requests to the backend using Fetch API.
- **Build Tool**:
  - [Maven](https://maven.apache.org/): For project build and dependency management.

## Setup Instructions

To set up the project locally:

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/jakubdziem/WineCellarManager.git
   cd WineCellarManager
   ```
   To run the WineCellarManager app, you need to have PostgreSQL installed and configured on your machine.

2. **Install PostgreSQL**:
   You need to install PostgreSQL manually. You can follow the installation instructions for different operating systems from the official PostgreSQL website: [PostgreSQL Installation Guide]    (https://www.postgresql.org/download/).

3. **Create the Database**:
   After installing PostgreSQL, you'll need to create the `wine-cellar-manager` database (or you can modify the `application.properties` file to use a different name).
   To create the database, you can run the following commands if you're using the `psql` command-line tool:
   ```bash
   psql -U postgres
   CREATE DATABASE "wine-cellar-manager";
   ```
   
4. **Build the Project**:
   Ensure you have Maven installed. Then, run:
   ```bash
   mvn clean install
   ```

5. **Run the Application**:
   Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the Application**:
   Open your browser and navigate to `http://localhost:8080` to interact with the WineCellarManager.

## Usage

Once the application is running:

![image](https://github.com/user-attachments/assets/7f027dff-efa7-4c10-b0b0-da9a434eb742)

- **Register**:
  - Click "create your account" button.
  - Fill in your nickname, email address and password.

![image](https://github.com/user-attachments/assets/02a43e9f-2a60-4b31-9769-117932d95c5c)

- **Log in**:
  - Fill in details from the "Sing up" section: nickname and password.
  - Go to the "My collar" section.

![image](https://github.com/user-attachments/assets/46596ec9-7553-47f9-9dc8-f99657e45086)

- **Add a New Wine**:
  - Fill in the wine details and submit the form.
 
![image](https://github.com/user-attachments/assets/2cbfd6da-c449-4674-801e-43b2da489fee)
![image](https://github.com/user-attachments/assets/8b2d43ab-3e34-4b46-a0a0-4e8fda80be1f)

- **View Wine Collection**:
  - Use the "Sort by" options to group wines.
  - Rate the wine.
 
![image](https://github.com/user-attachments/assets/55a4fdf3-8e0a-4b4f-be9f-a5c61615c4e0)
![image](https://github.com/user-attachments/assets/dc02633d-bc0c-4eb4-a949-45f6acacd927)
![image](https://github.com/user-attachments/assets/b13edb57-c265-41e5-ac52-0efe998b2fed)
![image](https://github.com/user-attachments/assets/83fac07a-0391-452f-b89c-4aceff700c7e)
*Preview of another rating*
<br>
- **Edit or Delete a Wine**:
  - In the collection view, select the wine you wish to edit or delete.
  - Choose the appropriate action to update or remove the entry.

![image](https://github.com/user-attachments/assets/d99e407e-3c10-4f64-82be-3fa301db480f)
  
- **Browse Other Collars**:
  - In the "Collars" section search for nickname of one of the account that you created.

![image](https://github.com/user-attachments/assets/a564a4d7-1c4c-4457-81bb-348ab3aecd70)
![image](https://github.com/user-attachments/assets/8b86b4ee-fddc-4fb2-9ae6-cca4273c2caa)
![image](https://github.com/user-attachments/assets/50b3678e-97fe-410e-a5ba-4af9a2bfaffd)
