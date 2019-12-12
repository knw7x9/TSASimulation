package edu.isu.cs2235;

import edu.isu.cs2235.structures.LinkedDeque;
import edu.isu.cs2235.structures.LinkedStack;
import edu.isu.cs2235.structures.Queue;
import edu.isu.cs2235.structures.LinkedQueue;
import edu.isu.cs2235.Person;

import java.rmi.server.RemoteObjectInvocationHandler;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * Class representing a wait time simulation program.
 *
 * @author Isaac Griffith
 * @author Katherine Wilsdon
 */
public class Simulation {

    private int arrivalRate;
    private int maxNumQueues;
    private Random r;
    private int numIterations = 50;
    // You will probably need more fields

    /**
     * Constructs a new simulation with the given arrival rate and maximum number of queues. The Random
     * number generator is seeded with the current time. This defaults to using 50 iterations.
     *
     * @param arrivalRate the integer rate representing the maximum number of new people to arrive each minute
     * @param maxNumQueues the maximum number of lines that are open
     */
    public Simulation(int arrivalRate, int maxNumQueues) {
        this.arrivalRate = arrivalRate;
        this.maxNumQueues = maxNumQueues;
        r = new Random();
    }

    /**
     * Constructs a new simulation with the given arrival rate and maximum number of queues. The Random
     * number generator is seeded with the provided seed value, and the number of iterations is set to
     * the provided value.
     *
     * @param arrivalRate the integer rate representing the maximum number of new people to arrive each minute
     * @param maxNumQueues the maximum number of lines that are open
     * @param numIterations the number of iterations used to improve data
     * @param seed the initial seed value for the random number generator
     */
    public Simulation(int arrivalRate, int maxNumQueues, int numIterations, int seed) {
        this(arrivalRate, maxNumQueues);
        r = new Random(seed);
        this.numIterations = numIterations;
    }

    /**
     * Executes the Simulation
     * @auther Katherine Wilsdon
     */
    public void runSimulation() {
        runStackSimulation();
        runQueueSimulation();
        runDequeSimulation();
    }

    /**
     * Executes the Stack Simulation
     * @auther Katherine Wilsdon
     */
    private void runStackSimulation(){
        try {

            // Open given file in append mode.
            BufferedWriter file = new BufferedWriter(
                    new FileWriter("report.csv", true));

            double minutes = 720;
            System.out.println("\r\n" + "Stack" + "\r\n");
            file.write("\r\n" + "Stack" + "\r\n");
            System.out.println("Average arrival rate: " + arrivalRate);
            file.write("Average arrival rate: " + arrivalRate + "\r\n");
            int[] avgWaitTime = new int[this.maxNumQueues];

            // For each line number
            for (int i = 1; i <= this.maxNumQueues; ++i) {
                // For the number of iterations
                for (int j = 0; j < this.numIterations; ++j) {
                    // Create Stacks
                    ArrayList<LinkedStack> stackArr = new ArrayList<LinkedStack>();
                    int numQueuesRemaining = i;
                    while (numQueuesRemaining > 0) {
                        stackArr.add(new LinkedStack());
                        numQueuesRemaining--;
                    }

                    // Create person variables
                    ArrayList<Person> personArr = new ArrayList<>();
                    int personIn = 0;
                    int personOut = 0;

                    // The summation of the wait time for each person that exits the line
                    double totalWaitTime = 0;
                    // For 12 hours
                    for (int k = 0; k < minutes; ++k) {
                        int numPeopleArrive = getRandomNumPeople(arrivalRate);
                        for (int l = 0; l < numPeopleArrive; ++l) {
                            // If stack size is 1, add the person directly to the stack
                            if (stackArr.size() == 1) {
                                Person person = new Person();
                                person.setInLine(k);
                                person.setPersonNum(personIn);
                                personArr.add(person);
                                stackArr.get(0).push(personIn);
                                personIn++;
                            } // Else, the person is added to the shortest line
                            else {
                                int shortestLine = 0;
                                for (int m = 0; m < stackArr.size(); ++m) {
                                    if (stackArr.get(m).size() < stackArr.get(shortestLine).size())
                                        shortestLine = m;
                                }
                                Person person = new Person();
                                person.setInLine(k);
                                person.setPersonNum(personIn);
                                personArr.add(person);
                                stackArr.get(shortestLine).push(personIn);
                                personIn++;
                            }
                        }
                        // For each line, remove the first two people from the line
                        for (int l = 0; l < stackArr.size(); ++l) {
                            // Record when the person exited the line
                            if (stackArr.get(l).peek() != null) {
                                int personNumber = (int) stackArr.get(l).peek();
                                personArr.get(personNumber).setOutOfLine(k);
                                personOut++;
                            }
                            stackArr.get(l).pop();

                            // Record when the person exited the line
                            if (stackArr.get(l).peek() != null) {
                                int personNumber = (int) stackArr.get(l).peek();
                                personArr.get(personNumber).setOutOfLine(k);
                                personOut++;
                            }
                            stackArr.get(l).pop();

                        }
                    }
                    // Add the total wait times of every person
                    for (int k = 0; k < personArr.size(); ++k) {
                        totalWaitTime += personArr.get(k).getMinutesInLine();
                    }
                    // The total average wait time += total wait time / number of people who exited the line
                    avgWaitTime[i - 1] += totalWaitTime / personOut;
                }
                // Divide the total average wait time by the number of iterations
                avgWaitTime[i - 1] /= this.numIterations;

                // Print the average wait time to a csv and the console
                System.out.println("Average wait time using " + (i) + " stack(s): " + avgWaitTime[i - 1]);
                file.write("Average wait time using " + (i) + " stack(s): " + avgWaitTime[i - 1] + "\r\n");
            }
            file.close();
        }  catch (IOException e){
            System.err.println("An IOException was caught : " + e.getMessage());
        }
    }

