package PhotoDB;

public class DBData {
    private int id;
    private String filename;
    private String value;

    public DBData(int id, String filename) {
        this.id = id;
        this.filename = filename;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
