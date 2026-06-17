package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private Department sample() {
        Department d = new Department();
        d.setIdDepartment(1L);
        d.setName("Computer Science");
        d.setLocation("Building A");
        return d;
    }

    @Test
    void getAllDepartments_returnsList() {
        when(departmentRepository.findAll()).thenReturn(Arrays.asList(sample(), sample()));

        List<Department> result = departmentService.getAllDepartments();

        assertThat(result).hasSize(2);
    }

    @Test
    void getDepartmentById_returnsEntity() {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(sample()));

        Department result = departmentService.getDepartmentById(1L);

        assertThat(result.getName()).isEqualTo("Computer Science");
    }

    @Test
    void saveDepartment_persistsEntity() {
        Department d = sample();
        when(departmentRepository.save(d)).thenReturn(d);

        Department result = departmentService.saveDepartment(d);

        assertThat(result).isSameAs(d);
        verify(departmentRepository).save(d);
    }

    @Test
    void deleteDepartment_callsRepository() {
        departmentService.deleteDepartment(3L);
        verify(departmentRepository).deleteById(3L);
    }
}

