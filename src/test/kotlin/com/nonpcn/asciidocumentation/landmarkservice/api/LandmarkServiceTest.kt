package com.nonpcn.asciidocumentation.landmarkservice.api

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class LandmarkServiceTest {

    @InjectMockKs
    private lateinit var landmarkService: LandmarkService

    @MockK
    private lateinit var landmarkRepository: LandmarkRepository

    private val mockLandmark1 = LandmarkEntity("1", "landmark 1", "country 1", "provice 1", "type 1", 3.4)
    private val mockLandmark2 = LandmarkEntity("2", "landmark 2", "country 2", "provice 2", "type 2", 4.5)
    private val expectedEntityGetLandmark = mutableListOf(mockLandmark1, mockLandmark2)
    private val expectedResponseGetLandmark = mutableListOf(buildData(mockLandmark1), buildData(mockLandmark2))

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

    @Test
    fun `should return list of landmarks when get landmarks success`() {
        // given
        every { landmarkRepository.findAll() } returns expectedEntityGetLandmark

        // when
        val actual = landmarkService.getLandmarks()

        // then
        assertEquals(expectedResponseGetLandmark, actual)
    }

    @Test
    fun `should return empty list when get landmarks success but not found landmark`() {
        // given
        every { landmarkRepository.findAll() } returns emptyList()

        // when
        val actual = landmarkService.getLandmarks()

        // then
        assertTrue(actual.isEmpty())
    }

}