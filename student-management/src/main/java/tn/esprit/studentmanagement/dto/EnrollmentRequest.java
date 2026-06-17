package tn.esprit.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.studentmanagement.entities.Status;

import java.time.LocalDate;

/**
 * Simple POJO/DTO used to receive enrollment data from clients.
 * <p>
 * Using a dedicated request object (instead of the persistent {@code Enrollment}
 * entity) prevents mass-assignment vulnerabilities (java:S4684): clients can only
 * set the whitelisted fields below and cannot inject arbitrary nested entity state.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequest {
    /** Only used for update operations. Ignored on creation. */
    private Long idEnrollment;
    private LocalDate enrollmentDate;
    private Double grade;
    private Status status;
    private Long studentId;
    private Long courseId;
}

