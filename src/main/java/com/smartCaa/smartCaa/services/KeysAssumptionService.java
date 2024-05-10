package com.smartCaa.smartCaa.services;

import com.smartCaa.smartCaa.DTOS.KeysUpdateDTO;
import com.smartCaa.smartCaa.models.KeyAssumptions;
import com.smartCaa.smartCaa.repositories.KeyAssumptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KeysAssumptionService {

    @Autowired
    private KeyAssumptionsRepository keyAssumptionsRepository;


    public List<KeyAssumptions> getByKeys(List<String> keysList){

        return keyAssumptionsRepository.findByKeyIn(keysList);

    }

    public void updateKeys (KeysUpdateDTO keysUpdateDTO){

        Optional<KeyAssumptions> key = keyAssumptionsRepository.findById(keysUpdateDTO.id());

        key.ifPresent(k -> {
            k.setValue(keysUpdateDTO.value());
            keyAssumptionsRepository.save(k);
        });

    }

}
