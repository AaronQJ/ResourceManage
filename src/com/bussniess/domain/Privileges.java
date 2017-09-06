package com.bussniess.domain;

import java.util.HashSet;
import java.util.Set;


public class Privileges {
	private Integer privilegeId;
	private String path;
	private String privilegeName;
	
	private Set<Roles> roles = new HashSet<Roles>();
	
	public Integer getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPrivilegeName() {
		return privilegeName;
	}
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	@Override
	public String toString() {
		return "Privileges [privilegeId=" + privilegeId + ", path=" + path
				+ ", privilegeName=" + privilegeName + "]";
	}
	public Set<Roles> getRoles() {
		return roles;
	}
	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}
	

}
