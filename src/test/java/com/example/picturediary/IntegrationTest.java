package com.example.picturediary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public abstract class IntegrationTest
{
    @Autowired
    protected MockMvc mockMvc;

    static final DockerComposeContainer composeContainer;

    static
    {
        composeContainer = new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

        composeContainer.start();
    }
}
