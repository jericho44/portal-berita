package com.example.rico.portalberita.controllers

import com.example.rico.portalberita.Message
import com.example.rico.portalberita.models.News
import com.example.rico.portalberita.services.NewsService
import com.example.rico.portalberita.services.UserService
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/news"], produces = [org.springframework.http.MediaType.APPLICATION_JSON_VALUE])
class NewsController(
    private val userService: UserService,
    private val newsService: NewsService
) {

    @PostMapping("/add")
    fun addNews(
        @RequestBody body: News,
        @CookieValue("jwt") jwt: String?
    ): ResponseEntity<Any> {
        try {
            if (jwt == null){
                return ResponseEntity.status(401).body(Message("unauthenticated"))
            }
            val jwtId = Jwts.parser().setSigningKey("password").parseClaimsJws(jwt).body

            val user = this.userService.getById(jwtId.issuer.toInt())

            val news = News()
            news.user_id = user.id
            news.title = body.title
            news.description = body.description

            return  ResponseEntity(this.newsService.addNews(news), HttpStatus.OK)
        } catch(e: Exception) {
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }

    }

    @GetMapping
    fun allNews(
        @CookieValue("jwt") jwt: String
    ): ResponseEntity<Any>{
        try {
            if (jwt == null){
                return ResponseEntity.status(401).body(Message("unauthenticated"))
            }

            return ResponseEntity(newsService.getNews(), HttpStatus.OK)
        }catch (e: Exception){
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }
    }

    @PutMapping("/edit/{id}")
    fun updateNews(
        @PathVariable("id") id: Int,
        @RequestBody body: News,
        @CookieValue("jwt") jwt: String
    ): ResponseEntity<Any> {
        try {
            if (jwt == null){
                return ResponseEntity.status(401).body(Message("unauthenticated"))
            }
            val jwtId = Jwts.parser().setSigningKey("password").parseClaimsJws(jwt).body

            val user = this.userService.getById(jwtId.issuer.toInt())

            val newsSameId = this.newsService.findNewsById(id)
            if (newsSameId.user_id == user.id){
                return ResponseEntity(newsService.updateNews(id, body), HttpStatus.OK)
            }

            return ResponseEntity(Message("Not Your News"), HttpStatus.BAD_REQUEST)
        } catch (e: Exception){
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }
    }

    @DeleteMapping("{id}")
    fun deleteNews(
        @PathVariable("id") id: Int,
        @CookieValue("jwt") jwt: String
    ): ResponseEntity<Any> {
        try {
            if (jwt == null){
                return ResponseEntity.status(401).body(Message("unauthenticated"))
            }
            val jwtId = Jwts.parser().setSigningKey("password").parseClaimsJws(jwt).body

            val user = this.userService.getById(jwtId.issuer.toInt())
            val newsSameId = this.newsService.findNewsById(id)

            if (newsSameId.user_id == user.id){
                return ResponseEntity(newsService.deleteNews(id), HttpStatus.OK)
            }

            return ResponseEntity(Message("Not Your News"), HttpStatus.BAD_REQUEST)

        }catch (e: Exception){
            return ResponseEntity.status(401).body(Message("unauthenticated"))
        }
    }
}