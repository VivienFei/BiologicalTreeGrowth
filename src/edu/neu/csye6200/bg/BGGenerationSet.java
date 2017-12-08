package edu.neu.csye6200.bg;

import java.util.ArrayList;

/**
 * A list of successive generations over time. This also has a simulation loop
 * that generates multiple generations, each based on the one before, but with
 * growth added to it.
 *
 * @author fjj1213
 */
public class BGGenerationSet implements Runnable {

    private ArrayList<BGGeneration> genList = new ArrayList<BGGeneration>();
    private int maxGen = 5; // What is the current limit to the number of generations that we'll construct
    private boolean done = false; // Is this simulation generation run finished?
    private boolean paused = false; // Should we puase execution of our
    private BGGeneration lastGen = null;

    public ArrayList<BGGeneration> getGenList() {
        return genList;
    }

    public void setGenList(ArrayList<BGGeneration> genList) {
        this.genList = genList;
    }

    public int getMaxGen() {
        return maxGen;
    }

    public void setMaxGen(int maxGen) {
        this.maxGen = maxGen;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public BGGeneration getLastGen() {
        return lastGen;
    }

    public void setLastGen(BGGeneration lastGen) {
        this.lastGen = lastGen;
    }

    /**
     * A simple time series constructor that creates an initial generation
     */
    public BGGenerationSet() {
        //initGen();
    }

    /*
	 * Initialize a starter generation and add it to our list
     */
    public void initGen() {
        BGGeneration gen = new BGGeneration();
        genList.add(lastGen); // Add it to our list
        lastGen = gen; // Save a local copy to allow our run method to know we're ready
    }

    public void init(BGGeneration gen) {
        genList.add(gen); // Add it to our list

        lastGen = gen; // Save a local copy to allow our run method to know we're ready
    }

    /**
     * A simulation run loop - Having a Thread to run this would be nice Use
     * 'pause' to control execution (i.e. either do work and nap, or just sleep
     * Use 'done' to stop working permanently
     */
    @Override
    public void run() {

        while (!done) { // Loop infinitely, or until we are 'done'

            if (lastGen == null) {
                paused = true; // It we haven't created a generation, just wait for one to be created
            }
            if (!paused) {
                BGGeneration nxtGen = lastGen.createNextGen();
                genList.add(nxtGen);

                lastGen = nxtGen;

            }
            // else - have the thread sleep (i.e do no work)

            if (genList.size() >= maxGen) {
                done = true;
            }
        }

    }

}
