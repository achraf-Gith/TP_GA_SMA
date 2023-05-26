package ma.enset.bddc.SMA;

import ma.enset.bddc.SMA.*;

public class Individual {
    private int[] chromosome;
    private double fitness;

    public Individual(int[] chromosome) {
        this.chromosome = chromosome;
        this.fitness = 0;
    }

    public int[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public static Individual fromString(String str) {
        String[] values = str.split(",");
        int[] chromosome = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            chromosome[i] = Integer.parseInt(values[i]);
        }
        return new Individual(chromosome);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chromosome.length; i++) {
            sb.append(chromosome[i]);
            if (i < chromosome.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}