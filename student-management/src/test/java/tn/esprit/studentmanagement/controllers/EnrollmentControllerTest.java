package tn.esprit.studentmanagement.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.dto.EnrollmentRequest;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.entities.Status;
import tn.esprit.studentmanagement.services.IEnrollment;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentControllerTest {

    @Mock
    private IEnrollment enrollmentService;

    private EnrollmentController controller() {
        return new EnrollmentController(enrollmentService);
    }

    @Test
    void getAllEnrollment_delegatesToService() {
        List<Enrollment> data = Arrays.asList(new Enrollment(), new Enrollment());
        when(enrollmentService.getAllEnrollments()).thenReturn(data);

        assertThat(controller().getAllEnrollment()).hasSize(2);
    }

    @Test
    void getEnrollment_delegatesToService() {
        Enrollment e = new Enrollment();
        e.setIdEnrollment(1L);
        when(enrollmentService.getEnrollmentById(1L)).thenReturn(e);

        assertThat(controller().getEnrollment(1L).getIdEnrollment()).isEqualTo(1L);
    }

    @Test
    void createEnrollment_mapsDtoAndIgnoresId() {
        EnrollmentRequest request = new EnrollmentRequest();
        request.setIdEnrollment(999L); // must be ignored on creation
        request.setEnrollmentDate(LocalDate.of(2024, 5, 1));
        request.setGrade(18.0);
        request.setStatus(Status.ACTIVE);
        request.setStudentId(10L);
        request.setCourseId(20L);
        when(enrollmentService.saveEnrollment(any())).thenAnswer(i -> i.getArgument(0));

        controller().createEnrollment(request);

        ArgumentCaptor<Enrollment> captor = ArgumentCaptor.forClass(Enrollment.class);
        verify(enrollmentService).saveEnrollment(captor.capture());
        Enrollment saved = captor.getValue();
        assertThat(saved.getIdEnrollment()).isNull();
        assertThat(saved.getGrade()).isEqualTo(18.0);
        assertThat(saved.getStatus()).isEqualTo(Status.ACTIVE);
        assertThat(saved.getStudent().getIdStudent()).isEqualTo(10L);
        assertThat(saved.getCourse().getIdCourse()).isEqualTo(20L);
    }

    @Test
    void updateEnrollment_keepsId_andHandlesNullRelations() {
        EnrollmentRequest request = new EnrollmentRequest();
        request.setIdEnrollment(5L);
        request.setStatus(Status.COMPLETED);
        when(enrollmentService.saveEnrollment(any())).thenAnswer(i -> i.getArgument(0));

        controller().updateEnrollment(request);

        ArgumentCaptor<Enrollment> captor = ArgumentCaptor.forClass(Enrollment.class);
        verify(enrollmentService).saveEnrollment(captor.capture());
        Enrollment saved = captor.getValue();
        assertThat(saved.getIdEnrollment()).isEqualTo(5L);
        assertThat(saved.getStudent()).isNull();
        assertThat(saved.getCourse()).isNull();
    }

    @Test
    void deleteEnrollment_delegatesToService() {
        controller().deleteEnrollment(3L);
        verify(enrollmentService).deleteEnrollment(3L);
    }
}

