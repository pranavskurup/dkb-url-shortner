package com.dkbcodefactory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
class DKBURLShortnerApplication

fun main(args: Array<String>) {
	runApplication<DKBURLShortnerApplication>(*args)
}
