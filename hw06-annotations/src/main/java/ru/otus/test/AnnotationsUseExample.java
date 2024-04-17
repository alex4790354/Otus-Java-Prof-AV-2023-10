package ru.otus.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.test.annotations.*;

public class AnnotationsUseExample {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationsUseExample.class);

    @BeforeAll
    public static void globalSetUp() {
        logger.info("Executing globalSetUp");
    }

    @BeforeAll
    public static void globalSetUp2() {
        logger.info("Executing globalSetUp2");
    }

    @BeforeEach
    public void setUp() {

        logger.info("SetUp executing");
    }

    @BeforeEach
    public void setUp2() {
        logger.info("SetUp-2 executing");
    }

    @Test
    public void test01() {
        logger.info("Executing test01");
    }

    @Test
    public void test02() {
        logger.info("Executing test02");
        throw new RuntimeException("Exception during secondTest");
    }

    @Test
    public void test03() {
        logger.info("Executing test03");
    }

    @AfterEach
    public void afterEach01() {
        logger.info("AfterEach-01");
    }

    @AfterEach
    public void afterEach02() {
        logger.info("AfterEach-02");
    }

    @AfterAll
    public static void afterAll01() {
        logger.info("AfterAll-01");
    }

    @AfterAll
    public static void afterAll02() {
        logger.info("AfterAll-02");
    }
}
