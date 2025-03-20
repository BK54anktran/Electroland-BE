package fpoly.electroland.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.model.Action;
import fpoly.electroland.service.ActionService;

@RestController
@RequestMapping("/admin/action")
public class AdminActionController {

    @Autowired
    ActionService actionService;
    
    @GetMapping
    public List<Action> getListAction(){
        return actionService.getListAction();
    }

    @GetMapping("/search")
    public List<Action> searchAction(@RequestParam String keyword){
        return actionService.searchAction(keyword);
    }
}
