package pers.zdl1004.SchoolLeaveSystem.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import pers.zdl1004.SchoolLeaveSystem.type.UserType;

/**
 * 访问添加此注解的Controller方法需要大于等于注解value的权限
 * @author dzj0821
 *
 */
@Retention(RetentionPolicy.RUNTIME)
//注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在； 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Target(ElementType.METHOD)//作用目标为方法
public @interface UserTypeRequired {
	public UserType value();
}
