package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.service.ConfigStoreService;
import fpoly.electroland.util.ResponseEntityUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/admin")
public class ConfigStoreController {

    @Autowired
    ConfigStoreService configStoreService;

    @GetMapping("/addressStore")
    public Object getAddressStore() {
        String addressStore = configStoreService.getKey("addressStore");
        return ResponseEntityUtil.ok("Chưa cấu hình");
    }

}