    /**
     * Executes the Queue Simulation
     * @auther Katherine Wilsdon
     */
    private void runQueueSimulation(){
        try {

            // Open given file in append mode.
            BufferedWriter file = new BufferedWriter(
                    new FileWriter("report.csv", true));

            double minutes = 720;
            System.out.println("\r\n" + "Queue" + "\r\n");
            file.write("\r\n" + "Queue" + "\r\n");
            System.out.println("Average arrival rate: " + arrivalRate);
            file.write("Average arrival rate: " + arrivalRate + "\r\n");
            int[] avgWaitTime = new int[this.maxNumQueues];

            // For each line number
            for (int i = 1; i <= this.maxNumQueues; ++i) {
                // For the number of iterations
                for (int j = 0; j < this.numIterations; ++j) {
                    // Create Queues
                    ArrayList<LinkedQueue> queueArr = new ArrayList<LinkedQueue>();
                    int numQueuesRemaining = i;
                    while (numQueuesRemaining > 0) {
                        queueArr.add(new LinkedQueue<Integer>());
                        numQueuesRemaining--;
                    }

                    // Create person variables
                    ArrayList<Person> personArr = new ArrayList<>();
                    int personIn = 0;
                    int personOut = 0;

                    // The summation of the wait time for each person that exits the line
                    double totalWaitTime = 0;
                    // For 12 hours
                    for (int k = 0; k < minutes; ++k) {
                        int numPeopleArrive = getRandomNumPeople(arrivalRate);
                        for (int l = 0; l < numPeopleArrive; ++l) {
                            // If queue size is 1, add the person directly to the queue
                            if (queueArr.size() == 1) {
                                Person person = new Person();
                                person.setInLine(k);
                                person.setPersonNum(personIn);
                                personArr.add(person);
                                queueArr.get(0).offer(personIn);
                                personIn++;
                            } // Else, the person is added to the shortest line
                            else {
                                int shortestLine = 0;
                                for (int m = 0; m < queueArr.size(); ++m) {
                                    if (queueArr.get(m).size() < queueArr.get(shortestLine).size())
                                        shortestLine = m;
                                }
                                Person person = new Person();
                                person.setInLine(k);
                                person.setPersonNum(personIn);
                                personArr.add(person);
                                queueArr.get(shortestLine).offer(personIn);
                                personIn++;
                            }
                        }

                        // For each line, remove the first two people from the line
                        for (int l = 0; l < queueArr.size(); ++l) {
                            // Record when the person exited the line
                            if (queueArr.get(l).peek() != null) {
                                int personNumber = (int) queueArr.get(l).peek();
                                personArr.get(personNumber).setOutOfLine(k);
                                personOut++;
                            }
                            queueArr.get(l).poll();
                            // Record when the person exited the line
                            if (queueArr.get(l).peek() != null) {
                                int personNumber = (int) queueArr.get(l).peek();
                                personArr.get(personNumber).setOutOfLine(k);
                                personOut++;
                            }
                            queueArr.get(l).poll();

                        }
                    }

                    // Add the total wait times of every person
                    for (int k = 0; k < personArr.size(); ++k) {
                        totalWaitTime += personArr.get(k).getMinutesInLine();
                    }
                    // The total average wait time += total wait time / number of people who exited the line
                    avgWaitTime[i - 1] += totalWaitTime / personOut;
                }
                // Divide the total average wait time by the number of iterations
                avgWaitTime[i - 1] /= this.numIterations;

                // Print the average wait time to a csv and the console
                System.out.println("Average wait time using " + (i) + " queue(s): " + avgWaitTime[i - 1]);
                file.write("Average wait time using " + (i) + " queue(s): " + avgWaitTime[i - 1] + "\r\n");
            }
            file.close();
        }  catch (IOException e){
            System.err.println("An IOException was caught : " + e.getMessage());
        }
    }

