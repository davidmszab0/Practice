package resources;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import soundsystem.CDPlayerTest;

/**
 * Created by grace on 06/02/17.
 */
@Configuration
@ComponentScan(basePackages={"soundsystem"})
//@ComponentScan(basePackageClasses={CDPlayerTest.class})
public class CDPlayerConfig {
}
