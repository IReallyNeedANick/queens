package compileworks.queens.model;

public enum ShellBooleanValue {
    TRUE(true), FALSE(false), NULL(null);

    public Boolean value;

    ShellBooleanValue(Boolean value) {
        this.value = value;
    }
}
