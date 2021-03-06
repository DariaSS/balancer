package ru.isu.compmodels.imitation.balancers;


import ru.isu.compmodels.imitation.Request;
import ru.isu.compmodels.imitation.Server;

public class StatisticalBalancer extends BalancerAbstract {

    @Override
    public Server balance(Request request) {
        Server targerServer = servers.get(0);
        float min = targerServer.getCurrentLoad();
        for (int i = 1; i < servers.size(); i++ ) {
            if (servers.get(i).getCurrentLoad() < min) {
                targerServer = servers.get(i);
                min = targerServer.getCurrentLoad();
            }
        }
        return targerServer;
    }
}
