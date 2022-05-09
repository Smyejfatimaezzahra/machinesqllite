package classes;

public class Machine {

        private int id;
        private  int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Machine(int id, String nom, double price, Salle s) {
        this.id = id;
        this.nom = nom;
        this.price = price;
        this.s = s;
    }
    public Machine( String nom, double price, Salle s) {

        this.nom = nom;
        this.price = price;
        this.s = s;
    }
    public Machine( Salle s,int count) {
        this.s = s;
        this.count=count;
    }

    private String nom;
        private double price;

    public Salle getS() {
        return s;
    }

    public void setS(Salle s) {
        this.s = s;
    }

    private  Salle s;

        public Machine(String nom, double machine) {
            this.nom = nom;
            this.price =machine;
        }

        public Machine() {

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

        }
