package taskmanager.ui;

import taskmanager.data.DataAccessException;
import taskmanager.domain.TaskResult;
import taskmanager.domain.TaskService;
import taskmanager.models.Task;

import java.util.List;

//Mediates between the view and domain layer
//2 dependencies: view, service
public class Controller {
    private final View view;
    private final TaskService taskService;


    public Controller(View view, TaskService taskService) {
        this.view = view;
        this.taskService = taskService;
    }

    //run application
    public void run() {
        view.displayHeader("Welcome to the Task Manager");
        try {
            runMenu();
        } catch (DataAccessException e) {
            view.displayText("Something went wrong.");
            view.displayText(e.getMessage());
        }
        view.displayText("Goodbye!");
    }

    private void runMenu() throws DataAccessException {
        boolean exit = false;

        while (!exit) {
            int selection = view.getMenuOption();
            switch (selection) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    updateTask();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    exit = true;
                    break;
            }
        }
    }

    private void addTask() throws DataAccessException {
        Task task = view.makeTask();

        TaskResult result = taskService.create(task);

        if (result.isSuccess()) {
            view.displayText("Your task was successfully created.");
        } else {
            view.displayErrors(result.getMessages());
        }
    }

    private void viewTasks() throws DataAccessException {
        List<Task> tasks = taskService.findAll();
        view.displayTasks(tasks);
    }

    private void updateTask() throws DataAccessException {
        view.displayHeader("Update a Task");
        int id = view.updateById();
        Task task = taskService.findById(id);
        if (task != null) {
            Task updatedTask = view.makeTask();
            updatedTask.setId(task.getId());
            TaskResult result = taskService.update(updatedTask);
            if (result.isSuccess()) {
                view.displayText("Success. Your task has been updated.");
            }
        } else {
            view.displayErrors(List.of(String.format("There is no task with id %s", id)));
        }
    }

    private void deleteTask() throws DataAccessException {
        view.displayHeader("Delete a Task");
        Task task = taskService.findById(view.updateById());
        if (task != null) {
            TaskResult result = taskService.deleteById(task.getId());
            if (result.isSuccess()) {
                view.displayText("Your task was successfully deleted.");
            }
        } else {
            view.displayErrors(List.of("There are no tasks with that id."));
        }
    }
}
