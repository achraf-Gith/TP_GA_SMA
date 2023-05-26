package ma.enset.bddc.SMA;

import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;

import java.util.ArrayList;
import java.util.List;

import ma.enset.bddc.SMA.*;
public class MasterAgent extends Agent {
    private static final int NUM_ISLANDS = 4;
    private static final int NUM_GENERATIONS = 100;

    private int receivedIndividuals = 0;
    private List<Individual> individuals = new ArrayList<>();

    @Override
    protected void setup() {
        System.out.println("Master Agent " + getAID().getName() + " started.");

        // Create behaviors
        SequentialBehaviour sequentialBehaviour = new SequentialBehaviour(this);
        sequentialBehaviour.addSubBehaviour(new ReceivingBehaviour());
        sequentialBehaviour.addSubBehaviour(new MigrationBehaviour());

        addBehaviour(sequentialBehaviour);
    }}
