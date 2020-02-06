package com.cleanup.todoc.injection;

import android.content.Context;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskRepository provideTaskRepository(Context context) {
        TodocDatabase todocDatabase = TodocDatabase.getInstance(context);
        return new TaskRepository(todocDatabase.taskDao());
    }

    public static ProjectRepository provideProjectRepository(Context context) {
        TodocDatabase todocDatabase = TodocDatabase.getInstance(context);
        return new ProjectRepository(todocDatabase.projectDao());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        return new ViewModelFactory(provideTaskRepository(context), provideProjectRepository(context), provideExecutor());
    }
}