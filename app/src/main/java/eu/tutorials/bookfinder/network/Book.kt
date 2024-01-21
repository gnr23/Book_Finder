package eu.tutorials.bookfinder.network

//a data class to hold the information about the book
data class BookResponse(
    val totalItems: Int,
    val items: List<Book>,
)

data class Book(
    val volumeInfo: VolumeInfo,
)

data class VolumeInfo(
    val title: String = "Unknown",
    val authors: List<String> = listOf("Unknown"),
    val publisher: String = "Unknown",
    val description: String = "No description available",
    val infoLink: String = "",
)