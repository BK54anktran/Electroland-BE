package fpoly.electroland.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpoly.electroland.model.ConfigStore;
import fpoly.electroland.repository.ConfigStoreRepository;

@Service
public class ConfigStoreService {

    @Autowired
    ConfigStoreRepository configStoreRepository;

    public String getKey(String key) {
        Optional<ConfigStore> configStore = configStoreRepository.findByKeyword(key);
        if (configStore.isPresent()) {
            return configStore.get().getValue();
        }
        return null;
    }

    public String setKey(String key, String value) {
        Optional<ConfigStore> configStore = configStoreRepository.findByKeyword(key);
        if (configStore.isPresent()) {
            configStore.get().setValue(value);
            configStoreRepository.save(configStore.get());
            return value;
        } else {
            ConfigStore newConfigStore = new ConfigStore();
            newConfigStore.setKeyword(key);
            newConfigStore.setValue(value);
            configStoreRepository.save(newConfigStore);
            return value;
        }
    }
}
