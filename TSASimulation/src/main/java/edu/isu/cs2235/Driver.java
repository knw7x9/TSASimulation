package edu.isu.cs2235;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.isu.cs2235.Simulation;

/**
 * @author Katherine Wilsdon
 */
public class Driver {

    public static void main(String[] args) {

        try {
            // Create csv file
            File file = new File("report.csv");
            file.createNewFile();
            FileWriter csvWriter = new FileWriter(file);

            int maxArrivalRate = 30;
            int maxLines = 10;
            int iterations = 50;
            int seed = 1024;

            for (int i = 1; i <= maxArrivalRate; ++i){
                // For each arrival rate, run the simulation on the stack, queue, and deque
                Simulation simulation = new Simulation(i, maxLines, iterations, seed);
                simulation.runSimulation();
            }
        } catch (IOException e){
            System.err.println("An IOException was caught : " + e.getMessage());
        }
    }
}
