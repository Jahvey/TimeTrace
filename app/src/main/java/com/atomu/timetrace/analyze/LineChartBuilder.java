package com.atomu.timetrace.analyze;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;

public class LineChartBuilder {
	public GraphicalView execute(Context context, long[] data, String unit){
		//设置颜色池
		int[] colors = new int[] { Color.rgb(0xa4, 0xc4, 0x00), Color.rgb(0x60, 0xa9, 0x17), 
                Color.rgb(0x00, 0x8a, 0x00), Color.rgb(0x00, 0xab, 0xa9), 
                Color.rgb(0x1b, 0xa1, 0xe2), Color.rgb(0x00, 0x50, 0xef),
                Color.rgb(0x6a, 0x00, 0xff), Color.rgb(0xaa, 0x00, 0xff),
                Color.rgb(0xf4, 0x72, 0xd0), Color.rgb(0xd8, 0x00, 0x73), 
                Color.rgb(0xa2, 0x00, 0x25), Color.rgb(0xe5, 0x14, 0x00),
                Color.rgb(0xfa, 0x68, 0x00), Color.rgb(0xf0, 0xa3, 0x0a),
                Color.rgb(0xe3, 0xc8, 0x00), Color.rgb(0x82, 0x5a, 0x2c),
                Color.rgb(0x6d, 0x87, 0x64), Color.CYAN}; 
		//设置tag
		String []tag={"beautifucation ","business ","communication ","education ","finance ",
			      "lifestyle ","media ","news_information ","online_shopping ","optimization ",
			      "reading ","social_network ","system ","tool ","travel ","ignore ","unknown ",
			      "game "};
		XYMultipleSeriesDataset dataset=new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		
		XYSeries series = new XYSeries(tag[0]);	
		for (int i=0; i<data.length; ++i){
			series.add(i,data[i]);  
		}
		
		XYSeriesRenderer r= new XYSeriesRenderer();
		r.setColor(colors[0]);//����line��ɫ
		//������Ⱦ
		renderer.setBackgroundColor(Color.BLACK);
		renderer.setApplyBackgroundColor(true);
		
	    renderer.addSeriesRenderer(r);
		dataset.addSeries(series);
		
		
		return ChartFactory.getLineChartView(context, dataset, renderer);
	}
		

}
