package by.softeq.test_task.service;

public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}
