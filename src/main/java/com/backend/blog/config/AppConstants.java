package com.backend.blog.config;

public class AppConstants {

	public static final String PAGE_NUMBER = "1";
	public static final String PAGE_SIZE = "5";
	public static final String SORT_BY = "postId";
	public static final String SORT_DIR = "asc";
	public static final Integer ONE = 1;
	public static final boolean FALSE = false;
	public static final boolean TRUE = true;
	public static final String INVALID_FILE_FORMAT = "Invalid File Format!!";
	public static final String INVALID_CREDENTIALS = "Invalid Username or Password!!";
	public static final long JWT_TOKEN_VALIDITY = 60 * 60 * 1000;
	public static final Integer NORMAL_USER_ID = 502;
	public static final Integer ADMIN_USER_ID = 501;
	public static final String ADMIN_USER_NAME = "ROLE_ADMIN";
	public static final String NORMAL_USER_NAME = "ROLE_NORMAL";
	public static final String[] PUBLIC_URLS = {
			                 "/api/v1/auth/**",
			                 "/v3/api-docs/**",
			                 "/v2/api-docs",
			                 "/swagger-resources/**",
			                 "/swagger-ui/**",
			                 "/webjars/**"};
}
