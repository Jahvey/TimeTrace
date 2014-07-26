
package com.atomu.timetrace.analyze;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * Sales demo bar chart.
 */
public class BarChartBuilder {
	
	public GraphicalView execute(Context context, long[] values) {
		XYMultipleSeriesDataset dataset=new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		int[] colors = new int[] { Color.rgb(0xa4, 0xc4, 0x00), Color.rgb(0x60, 0xa9, 0x17), 
                Color.rgb(0x00, 0x8a, 0x00), Color.rgb(0x00, 0xab, 0xa9), 
                Color.rgb(0x1b, 0xa1, 0xe2), Color.rgb(0x00, 0x50, 0xef),
                Color.rgb(0x6a, 0x00, 0xff), Color.rgb(0xaa, 0x00, 0xff),
                Color.rgb(0xf4, 0x72, 0xd0), Color.rgb(0xd8, 0x00, 0x73), 
                Color.rgb(0xa2, 0x00, 0x25), Color.rgb(0xe5, 0x14, 0x00),
                Color.rgb(0xfa, 0x68, 0x00), Color.rgb(0xf0, 0xa3, 0x0a),
                Color.rgb(0xe3, 0xc8, 0x00), Color.rgb(0x82, 0x5a, 0x2c),
                Color.rgb(0x6d, 0x87, 0x64), Color.CYAN}; 
		String []tag={"beautifucation ","business ","communication ","education ","finance ",
			      "lifestyle ","media ","news_information ","online_shopping ","optimization ",
			      "reading ","social_network ","system ","tool ","travel ","ignore ","unknown ",
			      "game "};
		
		
	
		
		for (int i=0; i<values.length; ++i){
			XYSeries series = new XYSeries(tag[i]);
			series.add(i,values[i]);
			dataset.addSeries(series);
			XYSeriesRenderer r= new XYSeriesRenderer();
	        r.setColor(colors[i]);//����bar��ɫ
	        renderer.addSeriesRenderer(r);
		}
		
		
	    renderer.setChartTitle("����");//���ñ���
	    renderer.setXTitle("x");//����X�����
	    renderer.setYTitle("y");//����Y�����
	   // renderer.setXAxisMin(0);
	   // renderer.setXAxisMax(19);
	   // renderer.setYAxisMin(0);
	   // renderer.setYAxisMax(10);
	    renderer.setBackgroundColor(Color.BLACK);//���ñ���ɫ
	    renderer.setApplyBackgroundColor(true);
	    renderer.setShowGrid(true);//������ʾ����
	   
	    
	    //renderer.setBarSpacing(0.5);
	    renderer.setBarWidth(6);
	    renderer.setXLabels(0);
	    renderer.setXLabelsAngle(-25);
	    //renderer.setPanLimits(new double []{-1,20,0,10});
	    //for (int i=0; i<values.length; ++i){
	    //	renderer.addXTextLabel(i, tag[i]);
	    //}
		
		return ChartFactory.getBarChartView(context, dataset, renderer, Type.DEFAULT);

  }

		


}
