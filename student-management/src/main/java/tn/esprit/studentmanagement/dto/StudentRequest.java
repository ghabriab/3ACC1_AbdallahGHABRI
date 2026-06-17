package tn.esprit.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Simple POJO/DTO used to receive student data from clients.
 * <p>
 * Replacing the persistent {@code Student} entity in the request body prevents
 * mass-assignment vulnerabilities (java:S4684).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
    /** Only used for update operations. Ignored on creation. */
    private Long idStudent;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String address;
    private Long departmentId;
}

