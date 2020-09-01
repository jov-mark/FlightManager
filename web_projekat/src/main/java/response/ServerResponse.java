package response;

public class ServerResponse {
    private String type;
    private String message;
    private int status;
    private boolean executed;

    public ServerResponse() {
        type="";
        message="ER";
        this.status = 500;
        executed=false;
    }
    public ServerResponse(String type) {
        this.type = type;
        this.message = "ER";
        this.status = 500;
        this.executed = false;
    }

    public ServerResponse(String type, String message, int status, boolean executed) {
        this.type = type;
        this.message = message;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
                ", status=" + status +
                ", executed=" + executed +
                '}';
    }
}
