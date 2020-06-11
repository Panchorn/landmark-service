package com.nonpcn.asciidocumentation.landmarkservice.api

import com.nonpcn.asciidocumentation.landmarkservice.exception.ExceptionController
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.hibernate.exception.DataException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.sql.SQLException

@ExtendWith(MockKExtension::class, RestDocumentationExtension::class, SpringExtension::class)
internal class LandmarkControllerTest {

    @InjectMockKs
    private lateinit var landmarkController: LandmarkController

    @MockK
    private lateinit var mockMvc: MockMvc

    @MockK
    private lateinit var landmarkService: LandmarkService

    private val mockLandmarkData = LandmarkData("1", "landmark 1", "country 1", "province 1", "type 1", 3.4)
    private val expectedResponseGetLandmark = mutableListOf(mockLandmarkData)

    private val mockAddLandmarkRequest: String =
        """
        {
            "id": "${mockLandmarkData.id}",
            "name": "${mockLandmarkData.name}",
            "country": "${mockLandmarkData.country}",
            "province": "${mockLandmarkData.province}",
            "type": "${mockLandmarkData.type}",
            "rating": ${mockLandmarkData.rating}
        }
        """
    private val mockAddLandmarkRequestNameTooLong: String =
        """
        {
            "id": "${mockLandmarkData.id}",
            "name": "namenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamenamename",
            "country": "${mockLandmarkData.country}",
            "province": "${mockLandmarkData.province}",
            "type": "${mockLandmarkData.type}",
            "rating": ${mockLandmarkData.rating}
        }
        """

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

    @Test
    fun `should response ok and landmark when add landmark success`() {
        every { landmarkService.addLandmark(any()) } returns mockLandmarkData

        mockMvc.perform(
            post("/api/landmarks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockAddLandmarkRequest)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.name").value("landmark 1"))
            .andExpect(jsonPath("$.country").value("country 1"))
            .andExpect(jsonPath("$.province").value("province 1"))
            .andExpect(jsonPath("$.type").value("type 1"))
            .andExpect(jsonPath("$.rating").value("3.4"))
    }

    @Test
    fun `should response internal server error when add landmark fail database error`() {
        every { landmarkService.addLandmark(any()) } throws DataException("could not execute statement", SQLException("Value too long for column \"TYPE VARCHAR(255)\""))

        mockMvc.perform(
            post("/api/landmarks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mockAddLandmarkRequestNameTooLong)
        )
            .andExpect(status().isInternalServerError)
    }

}