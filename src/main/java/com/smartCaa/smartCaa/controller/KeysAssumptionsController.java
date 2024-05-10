package com.smartCaa.smartCaa.controller;

import com.smartCaa.smartCaa.DTOS.KeysUpdateDTO;
import com.smartCaa.smartCaa.models.KeyAssumptions;
import com.smartCaa.smartCaa.services.KeysAssumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("keys")
public class KeysAssumptionsController {

    @Autowired
    KeysAssumptionService keysAssumptionService;

    @GetMapping("/getByKeys")
    public List<KeyAssumptions> getByKeys(@RequestParam List<String> keys) {
        return keysAssumptionService.getByKeys(keys);
    }

    @PutMapping("/updateKeys")
    public void updateKeys(@RequestBody KeysUpdateDTO keysUpdateDTO){
        keysAssumptionService.updateKeys(keysUpdateDTO);
    }
}
