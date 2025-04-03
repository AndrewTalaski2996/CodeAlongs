package taskmanager.ui;

import taskmanager.models.Status;
import taskmanager.models.Task;

import java.util.List;
import java.util.Scanner;

public class View {
    Scanner console = new Scanner(System.in);

    public int getMenuOption() {
        displayHeader("Welcome to the Main Menu");
        displayText("1. Add a task");
        displayText("2. View all tasks");
        displayText("3. Update a task");
        displayText("4. Delete a task");
        displayText("5. Exit");
        return readInt("What would you like to do? [1-5]", 1, 5);
    }

    public Task makeTask() {
        Task result = new Task();
        result.setCreatedOn(readString("Enter the date: "));
        result.setTitle(readString("Enter the title: "));
        result.setDescription(readString("Enter the description: "));
        result.setDueDate(readString("Enter the due date: "));
        result.setStatus(readStatus("Enter the status: "));
        return result;
    }

    public void displayTasks(List<Task> tasks) {
        for (Task task : tasks) {
            displayText(String.format("Id: %s,%nDate: %s,%nTitle: %s,%nDescription: %s,%nDue date: %s,%nStatus: %s%n",
                    task.getId(), task.getCreatedOn(), task.getTitle(), task.getDescription(), task.getDueDate(), task.getStatus()));
        }
    }

    public int updateById() {
        displayText("Which id would you like to modify?: ");
        int id = readInt("Enter the id: ", 1, 99999999);
        return id;
    }

    //helper methods
    public void displayHeader(String header) {
        System.out.println();
        System.out.println(header);
        System.out.println("*".repeat(header.length()));
    }

    public void displayText(String line) {
        System.out.println();
        System.out.println(line);
    }

    public void displayErrors(List<String> errors) {
        displayHeader("Errors: ");
        for (String error : errors) {
            displayText(error);
        }
        displayText("");
    }

    public String readString(String prompt) {
        displayText(prompt);
        String string = console.nextLine();
        if (string == null || string.isBlank()) {
            displayText("You must enter a value.");
            string = readString(prompt);
        }
        return string;
    }

    public int readInt(String prompt, int min, int max) {
        while(true) {
            String value = readString(prompt);
            try {
                int intValue = Integer.parseInt(value);
                if (intValue < min || intValue > max) {
                    System.out.printf("Sorry, that's not a valid choice, please choose another number between [%s - %s]%n", min, max);
                } else {
                    return intValue;
                }
            } catch (NumberFormatException ex) {
                System.out.printf("%s is not a valid number.%n", value);
            }
        }
    }

    public Status readStatus(String prompt) {
        displayHeader("Task Status:");
        for(Status status : Status.values()) {
            displayText(status.getDisplayText());
        }
        while(true) {
            String selection = readString(prompt);
            selection = selection.toUpperCase();
            try {
                return Status.valueOf(selection);
            } catch (IllegalArgumentException ex) {
                System.out.printf("%s is not a status.%n", selection);
            }
        }
    }
}
