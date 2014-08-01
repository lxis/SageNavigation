package example;

import com.sage.core.CommonSimpleHandlerGenic;
import com.sage.navigation.BaseActivity;
import com.sage.navigation.R;

import example.BActivity.BActivityParam;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	private void navigate()
	{
		BActivityParam param = new BActivityParam();
		param.Message = "message";
		param.Title = "title";

		navigate(BActivity.class).param(param).handler(MainActivityResultParam.class, new CommonSimpleHandlerGenic<Object>()
		{
			@Override
			public <T> void run(T item)
			{
				String title = ((MainActivityResultParam) item).Title;
			}
		}).go();
	}

	public static class MainActivityResultParam
	{
		public String Title;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is
		// present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
