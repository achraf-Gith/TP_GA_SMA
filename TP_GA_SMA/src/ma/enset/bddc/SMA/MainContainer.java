package ma.enset.bddc.SMA;

import jade.core.Agent;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

import ma.enset.bddc.SMA.*;

public class MainContainer extends Agent {
    private static final int NUM_ISLANDS = 4;

    @Override
    protected void setup() {
        System.out.println("Main Container Agent started.");

        // Create islands
        for (int i = 0; i < NUM_ISLANDS; i++) {
            try {
                AgentContainer container = getContainerController().createNewAgent("IslandAgent" + i,
                        "IslandAgent", null);
                container.start();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }

        // Create master agent
        try {
            AgentContainer container = getContainerController().createNewAgent("MasterAgent", "MasterAgent", null);
            container.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
