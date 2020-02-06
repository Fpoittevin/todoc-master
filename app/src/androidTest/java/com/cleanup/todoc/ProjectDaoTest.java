package com.cleanup.todoc;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.Dao.ProjectDao;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    private TodocDatabase databaseTest;
    private ProjectDao projectDao;
    private List<Project> projects;

    @Before
    public void initDb() {
        databaseTest = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .addCallback(TodocDatabase.prepopulateDatabase())
                .build();

        projectDao = databaseTest.projectDao();
    }

    @After
    public void closeDb() {
        databaseTest.close();
    }

    @Test
    public void getAllProjects() throws InterruptedException {

        projects = LiveDataTestUtil.getValue(projectDao.getProjects());

        assertEquals(projects.size(), Project.getAllProjects().length);
    }
}
