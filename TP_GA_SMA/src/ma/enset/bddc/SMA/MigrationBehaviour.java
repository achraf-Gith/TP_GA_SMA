package ma.enset.bddc.SMA;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import ma.enset.bddc.SMA.*;

import static ma.enset.bddc.SMA.IslandAgent.MIGRATION_INTERVAL;
import static ma.enset.bddc.SMA.IslandAgent.NUM_GENERATIONS;

public class MigrationBehaviour extends CyclicBehaviour {
    private int generationCount = 0;

    @Override
    public void action() {
        if (generationCount % MIGRATION_INTERVAL == 0) {
            // Perform migration of individuals to the master agent
            for (Individual individual : population) {
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.addReceiver(masterAgent.getAID());
                message.setContent(individual.toString());
                send(message);
            }
        }

        generationCount++;

        if (generationCount >= NUM_GENERATIONS) {
            // Terminate the island agent
            doDelete();
        } else {
            // Wait for a response from the master agent
            MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage message = receive(messageTemplate);
            if (message != null) {
                String content = message.getContent();
                Individual receivedIndividual = Individual.fromString(content);

                // Replace worst individual in the population with the received individual
                Individual worstIndividual = population.get(0);
                for (Individual individual : population) {
                    if (individual.getFitness() < worstIndividual.getFitness()) {
                        worstIndividual = individual;
                    }
                }
                population.remove(worstIndividual);
                population.add(receivedIndividual);
            } else {
                block();
            }
        }
    }
}
}
