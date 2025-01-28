package com.example.paginaLogare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    // Aceasta face ca această sursă de date să fie utilizată în mod implicit, dacă nu există alte surse de date configurate cu aceeași prioritate
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");  // sau alt driver de bază de date, în funcție de ce folosești
        dataSource.setUrl("jdbc:mysql://localhost:3306/pagina_logare"); // URL-ul bazei tale de date
        dataSource.setUsername("username"); // Numele de utilizator al bazei de date
        dataSource.setPassword("password"); // Parola bazei de date
        return dataSource;
    }
}
