//package com.stackrest.springrest.security;
//
//import liquibase.CatalogAndSchema;
//import liquibase.Liquibase;
//import liquibase.database.Database;
//import liquibase.database.DatabaseConnection;
//import liquibase.database.core.MySQLDatabase;
//import liquibase.database.core.PostgresDatabase;
//import liquibase.diff.DiffGeneratorFactory;
//import liquibase.diff.DiffResult;
//import liquibase.diff.compare.CompareControl;
//import liquibase.diff.output.DiffOutputControl;
//import liquibase.diff.output.changelog.DiffToChangeLog;
//import liquibase.exception.LiquibaseException;
//import liquibase.integration.spring.SpringLiquibase;
//import liquibase.serializer.core.xml.XMLChangeLogSerializer;
//import liquibase.structure.DatabaseObject;
//import liquibase.structure.core.View;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//
//import javax.persistence.Table;
//import javax.sql.DataSource;
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.File;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
//
//
//public class SpringLiquibaseDiffUpdate extends SpringLiquibase {
//
//    @Override
//    protected void performUpdate(Liquibase liquibase) throws LiquibaseException {
//
//        DataSource reference = getDataSource("com.mysql.cj.jdbc.Driver","jdbc:mysql://localhost:3306/userdetails","root","Chandan$haw1992");
//        DataSource primary = getDataSource("org.postgresql.Driver","jdbc:postgresql://localhost:5432/userdetails","postgres","chandan192");
////        Database referenceDatabase = new MySQLDatabase();
////        referenceDatabase.setConnection((DatabaseConnection) reference);
//
//        Database referenceDatabase = null;
//        try {
//            referenceDatabase = (Database) createLiquibase(reference.getConnection());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        Database primaryDatabase = null;
//        try {
//            primaryDatabase = (Database) createLiquibase(primary.getConnection());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
////        Database targetDatabase = new PostgresDatabase();
////        targetDatabase.setConnection((DatabaseConnection) primary);
//
////        CatalogAndSchema catalogAndSchemaReference = new CatalogAndSchema("userdetails","userdetails");
////        CatalogAndSchema catalogAndSchemaPrimary = new CatalogAndSchema("userdetails","public");
//
////        Set<Class<? extends DatabaseObject>> finalCompareTypes = null;
////        Class<? extends DatabaseObject>[] snapshotTypes = new Class[]{Table.class , View.class};
////        finalCompareTypes = new HashSet<>((Collection<? extends Class<? extends DatabaseObject>>) Arrays.asList(snapshotTypes));
//
////        CompareControl compareControl = new CompareControl(new CompareControl.SchemaComparison[]{new CompareControl.SchemaComparison(catalogAndSchemaReference, catalogAndSchemaPrimary)}, finalCompareTypes);
////        CompareControl compareControl = new CompareControl(new CompareControl.SchemaComparison(catalogAndSchemaReference,catalogAndSchemaPrimary), finalCompareTypes);
////        liquibase.diff(referenceDatabase, targetDatabase, compareControl);
////        liquibase.diff(reference, primary,CompareControl.STANDARD);
//        liquibase.diff(referenceDatabase,primaryDatabase,CompareControl.STANDARD);
//        DiffResult result = DiffGeneratorFactory.getInstance().compare(referenceDatabase, primaryDatabase, new CompareControl());
//
//        DiffToChangeLog changeLog = new DiffToChangeLog(result, new DiffOutputControl(false,false,false,new CompareControl.SchemaComparison[]{new CompareControl.SchemaComparison(referenceDatabase.getDefaultSchema(), primaryDatabase.getDefaultSchema())}));
//
//        try {
//            changeLog.print(new File("target/schemaDifferences.xml").getAbsolutePath(), new XMLChangeLogSerializer());
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            changeLog.print(System.out);
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        liquibase
//    }
//
//    private DataSource getDataSource(String driver,String url, String username, String password) {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
////        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
////        dataSourceBuilder.url("jdbc:mysql://localhost:3306/userdetails");
////        dataSourceBuilder.username("root");
////        dataSourceBuilder.password("Chandan$haw1992");
////        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName(driver);
//        dataSourceBuilder.url(url);
//        dataSourceBuilder.username(username);
//        dataSourceBuilder.password(password);
//        return dataSourceBuilder.build();
//    }
//}
