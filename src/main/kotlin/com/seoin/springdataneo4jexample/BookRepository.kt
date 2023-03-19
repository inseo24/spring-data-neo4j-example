package com.seoin.springdataneo4jexample

import org.springframework.data.neo4j.repository.Neo4jRepository

interface BookRepository : Neo4jRepository<Book, Long> {
    fun findByTitle(name: String): Book?
}
