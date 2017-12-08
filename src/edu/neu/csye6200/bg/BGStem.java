package edu.neu.csye6200.bg;

/**
 * A Biological Stem class
 *
 * @author fjj1213
 */
public class BGStem {

    protected Position startPos = new Position(0.0, 0.0); // The position where this stem begins - Should match the parent 'tip' position

    protected double length = 10.0; // How long is this stem
    protected double angle = 0.0; // Angle relative to the parent (in degrees)
    protected Position tipPos = new Position(0.0, 0.0); // Where is the end of this stem (calculated)
    protected int age = 0; // What generation created this stem (larger means more recent)

    protected int myIndex = -1; // The index where this stem resides in a BGGeneration array
    protected int parentIndex = -1; // -1 = no parent - The index where a parent stem resides in a BGGeneration array

    protected double parentAngle = 0.0; // What is the global angle of the parent in degrees? We'll add our own local angle to it to create an absolute direction

    protected int[] children = new int[10]; // A listing of child index values
    protected int childCnt = 0; // How many children are in our list?

    public final static double deg2Rad = Math.PI / 180.0; // A conversion factor for converting from degrees to radians

    public Position getStartPos() {
        return startPos;
    }

    public void setStartPos(Position startPos) {
        this.startPos = startPos;
    }

    /**
     * A simple constructor for a stem
     *
     * @param start The start position of this stem
     * @param length The length of the stem
     * @param angle The direction of the stem relative to the parent angle (in
     * degrees)
     */
    public BGStem(Position startPos, double length, double angle) {
        this.startPos = startPos;
        this.length = length;
        this.angle = angle;
    }

    /**
     * Copy constructor - build a stem that copies an input stem
     *
     * @param cpy the stem to use as a template for creating our current stem
     */
    public BGStem(BGStem inStem) {
        startPos.x = inStem.startPos.x;
        startPos.y = inStem.startPos.y;
        length = inStem.length;
        angle = inStem.angle;
        tipPos.x = inStem.tipPos.x;
        tipPos.y = inStem.tipPos.y;
        age = inStem.age;
        myIndex = inStem.myIndex;
        //Copy info about a parent stem
        parentIndex = inStem.parentIndex;
        parentAngle = inStem.parentAngle;
        // Copy info about child stems
        childCnt = inStem.childCnt;
        for (int i = 0; i < children.length; i++) {
            children[i] = inStem.children[i];
        }
    }

    /**
     * Set the start position
     *
     * @param startPos
     */
    public void setStart(Position startPos) {
        this.startPos = startPos;
    }

    /**
     * Get the length of this stem
     *
     * @return the stem length
     */
    public double getLength() {
        return length;
    }

    /**
     * Set the length of this stem
     *
     * @param length the length to assign
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Get the parent absolute angle
     *
     * @return the parent angle in degrees
     */
    public double getParentAngle() {
        return parentAngle;
    }

    /**
     * Set the parent absolute angle
     *
     * @param parentAngle the parent angle in degrees
     */
    public void setParentAngle(double parentAngle) {
        this.parentAngle = parentAngle;
    }

    /**
     * Get the local stem relative angle based on our parent angle
     *
     * @return the relative angle in degrees
     */
    public double getAngle() {
        return angle;
    }

    /**
     * The relative index of the parent stem
     *
     * @return the parentIndex
     */
    public int getParentIndex() {
        return parentIndex;
    }

    /**
     * Set the relative index of the parent stem
     *
     * @param parentIndex the parentIndex to set
     */
    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    /**
     * Get the relative index of this stem within the BGGeneration list
     *
     * @return the myIndex
     */
    public int getMyIndex() {
        return myIndex;
    }

    /**
     * Set the relative index of this stem within the BGGeneration list
     *
     * @param myIndex the myIndex to set
     */
    public void setMyIndex(int myIndex) {
        this.myIndex = myIndex;
    }

    /**
     * Set the local stem relative angle
     *
     * @param angle a relative ange in degrees
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * What generation was stem born on (older is younger)
     *
     * @return the 'born-on' date
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the generation number that this stem was born on
     *
     * @param age set the 'born-on' date
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Add a stem to the tip of this stem. Do this by recording its index into
     * an array, and advancing our child counter
     *
     * @param stem
     */
    public void add(BGStem stem) {
        children[childCnt] = stem.myIndex;
        childCnt++;
    }

    /**
     * Get the position of the stem 'tip' where child stems will emerge Always
     * force a recalculation
     *
     * @return a 2D position of the stem tip
     */
    public Position getTipPosition() {
        double localAngle = (parentAngle + angle) * deg2Rad;
        double xTip = startPos.x + length * Math.sin(localAngle);
        double yTip = startPos.y - length * Math.cos(localAngle);

        tipPos = new Position(xTip, yTip);
        return tipPos;
    }

    /**
     * Is the stem empty, or does it have children at the tip?
     *
     * @return
     */
    public boolean isEmpty() {
        return (childCnt == 0);
    }

    /**
     * Does the stem have any children?
     *
     * @return
     */
    public boolean hasChildren() {
        return (childCnt > 0);
    }

    /**
     * Clone the existing stem (a simple clone - no children), and advance the
     * age
     */
    public BGStem clone() {
        BGStem clstem = new BGStem(this);
        return clstem;
    }

    /**
     * What is the absolute angle for drawing this stem?
     *
     * @return The absolute parent angle, plus our local angle (in degrees)
     */
    public double getGlobalAngle() {
        return parentAngle + angle;
    }

    /**
     * A simple print routine for checking our BGStem values
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < age; i++) {
            sb.append("-+");
        }
        sb.append("BGStem[");
        sb.append(",inddx=").append(myIndex);
        sb.append(",startPos=" + startPos);
        sb.append(",length=" + length);
        sb.append(",angle=" + angle);
        sb.append(",gAngle=" + getGlobalAngle());
        sb.append(",child=" + childCnt);
        sb.append("]");
        return sb.toString();

    }

}
