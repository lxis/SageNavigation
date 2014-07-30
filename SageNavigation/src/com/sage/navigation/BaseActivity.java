package com.sage.navigation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;





import com.google.gson.Gson;

public class BaseActivity extends Activity
{	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){	
		super.onActivityResult(requestCode, resultCode, data);		
		Bundle bundle = data.getExtras();
		String fromType = bundle.getString("FromType");
		String resultTypeText = bundle.getString("ResultType");
		String resultText = bundle.getString("Result");
		
		Type resultType = new Gson().fromJson(resultTypeText,Type.class);
		Object result = new Gson().fromJson(resultText, resultType);
		
		CommonSimpleHandlerGenic<Object> handler = handlers.get(fromType);
		handler.Run(result);
//		new Gson().fromJson(json, typeOfT) result
//		CommonSimpleHandlerGenic<Object> handler = Result.get(resultType);
//		handler.Run(item);
//		handler.Run(bundle.get("Result"));			
	}
	
	public NavigationInterface Navigate(Class classType)
	{
		return new NavigationInterface(this,classType);
	}
	
	HashMap<String, CommonSimpleHandlerGenic<Object>> handlers = new HashMap<String, CommonSimpleHandlerGenic<Object>>();
	
	public void go(Class NavigateClass,Object Param,HashMap<Class, CommonSimpleHandlerGenic<Object>> handlers)
	{		
		Intent intent = new Intent(this,NavigateClass);
		Bundle b = new Bundle(); 		
		b.putString("NavigateParam", new Gson().toJson(Param));
		intent.putExtras(b);
		
		Iterator<Entry<Class, CommonSimpleHandlerGenic<Object>>> entryKeyIterator = handlers.entrySet().iterator();
		       while (entryKeyIterator.hasNext()) {
		            Entry<Class, CommonSimpleHandlerGenic<Object>> e = entryKeyIterator.next();
		            this.handlers.put(e.getKey().getName(), e.getValue());		            
		 }
		this.startActivityForResult(intent,1);
	}	
	
	public void setResult(Object object)
	{
		Intent intent = new Intent();
		Bundle bundle = new Bundle();				
		bundle.putString("FromType", this.getClass().getName());
		bundle.putString("ResultType", new Gson().toJson(object.getClass()));
		bundle.putString("Result",new Gson().toJson(object));
		intent.putExtras(bundle);
		setResult(1, intent);
	}
	
	public <T> T getParam(Class<T> classType )
	{
		return new Gson().fromJson(getIntent().getExtras().getString("NavigateParam"),classType);
	}		
}
