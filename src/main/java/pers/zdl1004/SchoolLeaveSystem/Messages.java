package pers.zdl1004.SchoolLeaveSystem;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

//TODO 分离各功能的本地化字符串，如页面跳转字符串、返回信息字符串分类

public class Messages {
	private static final String BUNDLE_NAME = "pers.zdl1004.SchoolLeaveSystem.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
