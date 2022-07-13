package com.example.rico.portalberita.services

import com.example.rico.portalberita.models.News
import com.example.rico.portalberita.repositories.NewsRepository
import com.example.rico.portalberita.repositories.UserRepository
import com.example.rico.portalberita.models.User
import org.aspectj.bridge.Message
import org.springframework.stereotype.Service

@Service
class NewsService(
    private val newsRepository: NewsRepository,
    private val userRepository: UserRepository
) {

    fun addNews(news: News): News {
        return this.newsRepository.save(news)
    }

    fun getNews(): List<News> {
        return this.newsRepository.findAll()
    }

    fun updateNews(id: Int, news: News): News {
        val updatedNews = newsRepository.findById(id).get()
        updatedNews.title = news.title
        updatedNews.description = news.description

        return this.newsRepository.save(updatedNews)
    }

    fun findNewsById(id: Int): News {
        return this.newsRepository.findById(id).get()
    }

    fun deleteNews(id: Int): String {
        val news = this.newsRepository.findById(id).orElse(null) ?: return "News Not Found"

        newsRepository.deleteById(id)
        return "News delete success"
    }
}