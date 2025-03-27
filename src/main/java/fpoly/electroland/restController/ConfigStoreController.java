package fpoly.electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import fpoly.electroland.service.ConfigStoreService;
import fpoly.electroland.util.ResponseEntityUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/admin")
public class ConfigStoreController {

    @Autowired
    ConfigStoreService configStoreService;

    @GetMapping("/addressStore")
    public Object getAddressStore() {
        String addressStore = configStoreService.getKey("addressStore");
        addressStore = addressStore == null ? "Chưa cấu hình" : addressStore;
        return ResponseEntityUtil.ok(addressStore);
    }

    @PostMapping("/addressStore")
    public Object postAddressStore(@RequestParam Integer WardCode, @RequestParam Integer DistrictID,
            @RequestParam String addressStore) {
        configStoreService.setKey("addressStore", addressStore);
        configStoreService.setKey("WardCode", WardCode.toString());
        configStoreService.setKey("DistrictID", DistrictID.toString());
        return ResponseEntityUtil.ok(addressStore);
    }

    @GetMapping("/key")
    public Object getKey(@RequestParam String key) {
        String value = configStoreService.getKey(key);
        return ResponseEntityUtil.ok(value);
    }

    @PostMapping("/key")
    public Object setKey(@RequestParam String key, @RequestParam String value) {
        String respon = configStoreService.setKey(key, value);
        return ResponseEntityUtil.ok(respon);
    }

}
