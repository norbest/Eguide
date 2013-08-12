package com.tongxin.locate;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.Overlay;

public class GraphOverlay extends Overlay
{
    GeoPoint geoPoint = new GeoPoint((int) (39.970997 * 1E6),(int)(116.360832 * 1E6));
    Paint paint = new Paint();
    int bmp_width;
    int bmp_height;
    Rect dst=new Rect(); 
    double scale=0.25;
    
    public void setscale(double rate)
    {
    	this.scale=rate;
    }
 
    public void draw(Canvas canvas, MapView mapView, boolean shadow) 
    {/*
		    Point point = mapView.getProjection().toPixels(geoPoint, null);
		    Bitmap bmp=BitmapFactory.decodeResource(LocateActivity.res, R.drawable.map3); 
		    bmp_width=Integer.parseInt(String.valueOf(bmp.getWidth()));
		    bmp_height=Integer.parseInt(String.valueOf(bmp.getHeight()));
		    bmp_width*=scale;
		    bmp_height*=scale; 
		    Bitmap dstbmp=Bitmap.createBitmap(bmp, 0,0,bmp_width,bmp_height);
		    dst.left=point.x;
		    dst.top=point.y;
		    dst.right=point.x+dstbmp.getWidth();
		    dst.bottom=point.y+dstbmp.getHeight();
		    canvas.drawBitmap(bmp, null,dst, paint);   
		    */
    }
    
}