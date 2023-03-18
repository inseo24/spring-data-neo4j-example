package com.seoin.springdataneo4jexample

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity.created
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/hi")
class ExampleController(
    private val exampleService: ExampleService
) {
    @GetMapping("/{name}")
    fun getPerson(
        @PathVariable(name = "name") name: String
    ): String = exampleService.getPerson(name)

    @PostMapping("/{name}")
    fun createPerson(
        @PathVariable(name = "name") name: String
    ) = ok(exampleService.create(name))

    @PatchMapping("/{name}")
    fun updatePerson(
        @PathVariable(name = "name") oldName: String,
        @RequestParam(name = "new-name") newName: String
    ) = ok(exampleService.update(oldName, newName))

    @DeleteMapping("/{name}")
    fun deletePerson(
        @PathVariable(name = "name") name: String
    ) = ok(exampleService.delete(name))
}
