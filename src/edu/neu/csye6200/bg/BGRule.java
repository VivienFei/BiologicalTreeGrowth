package edu.neu.csye6200.bg;

/**
 * A BGRule class that is responsible for taking a BGGeneration, and creating a
 * later version, which has grown and sprouted child stems.
 *
 * @author fjj1213
 */
public class BGRule {

    private double growFactor = 1.3; // Grow the stem by 30% each generation
    private int children = 2; // Fork inti two children
    private double dfltLength = 15.0; // The default length of a new stem
    private double interBranchAngle = 30; // interBranchAngle in radians

    /**
     * Biological Growth Rule
     *
     * @param growFactor
     * @param children
     * @param interBranchAngle
     */
    public BGRule(double growFactor, int children, double interBranchAngle) {
        this.growFactor = growFactor;
        this.children = children;
        this.interBranchAngle = interBranchAngle;
    }

    /**
     * Given an input generation, create a following generation using the growth
     * rules
     *
     * @param inGen
     * @return the resulting generation that is a grown copy if the input
     * original
     */
    public BGGeneration getNextGeneration(BGGeneration inGen) {
        BGGeneration nxtGen = new BGGeneration(inGen); // Copy the input generation

        growStems(nxtGen); //Loop through the existing stems and grow them
        populateStems(nxtGen); // Loop through the original stem list, and add children

        return nxtGen; // Return the copied and modified generation
    }

    /*
	 * Loop through the stems and grow them
     */
    private void growStems(BGGeneration nxtGen) {
        for (BGStem stem : nxtGen.stemList) {
            stem.length *= growFactor; // Elongate each stem from the list
            int parentIndex = stem.parentIndex;
            if (parentIndex > -1) { // If this isn't a root, get the parent's tip position and set it as our start
                stem.setStart(nxtGen.stemList.get(parentIndex).getTipPosition());
            }
        }
    }

    /*
	 * Loop through the stems and add children to the tips of an empty stem
     */
    private void populateStems(BGGeneration nxtGen) {
        int nxtSize = nxtGen.stemList.size();
        for (int i = 0; i < nxtSize; i++) {
            BGStem stem = nxtGen.stemList.get(i);
            if (stem.hasChildren()) {
                continue; // We already have child stems, so skip this one
            }
            addChildStems(nxtGen, stem); // Stem is empty, so add them based on the next method
        }
    }

    /*
	 * Add child stems to the end tip of a supplied stem
	 * Based on the number of children, calculate the start angle
	 * Then iterate for each child, adding on the inner-branch angle
     */
    private void addChildStems(BGGeneration nxtGen, BGStem prntStem) {
        double totalAngle = interBranchAngle * (children - 1);
        double startAngle = -totalAngle / 2.0; // start is half the total

        for (int i = 0; i < children; i++) {
            double angle = startAngle + i * interBranchAngle;
            BGStem childStem = new BGStem(prntStem.getTipPosition(), dfltLength,
                    angle);
            childStem.setParentIndex(prntStem.getMyIndex());
            childStem.setParentAngle(prntStem.getGlobalAngle());
            childStem.setAge((prntStem.getAge() + 1));

            prntStem.add(childStem); // Add this stem to the parent
            nxtGen.add(childStem); // Add the new stem to our tree Generation
        }

    }

}
