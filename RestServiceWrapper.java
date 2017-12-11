package com.api.framework;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jayway.restassured.RestAssured;

public class RestServiceWrapper {
	public static String restGetCall(String url, String parameter, int StatusCode) {
		String res = StringUtils.EMPTY;
		if (parameter != StringUtils.EMPTY) {
			res = RestAssured.given().when().get(url + "/{}", parameter).then().statusCode(StatusCode).extract()
					.asString();
		} else {
			res = RestAssured.given().when().get(url).then().statusCode(StatusCode).extract().asString();
		}
		return res;
	}

	public static String restPostCall(String url, Map values, int StatusCode) {
		String res = RestAssured.given().contentType("application/json").body(values).when().post(url).then()
				.statusCode(200).extract().asString();
		return res;
	}
}
