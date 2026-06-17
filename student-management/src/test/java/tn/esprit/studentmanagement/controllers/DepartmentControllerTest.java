package tn.esprit.studentmanagement.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.dto.DepartmentRequest;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.services.IDepartmentService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

    @Mock
    private IDepartmentService departmentService;

    private DepartmentController controller() {
        return new DepartmentController(departmentService);
    }

    @Test
    void getAllDepartment_delegatesToService() {
        List<Department> data = Arrays.asList(new Department(), new Department());
        when(departmentService.getAllDepartments()).thenReturn(data);

        assertThat(controller().getAllDepartment()).hasSize(2);
    }

    @Test
    void getDepartment_delegatesToService() {
        Department d = new Department();
        d.setIdDepartment(1L);
        when(departmentService.getDepartmentById(1L)).thenReturn(d);

        assertThat(controller().getDepartment(1L).getIdDepartment()).isEqualTo(1L);
    }

    @Test
    void createDepartment_mapsDtoAndIgnoresId() {
        DepartmentRequest request = new DepartmentRequest();
        request.setIdDepartment(999L);
        request.setName("Computer Science");
        request.setLocation("Building A");
        request.setPhone("555");
        request.setHead("Dr. Turing");
        when(departmentService.saveDepartment(any())).thenAnswer(i -> i.getArgument(0));

        controller().createDepartment(request);

        ArgumentCaptor<Department> captor = ArgumentCaptor.forClass(Department.class);
        verify(departmentService).saveDepartment(captor.capture());
        Department saved = captor.getValue();
        assertThat(saved.getIdDepartment()).isNull();
        assertThat(saved.getName()).isEqualTo("Computer Science");
        assertThat(saved.getHead()).isEqualTo("Dr. Turing");
    }

    @Test
    void updateDepartment_keepsId() {
        DepartmentRequest request = new DepartmentRequest();
        request.setIdDepartment(6L);
        request.setName("Maths");
        when(departmentService.saveDepartment(any())).thenAnswer(i -> i.getArgument(0));

        controller().updateDepartment(request);

        ArgumentCaptor<Department> captor = ArgumentCaptor.forClass(Department.class);
        verify(departmentService).saveDepartment(captor.capture());
        assertThat(captor.getValue().getIdDepartment()).isEqualTo(6L);
    }

    @Test
    void deleteDepartment_delegatesToService() {
        controller().deleteDepartment(4L);
        verify(departmentService).deleteDepartment(4L);
    }
}

