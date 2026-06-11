package com.example.flowable.controllers;

import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class PRController {

    @Autowired
    private RuntimeService runtimeService;
    

    @GetMapping("/startPR")
    public String createPR(@RequestParam String action) {
        //Start flowable process here
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("action", action);
        ProcessInstance process = runtimeService.startProcessInstanceByKey("genericDoaProcess",variables);
        //get flowable process instance id
        String id = process.getId();
        return "Purchase Request Created - Start Flowable Process : " + id;
    }
}
