
package com.example.flywaysample.config.interceptor;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.flywaysample.config.datasource.CompanyContextHolder;
import com.example.flywaysample.support.Company;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PathCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler
	) {

		String[] splitPath = request.getRequestURI().split("/");
		Company targetCompany = Company.NONE;

		if (splitPath.length > 1) {
			Optional<Company> companyOptional = Arrays.stream(Company.values())
				.filter(c -> splitPath[1].equalsIgnoreCase(c.toString()))
				.findAny();

			if (companyOptional.isPresent()) {
				targetCompany = companyOptional.get();
			}
		}

		CompanyContextHolder.set(targetCompany);

		log.info("{}", CompanyContextHolder.get());

		return true;
	}

	@Override
	public void postHandle(
		HttpServletRequest request,
		HttpServletResponse response,
		Object handler,
		ModelAndView modelAndView
	) {
		CompanyContextHolder.remove();
	}
}
