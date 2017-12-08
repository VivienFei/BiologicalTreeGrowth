package edu.neu.csye6200.bg;

import java.util.ArrayList;

/**
 * This class is a container for a set of BGStems (in order of creation), plus a
 * Rule class for creating successive generations.
 *
 * @author fjj1213
 */
public class BGGeneration {

    private BGRule rule = null;
    private BGStem rootStem = null;
    protected ArrayList<BGStem> stemList = new ArrayList<BGStem>();

    public BGRule getRule() {
        return rule;
    }

    public void setRule(BGRule rule) {
        this.rule = rule;
    }

    public void setRootStem(BGStem rootStem) {
        this.rootStem = rootStem;
    }

    public void setStemList(ArrayList<BGStem> stemList) {
        this.stemList = stemList;
    }

    /**
     * Standard Constructor
     */

    public BGGeneration(BGRule rule, BGStem rootStem) {
        super();
        this.rule = rule;
        this.rootStem = rootStem;
        add(rootStem);

    }

    public BGGeneration() {
        rule = new BGRule(1.2, 2, 30.0); // Let's create a stock rule

        Position start = new Position(0.0, 0.0);
        double stLength = 1.0;
        double stAngle = 0.0 * Math.PI / 180.0;

        rootStem = add(new BGStem(start, stLength, stAngle)); // Add the first stem to our generation
    }

    /**
     * Copy constructor - build a copy of an existing generation
     *
     * @param cpyGen
     */
    public BGGeneration(BGGeneration cpyGen) {

        rule = cpyGen.rule; // Let's just re-use the same rule instance
        for (BGStem stm : cpyGen.stemList) {
            add(stm.clone());
        }

        rootStem = cpyGen.getRootStem(); // Force the root stem to be the first cloned copy in our list of stems
    }

    /**
     * Get the root stem, which is usually the fist stem in our arrayList
     *
     * @return
     */
    public BGStem getRootStem() {
        return rootStem;
    }

    /**
     * Allow external classes to get access to our list of BGStems
     *
     * @return the stem array list
     */
    public ArrayList<BGStem> getStemList() {
        return stemList;
    }

    /**
     * Add a new stem by recording its index position and adding it to our array
     * list
     *
     * @param stem
     * @return
     */
    public BGStem add(BGStem stem) {
        stem.myIndex = stemList.size(); // Record the current position in the list
        stemList.add(stem);
        return stem;
    }

    /**
     * Create a new generation based on the existing generation Use the Rule to
     * create the next generation
     */
    public BGGeneration createNextGen() {
        BGGeneration nxtGen = rule.getNextGeneration(this);
        return nxtGen;
    }

}
