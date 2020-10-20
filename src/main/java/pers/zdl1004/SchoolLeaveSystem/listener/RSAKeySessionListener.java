package pers.zdl1004.SchoolLeaveSystem.listener;

import java.security.KeyPair;
import java.util.Base64;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import pers.zdl1004.SchoolLeaveSystem.adapter.HttpSessionAdapter;
import pers.zdl1004.SchoolLeaveSystem.util.RSAUtil;

/**
 * session创建时的监听器，用于给session分配RSA密钥对和时间戳（用于验证密钥对是否过期）
 * @author zdl1004
 *
 */
@WebListener
public class RSAKeySessionListener implements HttpSessionListener {

	/*每次创建一个session时，都会创建一个RSA密钥*/
	/*currentTimeMillis() ：获得当前的时间*/
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		//生成一个RSA密钥对
		KeyPair keyPair = RSAUtil.genKeyPair();
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(event.getSession());
		sessionAdapter.setRsaKeyPair(keyPair);
		sessionAdapter.setRsaPublicKey(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
		sessionAdapter.setRsaCreateTimestamp(System.currentTimeMillis());
	}

//	@Override
//	public void sessionDestroyed(HttpSessionEvent event) {
//	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {

	}
}
