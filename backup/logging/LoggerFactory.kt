package ilyin.logging

import mu.KLogger
import mu.KotlinLogging
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.context.Dependent
import javax.enterprise.inject.Default
import javax.enterprise.inject.Produces
import javax.enterprise.inject.spi.InjectionPoint

@ApplicationScoped
class LoggerFactory {
    @Default
    @Produces
    @Dependent
    fun logger(ip: InjectionPoint): KLogger {
        return KotlinLogging.logger(ip.bean.beanClass.typeName)
    }
}
