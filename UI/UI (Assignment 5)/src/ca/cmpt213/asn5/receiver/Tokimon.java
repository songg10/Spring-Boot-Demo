package ca.cmpt213.asn5.receiver;

/**
 * The POJO object to receive data from the server.
 */
public class Tokimon {
    private String name;
    private String ability;
    private double strength;
    private double weight;
    private double height;
    private String color;
    private long id;

    public Tokimon(String name, String ability, double strength, double weight, double height, String color, long id) {
        this.name = name;
        this.ability = ability;
        this.strength = strength;
        this.weight = weight;
        this.height = height;
        this.color = color;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
