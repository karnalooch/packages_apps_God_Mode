package com.t3hh4xx0r.addons.utils;

import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

public class DeviceType {
	
	private static String TAG = "DeviceType";
	private static final boolean DBG = ( false || Constants.FULL_DBG);


public static String DEVICE_TYPE;
// Defualt to all densities
private static String DEVICE_DENSITY = "Unset";


// Device name constatnts

/**
 * HTC Droid Incredible
 */
public static final String INCREDIBLE = "Incredible";
public static final String INCREDIBLE_SCRIPT = "inc.js";

public static final String ERIS = "Eris";
public static final String ERIS_SCRIPT = "desirec.js";


public static final String EVO = "Evo";
public static final String EVO_SCRIPT = "supersonic.js";

public static final String HERO = "Hero";
public static final String HERO_SCRIPT = "heroc.js";

public static final String THUNDERBOLT = "Thunderbolt" ;
public static final String THUNDERBOLT_SCRIPT = "mecha.js";

public static final String DROID = "Droid";
public static final String DROID_SCRIPT = "sholes.js";

public static final String INCREDIBLE2 = "Incredible 2";
public static final String INCREDIBLE2_SCRIPT = "vivow.js";

public static final String FASCINATEMTD = "SCH-I500";
public static final String FASCINATEMTD_SCRIPT = "fascinatemtd.js";

@SuppressWarnings("unused")
public static boolean deviceEquals(String s){
		
		if(Build.MODEL.equals(s))
			return true;
		else 
			return false;

}

/**
 * 
 * 
 * @param density the density as a specified int from DisplayMetrics
 * @return true if the density was set, else return false
 */
@SuppressWarnings("unused")
public static boolean determineDeviceDensity(int density){
		
	switch(density)
	{
	
	
		case DisplayMetrics.DENSITY_XHIGH:
			if(DBG)Log.i(TAG, "Device density is xhdpi");
			setDensity("xhdpi");
			return true;
		case DisplayMetrics.DENSITY_HIGH:
			if(DBG)Log.i(TAG, "Device density is hdpi");
			setDensity("hdpi");
			return true;
		case DisplayMetrics.DENSITY_MEDIUM:
			if(DBG)Log.i(TAG, "Device density is mdpi");
			setDensity("mdpi");
			return true;
		case DisplayMetrics.DENSITY_LOW:
			if(DBG)Log.i(TAG, "Device density is ldpi");
			setDensity("ldpi");
			return true;
	}
	
	
		return false;
	
		
	}

private static void setDensity(String density) {
	DEVICE_DENSITY = density;
}
public static String getDensity() {
	return DEVICE_DENSITY;
}

}
