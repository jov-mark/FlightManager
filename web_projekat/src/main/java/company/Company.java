package company;

public class Company {
    private int id;
    private String name;
    private int version;

    public Company() {
        this.id = 0;
        this.name = "";
        this.version = 0;
        System.out.println("YELLo");
    }

    public Company(int id, String name, int version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", version=" + version +
                '}';
    }
}
