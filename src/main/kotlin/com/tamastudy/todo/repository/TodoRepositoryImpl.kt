package com.tamastudy.todo.repository

import com.tamastudy.todo.database.Todo
import com.tamastudy.todo.database.TodoDataBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.lang.Exception
import java.time.LocalDateTime

@Repository
class TodoRepositoryImpl : TodoRepository {

    @Autowired
    lateinit var todoDataBase: TodoDataBase

    override fun save(todo: Todo): Todo {
//        val index = ++todoDataBase.index
//        todo.index = index
//        todoDataBase.todoList.add(todo)
//        return todo

        return todo.apply {
            this.index = ++todoDataBase.index
            this.createdAt = LocalDateTime.now()
            this.updatedAt = LocalDateTime.now()
        }.run {
            todoDataBase.todoList.add(todo)
            this
        }
    }

    override fun saveAll(todoList: MutableList<Todo>): Boolean {
        return try {
            todoList.forEach {
                save(it)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun update(todo: Todo): Todo {
        return todo.index?.let { index ->
            // update
            findOne(index)?.apply {
                this.title = todo.title
                this.description = todo.description
                this.schedule = todo.schedule
                this.updatedAt = LocalDateTime.now()
            }
        } ?: kotlin.run {
            // insert
            todo.apply {
                this.index = ++todoDataBase.index
                this.createdAt = LocalDateTime.now()
                this.updatedAt = LocalDateTime.now()
            }.run {
                todoDataBase.todoList.add(todo)
                this
            }
        }
    }

    override fun delete(index: Int): Boolean {
        val todo = findOne(index)

        return todo.let {
            todoDataBase.todoList.remove(it)
            true
        }.run {
            false
        }
    }

    override fun findOne(index: Int): Todo {
        return todoDataBase.todoList.first {
            it.index == index
        }
    }

    override fun findAll(): MutableList<Todo> {
        return todoDataBase.todoList
    }
}