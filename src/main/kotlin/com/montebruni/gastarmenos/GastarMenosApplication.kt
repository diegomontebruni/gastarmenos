package com.montebruni.gastarmenos

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import java.util.TimeZone

@SpringBootApplication
@ConfigurationPropertiesScan
@ServletComponentScan
class GastarMenosApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    runApplication<GastarMenosApplication>(*args)
}
