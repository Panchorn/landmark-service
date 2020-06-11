package com.nonpcn.asciidocumentation.landmarkservice.api

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "landmark")
data class LandmarkEntity(
    @Id @Column(name = "id") val id: String,
    @Column(name = "name") val name: String,
    @Column(name = "country") val country: String,
    @Column(name = "province") val province: String,
    @Column(name = "type") val type: String,
    @Column(name = "rating") val rating: Double
): Serializable
