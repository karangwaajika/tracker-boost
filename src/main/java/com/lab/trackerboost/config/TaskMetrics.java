package com.lab.trackerboost.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

public class TaskMetrics {
    private final Counter tasksProcessed;

    public TaskMetrics(MeterRegistry meterRegistry) {
        this.tasksProcessed = meterRegistry.counter("tasks_processed_total");
    }

    public void incrementTasksProcessed() {
        tasksProcessed.increment();
    }
}
