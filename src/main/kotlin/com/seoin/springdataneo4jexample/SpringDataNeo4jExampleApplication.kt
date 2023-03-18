package com.seoin.springdataneo4jexample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories

@SpringBootApplication
@EnableNeo4jRepositories
class SpringDataNeo4jExampleApplication {

    // Spring Doc Example Code
//
//    private val log = LoggerFactory.getLogger(SpringDataNeo4jExampleApplication::class.java)
//
//    @Bean
//    fun demo(personRepository: PersonRepository): CommandLineRunner {
//        return CommandLineRunner {
//            personRepository.deleteAll()
//
//            val greg = Person(name = "Greg")
//            val roy = Person(name = "Roy")
//            val craig = Person(name = "Craig")
//
//            val team = listOf(greg, roy, craig)
//
//            log.info("Before linking up with Neo4j...")
//
//            team.forEach { person -> log.info("\t$person") }
//
//            personRepository.save(greg)
//            personRepository.save(roy)
//            personRepository.save(craig)
//
//            personRepository.findByName(greg.name)?.apply {
//                worksWith(roy)
//                worksWith(craig)
//                personRepository.save(this)
//            }
//
//            personRepository.findByName(roy.name)?.apply {
//                worksWith(craig)
//                personRepository.save(this)
//            }
//
//            log.info("Lookup each person by name...")
//            team.forEach { person ->
//                log.info("\t${personRepository.findByName(person.name)}")
//            }
//
//            val teammates = personRepository.findByTeammatesName(greg.name)
//            log.info("The following have Greg as a teammate...")
//            teammates.forEach { person -> log.info("\t${person.name}") }
//        }
//    }
}

fun main(args: Array<String>) {
    runApplication<SpringDataNeo4jExampleApplication>(*args)
}
