package com.example.flywaysample.config.converter;

import java.util.Arrays;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.flywaysample.support.Company;

@Component
public class CompanyConverter implements Converter<String, Company> {
	@Override
	public Company convert(String source) {
		return Arrays.stream(Company.values())
			.filter(c -> source.equalsIgnoreCase(c.toString()))
			.findAny()
			.orElseThrow(IllegalArgumentException::new);
	}
}
