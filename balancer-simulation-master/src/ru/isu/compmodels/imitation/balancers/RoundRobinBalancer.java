package ru.isu.compmodels.imitation.balancers;

import ru.isu.compmodels.imitation.Request;
import ru.isu.compmodels.imitation.Server;

public class RoundRobinBalancer extends BalancerAbstract {

    private int next_server = 0;

    @Override
    public Server balance(Request request) {
        next_server++;
        if (next_server >= servers.size()) {next_server = 0;}
        return servers.get(next_server);
    }
}
