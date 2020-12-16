
package utils;

import org.jfree.data.xy.XYSeriesCollection;


public class XYChartFactory {
    
    public static XYChart createXYLineChart(String title, String x_name, String y_name) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYLineChart lineChart = new XYLineChart( title,  x_name,  y_name, dataset);
        return lineChart;
    }
    
   
}
