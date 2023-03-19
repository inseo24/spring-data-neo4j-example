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

    @Relationship(type = "HAS_READ", direction = Relationship.Direction.OUTGOING)
    var booksRead: MutableSet<Book>? = mutableSetOf()

    @Relationship(type = "FRIENDS_WITH", direction = Relationship.Direction.OUTGOING)
    var friends: MutableSet<Person>? = mutableSetOf()

    fun addFriend(stranger: Person) {
        this.friends?.add(stranger)
        stranger.friends?.add(this)
    }

    fun readBook(book: Book) {
        this.booksRead?.add(book)
    }

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
