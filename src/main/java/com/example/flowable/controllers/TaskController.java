package com.example.flowable.controllers;

import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	private void completeTask(String taskId, String action) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("action", action);
		taskService.complete(taskId,"son.tran", variables);
	}
	
	@GetMapping("/approve")
	public void approveTask(@RequestParam String taskId) {
		completeTask(taskId, "APPROVE");
	}

	@GetMapping("/reject")
	public void rejectTask(@RequestParam String taskId) {
		completeTask(taskId, "REJECT");
	}
}
