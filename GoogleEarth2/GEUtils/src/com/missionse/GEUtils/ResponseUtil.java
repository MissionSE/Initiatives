package com.missionse.GEUtils;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response.ResponseBuilder;

public class ResponseUtil {

	public static void addCacheTimeout(ResponseBuilder builder, int duration) {
		CacheControl cc = new CacheControl();
		cc.setMaxAge(86400);  // can you make it infinite?
		cc.setPrivate(true); // only browsers should cache it; why?
		builder.cacheControl(cc);
	}
}
