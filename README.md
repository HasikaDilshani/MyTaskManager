# Task Manager CLI Application

This is a command-line task manager application developed in Java using Maven and SQLite. The application allows users to manage their daily tasks efficiently through a command-line interface.

## Features

- Add tasks with necessary information.
- View all tasks.
- Mark tasks as complete.
- Remove tasks.

## Getting Started

### Prerequisites

- Java 11 or higher
- Apache Maven 3.6 or higher

### Installation

1. Clone the repository to your local machine:

    ```bash
    git clone https://github.com/your-username/task-manager-cli.git
    ```

2. Navigate to the project directory:

    ```bash
    cd task-manager-cli
    ```

3. Compile and run the project using Maven:

    ```bash
    mvn clean compile exec:java -Dexec.mainClass="com.example.taskmanager.TaskManager" -Dexec.args="-a 'Finish assignment'"
    ```

### Usage

- To add a task:

    ```bash
    mvn exec:java -Dexec.mainClass="com.example.taskmanager.TaskManager" -Dexec.args="-a 'Finish assignment'"
    ```

- To view all tasks:

    ```bash
    mvn exec:java -Dexec.mainClass="com.example.taskmanager.TaskManager" -Dexec.args="-v"
    ```

- To mark a task as complete:

    ```bash
    mvn exec:java -Dexec.mainClass="com.example.taskmanager.TaskManager" -Dexec.args="-c 1"
    ```

- To remove a task:

    ```bash
    mvn exec:java -Dexec.mainClass="com.example.taskmanager.TaskManager" -Dexec.args="-r 1"
    ```

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
