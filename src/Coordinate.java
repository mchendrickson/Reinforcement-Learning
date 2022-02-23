import java.util.Objects;

public class Coordinate {
    CoordinateType type;
    int xCoordinate;
    int yCoordinate;
    Integer value;
    float upCost;
    float leftCost;
    float rightCost;
    float downCost;

    public Coordinate(CoordinateType type, int xCoordinate, int yCoordinate, Integer value, float upCost, float leftCost, float rightCost, float downCost) {
        this.type = type;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.value = value;
        this.upCost = upCost;
        this.leftCost = leftCost;
        this.rightCost = rightCost;
        this.downCost = downCost;
    }

    public CoordinateType getType() {
        return type;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public Integer getValue() {
        return value;
    }

    public float getUpCost() {
        return upCost;
    }

    public float getLeftCost() {
        return leftCost;
    }

    public float getRightCost() {
        return rightCost;
    }

    public float getDownCost() {
        return downCost;
    }

    public void setType(CoordinateType type) {
        this.type = type;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setUpCost(float upCost) {
        this.upCost = upCost;
    }

    public void setLeftCost(float leftCost) {
        this.leftCost = leftCost;
    }

    public void setRightCost(float rightCost) {
        this.rightCost = rightCost;
    }

    public void setDownCost(float downCost) {
        this.downCost = downCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return xCoordinate == that.xCoordinate && yCoordinate == that.yCoordinate && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate, value);
    }
}
