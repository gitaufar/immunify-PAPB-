package com.example.immunify.data.repo

import com.example.immunify.data.firebase.dto.ChildDto
import com.example.immunify.data.firebase.firestore.FirebaseFirestoreDatasource
import com.example.immunify.data.mapper.ChildMapper.toDomain
import com.example.immunify.data.mapper.ChildMapper.toDto
import com.example.immunify.domain.model.Child
import com.example.immunify.domain.repo.ChildRepository
import com.example.immunify.util.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementasi ChildRepository
 * 
 * Struktur Firestore:
 * users/
 *   {userId}/
 *     children/ (subcollection)
 *       {childId}/
 */
class ChildRepositoryImpl @Inject constructor(
    private val firestoreDatasource: FirebaseFirestoreDatasource
) : ChildRepository {

    companion object {
        private const val COLLECTION_USERS = "users"
        private const val SUBCOLLECTION_CHILDREN = "children"
    }

    override suspend fun createChild(child: Child): Result<String> {
        return try {
            val childDto = child.toDto()
            
            // Generate ID jika belum ada
            val docId = if (childDto.id.isEmpty()) {
                firestoreDatasource.firestore
                    .collection(COLLECTION_USERS)
                    .document(child.userId)
                    .collection(SUBCOLLECTION_CHILDREN)
                    .document().id
            } else {
                childDto.id
            }

            val finalDto = childDto.copy(id = docId)
            
            // Save ke: users/{userId}/children/{childId}
            firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(child.userId)
                .collection(SUBCOLLECTION_CHILDREN)
                .document(docId)
                .set(finalDto)
                .await()
            
            Result.Success(docId)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getChildById(userId: String, childId: String): Result<Child> {
        return try {
            val snapshot = firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(userId)
                .collection(SUBCOLLECTION_CHILDREN)
                .document(childId)
                .get()
                .await()
            
            val dto = snapshot.toObject(ChildDto::class.java)
            if (dto != null) {
                Result.Success(dto.toDomain())
            } else {
                Result.Error(Exception("Child not found"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getChildrenByUserId(userId: String): Result<List<Child>> {
        return try {
            val snapshot = firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(userId)
                .collection(SUBCOLLECTION_CHILDREN)
                .orderBy("createdAt")
                .get()
                .await()

            val children = snapshot.toObjects(ChildDto::class.java)
                .map { it.toDomain() }
            
            Result.Success(children)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun updateChild(child: Child): Result<Unit> {
        return try {
            val childDto = child.toDto().copy(updatedAt = System.currentTimeMillis())
            
            firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(child.userId)
                .collection(SUBCOLLECTION_CHILDREN)
                .document(child.id)
                .set(childDto)
                .await()
            
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteChild(userId: String, childId: String): Result<Unit> {
        return try {
            firestoreDatasource.firestore
                .collection(COLLECTION_USERS)
                .document(userId)
                .collection(SUBCOLLECTION_CHILDREN)
                .document(childId)
                .delete()
                .await()
            
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
