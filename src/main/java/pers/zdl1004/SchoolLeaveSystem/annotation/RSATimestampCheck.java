package pers.zdl1004.SchoolLeaveSystem.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 添加此注解的Controller方法在执行前需要经过时间戳验证
 * @author dzj0821
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RSATimestampCheck {
	
}
