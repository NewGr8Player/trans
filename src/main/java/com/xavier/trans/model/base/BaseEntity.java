package com.xavier.trans.model.base;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class BaseEntity implements Serializable {

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
