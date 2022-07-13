package com.example.rico.portalberita.repositories

import com.example.rico.portalberita.models.News
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NewsRepository: JpaRepository<News, Int> {
}