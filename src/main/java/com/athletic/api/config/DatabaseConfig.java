package com.athletic.api.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.athletic.api.**.repository"},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
@MapperScan(
        basePackages = {"com.athletic.api.**.mapper"},
        sqlSessionFactoryRef = "sqlSessionFactory"
)
public class DatabaseConfig {
    private final String DATA_SOURCE_NAME = "dataSource";
    private final String JPA_PROPERTIES_NAME = "jpaProperties";
    private final String ENTITY_MANAGER_FACTORY_NAME = "entityManagerFactory";
    private final String TRANSACTION_MANAGER_NAME = "transactionManager";
    private final String SQL_SESSION_FACTORY_NAME = "sqlSessionFactory";
    private final String MAPPER_LOCATION_PATH = "classpath:/mybatis/mapper/**/*.xml";
    private final String CONFIG_LOCATION_PATH = "classpath:/mybatis/config/mybatis-config.xml";

    @Primary
    @Bean(name = DATA_SOURCE_NAME)
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = JPA_PROPERTIES_NAME)
    @ConfigurationProperties(prefix = "spring.jpa")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Primary
    @Bean(name = ENTITY_MANAGER_FACTORY_NAME)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier(DATA_SOURCE_NAME) DataSource dataSource,
                                                                       @Qualifier(JPA_PROPERTIES_NAME) JpaProperties jpaProperties,
                                                                       ConfigurableListableBeanFactory beanFactory) {

        LocalContainerEntityManagerFactoryBean build = builder
                .dataSource(dataSource)
                .properties(jpaProperties.getProperties())
                .packages("com.athletic.api.**.entity")
                .persistenceUnit("default")
                .build();

        /* Unable to determine basic type mapping via reflection 에러 해결 */
        build.getJpaPropertyMap().put(AvailableSettings.BEAN_CONTAINER, new SpringBeanContainer(beanFactory));

        return build;
    }

    @Primary
    @Bean(name = TRANSACTION_MANAGER_NAME)
    public PlatformTransactionManager transactionManager(@Qualifier(ENTITY_MANAGER_FACTORY_NAME) LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory.getObject());
        transactionManager.setNestedTransactionAllowed(true);
        return transactionManager;
    }

    @Bean(name = SQL_SESSION_FACTORY_NAME)
    public SqlSessionFactory sqlSessionFactory(@Qualifier(DATA_SOURCE_NAME) DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION_PATH));
        sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(CONFIG_LOCATION_PATH));
        return sqlSessionFactoryBean.getObject();
    }

}
