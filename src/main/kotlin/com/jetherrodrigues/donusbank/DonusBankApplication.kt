package com.jetherrodrigues.donusbank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
class DonusBankApplication

fun main(args: Array<String>) {
	runApplication<DonusBankApplication>(*args)
}
