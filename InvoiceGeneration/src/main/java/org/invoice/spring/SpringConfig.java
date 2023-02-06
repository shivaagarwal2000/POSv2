package org.invoice.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan("org.invoice")
@PropertySources({ //
		@PropertySource(value = "file:./invoice.properties", ignoreResourceNotFound = true) //
})
public class SpringConfig {


}
