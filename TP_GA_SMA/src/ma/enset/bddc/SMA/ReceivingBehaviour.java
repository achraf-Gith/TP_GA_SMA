package ma.enset.bddc.SMA;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.List;
import java.util.Random;

import ma.enset.bddc.SMA.*;
public class ReceivingBehaviour extends CyclicBehaviour {
    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
        ACLMessage message = receive(messageTemplate);

        if (message != null) {
            String content = message.getContent();
            Individual individual = Individual.fromString(content);
            individuals.add(individual);
            receivedIndividuals++;
        }

        if (receivedIndividuals >= NUM_ISLANDS * NUM_GENERATIONS) {
            // All individuals received, perform selection and migration
            double totalFitness = 0;
            for (Individual individual : individuals) {
                totalFitness += individual.getFitness();
            }

            // Normalize fitness values
            for (Individual individual : individuals) {
                individual.setFitness(individual.getFitness() / totalFitness);
            }

            // Perform selection (e.g., roulette wheel selection) and send selected individuals back to islands
            Random random = new Random();
            for (int i = 0; i < NUM_ISLANDS; i++) {
                Individual selectedIndividual = rouletteWheelSelection(individuals);
                ACLMessage selectionMessage = new ACLMessage(ACLMessage.INFORM);
                selectionMessage.addReceiver(getAID());
                selectionMessage.setContent(selectedIndividual.toString());
                send(selectionMessage);
            }

            individuals.clear();
            receivedIndividuals = 0;
        } else {
            block();
        }
    }

    private Individual rouletteWheelSelection(List<Individual> individuals) {
        double sumFitness = 0;
        for (Individual individual : individuals) {
            sumFitness += individual.getFitness();
        }

        Random random = new Random();
        double randomValue = random.nextDouble() * sumFitness;

        double partialSum = 0;
        for (Individual individual : individuals) {
            partialSum += individual.getFitness();
            if (partialSum >= randomValue) {
                return individual;
            }
        }

        return individuals.get(individuals.size() - 1);
    }
}
