package com.sap.mentors.lemonaid.jobs;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class QuartzConfig {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void init() {
		log.debug("QuartzConfig initialized.");
	}

	@Bean
	public SchedulerFactoryBean quartzScheduler() {
		SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();

		quartzScheduler.setDataSource(dataSource);
		quartzScheduler.setTransactionManager(transactionManager);
		quartzScheduler.setOverwriteExistingJobs(true);
		quartzScheduler.setSchedulerName("lemonaid-scheduler");

		// custom job factory of spring with DI support for @Autowired!
		JobFactory jobFactory = new JobFactory();
		jobFactory.setApplicationContext(applicationContext);
		quartzScheduler.setJobFactory(jobFactory);

		quartzScheduler.setQuartzProperties(quartzProperties());

		Trigger[] triggers = { 
				gravatarTrigger().getObject(), 
				locationTrigger().getObject(),
				insideTrackTrigger().getObject()
			};
		quartzScheduler.setTriggers(triggers);

		return quartzScheduler;
	}

	@Bean
	public JobDetailFactoryBean gravatarJob() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(GravatarJob.class);
		jobDetailFactory.setGroup("lemonaid-quartz");
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}

	@Bean
	public CronTriggerFactoryBean gravatarTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(gravatarJob().getObject());
		cronTriggerFactoryBean.setCronExpression("0 30 * * * ?"); // Every hour
		cronTriggerFactoryBean.setGroup("lemonaid-quartz");
		return cronTriggerFactoryBean;
	}

	@Bean
	public JobDetailFactoryBean locationJob() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(LocationJob.class);
		jobDetailFactory.setGroup("lemonaid-quartz");
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}

	@Bean
	public CronTriggerFactoryBean locationTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(locationJob().getObject());
		cronTriggerFactoryBean.setCronExpression("0 15 0 * * ?"); // Every day
		cronTriggerFactoryBean.setGroup("lemonaid-quartz");
		return cronTriggerFactoryBean;
	}

	@Bean
	public JobDetailFactoryBean insideTrackJob() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(InsideTrackJob.class);
		jobDetailFactory.setGroup("lemonaid-quartz");
		jobDetailFactory.setDurability(true);
		return jobDetailFactory;
	}

	@Bean
	public CronTriggerFactoryBean insideTrackTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(insideTrackJob().getObject());
		cronTriggerFactoryBean.setCronExpression("0 0 * * * ?"); // Every hour
		cronTriggerFactoryBean.setGroup("lemonaid-quartz");
		return cronTriggerFactoryBean;
	}
	
	@Bean
	public Properties quartzProperties() {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		Properties properties = null;
		try {
			propertiesFactoryBean.afterPropertiesSet();
			properties = propertiesFactoryBean.getObject();

		} catch (IOException e) {
			log.warn("Cannot load quartz.properties.");
		}

		return properties;
	}
}