package com.example.taskmanager;

import org.apache.commons.cli.*;

import java.sql.*;

public class TaskManager {

    // JDBC connection string
    private static final String JDBC_URL = "jdbc:sqlite:src/main/resources/task_manager.db";

    public static void main(String[] args) {
        // Initialize command line options
        Options options = new Options();
        options.addOption("a", "add", true, "Add a new task");
        options.addOption("v", "view", false, "View all tasks");
        options.addOption("c", "complete", true, "Mark a task as complete");
        options.addOption("r", "remove", true, "Remove a task");

        // Parse command line arguments
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            // Establish database connection
            try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
                createTable(conn); // Create table if not exists
                if (cmd.hasOption("a")) {
                    addTask(conn, cmd.getOptionValue("a"));
                } else if (cmd.hasOption("v")) {
                    viewTasks(conn);
                } else if (cmd.hasOption("c")) {
                    markComplete(conn, Integer.parseInt(cmd.getOptionValue("c")));
                } else if (cmd.hasOption("r")) {
                    removeTask(conn, Integer.parseInt(cmd.getOptionValue("r")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ParseException e) {
            System.err.println("Error parsing command line arguments: " + e.getMessage());
            // Print usage information
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("TaskManager", options);
        }
    }

    private static void createTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "completed BOOLEAN NOT NULL DEFAULT FALSE)";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // Function to add a task
    private static void addTask(Connection conn, String taskName) throws SQLException {
        String sql = "INSERT INTO tasks(name, completed) VALUES(?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, taskName);
            pstmt.setBoolean(2, false);
            pstmt.executeUpdate();
            System.out.println("Task added successfully.");
        }
    }

    // Function to view all tasks
    private static void viewTasks(Connection conn) throws SQLException {
        String sql = "SELECT * FROM tasks";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Task: " + rs.getString("name") +
                        ", Completed: " + rs.getBoolean("completed"));
            }
        }
    }

    // Function to mark a task as complete
    private static void markComplete(Connection conn, int taskId) throws SQLException {
        String sql = "UPDATE tasks SET completed = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, taskId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task marked as complete.");
            } else {
                System.out.println("Task not found.");
            }
        }
    }

    // Function to remove a task
    private static void removeTask(Connection conn, int taskId) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, taskId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task removed successfully.");
            } else {
                System.out.println("Task not found.");
            }
        }
    }
}
