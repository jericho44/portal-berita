package com.example.rico.portalberita.models

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "news")
class News {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    @Column(name = "user_id")
    var user_id: Int? = null

    @Column(name = "title")
    var title: String? = null

    @Column(name = "description")
    var description: String? = null

    @Column(name = "file_name")
    var fileName: String? = null

    @Column(name = "url_path")
    var urlPath: String? = null

    @field:CreationTimestamp
    lateinit var createdDate: Date

    @field:UpdateTimestamp
    lateinit var updatedDate: Date
}