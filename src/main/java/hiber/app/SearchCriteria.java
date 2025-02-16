package hiber.app;

public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;

    public SearchCriteria(String key, Object value, String operation) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }
}
