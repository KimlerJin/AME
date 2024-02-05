package com.ame.config;

import com.ame.dao.BaseDao;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@AutoConfigureBefore({DataSourceAutoConfiguration.class, LiquibaseAutoConfiguration.class})
public class CommonsDataAutoConfiguration {



    @Bean
    @Primary
    public BaseDao baseDao() {
        return new BaseDao();
    }



//    @Bean
//    public EntityExtensionHandler entityExtensionService() {
//        return new EntityExtensionHandler();
//    }
//
//    @Bean
//    public DynamicEntityDao dynamicEntityDao() {
//        return new DynamicEntityDao();
//    }
//
//    @Bean
//    public DynamicEntityHandler dynamicEntityHandler() {
//        return new DynamicEntityHandler();
//    }
//
//    @Bean
//    public RelationShipLoader relationShipLoader() {
//        return new RelationShipLoader();
//    }
//
//    @Bean
//    public RelationShipManager relationShipManager() {
//        return new RelationShipManager();
//    }
//
//    @Bean
//    @DependsOn("sessionFactoryResetService")
//    public IIdentityLoader identityLoader() {
//        return new IdentityLoader();
//    }
//
//    @Bean
//    public ILicenceLoader licenceLoader(IIdentityLoader identityLoader) {
//        return new LicenceLoader(identityLoader);
//    }
//
//    @Bean
//    public LicenceAspect licenceAspect(ILicenceLoader licenceLoader) {
//        return new LicenceAspect(licenceLoader);
//    }

//    @Configuration
//    @ConditionalOnProperty("lumos.history.data.datasource.url")
//    @EnableConfigurationProperties({HistoryDataConfigurationProperties.class})
//    static class HistoryDataConfiguration {
//
//        @Autowired
//        private HistoryDataConfigurationProperties historyDataConfigurationProperties;
//
//        @Bean
//        @ConfigurationProperties(prefix = "lumos.history.data.datasource-hikari")
//        public HikariDataSource historyDataSource() {
//            HistoryDataConfigurationProperties.DataSourceConfig dataSourceConfig =
//                historyDataConfigurationProperties.getDatasource();
//            DataSourceBuilder<HikariDataSource> dataSourceBuilder =
//                DataSourceBuilder.create().type(HikariDataSource.class).username(dataSourceConfig.getUsername())
//                    .password(dataSourceConfig.getPassword()).url(dataSourceConfig.getUrl());
//            if (!Strings.isNullOrEmpty(dataSourceConfig.getDriver())) {
//                dataSourceBuilder.driverClassName(dataSourceConfig.getDriver());
//            }
//            return dataSourceBuilder.build();
//        }
//
//        @Bean
//        public StandardServiceRegistry historyStandardServiceRegistry(List<AbstractIntegrator> abstractIntegrators,
//            @Qualifier("historyDataSource") DataSource historyDataSource) {
//            BootstrapServiceRegistryBuilder bootstrapServiceRegistryBuilder = new BootstrapServiceRegistryBuilder();
//            abstractIntegrators.forEach(abstractIntegrator -> {
//                bootstrapServiceRegistryBuilder.applyIntegrator(abstractIntegrator);
//            });
//
//            BootstrapServiceRegistry serviceRegistry = bootstrapServiceRegistryBuilder.build();
//            return new StandardServiceRegistryBuilder(serviceRegistry)
//                .applySettings(historyDataConfigurationProperties.getHibernateProperties())
//                // .applySetting(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, SpringSessionContext.class.getName())
//                .applySetting(AvailableSettings.DATASOURCE, historyDataSource).build();
//        }
//
//        @Bean
//        public SessionFactory historySessionFactory(
//            @Qualifier("historyStandardServiceRegistry") StandardServiceRegistry standardServiceRegistry,
//            EntityScanPackages entityScanPackages) {
//            MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
//            MetadataSourcesConfigurer sourcesConfigurer = new MetadataSourcesConfigurer(metadataSources);
//            entityScanPackages.getPackageNames().forEach(sourcesConfigurer::addPackage);
//            sourcesConfigurer.config();
//            Metadata metadata = metadataSources.getMetadataBuilder()
//                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE).build();
//
//            SessionFactoryBuilder sessionFactoryBuilder = metadata.getSessionFactoryBuilder();
//            return sessionFactoryBuilder.build();
//        }
//    }
}
