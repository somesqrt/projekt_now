package storage.constructors;

public class Position {
    private Long idPosiiton;
    private int floor;
    private int positionNumber;
    private String shelf;
    private double height;
    private double weight;
    private double length;
    private double BearingCapacity;

    public Position(Long idPosiiton, int floor, int positionNumber, String shelf, double height, double weight, double length, double bearingCapacity) {
        this.idPosiiton = idPosiiton;
        this.floor = floor;
        this.positionNumber = positionNumber;
        this.shelf = shelf;
        this.height = height;
        this.weight = weight;
        this.length = length;
        BearingCapacity = bearingCapacity;
    }

    public Long getIdPosiiton() {
        return idPosiiton;
    }

    public void setIdPosiiton(Long idPosiiton) {
        this.idPosiiton = idPosiiton;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getPositionNumber() {
        return positionNumber;
    }

    public void setPositionNumber(int positionNumber) {
        this.positionNumber = positionNumber;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getBearingCapacity() {
        return BearingCapacity;
    }

    public void setBearingCapacity(double bearingCapacity) {
        BearingCapacity = bearingCapacity;
    }

    @Override
    public String toString() {
        return "Position{" +
                "idPosiiton=" + idPosiiton +
                ", floor=" + floor +
                ", positionNumber=" + positionNumber +
                ", shelf='" + shelf + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", length=" + length +
                ", BearingCapacity=" + BearingCapacity +
                '}'+"\n";

    }
}
