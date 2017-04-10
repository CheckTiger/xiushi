/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013�com.example.sharesdkloginsampleghts reserved.
 */

package demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.lanou.olddriver.R;
import com.lanou.olddriver.db.MyDBHelper;
import com.lanou.olddriver.utils.sys.Utilss;

import java.util.HashMap;

import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import demo.login.LoginApi;
import demo.login.OnLoginListener;
import demo.login.Tool;
import demo.login.UserInfo;

public class LoginPage extends Activity implements OnClickListener{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);
		initPlatformList();
	}

	/* 获取平台列表,显示平台按钮*/
	private void initPlatformList() {
		ShareSDK.initSDK(this);
		Platform[] Platformlist = ShareSDK.getPlatformList();
		if (Platformlist != null) {
			for (Platform platform : Platformlist) {
				if (!Tool.canGetUserInfo(platform)) {
					continue;
				}
				if (platform instanceof CustomPlatform) {
					continue;
				}
				String name = platform.getName();
				if (name.equals("QQ")){
					Button btn = (Button) findViewById(R.id.qq_login);
					btn.setSingleLine();

					System.out.println("名字"+name);
					if(platform.isAuthValid()){
						btn.setText(getString(R.string.remove_to_format, name));
					}else{
						btn.setText(getString(R.string.login_to_format, name));
					}
					btn.setTextSize(16);
					btn.setTag(platform);
					btn.setVisibility(View.VISIBLE);
					btn.setOnClickListener(this);
				}else if (name.equals("SinaWeibo"))
				{
					Button btn = (Button) findViewById(R.id.weibo_login);
					//new Button(this);
					btn.setSingleLine();

					System.out.println("名字"+name);
					if(platform.isAuthValid()){
						btn.setText(getString(R.string.remove_to_format, name));
					}else{
						btn.setText(getString(R.string.login_to_format, name));
					}
					btn.setTextSize(16);
					btn.setTag(platform);
					btn.setVisibility(View.VISIBLE);
					btn.setOnClickListener(this);
				}else if (name.equals("Wechat"))
				{
					Button btn = (Button) findViewById(R.id.weixin_login);
					//new Button(this);
					btn.setSingleLine();

					System.out.println("名字"+name);
					if(platform.isAuthValid()){
						btn.setText(getString(R.string.remove_to_format, name));
					}else{
						btn.setText(getString(R.string.login_to_format, name));
					}
					btn.setTextSize(16);
					btn.setTag(platform);
					btn.setVisibility(View.VISIBLE);
					btn.setOnClickListener(this);
				}
			}
		}
	}

	public void onClick(View v) {
		Button btn = (Button) v;
		Object tag = v.getTag();
		if (tag != null) {
			Platform platform = (Platform) tag;
			String name = platform.getName();
			System.out.println("名字"+name+" "+getString(R.string.login_to_format, name));
			if(!platform.isAuthValid()){
				btn.setText(getString(R.string.remove_to_format, name));
			}else{
				btn.setText(getString(R.string.login_to_format, name));
				String msg = getString(R.string.remove_to_format_success, name);
				MyDBHelper.getInstances(this).updata1(1,null,null);
				Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				Utilss.Re=1;

				setResult(0);
				finish();
			}
			//登陆逻辑的调用
			login(name);
		}
	}

	/*
	 * 演示执行第三方登录/注册的方法
	 * <p>
	 * 这不是一个完整的示例代码，需要根据您项目的业务需求，改写登录/注册回调函数
	 *
	 * @param platformName 执行登录/注册的平台名称，如：SinaWeibo.NAME
	 */
	private void login(String platformName) {
		LoginApi api = new LoginApi();
		//设置登陆的平台后执行登陆的方法
		api.setPlatform(platformName);
		api.setOnLoginListener(new OnLoginListener() {
			public boolean onLogin(String platform, HashMap<String, Object> res) {
				// 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
				// 此处全部给回需要注册
				return true;
			}

			public boolean onRegister(UserInfo info) {
				// 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
				return true;
			}
		});
		api.login(this);
	}

}
