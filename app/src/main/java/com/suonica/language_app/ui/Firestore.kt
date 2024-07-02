package com.suonica.language_app.ui

import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore


@SuppressLint("StaticFieldLeak")
val db = FirebaseFirestore.getInstance()


fun getWordsForUser(userId: String): mutableMap<String, String> {  // TODO а что тут
    val words = mutableMapOf<String, String>()
    db.collection("users").document(userId).collection("words")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                val word = document.toObject(DBWord::class.java)
                words[word.original] = word.translated
            }
        }
        .addOnFailureListener { exception ->
            println("Error getting documents: $exception")
        }
    return words
}

fun addUser(username: String) {
    val user = hashMapOf(
        "username" to username
    )

    db.collection("users")
        .add(user)
        .addOnSuccessListener { documentReference ->
            println("Новый пользователь успешно добавлен с ID: ${documentReference.id}")
        }
        .addOnFailureListener { exception ->
            println("Ошибка при добавлении нового пользователя: $exception")
        }
}


fun getUser(username: String, onUserReceived: (DBUser) -> Unit) {
    db.collection("users")
        .whereEqualTo("username", username)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                val user = document.toObject(DBUser::class.java)
                onUserReceived(user) // Вызываем колбэк с полученным пользователем
            }
        }
        .addOnFailureListener { exception ->
            println("Ошибка при получении пользователя: $exception")
        }
}
// использование:
//getUser("example_username") { user ->
//    println("Получен пользователь: ${user.username}")
//    // Здесь можно делать что-то с полученным пользователем
//}


fun getArticles(onArticleReceived: (DBArticle) -> Unit) {
    db.collection("users")
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                val article = document.toObject(DBArticle::class.java)
                onArticleReceived(article) // Вызываем колбэк с полученной статьей
            }
        }
        .addOnFailureListener { exception ->
            println("Ошибка при получении пользователя: $exception")
        }
}


data class DBUser(val username: String)
data class DBWord(val learnt: Boolean, val original: String, val translated: String)

data class DBArticle(val title: String, val text: String)
