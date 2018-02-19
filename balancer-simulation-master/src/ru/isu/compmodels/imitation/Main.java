package ru.isu.compmodels.imitation;

import ru.isu.compmodels.imitation.balancers.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Server> l = new ArrayList<>();
        int[] loads = {50, 300, 550, 250};
        for (int load : loads) {
            DummyServer my_server = new DummyServer();
            my_server.setPerformance(load);
            Thread t = new Thread(my_server);
            t.start();
            l.add(my_server);
        }
        Balancer balancer = new RandomBalancer();
        Scanner in = new Scanner(System.in);
        System.out.println("Выберите балансер: \n 1 - Random \n 2 - RoundRobin \n 3 - WeightedRoundRobin \n 4 - Statistical");
        int val1;
        while (true){
            val1 = in.nextInt();
            if (val1 > 0 && val1 <= 4)
                break;
            else
                System.out.println("Введите число от 1 до 4");
        }

        switch (val1){
            case 1:
                break;
            case 2:
                balancer = new RoundRobinBalancer();
                break;
            case 3:
                balancer = new WeightedRoundRobinBalancer();
                break;
            case 4:
                balancer = new StatisticalBalancer();
                break;
        }
        balancer.setServerPool(l);

        System.out.println("Выберите нужный вариант: \n 1 - WeightedRoundRobin не справляется, а Statistical справляется \n 2 - Random не справляется, а RoundRobin справляется \n 3 - RoundRobin не справляется, а WeightedRoundRobin справляется \n 4 - Рандомная нагрузка");

        int val2;
        while (true){
            val2 = in.nextInt();
            if (val2 > 0 && val2 <= 4)
                break;
            else
                System.out.println("Введите число от 1 до 4");
        }
        Random random = new Random();
        int unitsPerReq = 18;
        int sleepTime = 20;
        switch (val2){
            case 1:
                break;
            case 2:
                unitsPerReq = 25;
                sleepTime = 140;
                break;
            case 3:
                unitsPerReq = 7;
                sleepTime = 10;
                break;
            case 4:
                unitsPerReq = random.nextInt(25)+5;
                sleepTime = random.nextInt(140)+10;
                break;
            }
        System.out.println("unitsPerReq: " + unitsPerReq + ", " + "sleepTime: " + sleepTime);
        Thread tr = new Thread(new RequestGenerator(balancer, unitsPerReq, sleepTime));

        tr.start();
        Thread tr1 = new Thread(new Out(l));
        tr1.start();
    }
}

