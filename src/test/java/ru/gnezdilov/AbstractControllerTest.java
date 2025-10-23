package ru.gnezdilov;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import ru.gnezdilov.config.SecurityConfiguration;

import java.sql.SQLException;

@WebMvcTest
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@ContextConfiguration(classes = WebApplication.class)
@WithUserDetails(value="john@mail.ru", userDetailsServiceBeanName = "customTestUserDetailsService")
public abstract class AbstractControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    protected ObjectMapper om;
    protected ObjectWriter ow;
    protected ConstraintViolationException constraintViolationSQLAlreadyExistException;

    @BeforeEach
    public void setUp() throws Exception {
        om = new ObjectMapper();
        ow = om.writer().withDefaultPrettyPrinter();

        SQLException sqlException = new SQLException("repeat recording", "23505");
        constraintViolationSQLAlreadyExistException = new ConstraintViolationException("Such a record already exists", sqlException, "repeat recording");
    }
}
