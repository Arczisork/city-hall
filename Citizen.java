import java.time.LocalDate;

public class Citizen {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private LocalDate expirationDate;

    public Citizen(String firstName, String lastName, LocalDate birthDate, LocalDate expirationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", expirationDate=" + expirationDate +
                '}';
    }

    // Gettery i Settery
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
