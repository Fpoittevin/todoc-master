package com.cleanup.todoc.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final Executor executor;

    public TaskViewModel(TaskRepository taskRepository, ProjectRepository projectRepository, Executor executor) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.executor = executor;
    }

    // PROJECTS

    public LiveData<List<Project>> getProjects() {

        return projectRepository.getProjects();
    }

    // TASKS

    public LiveData<List<Task>> getTasks() {
        return taskRepository.getTasks();
    }

    public void createTask(Task task) {
        executor.execute(() -> {
            taskRepository.createTask(task);
        });
    }

    public void deleteTask(Task task) {
        executor.execute(() -> {
            taskRepository.deleteTask(task);
        });
    }
}
