package com.example.flowable.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flowable.entities.DoaRule;
import com.example.flowable.services.DoaRuleService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RequestMapping("/api/v1/doaRules")
@RestController
public class DOARuleController {

    public DOARuleController(DoaRuleService doaRuleService) {
        this.doaRuleService = doaRuleService;
    }

    private DoaRuleService doaRuleService;
    
    //method to get all doa rules, return list of doa rules
    @GetMapping("/getAll")
    public List<DoaRule> getAllDoaRules() {
        List<DoaRule> doaRules = doaRuleService.findAll();
        return doaRules;
    }
    
}
