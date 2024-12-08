package shahdabuzer;

import java.sql.*;
import java.util.*;

public class ReportGenerator {

    private final String DB_URL;
    private final String DB_USERNAME;
    private final String DB_PASSWORD;

    public ReportGenerator(String dbUrl, String dbUsername, String dbPassword) {
        this.DB_URL = dbUrl;
        this.DB_USERNAME = dbUsername;
        this.DB_PASSWORD = dbPassword;
    }

    public String generateReport(String reportType, Map<String, String> parameters) {
        StringBuilder report = new StringBuilder();
        switch (reportType) {
            case "Services for Car or Customer":
                generateServicesReport(parameters, report);
                break;
            case "Sales by Employee":
                generateSalesReport(parameters, report);
                break;
            case "Payments by Customer":
                generatePaymentsReport(parameters, report);
                break;
            case "Revenue per Service Type":
                generateRevenueReport(parameters, report);
                break;
            case "Frequency of Services":
                generateFrequencyReport(parameters, report);
                break;
            case "Service History by Vehicle":
                generateServiceHistoryReport(parameters, report);
                break;
            default:
                report.append("Invalid report type.");
        }
        return report.toString();
    }

    private void generateServicesReport(Map<String, String> parameters, StringBuilder report) {
        String query = "SELECT * FROM services WHERE 1=1";
        if (parameters.containsKey("car_id")) {
            query += " AND CarID = ?";
        }
        if (parameters.containsKey("customer_id")) {
            query += " AND CustomerID = ?"; 
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            int index = 1;
            if (parameters.containsKey("car_id")) {
                stmt.setInt(index++, Integer.parseInt(parameters.get("car_id")));
            }
            if (parameters.containsKey("customer_id")) {
                stmt.setInt(index, Integer.parseInt(parameters.get("customer_id")));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) { 
                report.append("Service ID: ").append(rs.getInt("ServiceID")).append("\n");
                report.append("Car ID: ").append(rs.getInt("CarID")).append("\n");
                report.append("Customer ID: ").append(rs.getInt("CustomerID")).append("\n");
                report.append("Service Date: ").append(rs.getDate("ServiceDate")).append("\n");
                report.append("Service Type: ").append(rs.getString("ServiceDescription")).append("\n");
                report.append("Cost: ").append(rs.getDouble("Cost")).append("\n");
                report.append("-------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateSalesReport(Map<String, String> parameters, StringBuilder report) {
        String query = "SELECT * FROM orders WHERE 1=1";
        if (parameters.containsKey("employee_id")) {
            query += " AND EmployeeID = ?";
        }
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {
    
            int index = 1;
            if (parameters.containsKey("employee_id")) {
                stmt.setInt(index++, Integer.parseInt(parameters.get("employee_id")));
            }
    
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                report.append("Sale ID: ").append(rs.getInt("SaleID")).append("\n");
                report.append("Employee ID: ").append(rs.getInt("EmployeeID")).append("\n");
                report.append("Customer ID: ").append(rs.getInt("CustomerID")).append("\n");
                report.append("Sale Date: ").append(rs.getDate("PaymentDate")).append("\n");
                report.append("Sale Amount: ").append(rs.getDouble("Amount")).append("\n");
                report.append("-------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void generatePaymentsReport(Map<String, String> parameters, StringBuilder report) {
        String query = "SELECT * FROM payments WHERE 1=1";
        if (parameters.containsKey("customer_id")) {
            query += " AND CustomerID = ?";
        }
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {
    
            int index = 1;
            if (parameters.containsKey("customer_id")) {
                stmt.setInt(index++, Integer.parseInt(parameters.get("customer_id")));
            }
    
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                report.append("Payment ID: ").append(rs.getInt("PaymentID")).append("\n");
                report.append("Customer ID: ").append(rs.getInt("CustomerID")).append("\n");
                report.append("Payment Amount: ").append(rs.getDouble("Amount")).append("\n");
                report.append("Payment Method: ").append(rs.getString("PaymentMethod")).append("\n");
                report.append("-------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void generateRevenueReport(Map<String, String> parameters, StringBuilder report) {
        String query = "SELECT ServiceDescription, SUM(cost) AS revenue FROM services WHERE 1=1";
        if (parameters.containsKey("start_date") && parameters.containsKey("end_date")) {
            query += " AND ServiceDate BETWEEN ? AND ?";
        }
        query += " GROUP BY ServiceDescription";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {
    
            int index = 1;
            if (parameters.containsKey("start_date") && parameters.containsKey("end_date")) {
                stmt.setString(index++, parameters.get("start_date"));
                stmt.setString(index++, parameters.get("end_date"));
            }
    
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                report.append("Service Type: ").append(rs.getString("ServiceDescription")).append("\n");
                report.append("Revenue: ").append(rs.getDouble("revenue")).append("\n");
                report.append("-------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void generateFrequencyReport(Map<String, String> parameters, StringBuilder report) {
        String query = "SELECT CarModel, COUNT(*) AS ServiceCount FROM services WHERE 1=1";
        query += " GROUP BY CarModel";
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {
    
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                report.append("Car Model: ").append(rs.getString("CarModel")).append("\n");
                report.append("Service Count: ").append(rs.getInt("ServiceCount")).append("\n");
                report.append("-------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void generateServiceHistoryReport(Map<String, String> parameters, StringBuilder report) {
        String query = "SELECT * FROM services WHERE 1=1";
        if (parameters.containsKey("car_id")) {
            query += " AND CarID = ?";
        }
    
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {
    
            int index = 1;
            if (parameters.containsKey("car_id")) {
                stmt.setInt(index++, Integer.parseInt(parameters.get("car_id")));
            }
    
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                report.append("Service ID: ").append(rs.getInt("ServiceID")).append("\n");
                report.append("Car ID: ").append(rs.getInt("CarID")).append("\n");
                report.append("Service Type: ").append(rs.getString("ServiceDescription")).append("\n");
                report.append("Service Date: ").append(rs.getDate("ServiceDate")).append("\n");
                report.append("Cost: ").append(rs.getDouble("Cost")).append("\n");
                report.append("-------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
}
