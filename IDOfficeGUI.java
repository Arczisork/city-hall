import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.time.LocalDate;

public class IDOfficeGUI extends Application {

    private IDOffice idOffice = new IDOffice(); // Tworzymy instancję klasy IDOffice
    private GridPane grid; // Przechowywanie referencji do GridPane

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Urząd Wydawania Dowodów");

        // Tworzymy formularz z polami
        grid = createForm();  // Teraz przypisujemy do pola klasy

        // Przycisk do wydawania dowodu
        Button issueButton = new Button("Wydaj Dowód");
        issueButton.setOnAction(e -> issueID());

        // Dodajemy przycisk do układu
        grid.add(issueButton, 0, 4, 2, 1);

        // Ustawienie sceny
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createForm() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        // Tworzymy etykiety i pola tekstowe
        Label firstNameLabel = new Label("Imię:");
        grid.add(firstNameLabel, 0, 0);
        TextField firstNameField = new TextField();
        grid.add(firstNameField, 1, 0);

        Label lastNameLabel = new Label("Nazwisko:");
        grid.add(lastNameLabel, 0, 1);
        TextField lastNameField = new TextField();
        grid.add(lastNameField, 1, 1);

        Label birthDateLabel = new Label("Data Urodzenia:");
        grid.add(birthDateLabel, 0, 2);
        DatePicker birthDatePicker = new DatePicker();
        grid.add(birthDatePicker, 1, 2);

        return grid;
    }

    private void issueID() {
        // Pobieranie danych z formularza
        TextField firstNameField = (TextField) grid.getChildren().get(1); // Pobierz pole imienia
        TextField lastNameField = (TextField) grid.getChildren().get(3);  // Pobierz pole nazwiska
        DatePicker birthDatePicker = (DatePicker) grid.getChildren().get(5); // Pobierz wybór daty

        // Walidacja danych
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        LocalDate birthDate = birthDatePicker.getValue();

        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty() || birthDate == null) {
            showError("Wszystkie pola muszą być wypełnione.");
            return;
        }

        // Obliczenie wieku
        int age = LocalDate.now().getYear() - birthDate.getYear();
        LocalDate expirationDate;
        String message;

        if (age < 0) {
            showError("Data urodzenia nie może być z przyszłości!");
            return;
        } else if (age < 5) {
            // Tymczasowy dowód na 3 lata
            expirationDate = LocalDate.now().plusYears(3);
            message = "Ze względu na Twój wiek (0-5 lat), dowód został wydany z terminem ważności do: " + expirationDate + ".";
        } else if (age < 18) {
            // Dowód ważny do ukończenia 18 roku życia
            expirationDate = birthDate.plusYears(18);
            message = "Ze względu na Twój wiek (10-17 lat), dowód został wydany z terminem ważności do: " + expirationDate + ".";
        } else {
            // Dowód bezterminowy (np. 10 lat)
            expirationDate = LocalDate.now().plusYears(10);
            message = "Dowód został wydany z terminem ważności do: " + expirationDate + ".";
        }

        // Tworzenie obywatela i dodanie do listy
        Citizen citizen = new Citizen(firstName, lastName, birthDate, expirationDate);
        idOffice.issueID(firstName, lastName, birthDate);

        // Wyświetlenie komunikatu o wydaniu dowodu
        showInfo(message);
    }




    // Pomocnicza metoda do pobierania elementów z formularza na podstawie indeksu
    private javafx.scene.Node getNodeByRowColumnIndex(final int row, final int column) {
        javafx.scene.Node result = null;
        java.util.Optional<javafx.scene.Node> resultNode = grid.getChildren().stream()
                .filter(node -> grid.getRowIndex(node) == row && grid.getColumnIndex(node) == column)
                .findFirst();
        if (resultNode.isPresent()) {
            result = resultNode.get();
        }
        return result;
    }

    // Wyświetlanie komunikatu o błędzie
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Wyświetlanie komunikatu o sukcesie
    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukces");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
