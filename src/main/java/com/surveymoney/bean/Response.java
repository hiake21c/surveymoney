package com.surveymoney.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections4.MapUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
// 비동기 통신을 하기 위해 사용되는 response 클래스
public class Response extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 2405172041950251807L;

	private Integer returnCode;
	private String returnMessage;
	private Map<String, Object> context;

	public Response() {
		context = new HashMap<>();
		this.setReturnCode(200);
		this.setReturnMessage("success");
	}

	@JsonProperty(value="returnCode")
	public Integer getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	@JsonProperty(value="returnMessage")
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	@JsonProperty(value="context")
	@JsonInclude(Include.NON_NULL)
	public Map<String, Object> getContext() {
		if (MapUtils.isEmpty(context)) {
			return null;
		}
		return context;
	}
	public Object putContext(String key, Object value) {
		return context.put(key, value);
	}
	public void putContextAll(Map<String, Object> contextMap) {
		context.putAll(contextMap);
	}
}
