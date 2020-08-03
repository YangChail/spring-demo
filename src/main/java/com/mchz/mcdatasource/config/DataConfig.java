package com.mchz.mcdatasource.config;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
 * @author yangc
 *
 */
@Configuration
@MapperScan("com.mchz.mcdatasource.mapper")
public class DataConfig {

	@Value("${spring.datasource.driver-class-name}")
	private String jdbcDriver;

	@Value("${spring.datasource.url}")
	private String jdbcURL;

	@Value("${spring.datasource.username}")
	private String jdbcUser;

	@Value("${spring.datasource.password}")
	private String jdbcPass;


	@Bean
	@Primary
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(jdbcDriver);
		dataSource.setUrl(jdbcURL);
		dataSource.setUsername(jdbcUser);
		dataSource.setPassword(jdbcPass);
		dataSource.setInitialSize(2);
		dataSource.setMaxActive(10);
		dataSource.setMinIdle(1);
		dataSource.setMaxWait(60000);
		dataSource.setValidationQuery("SELECT 'x'");
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnReturn(false);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setTimeBetweenEvictionRunsMillis(6000);
		dataSource.setMinEvictableIdleTimeMillis(300000);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
		return dataSource;
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean(name = "jdbcTemplate")
	public JdbcTemplate dumpJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}