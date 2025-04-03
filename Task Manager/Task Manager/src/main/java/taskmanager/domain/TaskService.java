package taskmanager.domain;

import taskmanager.data.DataAccessException;
import taskmanager.data.TaskRepository;
import taskmanager.models.Task;

import java.util.List;

public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public Task findById(int taskId) throws DataAccessException {
        return repository.findById(taskId);
    }

    public TaskResult create(Task task) throws DataAccessException {
        TaskResult result = validate(task);
        if (!result.isSuccess()) {
            return result;
        }
        if (task == null) {
            result.addMessage("Task cannot be null.");
            return result;
        }
        if (task.getId() > 0) {
            result.addMessage("Cannot create an existing task.");
            return result;
        }
        task = repository.create(task);
        result.setTask(task);
        return result;
    }

    public TaskResult update(Task task) throws DataAccessException {
        TaskResult result = validate(task);
        if (!result.isSuccess()) {
            return result;
        }
        boolean updated = repository.update(task);
        if (!updated) {
            result.addMessage(String.format("Task with id %s does not exist.", task.getId()));
        }
        return result;
    }

    public TaskResult deleteById(int taskId) throws DataAccessException {
        TaskResult result = new TaskResult();
        if (!repository.delete(taskId)) {
            result.addMessage(String.format("Task with id: %s does not exist", taskId));
        }
        return result;
    }

    public TaskResult validate(Task task) {
        TaskResult result = new TaskResult();
        if (task == null) {
            result.addMessage("Task cannot be null.");
            return result;
        }
        if (task.getCreatedOn() == null || task.getCreatedOn().isBlank()) {
            result.addMessage("CreatedOn date is required.");
            return result;
        }
        if (task.getTitle() == null || task.getTitle().isBlank() || task.getTitle().length() > 50) {
            result.addMessage("title must exist, and cannot be longer than 50 characters.");
            return result;
        }
        if (task.getDescription() == null || task.getDescription().isBlank() || task.getDescription().length() < 20) {
            result.addMessage("Description is required and must be more than 20 characters.");
            return result;
        }
        if (task.getDueDate() == null || task.getDueDate().isBlank()) {
            result.addMessage("Due date is required.");
            return result;
        }
        if (task.getStatus() == null) {
            result.addMessage("Status is required.");
            return result;
        }
        return result;
    }
}
