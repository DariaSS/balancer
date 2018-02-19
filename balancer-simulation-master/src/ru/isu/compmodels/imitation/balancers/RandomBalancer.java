package ru.isu.compmodels.imitation.balancers;

import ru.isu.compmodels.imitation.Request;
import ru.isu.compmodels.imitation.Server;

import java.util.Random;

public class RandomBalancer extends BalancerAbstract {

    @Override
    public Server balance(Request request) {
        Random random = new Random();
        return servers.get(random.nextInt(servers.size()));
    }
}
