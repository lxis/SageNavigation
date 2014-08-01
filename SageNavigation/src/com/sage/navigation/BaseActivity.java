package com.sage.navigation;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.sage.core.CommonSimpleHandlerGenic;

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
		String fromType = bundle.getString(TransferDataConstant.FromType);
		String resultTypeText = bundle.getString(TransferDataConstant.ResultType);
		String resultText = bundle.getString(TransferDataConstant.Result);
		
		Type resultType = new Gson().fromJson(resultTypeText,Type.class);
		Object result = new Gson().fromJson(resultText, resultType);
		
		CommonSimpleHandlerGenic<Object> handler = handlers.get(fromType);
		handler.run(result);
	}
	
	/*
	 *  Start buiding navigation info 
	 */
	public NavigationInterface navigate(Class classType)
	{
		return new NavigationInterface(this,classType);
	}
	
	HashMap<String, CommonSimpleHandlerGenic<Object>> handlers = new HashMap<String, CommonSimpleHandlerGenic<Object>>();
	
	/*
	 *  Navigate by all navigation info
	 */
	public void go(Class NavigateClass,Object Param,HashMap<Class, CommonSimpleHandlerGenic<Object>> handlers)
	{		
		Intent intent = new Intent(this,NavigateClass);
		Bundle b = new Bundle(); 		
		b.putString(TransferDataConstant.NavigateParam, new Gson().toJson(Param));
		intent.putExtras(b);
		
		Iterator<Entry<Class, CommonSimpleHandlerGenic<Object>>> entryKeyIterator = handlers.entrySet().iterator();
		       while (entryKeyIterator.hasNext()) {
		            Entry<Class, CommonSimpleHandlerGenic<Object>> e = entryKeyIterator.next();
		            this.handlers.put(e.getKey().getName(), e.getValue());		            
		 }
		this.startActivityForResult(intent,1);
	}	
	
	/*
	 *  Send param to previous activity
	 */
	public void setResult(Object object)
	{
		Intent intent = new Intent();
		Bundle bundle = new Bundle();				
		bundle.putString(TransferDataConstant.FromType, this.getClass().getName());
		bundle.putString(TransferDataConstant.ResultType, new Gson().toJson(object.getClass()));
		bundle.putString(TransferDataConstant.Result,new Gson().toJson(object));
		intent.putExtras(bundle);
		setResult(1, intent);
	}
	
	/*
	 *  Get param from previous activity
	 */
	public <T> T getParam(Class<T> classType )
	{
		return new Gson().fromJson(getIntent().getExtras().getString(TransferDataConstant.NavigateParam),classType);
	}		
}
