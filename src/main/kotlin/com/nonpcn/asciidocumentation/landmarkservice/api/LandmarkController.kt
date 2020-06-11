package com.nonpcn.asciidocumentation.landmarkservice.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/landmarks")
class LandmarkController(
    private val landmarkService: LandmarkService
) {

    @PostMapping
    fun addLandmark(@RequestBody landmarkData: LandmarkData): ResponseEntity<LandmarkData> {
        return ResponseEntity.ok().body(landmarkService.addLandmark(landmarkData))
    }

    @GetMapping
    fun getLandmarks(): ResponseEntity<List<LandmarkData>> {
        return ResponseEntity.ok().body(landmarkService.getLandmarks())
    }

}