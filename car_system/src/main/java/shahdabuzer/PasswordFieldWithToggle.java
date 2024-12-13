package shahdabuzer;


import javafx.geometry.Pos;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class PasswordFieldWithToggle extends StackPane {
    private final PasswordField passwordField;
    private final TextField textField;
    private final ToggleButton toggleButton;
    private boolean isPasswordVisible = false;

    public PasswordFieldWithToggle(String placeholder) {
        
        passwordField = new PasswordField();
        passwordField.setPromptText(placeholder);
        passwordField.getStyleClass().add("password-field");
        passwordField.setMouseTransparent(false);
        textField = new TextField("Password");
        textField.setPromptText(placeholder);
        textField.setVisible(false);
        textField.setManaged(false);
        textField.getStyleClass().add("password-field");

        
        textField.textProperty().bindBidirectional(passwordField.textProperty());

        
        toggleButton = new ToggleButton();
        toggleButton.getStyleClass().add("toggle-button");
        toggleButton.setFocusTraversable(false); 

        
        

        
        toggleButton.setOnAction(event -> {
            isPasswordVisible = toggleButton.isSelected();
            textField.setVisible(isPasswordVisible);
            textField.setManaged(isPasswordVisible); 
            passwordField.setVisible(!isPasswordVisible);
            passwordField.setManaged(!isPasswordVisible); 
        });

        
        HBox toggleContainer = new HBox(toggleButton);
        toggleContainer.setAlignment(Pos.CENTER_RIGHT);
        toggleContainer.setStyle("-fx-padding: 0 5 0 0;");

        
        this.setMaxWidth(300);
        this.getChildren().addAll(passwordField, textField, toggleContainer);
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public TextField getTextField() {
        return textField;
    }

    public ToggleButton getToggleButton() {
        return toggleButton;
    }
}
