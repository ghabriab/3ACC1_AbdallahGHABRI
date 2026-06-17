package tn.esprit.studentmanagement.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.dto.StudentRequest;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.services.IStudentService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private IStudentService studentService;

    private StudentController controller() {
        return new StudentController(studentService);
    }

    @Test
    void getAllStudents_delegatesToService() {
        List<Student> data = Arrays.asList(new Student(), new Student());
        when(studentService.getAllStudents()).thenReturn(data);

        assertThat(controller().getAllStudents()).hasSize(2);
    }

    @Test
    void getStudent_delegatesToService() {
        Student s = new Student();
        s.setIdStudent(1L);
        when(studentService.getStudentById(1L)).thenReturn(s);

        assertThat(controller().getStudent(1L).getIdStudent()).isEqualTo(1L);
    }

    @Test
    void createStudent_mapsDtoAndIgnoresId() {
        StudentRequest request = new StudentRequest();
        request.setIdStudent(999L);
        request.setFirstName("Ada");
        request.setLastName("Lovelace");
        request.setEmail("ada@example.com");
        request.setPhone("123");
        request.setDateOfBirth(LocalDate.of(2000, 1, 1));
        request.setAddress("London");
        request.setDepartmentId(4L);
        when(studentService.saveStudent(any())).thenAnswer(i -> i.getArgument(0));

        controller().createStudent(request);

        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(studentService).saveStudent(captor.capture());
        Student saved = captor.getValue();
        assertThat(saved.getIdStudent()).isNull();
        assertThat(saved.getFirstName()).isEqualTo("Ada");
        assertThat(saved.getEmail()).isEqualTo("ada@example.com");
        assertThat(saved.getDepartment().getIdDepartment()).isEqualTo(4L);
    }

    @Test
    void updateStudent_keepsId_andHandlesNullDepartment() {
        StudentRequest request = new StudentRequest();
        request.setIdStudent(8L);
        request.setFirstName("Grace");
        when(studentService.saveStudent(any())).thenAnswer(i -> i.getArgument(0));

        controller().updateStudent(request);

        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(studentService).saveStudent(captor.capture());
        Student saved = captor.getValue();
        assertThat(saved.getIdStudent()).isEqualTo(8L);
        assertThat(saved.getDepartment()).isNull();
    }

    @Test
    void deleteStudent_delegatesToService() {
        controller().deleteStudent(2L);
        verify(studentService).deleteStudent(2L);
    }
}

