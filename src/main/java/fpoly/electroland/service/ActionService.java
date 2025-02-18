package fpoly.electroland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.Action;
import fpoly.electroland.model.Employee;
import fpoly.electroland.repository.ActionRepository;

@Service
public class ActionService {

    @Autowired
    ActionRepository actionRepository;
    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public void saveAction(Action action) {
        actionRepository.save(action);
    }

    public void createAction(String string, String string2, int id, String oldValue, String string3,
            Employee creatorEmployee) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createAction'");
    }
}
