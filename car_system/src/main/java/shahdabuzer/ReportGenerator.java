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
            query += " AND car_id = ?";
        }
        if (parameters.containsKey("customer_id")) {
            query += " AND customer_id = ?";
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
                report.append("Service ID: ").append(rs.getInt("service_id")).append("\n");
                report.append("Car ID: ").append(rs.getInt("car_id")).append("\n");
                report.append("Customer ID: ").append(rs.getInt("customer_id")).append("\n");
                report.append("Service Date: ").append(rs.getDate("service_date")).append("\n");
                report.append("Service Type: ").append(rs.getString("service_type")).append("\n");
                report.append("Cost: ").append(rs.getDouble("cost")).append("\n");
                report.append("-------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateSalesReport(Map<String, String> parameters, StringBuilder report) {
        String query = "SELECT * FROM sales WHERE employee_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(parameters.get("employee_id")));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                report.append("Sale ID: ").append(rs.getInt("sale_id")).append("\n");
                report.append("Employee ID: ").append(rs.getInt("employee_id")).append("\n");
                report.append("Customer ID: ").append(rs.getInt("customer_id")).append("\n");
                report.append("Sale Date: ").append(rs.getDate("sale_date")).append("\n");
                report.append("Sale Amount: ").append(rs.getDouble("sale_amount")).append("\n");
                report.append("-------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generatePaymentsReport(Map<String, String> parameters, StringBuilder report) {
        String query = "SELECT * FROM payments WHERE customer_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(parameters.get("customer_id")));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                report.append("Payment ID: ").append(rs.getInt("payment_id")).append("\n");
                report.append("Customer ID: ").append(rs.getInt("customer_id")).append("\n");
                report.append("Payment Amount: ").append(rs.getDouble("payment_amount")).append("\n");
                report.append("Payment Method: ").append(rs.getString("payment_method")).append("\n");
                report.append("-------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateRevenueReport(Map<String, String> parameters, StringBuilder report) {
        String period = parameters.get("period");
        String query = "SELECT service_type, SUM(cost) AS revenue FROM services WHERE service_date BETWEEN ? AND ? GROUP BY service_type";

        String startDate = "2024-01-01";
        String endDate = "2024-01-31";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, startDate);
            stmt.setString(2, endDate);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                report.append("Service Type: ").append(rs.getString("service_type")).append("\n");
                report.append("Revenue: ").append(rs.getDouble("revenue")).append("\n");
                report.append("-------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateFrequencyReport(Map<String, String> parameters, StringBuilder report) {
        String query = "SELECT car_model, COUNT(*) AS service_count FROM services GROUP BY car_model";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                report.append("Car Model: ").append(rs.getString("car_model")).append("\n");
                report.append("Service Count: ").append(rs.getInt("service_count")).append("\n");
                report.append("-------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateServiceHistoryReport(Map<String, String> parameters, StringBuilder report) {
        String query = "SELECT * FROM services WHERE car_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(parameters.get("car_id")));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                report.append("Service ID: ").append(rs.getInt("service_id")).append("\n");
                report.append("Car ID: ").append(rs.getInt("car_id")).append("\n");
                report.append("Service Type: ").append(rs.getString("service_type")).append("\n");
                report.append("Service Date: ").append(rs.getDate("service_date")).append("\n");
                report.append("Cost: ").append(rs.getDouble("cost")).append("\n");
                report.append("-------------------------------\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}