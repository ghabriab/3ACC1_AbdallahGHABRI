package tn.esprit.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Simple POJO/DTO used to receive department data from clients.
 * <p>
 * Replacing the persistent {@code Department} entity in the request body prevents
 * mass-assignment vulnerabilities (java:S4684).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequest {
    /** Only used for update operations. Ignored on creation. */
    private Long idDepartment;
    private String name;
    private String location;
    private String phone;
    private String head;
}

