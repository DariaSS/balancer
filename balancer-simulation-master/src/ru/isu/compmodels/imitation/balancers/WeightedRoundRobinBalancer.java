package ru.isu.compmodels.imitation.balancers;

import ru.isu.compmodels.imitation.Request;
import ru.isu.compmodels.imitation.Server;

import java.util.Collection;
import java.util.Random;


public class WeightedRoundRobinBalancer extends BalancerAbstract {
    private int sumUnitsPerSecond;
    private Random random = new Random();

    @Override
    public void setServerPool(Collection<Server> servers) {
        super.setServerPool(servers);
        for (Server server: servers) {
            sumUnitsPerSecond += server.getPerformance();
        }
    }

    @Override
    public Server balance(Request request) {
        int rnd_chance = random.nextInt(sumUnitsPerSecond);
        int partialSum = 0;
        for (Server server: servers) {
            partialSum += server.getPerformance();
            if (rnd_chance < partialSum) {
                return server;
            }
        }
        return null;
    }
}
