package com.example.rico.portalberita

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class PortalBeritaApplication

fun main(args: Array<String>) {
	runApplication<PortalBeritaApplication>(*args)
}
