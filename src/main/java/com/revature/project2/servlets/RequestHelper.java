package com.revature.project2.servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {
	public static class Response {
		public static enum ResponseType {
			FORWARD, REDIRECT, PRINTWRITER
		}

		private ResponseType responseType;
		private String response;
		private Map<String,String> headers;

		public Response(ResponseType responseType, String response) {
			this.responseType = responseType;
			this.response = response;
			this.headers = new HashMap<String, String>();
		}

		public ResponseType getResponseType() {
			return responseType;
		}

		public String getResponse() {
			return response;
		}

		public Map<String, String> getHeaders() {
			return headers;
		}

		public Response setHeader(String name, String value) {
			if (name == null || value == null)
				return this;
			headers.put(name, value);
			return this;
		}

		public Response setResponseType(ResponseType responseType) {
			this.responseType = responseType;
			return this;
		}

		public Response setResponse(String response) {
			this.response = response;
			return this;
		}
	}

	public static Response processGET(HttpServletRequest request) {
		switch (request.getServletPath()) {
//		case "login.do":
//			return LoginController.loginAction(request);
//		case "posts.data":
//			return PostsController.postsJson(request);
		default:
			return null;
		}
	}
	
	public static Response processPOST(HttpServletRequest request) {
		switch (request.getServletPath()) {
//		case "login.do":
//			return LoginController.loginAction(request);
//		case "posts.data":
//			return PostsController.postsJson(request);
		default:
			return null;
		}
	}
}