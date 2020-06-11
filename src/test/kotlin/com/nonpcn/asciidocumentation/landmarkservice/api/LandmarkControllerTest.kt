package com.nonpcn.asciidocumentation.landmarkservice.api

import com.nonpcn.asciidocumentation.landmarkservice.exception.ExceptionController
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockKExtension::class, RestDocumentationExtension::class, SpringExtension::class)
internal class LandmarkControllerTest {

    @InjectMockKs
    private lateinit var landmarkController: LandmarkController

    @MockK
    private lateinit var mockMvc: MockMvc

    @MockK
    private lateinit var landmarkService: LandmarkService

    private val mockLandmark1 = LandmarkEntity("1", "landmark 1", "country 1", "province 1", "type 1", 3.4)
    private val mockLandmarkData1 = buildData(mockLandmark1)
    private val expectedResponseGetLandmark = mutableListOf(mockLandmarkData1)

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

    @BeforeEach
    fun setup(restDocumentation: RestDocumentationContextProvider) {
        mockMvc = MockMvcBuilders
            .standaloneSetup(landmarkController)
            .setControllerAdvice(ExceptionController())
            .build()
    }

    @Test
    fun `should response ok and list of landmarks when get landmarks success`() {
        every { landmarkService.getLandmarks() } returns expectedResponseGetLandmark

        mockMvc.perform(
            get("/api/landmarks")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.[0].id").value("1"))
            .andExpect(jsonPath("$.[0].name").value("landmark 1"))
            .andExpect(jsonPath("$.[0].country").value("country 1"))
            .andExpect(jsonPath("$.[0].province").value("province 1"))
            .andExpect(jsonPath("$.[0].type").value("type 1"))
            .andExpect(jsonPath("$.[0].rating").value("3.4"))
    }

    @Test
    fun `should response ok and empty list when get landmarks success but not found landmark`() {
        every { landmarkService.getLandmarks() } returns emptyList()

        mockMvc.perform(
            get("/api/landmarks")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isEmpty)
    }

    @Test
    fun `should response internal server error when get landmarks fail something`() {
        every { landmarkService.getLandmarks() } throws Exception("something wrong")

        mockMvc.perform(
            get("/api/landmarks")
        )
            .andExpect(status().isInternalServerError)
    }

}