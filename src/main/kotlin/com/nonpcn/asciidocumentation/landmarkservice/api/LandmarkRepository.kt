package com.nonpcn.asciidocumentation.landmarkservice.api

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LandmarkRepository: JpaRepository<LandmarkEntity, String>
