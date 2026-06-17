package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.repositories.StudentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student sample() {
        Student s = new Student();
        s.setIdStudent(1L);
        s.setFirstName("Ada");
        s.setLastName("Lovelace");
        s.setEmail("ada@example.com");
        return s;
    }

    @Test
    void getAllStudents_returnsList() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(sample(), sample()));

        List<Student> result = studentService.getAllStudents();

        assertThat(result).hasSize(2);
    }

    @Test
    void getStudentById_found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sample()));

        Student result = studentService.getStudentById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("Ada");
    }

    @Test
    void getStudentById_notFound_returnsNull() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        Student result = studentService.getStudentById(99L);

        assertThat(result).isNull();
    }

    @Test
    void saveStudent_persistsEntity() {
        Student s = sample();
        when(studentRepository.save(s)).thenReturn(s);

        Student result = studentService.saveStudent(s);

        assertThat(result).isSameAs(s);
        verify(studentRepository).save(s);
    }

    @Test
    void deleteStudent_callsRepository() {
        studentService.deleteStudent(7L);
        verify(studentRepository).deleteById(7L);
    }
}

