package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fpoly.electroland.model.Action;
import fpoly.electroland.model.Employee;
import fpoly.electroland.repository.ActionRepository;

@ExtendWith(MockitoExtension.class) 
public class ActionTest {
    
    @Mock
    ActionRepository actionRepository;

    @InjectMocks
    ActionService actionService;

    @Test
    void saveAction(){
        Action action = new Action();
        actionService.saveAction(action);
        verify(actionRepository, times(1)).save(action);
    }

    @Test
    void createAction(){
        String param1 = "Test";
        String param2 = "Value";
        int id = 1;
        String oldValue = "Old";
        String param3 = "New";
        Employee creatorEmployee = new Employee();

        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> {
            actionService.createAction(param1, param2, id, oldValue, param3, creatorEmployee);
        });

        assertEquals("Unimplemented method 'createAction'", exception.getMessage());
    }

    @Test
    void getListAction(){
        List<Action> list = new ArrayList<>();
        when(actionRepository.findAll()).thenReturn(list);
        List<Action> result = actionService.getListAction();
        assertEquals(list, result, "oke");
    }

    @Test
    void searchAction_ShouldReturnMatchingActions() {
        Employee employee = new Employee();
        employee.setFullName("Dao tan kiet");
        String keyword = "update";
        Action action1 = new Action(1, "Table1", "update", 1, null, keyword, keyword, employee);
        Action action2 = new Action(2, "Table2", "create", 0, null, keyword, keyword, employee);

        List<Action> mockActions = Arrays.asList(action1, action2);

        when(actionRepository.findByTableNameContainingOrActionContainingOrEmployee_FullNameContaining(keyword, keyword, keyword))
                .thenReturn(mockActions);

        List<Action> result = actionService.searchAction(keyword);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(action1, result.get(0));
        assertEquals(action2, result.get(1));

        verify(actionRepository, times(1))
                .findByTableNameContainingOrActionContainingOrEmployee_FullNameContaining(keyword, keyword, keyword);
    }
}
