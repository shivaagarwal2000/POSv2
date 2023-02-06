package com.pos.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan("com.pos")
@PropertySources({ //
		@PropertySource(value = "file:./pos.properties", ignoreResourceNotFound = true) //
})
public class SpringConfig {


}
