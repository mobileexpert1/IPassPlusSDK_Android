package com.sdk.ipassplussdk.model.response.aml_manual

data class Doc(
    val aka: List<Aka>,
    val created_utc: String,
    val entity_type: String,
    val fields: List<Field>,
    val id: String,
    val keywords: List<Any>,
    val last_updated_utc: String,
    val media: List<Media>,
    val name: String,
    val source_notes: SourceNotes,
    val sources: List<String>,
    val types: List<String>
)