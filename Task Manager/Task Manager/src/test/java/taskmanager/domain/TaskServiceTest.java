package taskmanager.domain;

import org.junit.jupiter.api.Test;
import taskmanager.data.DataAccessException;
import taskmanager.data.TaskRepository;
import taskmanager.data.TaskRepositoryDouble;
import taskmanager.models.Status;
import taskmanager.models.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    TaskRepository repository = new TaskRepositoryDouble();
    TaskService service = new TaskService(repository);

    @Test
    void shouldCreateTask() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "2023-05-09", "Prepare snacks", "prepare apple slices and snacks", "2023-05-11", Status.COMPLETE));
        assertNotNull(actual);
        assertTrue(actual.isSuccess());
        assertEquals(99, actual.getTask().getId());
    }

    @Test
    void shouldNotCreateNullTask() throws DataAccessException {
        TaskResult actual = service.create(null);
        assertFalse(actual.isSuccess());
        assertNull(actual.getTask());
        assertEquals("Task cannot be null.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithoutStartDate() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "", "Prepare snacks", "prepare apple slices and snacks", "2023-05-11", Status.COMPLETE));
        assertFalse(actual.isSuccess());
        assertNull(actual.getTask());
        assertEquals("CreatedOn date is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateTaskWithoutTitle() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "2023-05-09", "", "prepare apple slices and snacks", "2023-05-11", Status.COMPLETE));
        assertFalse(actual.isSuccess());
        assertNull(actual.getTask());
        assertEquals("title must exist, and cannot be longer than 50 characters.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateTaskWithTitleLongerThan50Char() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "2023-05-09", "ewroiwhefidnsakjfhqliewjnfdlksafuwejlnmcwljCNhfJADSCDSJFOAJFOASFJOAS", "prepare apple slices and snacks", "2023-05-11", Status.COMPLETE));
        assertFalse(actual.isSuccess());
        assertNull(actual.getTask());
        assertEquals("title must exist, and cannot be longer than 50 characters.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateTaskWithoutDescription() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "2023-05-09", "Prepare snacks", "", "2023-05-11", Status.COMPLETE));
        assertFalse(actual.isSuccess());
        assertNull(actual.getTask());
        assertEquals("Description is required and must be more than 20 characters.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateTaskWithDescriptionLessThan20Char() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "2023-05-09", "Prepare snacks", "prepare", "2023-05-11", Status.COMPLETE));
        assertFalse(actual.isSuccess());
        assertNull(actual.getTask());
        assertEquals("Description is required and must be more than 20 characters.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateTaskWithoutDueDate() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "2023-05-09", "Prepare snacks", "prepare apple slices and snacks", "", Status.COMPLETE));
        assertFalse(actual.isSuccess());
        assertNull(actual.getTask());
        assertEquals("Due date is required.", actual.getMessages().get(0));
    }

    @Test
    void shouldNotCreateTaskWithoutStatus() throws DataAccessException {
        TaskResult actual = service.create(new Task(0, "2023-05-09", "Prepare snacks", "prepare apple slices and snacks", "2023-05-11", null));
        assertFalse(actual.isSuccess());
        assertNull(actual.getTask());
        assertEquals("Status is required.", actual.getMessages().get(0));
    }

    @Test
    void findAll() throws DataAccessException {
        List<Task> actual = service.findAll();
        assertEquals(3, actual.size());
        Task task = actual.get(0);

        assertEquals(1, task.getId());
        assertEquals("2024-08-01", task.getCreatedOn());
        assertEquals("Bake cake", task.getTitle());
        assertEquals("bake a cake for rovers birthday", task.getDescription());
        assertEquals("2024-08-02", task.getDueDate());
        assertEquals(Status.TODO, task.getStatus());
    }

    @Test
    void findById() throws DataAccessException {
        Task task = service.findById(1);
        assertNotNull(task);

        assertEquals(1, task.getId());
        assertEquals("2024-08-01", task.getCreatedOn());
        assertEquals("Bake cake", task.getTitle());
        assertEquals("bake a cake for rovers birthday", task.getDescription());
        assertEquals("2024-08-02", task.getDueDate());
        assertEquals(Status.TODO, task.getStatus());
    }

    @Test
    void shouldNotFindUnknownId() throws DataAccessException {
        Task actual = service.findById(9999);
        assertNull(actual);
    }

    @Test
    void shouldUpdateExistingTask() throws DataAccessException {
        List<Task> all = service.findAll();
        Task toUpdate = all.get(0);
        toUpdate.setDescription("This is updated test description.");

        TaskResult actual = service.update(toUpdate);
        assertTrue(actual.isSuccess());
        assertEquals(0, actual.getMessages().size());
        assertEquals("This is updated test description.", all.get(0).getDescription());
    }

    @Test
    void shouldNotUpdateUnknownTask() throws DataAccessException {
        Task task = new Task(0, "2024-08-01", "Fake Task", "this is a fake description", "2024-08-22", Status.TODO);
        TaskResult actual = service.update(task);
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
        assertTrue(actual.getMessages().get(0).contains("does not exist"));
    }

    @Test
    void shouldNotUpdateNullTask() throws DataAccessException {
        TaskResult actual = service.update(null);
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
        assertEquals("Task cannot be null.", actual.getMessages().get(0));
    }

    @Test
    void shouldDeleteTask() throws DataAccessException {
        TaskResult actual = service.deleteById(1);
        assertTrue(actual.isSuccess());
    }

    @Test
    void shouldNotDeleteUnknownTask() throws DataAccessException {
        TaskResult actual = service.deleteById(999);
        assertFalse(actual.isSuccess());
        assertEquals(1, actual.getMessages().size());
        assertTrue(actual.getMessages().get(0).contains("does not exist"));
    }
}