package tn.esprit.studentmanagement.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.studentmanagement.dto.EnrollmentRequest;
import tn.esprit.studentmanagement.entities.Course;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.services.IEnrollment;

import java.util.List;

@RestController
@RequestMapping("/Enrollment")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class EnrollmentController {
    IEnrollment enrollmentService;

    @GetMapping("/getAllEnrollment")
    public List<Enrollment> getAllEnrollment() { return enrollmentService.getAllEnrollments(); }

    @GetMapping("/getEnrollment/{id}")
    public Enrollment getEnrollment(@PathVariable Long id) { return enrollmentService.getEnrollmentById(id); }

    @PostMapping("/createEnrollment")
    public Enrollment createEnrollment(@RequestBody EnrollmentRequest request) {
        return enrollmentService.saveEnrollment(toEntity(request, null));
    }

    @PutMapping("/updateEnrollment")
    public Enrollment updateEnrollment(@RequestBody EnrollmentRequest request) {
        return enrollmentService.saveEnrollment(toEntity(request, request.getIdEnrollment()));
    }

    @DeleteMapping("/deleteEnrollment/{id}")
    public void deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
    }

    /**
     * Maps the whitelisted DTO fields onto a persistent entity. Relations are
     * resolved by id only, which avoids mass-assignment of nested entity state.
     */
    private Enrollment toEntity(EnrollmentRequest request, Long id) {
        Enrollment enrollment = new Enrollment();
        enrollment.setIdEnrollment(id);
        enrollment.setEnrollmentDate(request.getEnrollmentDate());
        enrollment.setGrade(request.getGrade());
        enrollment.setStatus(request.getStatus());
        if (request.getStudentId() != null) {
            Student student = new Student();
            student.setIdStudent(request.getStudentId());
            enrollment.setStudent(student);
        }
        if (request.getCourseId() != null) {
            Course course = new Course();
            course.setIdCourse(request.getCourseId());
            enrollment.setCourse(course);
        }
        return enrollment;
    }
}
