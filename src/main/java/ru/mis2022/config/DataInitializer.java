package ru.mis2022.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnExpression("${app.runInitialize:true}")
public class DataInitializer {

    public DataInitializer() {

    }
}
