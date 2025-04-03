package taskmanager.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskmanager.models.Status;
import taskmanager.models.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskFileRepositoryTest {

    private static final String SEED_FILE_PATH = "./data/tasks-seed.csv";
    private static final String TEST_FILE_PATH = "./data/tasks-test.csv";

    private final TaskFileRepository repository = new TaskFileRepository(TEST_FILE_PATH);

    @BeforeEach
    public void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);

        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @org.junit.jupiter.api.Test
    void findAll() throws DataAccessException {
        List<Task> actual = repository.findAll();
        assertEquals(3, actual.size());

        /*
        1,2023-10-08,Review Curriculum,check content for spelling and grammar,2023-10-10,TODO
2,2023-10-03,Water Plants,water indoor house plants,2023-10-08,COMPLETE
3,2023-09-08,Walk Dogs,take dogs for long walk,2023-09-27,COMPLETE
         */
        Task task = actual.get(0);
        assertEquals(1, task.getId());
        assertEquals("2023-10-08", task.getCreatedOn());
        assertEquals("Review Curriculum", task.getTitle());
        assertEquals("check content for spelling and grammar", task.getDescription());
        assertEquals("2023-10-10", task.getDueDate());
        assertEquals(Status.TODO, task.getStatus());
    }

    @org.junit.jupiter.api.Test
    void findById() throws DataAccessException {
        Task taskOne = repository.findById(1);
        assertNotNull(taskOne);
        assertEquals(1, taskOne.getId());
        assertEquals("2023-10-08", taskOne.getCreatedOn());
        assertEquals("Review Curriculum", taskOne.getTitle());
        assertEquals("check content for spelling and grammar", taskOne.getDescription());
        assertEquals("2023-10-10", taskOne.getDueDate());
        assertEquals(Status.TODO, taskOne.getStatus());
    }

    @Test
    void shouldNotFindId() throws DataAccessException {
        Task notValid = repository.findById(9999);
        assertNull(notValid);
    }

    @org.junit.jupiter.api.Test
    void create() throws DataAccessException {
        Task task = new Task(0, "2024-02-01", "Mom's birthday reminder", "send flowers to mom", "2024-02-05", Status.TODO);
        Task actual = repository.create(task);
        assertEquals(4, actual.getId());

        List<Task> all = repository.findAll();
        assertEquals(4, all.size());

        assertEquals("2024-02-01", actual.getCreatedOn());
        assertEquals("Mom's birthday reminder", actual.getTitle());
        assertEquals("send flowers to mom", actual.getDescription());
        assertEquals("2024-02-05", actual.getDueDate());
        assertEquals(Status.TODO, actual.getStatus());
    }

    @Test
    public void shouldCreateWithCommas() throws DataAccessException {
        Task task = new Task(0, "2024-05-01", "Book venues, wedding, reception, shower", "book venues for upcoming events; wedding, reception, shower", "2024-05-05", Status.IN_PROGRESS);
        Task actual = repository.create(task);
        assertEquals(4, actual.getId());

        List<Task> all = repository.findAll();
        assertEquals(4, all.size());

        assertEquals("2024-05-01", actual.getCreatedOn());
        assertEquals("Book venues, wedding, reception, shower", actual.getTitle());
        assertEquals("book venues for upcoming events; wedding, reception, shower", actual.getDescription());
        assertEquals("2024-05-05", actual.getDueDate());
        assertEquals(Status.IN_PROGRESS, actual.getStatus());
    }

    @org.junit.jupiter.api.Test
    void update() throws DataAccessException {
        Task task = repository.findById(1);
        task.setStatus(Status.IN_PROGRESS);
        task.setDescription("check content for spelling and grammar, and proofread");

        boolean result = repository.update(task);
        assertTrue(result);
        assertNotNull(task);
        assertEquals(1, task.getId());
        assertEquals("2023-10-08", task.getCreatedOn());
        assertEquals("Review Curriculum", task.getTitle());
        assertEquals("check content for spelling and grammar, and proofread", task.getDescription());
        assertEquals("2023-10-10", task.getDueDate());
        assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void shouldNotUpdateUnknownID() throws DataAccessException {
        Task task = new Task(9999, "", "", "", "", Status.TODO);
        boolean result = repository.update(task);
        assertFalse(result);
    }

    @org.junit.jupiter.api.Test
    void delete() throws DataAccessException {
        boolean result = repository.delete(1);
        assertTrue(result);

        List<Task> all = repository.findAll();
        assertEquals(2, all.size());

        Task task = repository.findById(1);
        assertNull(task);
    }

    @Test
    void shouldNotDeleteUnknownID() throws DataAccessException {
        boolean result = repository.delete(999);
        assertFalse(result);
    }
}