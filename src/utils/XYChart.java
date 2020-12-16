
package utils;

import java.awt.Color;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.LegendItemEntity;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public abstract class XYChart {

    private JFreeChart lineChart;
    private XYSeriesCollection dataset;
    private ChartPanel panel;
    private ChartMouseListener legendsSeriesListener;

    public XYChart(JFreeChart xyChart) {
        this.lineChart = xyChart;
        XYPlot plot = (XYPlot) lineChart.getPlot();
        this.dataset = (XYSeriesCollection) plot.getDataset();
        this.panel = new ChartPanel(this.lineChart);
        this.legendsSeriesListener = new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent cme) {
                ChartEntity entity = cme.getEntity();
                if (cme.getEntity() instanceof LegendItemEntity) {
                    LegendItemEntity itemEntity = (LegendItemEntity) entity;
                    XYDataset dataset = (XYDataset) itemEntity.getDataset();
                    int index = dataset.indexOf(itemEntity.getSeriesKey());
                    XYPlot plot = (XYPlot) cme.getChart().getPlot();
                    XYItemRenderer renderer = plot.getRenderer();
                    renderer.setSeriesVisible(index, !renderer.isSeriesVisible(index), true);
                }
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent cme) {

            }

        };
    }

    public void prepareSerie(String name, Color color, int[] x, float[] y) {
        XYSeries series = new XYSeries(name);
        for (int i = 0; i < x.length; i++) {
            series.add(x[i], y[i]);
        }
        dataset.addSeries(series);
        if (color != null) {
            XYPlot plot = (XYPlot) lineChart.getPlot();
            NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
            yAxis.setAutoRangeIncludesZero(false);
            XYItemRenderer renderer = plot.getRenderer();
            renderer.setSeriesPaint(plot.getSeriesCount() - 1, color);
        }
    }
    public void prepareSerie(String name, Color color, float[] x, float[] y) {
        XYSeries series = new XYSeries(name);
        for (int i = 0; i < x.length; i++) {
            series.add(x[i], y[i]);
        }
        dataset.addSeries(series);
        if (color != null) {
            XYPlot plot = (XYPlot) lineChart.getPlot();
            NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
            yAxis.setAutoRangeIncludesZero(false);
            XYItemRenderer renderer = plot.getRenderer();
            renderer.setSeriesPaint(plot.getSeriesCount() - 1, color);
        }
    }


    public void prepareSerie(String name, Color color, int[] x, int[] y) {
        XYSeries series = new XYSeries(name);
        for (int i = 0; i < x.length; i++) {
            series.add(x[i], y[i]);
        }
        dataset.addSeries(series);
        if (color != null) {
            XYPlot plot = (XYPlot) lineChart.getPlot();

            NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
            yAxis.setAutoRangeIncludesZero(false);

            XYItemRenderer renderer = plot.getRenderer();
            renderer.setSeriesPaint(plot.getSeriesCount() - 1, color);

        }

    }

    public void enable_seriesHidding() {
        XYPlot plot = lineChart.getXYPlot();
        LegendItemCollection legendItems = plot.getLegendItems();
        plot.setFixedLegendItems(legendItems);
        panel.addChartMouseListener(legendsSeriesListener);
    }

    public JFreeChart getLineChart() {
        return lineChart;
    }

    public void setLineChart(JFreeChart lineChart) {
        this.lineChart = lineChart;
    }

    public XYSeriesCollection getDataset() {
        return dataset;
    }

    public void setDataset(XYSeriesCollection dataset) {
        this.dataset = dataset;
    }

    public ChartPanel getPanel() {
        return panel;
    }

    public void setPanel(ChartPanel panel) {
        this.panel.removeAll();
        this.panel = panel;
    }

}
