package com.nonpcn.asciidocumentation.landmarkservice.api

import org.springframework.stereotype.Service

@Service
class LandmarkService(
    private val landmarkRepository: LandmarkRepository
) {

    fun addLandmark(landmarkData: LandmarkData): LandmarkData {
        val entity = buildEntity(landmarkData)
        return buildData(landmarkRepository.save(entity))
    }

    fun getLandmarks(): List<LandmarkData> {
        return landmarkRepository.findAll()
            .map { buildData(it) }
            .toList()
    }

    private fun buildData(landmarkEntity: LandmarkEntity): LandmarkData {
        return LandmarkData(
            id = landmarkEntity.id,
            name = landmarkEntity.name,
            country = landmarkEntity.country,
            province = landmarkEntity.province,
            type = landmarkEntity.type,
            rating = landmarkEntity.rating
        )
    }

    private fun buildEntity(landmarkData: LandmarkData): LandmarkEntity {
        return LandmarkEntity(
            id = landmarkData.id,
            name = landmarkData.name,
            country = landmarkData.country,
            province = landmarkData.province,
            type = landmarkData.type,
            rating = landmarkData.rating
        )
    }

}
