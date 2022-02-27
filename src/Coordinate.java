import java.util.Objects;

public class Coordinate implements Cloneable{
    CoordinateType type;
    public int col;
    public int row;
    float value;
    float upCost;
    float leftCost;
    float rightCost;
    float downCost;

    public Coordinate(CoordinateType type, int colCoordinate, int rowCoordinate, float value, float upCost, float leftCost, float rightCost, float downCost) {
        this.type = type;
        this.col = colCoordinate;
        this.row = rowCoordinate;
        this.value = value;
        this.upCost = upCost;
        this.leftCost = leftCost;
        this.rightCost = rightCost;
        this.downCost = downCost;
    }

    public float highestFloat(){
        float currHighest = this.upCost;
        if(currHighest < this.downCost){
            currHighest = this.downCost;
        }
        if(currHighest < this.rightCost){
            currHighest = this.rightCost;
        }
        if (currHighest < this.leftCost) {
            currHighest = this.leftCost;
        }
        return currHighest;
    }

    public Direction highestDir(){
        Direction currHighestDir = Direction.UP;
        float currHighest = this.upCost;
        if(currHighest < this.downCost){
            currHighest = this.downCost;
            currHighestDir = Direction.DOWN;
        }
        if(currHighest < this.rightCost){
            currHighest = this.rightCost;
            currHighestDir = Direction.RIGHT;
        }
        if (currHighest < this.leftCost) {
            currHighest = this.leftCost;
            currHighestDir = Direction.LEFT;
        }
        return currHighestDir;
    }

    public Coordinate(CoordinateType type, int colCoordinate, int rowCoordinate, float value) {
        this.type = type;
        this.col = colCoordinate;
        this.row = rowCoordinate;
        this.value = value;
    }

    public CoordinateType getType() {
        return type;
    }

    public int getcolCoordinate() {
        return col;
    }

    public int getyCoordinate() {
        return row;
    }

    public float getValue() {
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

    public void setcolCoordinate(int colCoordinate) {
        this.col = colCoordinate;
    }

    public void setyCoordinate(int rowCoordinate) {
        this.row = rowCoordinate;
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


    public boolean equals(Coordinate o) {
        return (this.col == o.col && this.row == o.row);
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row, value);
    }

    @Override
    public Coordinate clone() {
        return new Coordinate(type, col, row, value, upCost, leftCost, rightCost, downCost);
    }
}
