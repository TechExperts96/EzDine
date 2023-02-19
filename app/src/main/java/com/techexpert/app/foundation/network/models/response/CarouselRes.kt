package com.techexpert.app.foundation.network.models.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarouselRes(
    val data: List<Data>,
    val meta: Meta
) : Parcelable {
    @Parcelize
    data class Data(
        val attributes: Attributes,
        val id: Int
    ) : Parcelable {
        @Parcelize
        data class Attributes(
            val createdAt: String,
            val description: String,
            val publishedAt: String,
            val title: String,
            val updatedAt: String,
            val visual: Visual
        ) : Parcelable {
            @Parcelize
            data class Visual(
                val data: Data
            ) : Parcelable {
                @Parcelize
                data class Data(
                    val attributes: Attributes,
                    val id: Int
                ) : Parcelable {
                    @Parcelize
                    data class Attributes(
                        val alternativeText: String,
                        val caption: String,
                        val createdAt: String,
                        val ext: String,
                        val formats: Formats,
                        val hash: String,
                        val height: Int,
                        val mime: String,
                        val name: String,
                        val previewUrl: String,
                        val provider: String,
                        val providerMetadata: String,
                        val size: Double,
                        val updatedAt: String,
                        val url: String,
                        val width: Int
                    ) : Parcelable {
                        @Parcelize
                        data class Formats(
                            val large: Large,
                            val medium: Medium,
                            val small: Small,
                            val thumbnail: Thumbnail
                        ) : Parcelable {
                            @Parcelize
                            data class Large(
                                val ext: String,
                                val hash: String,
                                val height: Int,
                                val mime: String,
                                val name: String,
                                val path: String,
                                val size: Double,
                                val url: String,
                                val width: Int
                            ) : Parcelable

                            @Parcelize
                            data class Medium(
                                val ext: String,
                                val hash: String,
                                val height: Int,
                                val mime: String,
                                val name: String,
                                val path: String,
                                val size: Double,
                                val url: String,
                                val width: Int
                            ) : Parcelable

                            @Parcelize
                            data class Small(
                                val ext: String,
                                val hash: String,
                                val height: Int,
                                val mime: String,
                                val name: String,
                                val path: String,
                                val size: Double,
                                val url: String,
                                val width: Int
                            ) : Parcelable

                            @Parcelize
                            data class Thumbnail(
                                val ext: String,
                                val hash: String,
                                val height: Int,
                                val mime: String,
                                val name: String,
                                val path: String,
                                val size: Double,
                                val url: String,
                                val width: Int
                            ) : Parcelable
                        }
                    }
                }
            }
        }
    }

    @Parcelize
    data class Meta(
        @SerializedName("pagination") val pagination: Pagination
    ) : Parcelable {
        @Parcelize
        data class Pagination(
            val page: Int,
            val pageCount: Int,
            val pageSize: Int,
            val total: Int
        ) : Parcelable
    }
}
