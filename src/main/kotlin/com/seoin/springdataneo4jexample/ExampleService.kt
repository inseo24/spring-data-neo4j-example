package com.seoin.springdataneo4jexample

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExampleService(
    private val personRepository: PersonRepository
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
}
