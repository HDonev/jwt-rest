package bg.mvr.the.kiss.rest.configuration.application;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 06.01.2021.
 * Time: 09:56.
 * Organization: DKIS MOIA.
 */
@Configuration
public class ApplicationBeans {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
