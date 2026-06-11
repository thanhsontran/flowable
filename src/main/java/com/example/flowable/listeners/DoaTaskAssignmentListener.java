package com.example.flowable.listeners;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.stereotype.Component;

@Component
public class DoaTaskAssignmentListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
    }
}