package com.sbs.example.mysqlutil;

import java.io.PrintStream;
import java.io.PrintWriter;

public class MysqlUtilException extends RuntimeException {
	private Exception originException;
	private SecSql sql;
	
	public MysqlUtilException(Exception e) {
		this.originException = e;
		sql = sql;
		
		if( sql != null) {
			System.err.println("SQL 오류 : " + sql.getRawSql());
		}
	}

	@Override
	public String getMessage() {
		return originException.getMessage();
	}

	@Override
	public String getLocalizedMessage() {
		return originException.getLocalizedMessage();
	}

	@Override
	public synchronized Throwable getCause() {
		return originException.getCause();
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return originException.getStackTrace();
	}

	@Override
	public String toString() {
		return originException.toString();
	}

	@Override
	public void printStackTrace() {
		originException.printStackTrace();
	}

	@Override
	public void printStackTrace(PrintStream s) {
		originException.printStackTrace(s);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		originException.printStackTrace(s);
	}
}