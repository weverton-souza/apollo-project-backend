package annotation

import com.design.hub.DesignHubApplication
import com.design.hub.util.TestContainerConfiguration
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc
import org.springframework.boot.test.context.SpringBootTest

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(classes = [DesignHubApplication::class])
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@ImportAutoConfiguration
@ExtendWith(TestContainerConfiguration::class)
annotation class IntegrationTest
