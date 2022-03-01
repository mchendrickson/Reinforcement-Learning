import java.awt.*;
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

    /**
     * Create a coordinate with all the required data
     * @param type
     * @param colCoordinate
     * @param rowCoordinate
     * @param value
     * @param upCost
     * @param leftCost
     * @param rightCost
     * @param downCost
     */
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

    /**
     * Get the highest value of each of the move costs
     * @return highestValue
     */
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

    /**
     * Get the best direction to travel in based on values
     * @return
     */
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

    /**
     * For terminal state coordinates which don't need directional costs
     * @param type
     * @param colCoordinate
     * @param rowCoordinate
     * @param value
     */
    public Coordinate(CoordinateType type, int colCoordinate, int rowCoordinate, float value) {
        this.type = type;
        this.col = colCoordinate;
        this.row = rowCoordinate;
        this.value = value;
    }

    /**
     * Getter for type of coordinate
     * @return TERMINAL or CURRENT
     */
    public CoordinateType getType() {
        return type;
    }

    /**
     * @return X coordinate
     */
    public int getColCoordinate() {
        return col;
    }

    /**
     * @return Y coordinate
     */
    public int getRowCoordinate() {
        return row;
    }

    /**
     * Value of specific coordinate
     * @return value
     */
    public float getValue() {
        return value;
    }

    /**
     * Value of up cost
     * @return value
     */
    public float getUpCost() {
        return upCost;
    }

    /**
     * Value of left cost
     * @return value
     */
    public float getLeftCost() {
        return leftCost;
    }

    /**
     * Value of right cost
     * @return value
     */
    public float getRightCost() {
        return rightCost;
    }

    /**
     * Value of down cost
     * @return value
     */
    public float getDownCost() {
        return downCost;
    }

    /**
     * Set TERMINAL or CURRENT
     */
    public void setType(CoordinateType type) {
        this.type = type;
    }

    /**
     * Set X coordinate
     * @param colCoordinate
     */
    public void setColCoordinate(int colCoordinate) {
        this.col = colCoordinate;
    }

    /**
     * Set Y coordinate
     * @param rowCoordinate
     */
    public void setRowCoordinate(int rowCoordinate) {
        this.row = rowCoordinate;
    }

    /**
     * Set value
     * @param value
     */
    public void setValue(Integer value) {
        this.value = value;
    }
    
    /**
     * Set up cost
     * @param upCost
     */
    public void setUpCost(float upCost) {
        this.upCost = upCost;
    }

    /**
     * Set left cost
     * @param leftCost
     */
    public void setLeftCost(float leftCost) {
        this.leftCost = leftCost;
    }

    /**
     * Set right cost
     * @param rightCost
     */
    public void setRightCost(float rightCost) {
        this.rightCost = rightCost;
    }

    /**
     * Set down cost
     * @param downCost
     */
    public void setDownCost(float downCost) {
        this.downCost = downCost;
    }

    /**
     * Check if two coordinates have the same position
     * @param o
     * @return true/false
     */
    public boolean equals(Coordinate o) {
        return (this.col == o.col && this.row == o.row);
    }

    @Override
    /**
     * Get the hash
     */
    public int hashCode() {
        return Objects.hash(col, row, value);
    }

    @Override
    /**
     * Deep clone the coordinate to another
     */
    public Coordinate clone() {
        return new Coordinate(type, col, row, value, upCost, leftCost, rightCost, downCost);
    }
}
