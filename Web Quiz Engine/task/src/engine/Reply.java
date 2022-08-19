package engine;

public class Reply {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    private String feedback;

    public String getFeedback() {
        return feedback;
    }

    public static final Reply WIN = new Reply(true, "Congratulations, you're right!");
    public static final Reply LOSE = new Reply(false, "Wrong answer! Please, try again.");

    public Reply(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }
}
