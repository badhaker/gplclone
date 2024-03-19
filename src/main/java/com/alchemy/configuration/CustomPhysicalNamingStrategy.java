//package com.alchemy.configuration;
//
//import org.hibernate.boot.model.naming.Identifier;
//import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
//import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
//import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomPhysicalNamingStrategy implements PhysicalNamingStrategy {
//
//	private final PhysicalNamingStrategy delegate = new PhysicalNamingStrategyStandardImpl();
//
//	@Autowired
//	private Environment environment;
//
//	@Override
//	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
//		return delegate.toPhysicalCatalogName(name, jdbcEnvironment);
//	}
//
//	@Override
//	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
//		String schemaName = environment.getProperty("spring.jpa.properties.hibernate.default_schema", "public");
//		return Identifier.toIdentifier(schemaName);
//	}
//
//	@Override
//	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
//		return delegate.toPhysicalTableName(name, jdbcEnvironment);
//	}
//
//	@Override
//	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
//		return delegate.toPhysicalSequenceName(name, jdbcEnvironment);
//	}
//
//	@Override
//	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
//		return delegate.toPhysicalColumnName(name, jdbcEnvironment);
//	}
//}
