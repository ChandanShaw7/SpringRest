package com.stackrest.springrest;

//import com.stackrest.springrest.security.SpringLiquibaseDiffUpdate;
import liquibase.Liquibase;
import liquibase.configuration.LiquibaseConfiguration;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.diff.DiffGeneratorFactory;
import liquibase.diff.DiffResult;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.DiffToChangeLog;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class SpringRestLiquiApplication{

      private String filepath = "src/main/resources/db/changelog/schemaDifference.xml";
//    @Autowired
//    private SpringLiquibaseDiffUpdate springDiff;

    public static void main(String[] args) {
        SpringApplication.run(SpringRestLiquiApplication.class, args);
    }

//    @Bean
//    public SpringLiquibase liquibase() {
//        SpringLiquibase liquibase = new SpringLiquibase();
//        liquibase.setChangeLog("classpath:/db/changelog/changelog-master.xml");
//        liquibase.setDataSource(getDataSource());
//        return liquibase;
//    }

    @Bean
    @Primary
    public LiquibaseProperties LiquibaseProperty() throws LiquibaseException {
//        LiquibaseConfiguration lConfig = new LiquibaseConfiguration();
        LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
//        liquibaseProperties.setChangeLog("classpath:/db/changelog/changelog-master.xml");
        liquibaseProperties.setUser("root");
        liquibaseProperties.setPassword("Chandan$haw1992");
        liquibaseProperties.setUrl("jdbc:mysql://localhost:3306/userdetails");
        ResourceAccessor resourceAccessor = new FileSystemResourceAccessor();

        Database database = DatabaseFactory.getInstance().openDatabase(
                "jdbc:mysql://localhost:3306/userdetails",
                "root",
                "Chandan$haw1992",
                null, resourceAccessor);
        Liquibase liquibase = new Liquibase(filepath, resourceAccessor, database);
        performUpdate();
        liquibaseProperties.setChangeLog(filepath);
        return liquibaseProperties;
    }
//
//
//
//        @Bean
//        public DataSource getDataSource() {
//            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//            dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
//            dataSourceBuilder.url("jdbc:mysql://localhost:3306/userdetails");
//            dataSourceBuilder.username("root");
//            dataSourceBuilder.password("Chandan$haw1992");
//            return dataSourceBuilder.build();
//        }

//    @Override
    private void performUpdate() throws LiquibaseException {

        ResourceAccessor resourceAccessor = new FileSystemResourceAccessor();
        Liquibase liquibase = null;
        DataSource reference = getDataSource("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/userdetails","root","Chandan$haw1992");
        DataSource primary = getDataSource("org.postgresql.Driver","jdbc:postgresql://localhost:5432/userdetails","postgres","chandan192");


        Database referenceDatabase = DatabaseFactory.getInstance().openDatabase(
                    "jdbc:mysql://localhost:3306/userdetails",
                    "root",
                    "Chandan$haw1992",
                    null, resourceAccessor);


        Database primaryDatabase = DatabaseFactory.getInstance().openDatabase(
                    "jdbc:postgresql://localhost:5432/userdetails",
                    "postgres",
                    "chandan192",
                    null, resourceAccessor);

//        liquibase.diff(referenceDatabase,primaryDatabase, CompareControl.STANDARD);
        DiffResult result = DiffGeneratorFactory.getInstance().compare(referenceDatabase, primaryDatabase, new CompareControl());

        DiffToChangeLog changeLog = new DiffToChangeLog(result, new DiffOutputControl(false,false,false,new CompareControl.SchemaComparison[]{new CompareControl.SchemaComparison(referenceDatabase.getDefaultSchema(), primaryDatabase.getDefaultSchema())}));

        try {
            changeLog.print(new File(filepath).getAbsolutePath(), new XMLChangeLogSerializer());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            changeLog.print(System.out);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private Object createLiquibase(Connection connection) {
//        return connection;
//    }

    private DataSource getDataSource(String driver,String url, String username, String password) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
//        dataSourceBuilder.url("jdbc:mysql://localhost:3306/userdetails");
//        dataSourceBuilder.username("root");
//        dataSourceBuilder.password("Chandan$haw1992");
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

}
