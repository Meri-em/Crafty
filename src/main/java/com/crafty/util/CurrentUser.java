package com.crafty.util;

import com.crafty.security.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {

	public static String getId() {
		return getJwtUser().getId();
	}

	public static String getMemberId() {
		return getJwtUser().getMemberId();
	}

	public static JwtUser getJwtUser() {
		return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
