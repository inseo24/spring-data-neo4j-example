package com.seoin.springdataneo4jexample

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
class Person(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var name: String
) {

    @Relationship(type = "TEAMMATE")
    var teammates: MutableSet<Person>? = null

    fun worksWith(person: Person) {
        if (teammates == null) {
            teammates = HashSet()
        }
        teammates?.add(person)
    }

    fun updatePersonName(name: String) {
        this.name = name
    }

    override fun toString(): String {
        return "$name's teammates => " +
            (teammates?.map { it.name } ?: emptyList<String>())
    }
}
