# Task And ToDo for Code Testing

This is a project to implement the feature mentioned in code test

/task and /todo are implemented
To make it easier to run in server, /todo items are simply stored in an h2 database. Which will wiped out when service restarted

* /Task
Task use the validation bracket service, which use stack to validate the brackets

* /todo
Todo use the spring-data-jpa to handle CRUD operations. The data is stored in memory db h2

To test the endpoint, please fidn to 188.166.209.200:8080