package com.design.hub.adapters.persistence.entity

import com.design.hub.adapters.persistence.enumeration.FileType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "\"file\"")
data class FileEntity(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    @Schema(
        description = "Unique ID for the file, generated automatically",
        example = "f2eb72ca-3ba0-474c-8f87-e448a6e4f4bf"
    )
    val id: UUID? = null,

    @Column(name = "file_name", nullable = false, length = 255)
    @Schema(description = "Name of the file", example = "example.jpg")
    val fileName: String,

    @Column(name = "s3_url", nullable = false, length = 255)
    @Schema(description = "URL of the file stored in S3", example = "https://example.com/files/example.jpg")
    val s3Url: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    @Schema(
        description = "Type of the file, PHOTO, ADDRESS_PROOF, THUMB, or RECEIPT",
        example = "PHOTO"
    )
    val fileType: FileType
) : AbstractEntity()
