package ru.isu.compmodels.imitation;

import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class Out implements Runnable{

    private List<Server> servers;
    private boolean shouldStop = false;
    private LinkedList<Double> times;
    private LinkedList<LinkedList<Double>> loads;
    final List<XYChart> charts;
    final SwingWrapper<XYChart> sw;

    public Out(List<Server> servers) {

        this.servers = servers;
        int numCharts = servers.size();
        charts = new ArrayList<XYChart>();

        times = new LinkedList<>();
        times.add(0D);

        loads = new LinkedList<>();

        for (int i = 0; i < numCharts; i++) {
            LinkedList<Double> list = new LinkedList<>();
            list.add(0D);
            loads.add(list);
            XYChart chart = new XYChartBuilder().xAxisTitle("Время").yAxisTitle("Нагруженность").width(600).height(400).build();
            chart.getStyler().setYAxisMin((double) 0);
            XYSeries series = chart.addSeries("сервер " + (i+1) + " (" +servers.get(i).getPerformance()+")", new double[] {0D}, new double[] {0D});
            charts.add(chart);
        }

        sw = new SwingWrapper<XYChart>(charts);
        sw.displayChartMatrix();
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    @Override
    public void run() {
        while (!shouldStop) {
            System.out.print("Нагруженность: ");
            times.add(times.getLast()+0.5D);
            for (int i = 0; i < servers.size(); i++) {
                System.out.print(servers.get(i).getCurrentLoad() + ", ");
                loads.get(i).add((double)servers.get(i).getCurrentLoad());
                charts.get(i).updateXYSeries("сервер " + (i+1) + " (" +servers.get(i).getPerformance()+")", times, loads.get(i), null);
            }

            if (times.size() > 10) {
                times.removeFirst();
                for (LinkedList list: loads) {
                    list.removeFirst();
                }
            }
            try {
                for (int i = 0; i < servers.size(); i++) {
                    sw.repaintChart(i);
                }
            }
            catch (Exception ignored) {}

            System.out.println();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutDown() {
        shouldStop = true;
        Thread.currentThread().interrupt();
    }
}
