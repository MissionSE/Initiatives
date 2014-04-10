package com.missionse.GEUtils;

import java.sql.ResultSet;

public class Util {
	public static boolean isResultSetEmpty (ResultSet rs) {
		boolean rval = false;
		try {
			rval = (rs == null || rs.isBeforeFirst() == false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rval;
	}
}
