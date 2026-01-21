package com.asude.animekeep.data.repository

import com.asude.animekeep.data.remote.AnimeDto
import com.asude.animekeep.data.remote.JikanApi
import com.asude.animekeep.model.Anime
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

interface AnimeRepository {
    suspend fun getTopAnime(): List<Anime>
    suspend fun getUpcomingAnime(): List<Anime>
    suspend fun getPopularAnime(): List<Anime>
    suspend fun searchAnime(query: String): List<Anime>
    suspend fun getAnimeDetail(id: Int): Anime?

    fun getAnimeByStatus(status: String): Flow<List<Anime>>
    suspend fun insertAnime(anime: Anime)
    suspend fun deleteAnime(anime: Anime)
}

class NetworkAnimeRepository(
    private val api: JikanApi
) : AnimeRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val collectionName = "user_anime_lists"

    override suspend fun getTopAnime(): List<Anime> {
        return try {
            api.getTopAnime().data.map { it.toDomainModel() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getUpcomingAnime(): List<Anime> {
        return try {
            api.getUpcomingAnime().data.map { it.toDomainModel() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getPopularAnime(): List<Anime> {
        return try {
            api.getPopularAnime().data.map { it.toDomainModel() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun searchAnime(query: String): List<Anime> {
        return try {
            api.searchAnime(query).data.map { it.toDomainModel() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getAnimeDetail(id: Int): Anime? {
        return try {
            api.getAnimeDetails(id).data.toDomainModel()
        } catch (e: Exception) {
            null
        }
    }

    override fun getAnimeByStatus(status: String): Flow<List<Anime>> = callbackFlow {
        val user = auth.currentUser
        if (user == null) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val subscription = firestore.collection(collectionName)
            .whereEqualTo("userId", user.uid)
            .whereEqualTo("userStatus", status)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                snapshot?.let {
                    val animeList = it.toObjects(Anime::class.java)
                    trySend(animeList)
                }
            }

        awaitClose { subscription.remove() }
    }

    override suspend fun insertAnime(anime: Anime) {
        val user = auth.currentUser ?: return
        val animeWithUser = anime.copy(userId = user.uid)

        // Create unique ID composite of AnimeID + UserID
        val documentId = "${anime.id}_${user.uid}"

        firestore.collection(collectionName)
            .document(documentId)
            .set(animeWithUser)
            .await()
    }

    override suspend fun deleteAnime(anime: Anime) {
        val user = auth.currentUser ?: return
        val documentId = "${anime.id}_${user.uid}"

        firestore.collection(collectionName)
            .document(documentId)
            .delete()
            .await()
    }

    private fun AnimeDto.toDomainModel(): Anime {
        return Anime(
            id = this.mal_id,
            title = this.title,
            imageUrl = this.images.jpg.image_url,
            synopsis = this.synopsis ?: "No summary available.",
            score = this.score,
            episodes = this.episodes,
            userStatus = "NONE",
            userId = ""
        )
    }
}