    /**
     * Executes the Deque Simulation
     * @auther Katherine Wilsdon
     */
    private void runDequeSimulation(){
        try {
            r = new Random();
            // Open given file in append mode.
            BufferedWriter file = new BufferedWriter(
                    new FileWriter("report.csv", true));

            double minutes = 720;
            System.out.println("\r\n" + "Deque" + "\r\n");
            file.write("\r\n" + "Deque" + "\r\n");
            System.out.println("Average arrival rate: " + arrivalRate);
            file.write("Average arrival rate: " + arrivalRate + "\r\n");
            int[] avgWaitTime = new int[this.maxNumQueues];

            // For each line number
            for (int i = 1; i <= this.maxNumQueues; ++i) {
                // For the number of iterations
                for (int j = 0; j < this.numIterations; ++j) {
                    // Create Deques
                    ArrayList<LinkedDeque> dequesArr = new ArrayList<LinkedDeque>();
                    int numQueuesRemaining = i;
                    while (numQueuesRemaining > 0) {
                        dequesArr.add(new LinkedDeque());
                        numQueuesRemaining--;
                    }

                    // Create person variables
                    ArrayList<Person> personArr = new ArrayList<>();
                    int personIn = 0;
                    int personOut = 0;

                    // The summation of the wait time for each person that exits the line
                    double totalWaitTime = 0;
                    // For 12 hours
                    for (int k = 0; k < minutes; ++k) {
                        int numPeopleArrive = getRandomNumPeople(arrivalRate);
                        for (int l = 0; l < numPeopleArrive; ++l) {
                            // If deque size is 1, add the person directly to the deque
                            if (dequesArr.size() == 1) {
                                Person person = new Person();
                                person.setInLine(k);
                                person.setPersonNum(personIn);
                                personArr.add(person);
                                // Randomly determine to offer first or offer last
                                int randomNumber = r.nextInt(2);
                                if(randomNumber == 0)
                                    dequesArr.get(0).offerFirst(personIn);
                                else if (randomNumber == 1)
                                    dequesArr.get(0).offer(personIn);
                                personIn++;
                            } // Else, the person is added to the shortest line
                            else {
                                int shortestLine = 0;
                                for (int m = 0; m < dequesArr.size(); ++m) {
                                    if (dequesArr.get(m).size() < dequesArr.get(shortestLine).size())
                                        shortestLine = m;
                                }
                                Person person = new Person();
                                person.setInLine(k);
                                person.setPersonNum(personIn);
                                personArr.add(person);
                                // Randomly determine to offer first or offer last
                                int randomNumber = r.nextInt(2);
                                if(randomNumber == 0)
                                    dequesArr.get(shortestLine).offerFirst(personIn);
                                else if (randomNumber == 1)
                                    dequesArr.get(shortestLine).offer(personIn);
                                personIn++;
                            }
                        }

                        // For each line, remove the first or last person from the line, twice
                        for (int l = 0; l < dequesArr.size(); ++l) {
                            // Randomly determine to poll first or poll last
                            int randomNumber = r.nextInt(2);
                            int pollPeople = 2;
                            for (int m = 0; m < pollPeople; ++m) {
                                // Poll last
                                if (randomNumber == 0) {
                                    // Record when the person exited the line
                                    if (dequesArr.get(l).peekLast() != null) {
                                        int personNumber = (int) dequesArr.get(l).peekLast();
                                        personArr.get(personNumber).setOutOfLine(k);
                                        personOut++;
                                    }
                                    dequesArr.get(l).pollLast();
                                } // Poll first
                                else if (randomNumber == 1){
                                    // Record when the person exited the line
                                    if (dequesArr.get(l).peek() != null) {
                                        int personNumber = (int) dequesArr.get(l).peek();
                                        personArr.get(personNumber).setOutOfLine(k);
                                        personOut++;
                                    }
                                    dequesArr.get(l).poll();
                                }
                            }

                        }
                    }

                    // Add the total wait times of every person
                    for (int k = 0; k < personArr.size(); ++k) {
                        totalWaitTime += personArr.get(k).getMinutesInLine();
                    }
                    // The total average wait time += total wait time / number of people who exited the line
                    avgWaitTime[i - 1] += totalWaitTime / personOut;
                }
                // Divide the total average wait time by the number of iterations
                avgWaitTime[i - 1] /= this.numIterations;

                // Print the average wait time to a csv and the console
                System.out.println("Average wait time using " + (i) + " deque(s): " + avgWaitTime[i - 1]);
                file.write("Average wait time using " + (i) + " deque(s): " + avgWaitTime[i - 1] + "\r\n");
            }
            file.close();
        }  catch (IOException e){
            System.err.println("An IOException was caught : " + e.getMessage());
        }
    }

    /**
     * returns a number of people based on the provided average
     *
     * @param avg The average number of people to generate
     * @return An integer representing the number of people generated this minute
     */
    //Don't change this method.
    private static int getRandomNumPeople(double avg) {
        Random r = new Random();
        double L = Math.exp(-avg);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}
