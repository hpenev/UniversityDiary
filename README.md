# Application Description:
We need an application for storing Students and Teachers information like name, age, group and courses. We have two type of courses -Main and Secondary.
The application should be able to add remove or modify students and teachers. With this application we should be able to create different reports :
*   how many students we have - http://localhost:8080/api/students/count
*   how many teachers we have - http://localhost:8080/api/teachers/count
*   how many courses by type we have - http://localhost:8080/api/courses/count?type=Main
*   which students participate in specific course
    - http://localhost:8080/api/students/course/1
    - http://localhost:8080/api/students/course?name=Math101
*   which students participate in specific group 
    - http://localhost:8080/api/students/group/1
    - http://localhost:8080/api/students/group?name=GroupA
*   find all teachers and students for specific group and course
    - http://localhost:8080/api/course/1/group/1/byId
    - http://localhost:8080/api/course/Math101/group/GroupA/byName
*   find all students older than specific age and participate in specific course
    - http://localhost:8080/api/students?age=gte:21&courseId=1
*   Please write tests and use an in-memory database.

The application should provide public API.

## What the Application Does

### Main Features
- **Manage Students and Teachers**: Add, remove, and modify student and teacher information including name, age, group, and courses.
- **Course Management**: Handle two types of courses - Main and Secondary.
- **Reporting**: Generate various reports such as:
    - Total number of students
    - Total number of teachers
    - Number of courses by type
    - Students participating in a specific course
    - Students participating in a specific group
    - Teachers and students for a specific group and course
    - Students older than a specific age participating in a specific course

### API Endpoints
- **Students**: CRUD operations for managing student information.
- **Teachers**: CRUD operations for managing teacher information.
- **Courses**: CRUD operations for managing course information.

### Authentication
The application uses Spring Security to provide basic authentication. The authentication mechanism is configured to use in-memory user details with two predefined users: user and admin.
#### User Roles and Access
##### User:  
1. [ ] Username: user
2. [ ] Password: password
3. [ ] Role: USER
4. [ ] Access: Can only access GET endpoints.
#### Admin:  
1. [ ] Username: admin
2. [ ] Password: admin
3. [ ] Role: ADMIN
4. [ ] Access: Can access all endpoints.

### Additional Features
- **Swagger UI**: Access the Swagger UI at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) for API documentation and testing.
- **H2 Console**: Access the H2 console at [http://localhost:8080/h2-console](http://localhost:8080/h2-console) using the credentials defined in the `application.properties` file.
- **Metrics**: Access the metrics endpoint at [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus) for application metrics.


## How to Start the Application

1. **Clone the Repository**: Clone the project repository to your local machine.
    ```sh
    git clone <repository-url>
    cd <repository-directory>
    ```

2. **Build the Project**: Use Maven to build the project and download dependencies.
    ```sh
    mvn clean install
    ```

3. **Run the Application**: Use Maven to run the Spring Boot application.
    ```sh
    mvn spring-boot:run
    ```
