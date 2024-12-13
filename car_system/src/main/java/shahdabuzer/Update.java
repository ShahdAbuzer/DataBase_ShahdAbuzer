// public void updateRecord(TableView<ObservableList<String>> table, VBox mainContentPane, Color notificationColor) {
//     Permissions permissions = new Permissions();

//     if (permissions.checkPermission("shahd")) {
//         table.setOnMouseClicked(event -> {
//             if (event.getClickCount() == 2) {
//                 int index = table.getSelectionModel().getSelectedIndex();
//                 if (index >= 0) {
//                     ObservableList<String> row = table.getItems().get(index);
//                     if (row == null) {
//                         showNotification("Row data is missing.", mainContentPane, redColor);
//                         return;
//                     }

//                     String tableName = table.getId();
//                     if (tableName == null || tableName.isEmpty()) {
//                         showNotification("Table name (id) is not set.", mainContentPane, redColor);
//                         return;
//                     }

//                     openEditScene(row, tableName, mainContentPane, notificationColor);
//                 }
//             }
//         });
//     } else {
//         showNotification("You do not have permission to edit records.", mainContentPane, redColor);
//     }
// }


// public void openEditScene(ObservableList<String> row, String tableName, VBox mainContentPane, Color notificationColor) {
//     Stage stage = new Stage();
//     stage.setTitle("Edit Record");

//     GridPane gridPane = new GridPane();
//     gridPane.setPadding(new Insets(15));
//     gridPane.setHgap(15);
//     gridPane.setVgap(15);

//     String[] columnNames = getColumnNamesForTable(tableName);
    
//     if (columnNames == null || columnNames.length == 0) {
//         showNotification("No columns found for table " + tableName, mainContentPane, redColor);
//         return;
//     }

//     TextField[] textFields = new TextField[columnNames.length];

//     for (int i = 0; i < columnNames.length; i++) {
//         Label label = new Label(columnNames[i] + ":");
//         TextField textField = new TextField(row.get(i + 1)); 
//         textFields[i] = textField;

//         gridPane.add(label, 0, i);
//         gridPane.add(textField, 1, i);
//     }

//     Button saveButton = new Button("Save Changes");
//     saveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px;");
//     saveButton.setOnAction(event -> {
//         try {
//             updateDatabase(row.get(0), columnNames, textFields, tableName); 
//             for (int i = 0; i < textFields.length; i++) {
//                 row.set(i + 1, textFields[i].getText()); 
//             }
//             table.refresh();
//             showNotification("Record updated successfully.", mainContentPane, usedColor);
//             stage.close();
//         } catch (SQLException e) {
//             showNotification("Error updating record: " + e.getMessage(), mainContentPane, redColor);
//             e.printStackTrace();
//         }
//     });

//     Button cancelButton = new Button("Cancel");
//     cancelButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-padding: 10px;");
//     cancelButton.setOnAction(event -> stage.close());

//     gridPane.add(saveButton, 0, columnNames.length);
//     gridPane.add(cancelButton, 1, columnNames.length);

//     Scene scene = new Scene(gridPane);
//     stage.setScene(scene);
//     stage.show();
// }

// private String[] getColumnNamesForTable(String tableName) {
//     // Fetch column names dynamically from the database
//     List<String> columnNames = new ArrayList<>();
//     try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
//         DatabaseMetaData metaData = connection.getMetaData();
//         ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
//         while (resultSet.next()) {
//             columnNames.add(resultSet.getString("COLUMN_NAME"));
//         }
//     } catch (SQLException e) {
//         e.printStackTrace();
//     }
//     return columnNames.toArray(new String[0]);
// }


// private void updateDatabase(String primaryKey, String[] columnNames, TextField[] textFields, String tableName) throws SQLException {
//     StringBuilder queryBuilder = new StringBuilder("UPDATE ").append(tableName).append(" SET ");

//     for (int i = 0; i < columnNames.length; i++) {
//         queryBuilder.append(columnNames[i]).append(" = ?");
//         if (i < columnNames.length - 1) queryBuilder.append(", ");
//     }

//     queryBuilder.append(" WHERE ").append(columnNames[0]).append(" = ?"); // Assuming the first column is the primary key

//     try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
//          PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {

//         for (int i = 0; i < textFields.length; i++) {
//             preparedStatement.setString(i + 1, textFields[i].getText());
//         }

//         preparedStatement.setString(textFields.length + 1, primaryKey);
//         preparedStatement.executeUpdate();
//     }
// }

