package com.tongxin.locate;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.Overlay;

public class MyOverlay extends Overlay
{
	    GeoPoint geoPoint; 
	    GeoPoint geoPoint2; 
	    Paint paint = new Paint();
	    float ax,ay,bx,by;
	    
	    public MyOverlay(double ax,double ay,double bx,double by)
	    {
	    	this.ax=(float)ax;
	    	this.ay=(float)ay;
	    	this.bx=(float)bx;
	    	this.by=(float)by;
	    	
	    	geoPoint= new GeoPoint((int) (ax * 1E6),(int)(ay * 1E6));
	    	geoPoint2= new GeoPoint((int) (bx * 1E6),(int)(by * 1E6));
	    }
	    
	    public void draw(Canvas canvas, MapView mapView, boolean shadow) 
	    {
	    	Point point = mapView.getProjection().toPixels(geoPoint, null);
	    	Point point2 = mapView.getProjection().toPixels(geoPoint2, null);	    
	    	paint.setColor(Color.BLUE);
	    	paint.setAntiAlias(true);
	    	paint.setStrokeWidth(5);
	    	canvas.drawLine(point.x, point.y, point2.x, point2.y, paint);
	    	
	    	
	    //canvas.drawText("★这里是天安门", point.x, point.y, paint);
	   // canvas.drawPoint(point.x, point.y, paint);
	    
    }
}
