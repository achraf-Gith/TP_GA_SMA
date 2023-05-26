package ma.enset.bddc.SMA;

import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;

import ma.enset.bddc.SMA.*;

import static ma.enset.bddc.SMA.IslandAgent.POPULATION_SIZE;

public class GenerationBehaviour extends SequentialBehaviour {
    public GenerationBehaviour() {
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour(IslandAgent.this, ParallelBehaviour.WHEN_ALL);

        // Perform selection, crossover, and mutation on the island's population
        for (int i = 0; i < POPULATION_SIZE; i++) {
            parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
                @Override
                public void action() {
                    // Selection
                    Individual parent1 = selectParent();
                    Individual parent2 = selectParent();

                    // Crossover
                    Individual child = performCrossover(parent1, parent2);

                    // Mutation
                    performMutation(child);

                    // Evaluate fitness
                    evaluateFitness(child);

                    // Replace worst individual in the population with the child
                    Individual worstIndividual = population.get(0);
                    for (Individual individual : population) {
                        if (individual.getFitness() < worstIndividual.getFitness()) {
                            worstIndividual = individual;
                        }
                    }
                    population.remove(worstIndividual);
                    population.add(child);
                }
            });
        }

        addSubBehaviour(parallelBehaviour);
    }
}