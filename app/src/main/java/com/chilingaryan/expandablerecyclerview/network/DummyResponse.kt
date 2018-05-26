package com.chilingaryan.expandablerecyclerview.network

import com.google.gson.annotations.SerializedName

data class DummyResponse(
    @SerializedName("\$schema") val schema: String = "", //http://json-schema.org/draft-06/schema#
    @SerializedName("description") val description: String = "", //A representation of an event
    @SerializedName("type") val type: String = "", //object
    @SerializedName("required") val required: List<String> = listOf(),
    @SerializedName("properties") val properties: Properties = Properties()
) {
    data class Properties(
        @SerializedName("dtstart") val dtstart: Dtstart = Dtstart(),
        @SerializedName("dtend") val dtend: Dtend = Dtend(),
        @SerializedName("summary") val summary: Summary = Summary(),
        @SerializedName("location") val location: Location = Location(),
        @SerializedName("url") val url: Url = Url(),
        @SerializedName("duration") val duration: Duration = Duration(),
        @SerializedName("rdate") val rdate: Rdate = Rdate(),
        @SerializedName("rrule") val rrule: Rrule = Rrule(),
        @SerializedName("category") val category: Category = Category(),
        @SerializedName("description") val description: Description = Description(),
        @SerializedName("geo") val geo: Geo = Geo()
    ) {
        data class Category(
            @SerializedName("type") val type: String = "" //string
        )
        data class Description(
            @SerializedName("type") val type: String = "" //string
        )
        data class Duration(
            @SerializedName("format") val format: String = "", //time
            @SerializedName("type") val type: String = "", //string
            @SerializedName("description") val description: String = "" //Event duration
        )
        data class Dtstart(
            @SerializedName("format") val format: String = "", //date-time
            @SerializedName("type") val type: String = "", //string
            @SerializedName("description") val description: String = "" //Event starting time
        )
        data class Summary(
            @SerializedName("type") val type: String = "" //string
        )
        data class Rdate(
            @SerializedName("format") val format: String = "", //date-time
            @SerializedName("type") val type: String = "", //string
            @SerializedName("description") val description: String = "" //Recurrence date
        )
        data class Location(
            @SerializedName("type") val type: String = "" //string
        )
        data class Dtend(
            @SerializedName("format") val format: String = "", //date-time
            @SerializedName("type") val type: String = "", //string
            @SerializedName("description") val description: String = "" //Event ending time
        )
        data class Rrule(
            @SerializedName("type") val type: String = "", //string
            @SerializedName("description") val description: String = "" //Recurrence rule
        )
        data class Url(
            @SerializedName("type") val type: String = "", //string
            @SerializedName("format") val format: String = "" //uri
        )
        data class Geo(
            @SerializedName("\$ref") val ref: String = "" //http://json-schema.org/geo
        )
    }
}