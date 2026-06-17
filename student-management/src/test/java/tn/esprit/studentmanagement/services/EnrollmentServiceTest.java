package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.entities.Status;
import tn.esprit.studentmanagement.repositories.EnrollmentRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private Enrollment sample() {
        Enrollment e = new Enrollment();
        e.setIdEnrollment(1L);
        e.setEnrollmentDate(LocalDate.of(2024, 1, 1));
        e.setGrade(15.0);
        e.setStatus(Status.ACTIVE);
        return e;
    }

    @Test
    void getAllEnrollments_returnsList() {
        List<Enrollment> data = Arrays.asList(sample(), sample());
        when(enrollmentRepository.findAll()).thenReturn(data);

        List<Enrollment> result = enrollmentService.getAllEnrollments();

        assertThat(result).hasSize(2);
        verify(enrollmentRepository).findAll();
    }

    @Test
    void getEnrollmentById_returnsEntity() {
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(sample()));

        Enrollment result = enrollmentService.getEnrollmentById(1L);

        assertThat(result.getIdEnrollment()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo(Status.ACTIVE);
    }

    @Test
    void saveEnrollment_persistsEntity() {
        Enrollment e = sample();
        when(enrollmentRepository.save(e)).thenReturn(e);

        Enrollment result = enrollmentService.saveEnrollment(e);

        assertThat(result).isSameAs(e);
        verify(enrollmentRepository).save(e);
    }

    @Test
    void deleteEnrollment_callsRepository() {
        enrollmentService.deleteEnrollment(5L);
        verify(enrollmentRepository).deleteById(5L);
    }
}

