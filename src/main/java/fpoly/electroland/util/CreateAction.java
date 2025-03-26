package fpoly.electroland.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fpoly.electroland.model.Action;
import fpoly.electroland.model.Employee;
import fpoly.electroland.repository.ActionRepository;
import fpoly.electroland.service.ActionService;

@Component
public class CreateAction {
    @Autowired
    ActionService actionService;

    public void createAction(String tableName, String actionName, int objectID, String oldValue, String newValue,
            Employee creatorEmployee) {
        // Tạo bản ghi Action
        Action action = new Action();
        action.setTableName(tableName);
        action.setAction(actionName);
        action.setIdRecord(objectID);
        action.setOldValue(oldValue); // Gắn giá trị cũ
        action.setNewValue(newValue); // Gắn giá trị mới
        action.setEmployee(creatorEmployee); // Gắn nhân viên tạo vào Action

        // Lưu Action vào cơ sở dữ liệu
        actionService.saveAction(action);

        // Log thông tin
        // System.out.println("Action created: " + action);
    }
}
