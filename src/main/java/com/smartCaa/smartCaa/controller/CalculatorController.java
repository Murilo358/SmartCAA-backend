package com.smartCaa.smartCaa.controller;

import com.smartCaa.smartCaa.models.ProjectReference;
import com.smartCaa.smartCaa.services.ProjectReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("calculate")
public class CalculatorController {

    @Autowired
    ProjectReferenceService projectReferenceService;


    @GetMapping("/calculateProjects")
    public List<ProjectReference> calculatorReturnDto() {
        return projectReferenceService.useCalculator();
    }
}
