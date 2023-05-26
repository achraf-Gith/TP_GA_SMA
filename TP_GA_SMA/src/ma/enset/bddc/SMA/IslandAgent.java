package ma.enset.bddc.SMA;

import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;
import jade.wrapper.AgentController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ma.enset.bddc.SMA.*;

public class IslandAgent extends Agent {
    public static final int POPULATION_SIZE = 100;
    public static final int NUM_GENERATIONS = 100;
    public static final double MUTATION_RATE = 0.01;
    public static final int MIGRATION_INTERVAL = 10;

    private List<Individual> population;
    private AgentController masterAgent;

    @Override
    protected void setup() {
        System.out.println("Island Agent " + getAID().getName() + " started.");

        // Initialize the population
        population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Individual individual = generateRandomIndividual();
            population.add(individual);
        }

        // Get reference to the master agent
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            masterAgent = (AgentController) args[0];
        }

        // Create behaviors
        SequentialBehaviour sequentialBehaviour = new SequentialBehaviour(this);
        sequentialBehaviour.addSubBehaviour(new GenerationBehaviour());
        sequentialBehaviour.addSubBehaviour(new MigrationBehaviour());

        addBehaviour(sequentialBehaviour);
    }

    private Individual generateRandomIndividual() {
        // Generate a random individual based on your problem-specific representation
        // For example, a binary representation for a simple problem with fixed-length chromosomes
        Random random = new Random();
        int[] chromosome = new int[10];
        for (int i = 0; i < chromosome.length; i++) {
            chromosome[i] = random.nextInt(2);
        }
        return new Individual(chromosome);
    }

    private Individual performCrossover(Individual parent1, Individual parent2) {
        // Perform crossover operation between two parents to create a new individual
        // For example, single-point crossover
        Random random = new Random();
        int crossoverPoint = random.nextInt(parent1.getChromosome().length);
        int[] newChromosome = new int[parent1.getChromosome().length];
        for (int i = 0; i < crossoverPoint; i++) {
            newChromosome[i] = parent1.getChromosome()[i];
        }
        for (int i = crossoverPoint; i < parent2.getChromosome().length; i++) {
            newChromosome[i] = parent2.getChromosome()[i];
        }
        return new Individual(newChromosome);
    }

    private void performMutation(Individual individual) {
        // Perform mutation on an individual
        // For example, flip a random bit with a given mutation rate
        Random random = new Random();
        for (int i = 0; i < individual.getChromosome().length; i++) {
            if (random.nextDouble() < MUTATION_RATE) {
                individual.getChromosome()[i] = 1 - individual.getChromosome()[i];
            }
        }
    }

    private void evaluateFitness(Individual individual) {
        // Evaluate the fitness of an individual based on your problem-specific fitness function
        // Set the fitness value in the individual object
        double fitness = 0 ;// Calculate fitness
        individual.setFitness(fitness);
    }

    private Individual selectParent() {
        // Select a parent from the population based on a selection strategy (e.g., tournament selection)
        Random random = new Random();
        Individual parent = population.get(random.nextInt(population.size()));
        for (int i = 0; i < 3; i++) {
            Individual competitor = population.get(random.nextInt(population.size()));
            if (competitor.getFitness() > parent.getFitness()) {
                parent = competitor;
            }
        }
        return parent;
    }

    public List<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(List<Individual> population) {
        this.population = population;
    }

    public AgentController getMasterAgent() {
        return masterAgent;
    }

    public void setMasterAgent(AgentController masterAgent) {
        this.masterAgent = masterAgent;
    }

    private Individual selectBestIndividual() {
        // Select the best individual from the population
        Individual bestIndividual = population.get(0);
        for (Individual individual : population) {
            if (individual.getFitness() > bestIndividual.getFitness()) {
                bestIndividual = individual;
            }
        }
        return bestIndividual;



    }
}