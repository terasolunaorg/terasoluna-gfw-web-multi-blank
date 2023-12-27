package xxxxxx.yyyyyy.zzzzzz.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import xxxxxx.yyyyyy.zzzzzz.domain.service.errortest.MockTestService;

/**
 * Bean definition of SpringMvc(MockMvc).
 */
@Configuration
public class SpringMvcMockMvcConfig {

    /**
     * Mock definition of MockTestService.
     * @return MockTestService(Mock) bean definition
     */
    @Bean(name = "mockTestService")
    public MockTestService mockTestService() {
        return Mockito.mock(MockTestService.class,
                "org.mockito.internal.creation.bytebuddy.MockMethodInterceptor");
    }
}
