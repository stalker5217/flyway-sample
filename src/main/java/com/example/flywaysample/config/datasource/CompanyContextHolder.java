package com.example.flywaysample.config.datasource;

import com.example.flywaysample.support.Company;

public class CompanyContextHolder {
	private CompanyContextHolder() {
		throw new IllegalStateException();
	}

	private static final ThreadLocal<Company> target = new ThreadLocal<>();

	public static void set(Company company) {
		target.set(company);
	}

	public static Company get() {
		return target.get();
	}

	public static void remove() {
		target.remove();
	}
}
