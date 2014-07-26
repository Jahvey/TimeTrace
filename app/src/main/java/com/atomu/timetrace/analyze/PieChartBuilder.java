package com.atomu.timetrace.analyze;

import java.text.DecimalFormat;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class PieChartBuilder{
	public GraphicalView execute(Context context, long[] data){
		//������ɫ��
		int[] colors = new int[] { Color.rgb(0xa4, 0xc4, 0x00), Color.rgb(0x60, 0xa9, 0x17), 
				                   Color.rgb(0x00, 0x8a, 0x00), Color.rgb(0x00, 0xab, 0xa9), 
				                   Color.rgb(0x1b, 0xa1, 0xe2), Color.rgb(0x00, 0x50, 0xef),
				                   Color.rgb(0x6a, 0x00, 0xff), Color.rgb(0xaa, 0x00, 0xff),
				                   Color.rgb(0xf4, 0x72, 0xd0), Color.rgb(0xd8, 0x00, 0x73), 
				                   Color.rgb(0xa2, 0x00, 0x25), Color.rgb(0xe5, 0x14, 0x00),
				                   Color.rgb(0xfa, 0x68, 0x00), Color.rgb(0xf0, 0xa3, 0x0a),
				                   Color.rgb(0xe3, 0xc8, 0x00), Color.rgb(0x82, 0x5a, 0x2c),
				                   Color.rgb(0x6d, 0x87, 0x64), Color.CYAN}; 
		//Ϊÿ������������ɫ
		int[] result =new int [data.length];
		for (int i=0;i<data.length;i++){
			result[i]=colors[i];
		}
		//��ʼ��
		DefaultRenderer renderer = buildCategoryRenderer(result);
		CategorySeries categorySeries = new CategorySeries("title");
		
		//�������
		double[] percentage =new double[data.length];
		long total=0;
		for (long item :data){
			total+=item;
		}
		for (int i=0;i<data.length;++i){
			percentage[i]=(double)data[i]/total*100;
		}
		
		System.out.println(percentage[0]);
		DecimalFormat df =new DecimalFormat("#.00");
		
		String []tag={"beautifucation ","business ","communication ","education ","finance ",
				      "lifestyle ","media ","news_information ","online_shopping ","optimization ",
				      "reading ","social_network ","system ","tool ","travel ","ignore ","unknown ",
				      "game "};
		
		for (int i=0; i<data.length; ++i){
			categorySeries.add(tag[i]+df.format(percentage[i])+"%" , percentage[i]);
		}
		
		
		return ChartFactory.getPieChartView(context, categorySeries, renderer);
		
		
		
	}

	public DefaultRenderer buildCategoryRenderer(int[] colors) {
		DefaultRenderer renderer = new DefaultRenderer();
		
		for (int color : colors){
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
			
		}
		renderer.setBackgroundColor(Color.WHITE);
		renderer.setApplyBackgroundColor(true);
		renderer.setLabelsColor(Color.rgb(0x3d, 0x59, 0xab));
		renderer.setChartTitle("");
		renderer.setShowLegend(false);
		
		
		return renderer;
	}
}