package com.seoin.springdataneo4jexample

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExampleService(
    private val personRepository: PersonRepository,
    private val bookRepository: BookRepository
) {

    @Transactional(readOnly = true)
    fun getPerson(name: String): String {
        return personRepository.findByName(name)?.name ?: "no-user"
    }

    @Transactional
    fun create(name: String): String {
        val user = personRepository.save(Person(name = name))
        return user.name
    }

    @Transactional
    fun update(oldName: String, newName: String) {
        personRepository.findByName(oldName)?.run {
            updatePersonName(newName)
            personRepository.save(this)
        } ?: throw NoSuchElementException()
    }

    @Transactional
    fun delete(name: String) {
        personRepository.findByName(name)?.run {
            personRepository.delete(this)
        } ?: throw NoSuchElementException()
    }

    @Transactional
    fun addFriendToSeoin(strangerName: String) {
        val seoin = getSeoin()
        val stranger = personRepository.findByName(strangerName)
            ?: throw NoSuchElementException("Stranger with name $strangerName not found")

        stranger.addFriend(seoin)
        seoin.addFriend(stranger)

        personRepository.saveAll(listOf(seoin, stranger))
    }

    @Transactional
    fun readBook(name: String) {
        val book = bookRepository.findByTitle(name)
            ?: Book(title = "Graph Database").also { bookRepository.save(it) }
        val seoin = getSeoin()

        seoin.readBook(book)
        personRepository.save(seoin)
    }

    fun getFriendsList(): List<String>? {
        val seoin = getSeoin()
        return seoin.friends?.map { it.name }
    }

    fun getBookReadList(): MutableSet<Book>? {
        val seoin = getSeoin()
        return seoin.booksRead
    }

    private fun getSeoin() = personRepository.findByName("seoin") ?: throw NoSuchElementException()
}
