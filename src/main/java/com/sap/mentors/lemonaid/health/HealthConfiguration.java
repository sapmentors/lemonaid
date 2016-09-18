package com.sap.mentors.lemonaid.health;

import javax.sql.DataSource;

import org.springframework.boot.actuate.health.DataSourceHealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HealthConfiguration {

	@Bean
	public HealthIndicator dbHealthIndicator(final DataSource dataSource) {
	    DataSourceHealthIndicator indicator = new DataSourceHealthIndicator(dataSource);
	    indicator.setQuery("SELECT COUNT(1) id FROM mentors");
	    return indicator;
	}

}
