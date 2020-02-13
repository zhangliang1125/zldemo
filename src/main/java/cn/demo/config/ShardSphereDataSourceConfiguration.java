package cn.demo.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import io.shardingsphere.api.config.MasterSlaveRuleConfiguration;
import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.api.config.strategy.NoneShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @author zhangliang
 */
@Configuration
public class ShardSphereDataSourceConfiguration {
	@Value("${shard.num.some.table}")
	private int shardNumSomeTable = 4;

	@Bean("dataSource_0")
	@ConfigurationProperties(prefix = "spring.datasource.master.hikari")
	public DataSource masterDataSource() {
		return new HikariDataSource();
	}

	@Bean("dataSource_1")
	@ConfigurationProperties(prefix = "spring.datasource.slaver.hikari")
	public DataSource slaveDataSource(){
		return new HikariDataSource();
	}

	@Bean("dataSource")
	@Autowired
	public DataSource dataSource(@Qualifier("dataSource_0") DataSource masterDataSource, @Qualifier("dataSource_1") DataSource slaveDataSource)
			throws SQLException {
		/** 设置读写分离(配置单库) **/
		MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration("masterSlave", "dataSource_0", Arrays
				.asList("dataSource_1"));

		/** 设置分片规则 **/
		ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
		/** 设置周报分片规则 **/


		/** 数据库路由为空 **/
		shardingRuleConfiguration.setDefaultDatabaseShardingStrategyConfig(new NoneShardingStrategyConfiguration());
		shardingRuleConfiguration.setMasterSlaveRuleConfigs(Arrays.asList(masterSlaveRuleConfiguration));

		/** 每个数据源的配置 **/
		Map<String, DataSource> dataSourceMap = new HashMap<>();
		dataSourceMap.put("dataSource_0", masterDataSource);
		dataSourceMap.put("dataSource_1", slaveDataSource);

		Properties properties = new Properties();
		/** 日志打印 **/
		properties.setProperty("sql.show", "true");
		/** 执行线程数，默认是CPU数量 **/
		properties.setProperty("executor.size", "8");

		return ShardingDataSourceFactory
				.createDataSource(dataSourceMap, shardingRuleConfiguration, new HashMap(16), properties);
	}

//

	@Bean("sqlSessionFactory")
	@Autowired
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, PaginationInterceptor paginationInterceptor) throws Exception {
		MybatisSqlSessionFactoryBean mybatisSqlSessionFactory = new MybatisSqlSessionFactoryBean();
		mybatisSqlSessionFactory.setDataSource(dataSource);
//		mybatisSqlSessionFactory.setTypeEnumsPackage("com.xes.ops.insight.plus.mybatis.enums");
		MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
		mybatisConfiguration.setDefaultEnumTypeHandler(EnumOrdinalTypeHandler.class);
		mybatisConfiguration.setLogImpl(Log4j2Impl.class);
		mybatisSqlSessionFactory.setConfiguration(mybatisConfiguration);
		mybatisSqlSessionFactory.setPlugins(new Interceptor[]{paginationInterceptor});

		return mybatisSqlSessionFactory.getObject();
	}

	@Autowired
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Autowired
	@Bean
	public DataSourceTransactionManager txManager(@Qualifier("dataSource") DataSource dynamicDataSource) {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dynamicDataSource);
		return txManager;
	}
}