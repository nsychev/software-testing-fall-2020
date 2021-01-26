package ru.nsychev.hashback

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HashbackApplication

fun main(args: Array<String>) {
    runApplication<HashbackApplication>(*args)
}
