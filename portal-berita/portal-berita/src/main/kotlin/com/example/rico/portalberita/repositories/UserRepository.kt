package com.example.rico.portalberita.repositories

import com.example.rico.portalberita.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Int> {
    fun findByEmail(email: String): User?
}