package com.example.rico.portalberita.controllers

import com.example.rico.portalberita.Message
import com.example.rico.portalberita.models.User
import com.example.rico.portalberita.services.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
//@RequestMapping(value = ["/news"], produces = [MediaType.APPLICATION_JSON_VALUE])
class AuthController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody body: User
    ): ResponseEntity<User> {
        val user = User()
        user.name = body.name
        user.email = body.email
        user.password = body.password
        user.telp = body.telp

        return ResponseEntity(this.userService.register(user), HttpStatus.OK)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody body: User,
        response: HttpServletResponse
    ): ResponseEntity<Any> {
        val user = this.userService.findByEmail(body.email) ?: return ResponseEntity.badRequest()
            .body(Message("email not found"))

        if (!user.comparePassword(body.password)) {
            return ResponseEntity.badRequest().body(Message("invalid password!"))
        }

        val issuer = user.id.toString()

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 30 * 1000))
            .signWith(SignatureAlgorithm.HS512, "password").compact()

        val cookies = Cookie("jwt", jwt)
        cookies.isHttpOnly = true

        response.addCookie(cookies)

        return ResponseEntity(Message("Login Successfully"), HttpStatus.OK)
    }

    @GetMapping("/user")
    fun user(
        @CookieValue("jwt") jwt: String?
    ): ResponseEntity<Any>{
        try {
            if (jwt == null){
                return ResponseEntity.status(401).body(Message("unauthenticated"))
            }

            val body = Jwts.parser().setSigningKey("password").parseClaimsJws(jwt).body

            return ResponseEntity(this.userService.getById(body.issuer.toInt()), HttpStatus.OK)
        } catch (e: Exception){
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }

        @PostMapping("logout")
        fun logout(
            response: HttpServletResponse
        ): ResponseEntity<Any> {
            val cookie = Cookie("jwt", "")
            cookie.maxAge = 0

            response.addCookie(cookie)

            return ResponseEntity(Message("Logout Successfully"), HttpStatus.OK)
        }

    }

    @GetMapping("/hello")
    fun hello(): String {
        return "hello"
    }
}