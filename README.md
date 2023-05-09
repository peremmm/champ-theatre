<<<<<<< HEAD
## starter-spring-webapp


### Create the schema
```
CREATE USER champ_theatre IDENTIFIED BY champ_theatre;
GRANT CONNECT TO champ_theatre;
GRANT CONNECT, RESOURCE, DBA TO champ_theatre;
GRANT CREATE SESSION TO champ_theatre;
GRANT CREATE TABLE TO champ_theatre;
GRANT CREATE VIEW TO champ_theatre;
GRANT CREATE ANY TRIGGER TO champ_theatre;
GRANT CREATE ANY PROCEDURE TO champ_theatre;
GRANT CREATE SEQUENCE TO champ_theatre;
GRANT CREATE SYNONYM TO champ_theatre;
GRANT UNLIMITED TABLESPACE TO champ_theatre;

```

### Update persistence.xml unit name
```
<persistence-unit name="champtheatre">
    <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
</persistence-unit>
```

### Update JpaConfiguration.java
```
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setPersistenceUnitName("champtheatre");
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.bootcamp.webapp.web");
        factoryBean.setJpaProperties(hibernateProperties());
        return factoryBean;
    }
```

### Chrome Driver Download

=======
## starter-spring-webapp


### Create the schema
```
CREATE USER champ_theatre IDENTIFIED BY champ_theatre;
GRANT CONNECT TO champ_theatre;
GRANT CONNECT, RESOURCE, DBA TO champ_theatre;
GRANT CREATE SESSION TO champ_theatre;
GRANT CREATE TABLE TO champ_theatre;
GRANT CREATE VIEW TO champ_theatre;
GRANT CREATE ANY TRIGGER TO champ_theatre;
GRANT CREATE ANY PROCEDURE TO champ_theatre;
GRANT CREATE SEQUENCE TO champ_theatre;
GRANT CREATE SYNONYM TO champ_theatre;
GRANT UNLIMITED TABLESPACE TO champ_theatre;

```

### Update persistence.xml unit name
```
<persistence-unit name="champtheatre">
    <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
</persistence-unit>
```

### Update JpaConfiguration.java
```
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setPersistenceUnitName("champtheatre");
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.bootcamp.webapp.web");
        factoryBean.setJpaProperties(hibernateProperties());
        return factoryBean;
    }
```

### Chrome Driver Download

>>>>>>> d2daf4a (migrate to include Thymleaf)
https://chromedriver.chromium.org/downloads