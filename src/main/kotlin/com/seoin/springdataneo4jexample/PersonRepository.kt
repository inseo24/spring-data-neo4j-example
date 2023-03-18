package com.seoin.springdataneo4jexample

import org.springframework.data.neo4j.repository.Neo4jRepository

interface PersonRepository : Neo4jRepository<Person, Long> {

    fun findByName(name: String): Person?
    fun findByTeammatesName(name: String): List<Person>
}
