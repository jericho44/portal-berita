package com.example.rico.portalberita.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity
@Table(name = "users")
class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "email", unique = true)
    var email = ""

    @Column(name = "password")
    var password = ""
        get() = field
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            // field = passwordEncoder.encode(value)
            field = value
        }

    @Column(name = "telp")
    var telp: String? = null

//    fun comparePassword(password: String): Boolean {
//        return BCryptPasswordEncoder().matches(password, this.password)
//    }

    fun comparePassword(password: String): Boolean {
        return this.password.equals(password)
    }
}