package tn.esprit.studentmanagement.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.studentmanagement.dto.StudentRequest;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.services.IStudentService;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class StudentController {
    IStudentService studentService;

    @GetMapping("/getAllStudents")
    public List<Student> getAllStudents() { return studentService.getAllStudents(); }

    @GetMapping("/getStudent/{id}")
    public Student getStudent(@PathVariable Long id) { return studentService.getStudentById(id); }

    @PostMapping("/createStudent")
    public Student createStudent(@RequestBody StudentRequest request) {
        return studentService.saveStudent(toEntity(request, null));
    }

    @PutMapping("/updateStudent")
    public Student updateStudent(@RequestBody StudentRequest request) {
        return studentService.saveStudent(toEntity(request, request.getIdStudent()));
    }

    @DeleteMapping("/deleteStudent/{id}")
    public void deleteStudent(@PathVariable Long id) { studentService.deleteStudent(id); }

    /**
     * Maps the whitelisted DTO fields onto a persistent entity. Relations are
     * resolved by id only, which avoids mass-assignment of nested entity state.
     */
    private Student toEntity(StudentRequest request, Long id) {
        Student student = new Student();
        student.setIdStudent(id);
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setAddress(request.getAddress());
        if (request.getDepartmentId() != null) {
            Department department = new Department();
            department.setIdDepartment(request.getDepartmentId());
            student.setDepartment(department);
        }
        return student;
    }
}
