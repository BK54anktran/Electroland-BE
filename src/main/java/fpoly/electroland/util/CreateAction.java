package fpoly.electroland.util;

import org.springframework.beans.factory.annotation.Autowired;

import fpoly.electroland.model.Action;
import fpoly.electroland.model.Employee;
import fpoly.electroland.repository.ActionRepository;

public class CreateAction{
    @Autowired
    ActionRepository actionRepository;
    public void createAction(Object object,String tableName,String actionName, int ObjectID,String oldValue,String newValue,
    Employee creatorEmployee){ {
        // Tạo bản ghi Action
        Action action = new Action();
        action.setTableName(tableName);
        action.setAction(actionName);
        action.setIdRecord(ObjectID);
        action.setOldValue(oldValue); // Không có giá trị cũ vì đây là bản ghi mới
        action.setNewValue(newValue);
        action.setEmployee(creatorEmployee); // Gắn nhân viên tạo vào Action
    
        // Lưu Action vào cơ sở dữ liệu
        actionRepository.save(action);
        System.out.println("Action created" + action.toString());
    }
}
}
