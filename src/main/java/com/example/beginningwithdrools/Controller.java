package com.example.beginningwithdrools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final DecisioningService decisioningService;

    @Autowired
    public Controller(DecisioningService decisioningService) {
        this.decisioningService = decisioningService;
    }

    @GetMapping("getDecision/{param}")
    String getDecision(@PathVariable String param){
        return decisioningService.getDecision(param);
    }
}
