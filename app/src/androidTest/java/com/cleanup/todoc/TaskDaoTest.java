package com.cleanup.todoc;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.Dao.ProjectDao;
import com.cleanup.todoc.database.Dao.TaskDao;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase databaseTest;
    private TaskDao taskDao;
    private ProjectDao projectDao;

    private List<Task> tasks;
    private List<Project> projects;

    @Before
    public void initDb() {
        databaseTest = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .addCallback(TodocDatabase.prepopulateDatabase())
                .build();

        taskDao = databaseTest.taskDao();
        projectDao = databaseTest.projectDao();
    }

    @Before
    public void getAllProjects() throws InterruptedException {
        projects = LiveDataTestUtil.getValue(projectDao.getProjects());
    }

    @After
    public void closeDb() {
        databaseTest.close();
    }

    @Test
    public void insertAndDeleteTask() throws InterruptedException {
        updateTasksList();
        int numberOfTasks = tasks.size();

        Task task = new Task(projects.get(0).getId(), "task test", new Date().getTime());
        Long taskId = taskDao.insertTask(task);
        task.setId(taskId);

        updateTasksList();
        assertEquals(tasks.size(), numberOfTasks + 1);

        taskDao.deleteTask(task);
        updateTasksList();
        assertEquals(tasks.size(), numberOfTasks);
    }

    @Test
    public void getAllTasksAfterInsertTwoTasks() throws InterruptedException {
        Task task1 = new Task(projects.get(0).getId(), "task test 1", new Date().getTime());
        Task task2 = new Task(projects.get(1).getId(), "task test 2", new Date().getTime());

        updateTasksList();

        assertTrue(tasks.size() == 0);

        taskDao.insertTask(task1);
        taskDao.insertTask(task2);

        updateTasksList();

        assertTrue(tasks.size() == 2);
    }

    private void updateTasksList() throws InterruptedException {
        tasks = LiveDataTestUtil.getValue(taskDao.getTasks());
    }
}