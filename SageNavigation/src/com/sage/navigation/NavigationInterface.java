package com.sage.navigation;


import java.util.HashMap;

import com.sage.core.CommonSimpleHandlerGenic;

import android.app.Activity;

/*
 *  Fluent interface for building navigation info easily
 */
public class NavigationInterface
{
	BaseActivity currentActivity;	
	Object navigateParam;
	HashMap<Class, CommonSimpleHandlerGenic<Object>> resultHandlers = new HashMap<Class, CommonSimpleHandlerGenic<Object>>();
	Class navigateToClassType;
	
	/*
	 *  Init navigation interface with some init info
	 */
	public NavigationInterface(BaseActivity currentActivity,Class navigateToClassType)
	{
		this.currentActivity = currentActivity;		
		this.navigateToClassType = navigateToClassType;
	}
	
	/*
	 *  Set param which will set to navigated activity
	 */
	public NavigationInterface param(Object navigateParam)
	{
		this.navigateParam = navigateParam;
		return this;
	}
	
	/*
	 *  Set result handler in case navigated activity setting result.
	 */
	public NavigationInterface handler(Class resultClassType,CommonSimpleHandlerGenic<Object> resultHandler)
	{
		resultHandlers.put(resultClassType, resultHandler);
		return this;
	}
	
	/*
	 *  Finish setting navigating info
	 */
	public void go()
	{
		currentActivity.go(navigateToClassType,navigateParam, resultHandlers);		
	}
}
