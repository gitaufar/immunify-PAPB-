package com.example.immunify.data.firebase.firestore

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseFirestoreDatasource(
    val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    /**
     * Add new document with Auto ID
     */
    suspend inline fun <reified T> addDocument(
        collection: String,
        data: Any
    ): String {
        val docRef = firestore.collection(collection)
            .add(data)
            .await()

        return docRef.id
    }

    /**
     * Set document with provided ID (replace)
     */
    suspend inline fun <reified T> setDocument(
        collection: String,
        documentId: String,
        data: Any
    ) {
        firestore.collection(collection)
            .document(documentId)
            .set(data)
            .await()
    }

    /**
     * Update specific fields
     */
    suspend fun updateDocument(
        collection: String,
        documentId: String,
        fields: Map<String, Any>
    ) {
        firestore.collection(collection)
            .document(documentId)
            .update(fields)
            .await()
    }

    /**
     * Get document by ID
     */
    suspend inline fun <reified T> getDocument(
        collection: String,
        documentId: String
    ): T? {
        val snapshot = firestore.collection(collection)
            .document(documentId)
            .get()
            .await()

        return snapshot.toObject(T::class.java)
    }

    /**
     * Get all documents from a collection
     */
    suspend inline fun <reified T> getCollection(
        collection: String
    ): List<T> {
        val snapshot = firestore.collection(collection)
            .get()
            .await()

        return snapshot.toObjects(T::class.java)
    }

    /**
     * Delete document
     */
    suspend fun deleteDocument(
        collection: String,
        documentId: String
    ) {
        firestore.collection(collection)
            .document(documentId)
            .delete()
            .await()
    }
}
