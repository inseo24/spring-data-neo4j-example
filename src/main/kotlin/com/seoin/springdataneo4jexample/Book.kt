package com.seoin.springdataneo4jexample

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
class Book(
    @Id
    @GeneratedValue
    var id: Long? = null,
    val title: String
)
