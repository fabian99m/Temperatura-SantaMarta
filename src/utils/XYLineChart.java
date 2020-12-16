
package utils;

import org.jfree.chart.ChartFactory;
import org.jfree.data.xy.XYSeriesCollection;


public class XYLineChart extends XYChart{
    
    public XYLineChart(String title, String x_name, String y_name, XYSeriesCollection dataset) {
        super(ChartFactory.createXYLineChart(title, x_name, y_name, dataset));
    }
    
}
