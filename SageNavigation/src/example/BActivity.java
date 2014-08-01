package example;

import com.sage.navigation.BaseActivity;
import com.sage.navigation.R;

import example.MainActivity.MainActivityResultParam;

import android.os.Bundle;

public class BActivity extends BaseActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		NavigateMethod();
	}

	public static class BActivityParam {
		public String Title;
		public String Message;
	}

	private void NavigateMethod() {
		BActivityParam param1 = getParam(BActivityParam.class);
		MainActivityResultParam param = new MainActivityResultParam();
		param.Title = "aaa";
		setResult(param);
	}
}
