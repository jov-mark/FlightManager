package response;

public class ServerResponse {
    private String type;
    private String message;
    private boolean executed;

    public ServerResponse() {
        type="";
        message="ER";
        executed=false;
    }
    public ServerResponse(String type) {
        this.type = type;
        this.message = "ER";
        this.executed = false;
    }

    public ServerResponse(String type, String message, boolean executed) {
        this.type = type;
        this.message = message;
        this.executed = executed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    @Override
    public String toString() {
        return "ServerResponse{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", executed=" + executed +
                '}';
    }
}
