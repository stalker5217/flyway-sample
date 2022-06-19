package com.example.flywaysample.config.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import com.example.flywaysample.support.Company;

@Configuration
public class DataSourceConfiguration {
	@Bean
	@ConfigurationProperties("spring.datasource-common")
	public DataSource dataSourceCommon() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource-a")
	public DataSource dataSourceA() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource-b")
	public DataSource dataSourceB() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	public DataSource routingDataSource(
		@Qualifier("dataSourceCommon") DataSource dataSourceCommon,
		@Qualifier("dataSourceA") DataSource dataSourceA,
		@Qualifier("dataSourceB") DataSource dataSourceB
	) {

		Map<Object, Object> dataSourceMap = new HashMap<>();
		dataSourceMap.put(Company.NONE, dataSourceCommon);
		dataSourceMap.put(Company.A, dataSourceA);
		dataSourceMap.put(Company.B, dataSourceB);

		dataSourceMap.forEach((key, dataSource) -> {
			if (key == Company.NONE) {
				flywayMigration((DataSource) dataSource, "/db/migration/common");
			} else {
				flywayMigration((DataSource) dataSource, "/db/migration/client");
			}
		});

		RoutingDataSource routingDataSource = new RoutingDataSource();
		routingDataSource.setTargetDataSources(dataSourceMap);
		routingDataSource.setDefaultTargetDataSource(dataSourceCommon);

		return routingDataSource;
	}

	private void flywayMigration(DataSource dataSource, String... location) {
		Flyway.configure()
			.dataSource(dataSource)
			.locations(location)
			.load()
			.migrate();
	}

	@Bean
	@Primary
	public DataSource lazyConnectionDataSource(@Qualifier("routingDataSource") DataSource dataSource) {
		return new LazyConnectionDataSourceProxy(dataSource);
	}
}
