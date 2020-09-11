package ru.falin.RestaurantVotingSystem.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.falin.RestaurantVotingSystem.TimingExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.falin.RestaurantVotingSystem.util.ValidationUtil.getRootCause;

@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml"})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ExtendWith(TimingExtension.class)
abstract class AbstractServiceTest {
}
