import kotlinx.serialization.Serializable

@Serializable
data class Homework(
    val id: Int,
    val studentFullName: String,
    val title: String,
    val dueDate: String,
    val maxScore: Int,
    val receivedScore: Int?,
    val isDone: Boolean = false
)
