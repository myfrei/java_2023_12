package ru.otus;

import org.eclipse.jetty.security.LoginService;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.web.dao.InMemoryUserDao;
import ru.otus.web.dao.UserDao;
import ru.otus.web.server.UsersWebServer;
import ru.otus.web.server.UsersWebServerBase;
import ru.otus.web.service.InMemoryLoginServiceImpl;
import ru.otus.web.service.TemplateProcessor;
import ru.otus.web.service.TemplateProcessorImpl;

public class Runner {

    private static final Logger log = LoggerFactory.getLogger(Runner.class);
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        log.info("Start the application");
        var dbServiceClient = prepareDB();
        log.info("Start the template Processor");
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        log.info("Start preparing dao");
        UserDao userDao = new InMemoryUserDao();
        LoginService loginService = new InMemoryLoginServiceImpl(userDao);
        log.info("Start the web server");
        UsersWebServer usersWebServer =
                new UsersWebServerBase(WEB_SERVER_PORT, templateProcessor, dbServiceClient, loginService);
        usersWebServer.start();
        usersWebServer.join();
        // ...
    }

    private static DBServiceClient prepareDB() {

        log.info("Start preparing the database");
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");
        log.info("Start the MigrationsExecutorFlyway");
        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();
        log.info("Start the sessionFactory");
        var sessionFactory =
                HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        log.info("The database was prepared");

        return dbServiceClient;
    }
}
