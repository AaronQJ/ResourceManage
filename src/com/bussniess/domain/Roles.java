package com.bussniess.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class Roles implements Serializable{

	private Integer roleId;
	private String roleName;
	
	private Set<Users> users = new HashSet<Users>();
	
	private Set<Privileges> privileges = new HashSet<Privileges>();
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	@Override
	public String toString() {
		return "Roles [roleId=" + roleId + ", roleName=" + roleName
				+  ", privileges=" + privileges + "]";
	}
	public Set<Users> getUsers() {
		return users;
	}
	public void setUsers(Set<Users> users) {
		this.users = users;
	}
	public Set<Privileges> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<Privileges> privileges) {
		this.privileges = privileges;
	}
	
	
}
