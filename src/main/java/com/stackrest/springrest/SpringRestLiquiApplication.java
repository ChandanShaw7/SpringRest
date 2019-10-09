package com.stackrest.springrest;

//import com.stackrest.springrest.security.SpringLiquibaseDiffUpdate;
//import com.stackrest.springrest.security.SpringLiquibaseDiffUpdate;
//import liquibase.Liquibase;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
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
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.Logger;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class SpringRestLiquiApplication{

    private int currentTime = LocalTime.now().getMinute();
    private String filepath = "src/main/resources/db/changelog/schemaDifference"+currentTime+".xml";


//    private SpringLiquibaseDiffUpdate springDiff;

    public static void main(String[] args) {
        SpringApplication.run(SpringRestLiquiApplication.class, args);
    }

//    @Bean
//    @ConditionalOnBean(value = SpringLiquibaseDiffUpdate.class)
//    public SpringLiquibase liquibase() throws LiquibaseException, InterruptedException {
//        springDiff.performUpdate(filepath);
//        Thread.sleep(4000);
//        SpringLiquibase liquibase = new SpringLiquibase();
////        liquibase.setChangeLog("classpath:/db/changelog/changelog-master.xml");
////        liquibase.setDataSource(getDataSource("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/userdetails","root","Chandan$haw1992"));
//        liquibase.setDataSource(getDataSource());
//        String fileloc = "classpath:db/changelog/schemaDifference"+currentTime+".xml";
////        System.out.println(fileloc);
////        System.out.println(new File(filepath).exists());
////        if (new File(filepath).exists()){
//            liquibase.setChangeLog(fileloc);
////        }
//        System.out.println("after Assigning assigning changelog");
//        return liquibase;
//    }

//
    @Bean
    @Primary
//    @ConditionalOnBean(value = SpringLiquibaseDiffUpdate.class)
    public LiquibaseProperties LiquibaseProperty() throws LiquibaseException, InterruptedException {
        String filepath = "src/main/resources/db/changelog/schemaDifference"+currentTime+".xml";
        performUpdate();
//        Thread.sleep(10000);
//        LiquibaseConfiguration lConfig = new LiquibaseConfiguration();
        LiquibaseProperties liquibaseProperties = new LiquibaseProperties();
        liquibaseProperties.setChangeLog("classpath:/db/changelog/changelog-master.xml");
        liquibaseProperties.setUser("root");
        liquibaseProperties.setPassword("Chandan$haw1992");
        liquibaseProperties.setUrl("jdbc:mysql://localhost:3306/userdetails");
//        ResourceAccessor resourceAccessor = new FileSystemResourceAccessor();
//
//        Database database = DatabaseFactory.getInstance().openDatabase(
//                "jdbc:mysql://localhost:3306/userdetails",
//                "root",
//                "Chandan$haw1992",
//                null, resourceAccessor);
//        Liquibase liquibase = new Liquibase(filepath, resourceAccessor, database);
//        liquibaseProperties.setChangeLog(filepath);
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
    @Bean
    void performUpdate() throws LiquibaseException {

        ResourceAccessor resourceAccessor = new FileSystemResourceAccessor();
//        Liquibase liquibase = null;
//        DataSource primary = getDataSource("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/userdetails","root","Chandan$haw1992");
//        DataSource reference = getDataSource("org.postgresql.Driver","jdbc:postgresql://localhost:5432/userdetails","postgres","chandan192");
//

        Database primaryDatabase = DatabaseFactory.getInstance().openDatabase(
                    "jdbc:mysql://localhost:3306/userdetails",
                    "root",
                    "Chandan$haw1992",
                    null, resourceAccessor);


        Database referenceDatabase = DatabaseFactory.getInstance().openDatabase(
                    "jdbc:postgresql://localhost:5432/userdetails",
                    "postgres",
                    "chandan192",
                    null, resourceAccessor);

//        liquibase.diff(referenceDatabase,primaryDatabase, CompareControl.STANDARD);
        DiffResult result = DiffGeneratorFactory.getInstance().compare(primaryDatabase, referenceDatabase, new CompareControl());

        DiffToChangeLog changeLog = new DiffToChangeLog(result, new DiffOutputControl(false,false,false,new CompareControl.SchemaComparison[]{new CompareControl.SchemaComparison(primaryDatabase.getDefaultSchema(), referenceDatabase.getDefaultSchema())}));

        try {
//            changeLog.print(new File(filepath).getAbsolutePath(), new XMLChangeLogSerializer());
            changeLog.print(new File(filepath).getAbsolutePath(), new XMLChangeLogSerializer());
        } catch (ParserConfigurationException e) {
            System.out.println("perser exception in write");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Print io exception in write");
            e.printStackTrace();
        }

        System.out.println("Inside perform update");

//
//        try {
//            changeLog.print(System.out);
//        } catch (ParserConfigurationException e) {
//            System.out.println("perser exception");
//            e.printStackTrace();
//        } catch (IOException e) {
//            System.out.println("Print io exception");
//            e.printStackTrace();
//        }
    }

//    private Object createLiquibase(Connection connection) {
//        return connection;
//    }

//    private DataSource getDataSource(String driver, String url, String username, String password) {
    @Bean
    DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/userdetails");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("Chandan$haw1992");
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName(driver);
//        dataSourceBuilder.url(url);
//        dataSourceBuilder.username(username);
//        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

}
