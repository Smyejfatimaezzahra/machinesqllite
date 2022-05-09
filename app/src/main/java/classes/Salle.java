package classes;

public class Salle {

    private int id;
    private String ref;
    public Salle(int id, String ref) {
        this.id = id;
        this.ref = ref;
    }
    public Salle() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }


}
