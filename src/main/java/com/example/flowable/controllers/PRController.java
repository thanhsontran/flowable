package com.example.flowable.controllers;

import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.flowable.services.DoaRuleService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/pr")
@RestController
public class PRController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private DoaRuleService doaRuleService;
    

    @GetMapping("/createPR")
    public String createPR() {
        // TODO: create purchase request here
        
        // TODO: Set flowable process variables here
        Map<String, Object> variables = new HashMap<>();

        //Start flowable process
        ProcessInstance process = runtimeService.startProcessInstanceByKey("genericDoaProcess",variables);
        //get flowable process instance id
        String id = process.getId();
        return "Purchase Request Created - Start Flowable Process : " + id + "   with size of doa rules: " + doaRuleService.findAll().size();
    }
}
