import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IDOffice {

    private List<Citizen> citizens = new ArrayList<>();

    public void issueID(String firstName, String lastName, LocalDate birthDate) {
        if (!validateInput(firstName, lastName, birthDate)) {
            System.out.println("Niepoprawne dane.");
            return;
        }

        int age = LocalDate.now().getYear() - birthDate.getYear();
        LocalDate expirationDate;

        if (age >= 18) {
            expirationDate = LocalDate.now().plusYears(10);
        } else if (age < 5) {
            expirationDate = LocalDate.now().plusYears(2);
        } else if (age <= 10) {
            expirationDate = LocalDate.now().plusYears(3);
        } else {
            expirationDate = birthDate.plusYears(18 - age); // Do 18 roku życia
        }

        Citizen citizen = new Citizen(firstName, lastName, birthDate, expirationDate);
        citizens.add(citizen);

        System.out.println("Dodano obywatela: " + citizen);
    }

    private boolean validateInput(String firstName, String lastName, LocalDate birthDate) {
        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
            return false;
        }
        if (birthDate.isAfter(LocalDate.now())) {
            return false;
        }
        return true;
    }
}
