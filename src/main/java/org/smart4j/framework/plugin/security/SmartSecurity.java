package org.smart4j.framework.plugin.security;

import java.util.Set;

/**
 * Smart Security  接口
 * @author jwcjlu
 *
 */

public interface SmartSecurity {
	/**
	 *  根据用户名获取密码
	 * @param userName  用户名
	 * @return
	 */
	public String  getPassword(String userName);
	/**
	 * 根据用户名获取角色集合
	 * @param username用户名
	 * @return
	 */
	public  Set<String>  getRoleNameSet(String username);
	/**
	 * 根据角色名获取权限名集合
	 * @param roleName 角色名
	 * @return
	 */

	public Set<String>  getPermissionNameSet(String roleName);
}
