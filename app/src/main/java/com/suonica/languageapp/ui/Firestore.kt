package com.suonica.languageapp.ui

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.tasks.await

@SuppressLint("StaticFieldLeak")
val db = FirebaseFirestore.getInstance()

suspend fun getWordsForUser(userId: String): List<DBWord> {
    val snapshot =
        try {
            db.collection("users").document(userId).collection("words")
                .get()
                .await()
        } catch (e: FirebaseFirestoreException) {
            // Handle exception
            throw RuntimeException("Ошибка при получении данных из Firebase", e)
        }
    return snapshot.map { it.toObject(DBWord::class.java).also { word -> word.id = it.id } }
}

fun updateWord(
    userId: String,
    word: DBWord,
) {
    db.collection("users").document(userId).collection("words")
        .document(word.id)
        .update(hashMapOf("learnt" to word.learnt) as Map<String, Any>)
        .addOnSuccessListener { documentReference ->
            Log.d("firestore", "слово изменено")
        }
        .addOnFailureListener { exception ->
            Log.d("firestore", "Ошибка при изменении слова: $exception")
        }
}

fun addUser(username: String) {
    val user =
        hashMapOf(
            "username" to username,
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

fun addWord(
    userId: String,
    original: String,
    translated: String,
    callback: (String) -> Unit,
) {
    val word =
        hashMapOf(
            "original" to original,
            "translated" to translated,
            "learnt" to false,
        )

    db.collection("users").document(userId).collection("words")
        .add(word)
        .addOnSuccessListener { documentReference ->
            Log.d("firestore", "Новое слово успешно добавлен с ID: ${documentReference.id}")
            callback("Новое слово успешно добавлен с ID: ${documentReference.id}")
        }
        .addOnFailureListener { exception ->
            Log.d("firestore", "Ошибка при добавлении нового слова: $exception")
            callback("Ошибка при добавлении нового слова: $exception")
        }
}

fun getUser(
    username: String,
    onUserReceived: (DBUser) -> Unit,
) {
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

suspend fun getArticles(): List<DBArticle> {
    val snapshot =
        try {
            db.collection("articles")
                .get()
                .await()
        } catch (e: FirebaseFirestoreException) {
            throw RuntimeException("Ошибка при получении данных из Firebase", e)
        }
    return snapshot.map { it.toObject(DBArticle::class.java) }
}

data class DBUser(val username: String)

data class DBWord(var id: String, val learnt: Boolean, val original: String, val translated: String) {
    constructor() : this("", false, "", "")
}

data class DBArticle(val title: String, val text: String) {
    constructor() : this("", "")
}
