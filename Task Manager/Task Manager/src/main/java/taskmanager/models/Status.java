package taskmanager.models;

public enum Status {
    TODO("To Do"),
    IN_PROGRESS("In_progress"),
    COMPLETE("complete");

    private String displayText;

    Status(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }
}
