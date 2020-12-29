package com.tamastudy.todo.repository

import com.tamastudy.todo.config.AppConfig
import com.tamastudy.todo.database.Todo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

@SpringBootTest(classes = [TodoRepositoryImpl::class, AppConfig::class])
class TodoRepositoryImplTest {

    @Autowired
    lateinit var todoRepositoryImpl: TodoRepositoryImpl

    @BeforeEach
    fun before() {
        todoRepositoryImpl.todoDataBase.init()
    }

    @Test
    fun save() {
        val todo = Todo().apply {
            this.title = "테스트 일정"
            this.description = "TEST"
            this.schedule = LocalDateTime.now()
        }

        val result = todoRepositoryImpl.save(todo)

        Assertions.assertEquals(1, result.index)
        Assertions.assertNotNull(result.createdAt)
        Assertions.assertNotNull(result.updatedAt)
        Assertions.assertEquals("테스트 일정", result.title)
        Assertions.assertEquals("TEST", result.description)

    }

    @Test
    fun saveAll() {
        val todoList = mutableListOf(
                Todo().apply {
                    this.title = "테스트 일정"
                    this.description = "TEST"
                    this.schedule = LocalDateTime.now()
                },
                Todo().apply {
                    this.title = "테스트 일정"
                    this.description = "TEST"
                    this.schedule = LocalDateTime.now()
                },
                Todo().apply {
                    this.title = "테스트 일정"
                    this.description = "TEST"
                    this.schedule = LocalDateTime.now()
                }
        )

        val result = todoRepositoryImpl.saveAll(todoList)

        Assertions.assertEquals(true, result)
    }

    @Test
    fun update() {
        val todo = Todo().apply {
            this.title = "테스트 일정"
            this.description = "TEST"
            this.schedule = LocalDateTime.now()
        }
        val insertTodo = todoRepositoryImpl.save(todo)

        val newTodo = Todo().apply {
            this.index = insertTodo.index
            this.title = "업데이트 일정"
            this.description = "업데이트 테스트"
            this.schedule = LocalDateTime.now()
        }
        val result = todoRepositoryImpl.update(newTodo)

        Assertions.assertNotNull(result)
        Assertions.assertEquals(insertTodo?.index, result?.index)
        Assertions.assertEquals("업데이트 일정", result?.title)
        Assertions.assertEquals("업데이트 테스트", result?.description)
    }

    @Test
    fun delete() {
    }

    @Test
    fun findOne() {
        val todoList = mutableListOf(
                Todo().apply {
                    this.title = "테스트 일정1"
                    this.description = "TEST"
                    this.schedule = LocalDateTime.now()
                },
                Todo().apply {
                    this.title = "테스트 일정2"
                    this.description = "TEST"
                    this.schedule = LocalDateTime.now()
                },
                Todo().apply {
                    this.title = "테스트 일정3"
                    this.description = "TEST"
                    this.schedule = LocalDateTime.now()
                }
        )

        todoRepositoryImpl.saveAll(todoList)

        val result = todoRepositoryImpl.findOne(2)
        println(result)
        Assertions.assertNotNull(result)
        Assertions.assertEquals("테스트 일정2", result.title)
    }

    @Test
    fun findAll() {
    }
}