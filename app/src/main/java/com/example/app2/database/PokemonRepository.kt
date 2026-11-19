
package com.example.app2.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveDataScope
import com.example.app2.models.PokemonEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class PokemonRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun favoritosCollection() =
        auth.currentUser?.uid?.let { uid ->
            firestore.collection("users")
                .document(uid)
                .collection("favoritos")
        }

    fun inserir(pokemon: PokemonEntity) {
        val collection = favoritosCollection() ?: return
        val dados = hashMapOf(
            "id" to pokemon.id,
            "name" to pokemon.name,
            "height" to pokemon.height,
            "weight" to pokemon.weight,
            "image" to pokemon.image,
            "hp" to pokemon.hp,
            "attack" to pokemon.attack,
            "defense" to pokemon.defense,
            "speed" to pokemon.speed
        )

        collection.document(pokemon.id.toString()).set(dados)
    }

    fun remover(pokemonId: Int) {
        val collection = favoritosCollection() ?: return
        collection.document(pokemonId.toString()).delete()
    }

    fun estaFavoritado(pokemonId: Int): LiveData<Boolean> {
        val collection = favoritosCollection()
        if (collection == null) {
            return MutableLiveData(false)
        }

        val documento = collection.document(pokemonId.toString())

        return object : LiveData<Boolean>() {
            private var listener: ListenerRegistration? = null
            override fun onActive() {
                listener = documento.addSnapshotListener { snapshot, erro ->
                    if (erro == null && snapshot != null) {
                        postValue(snapshot.exists())
                    }
                }
            }
            override fun onInactive() {
                listener?.remove()
                listener = null
            }
        }
    }

    fun listarFavoritos(): LiveData<List<PokemonEntity>> {
        val collection = favoritosCollection()
        if (collection == null) {
            return MutableLiveData(emptyList())
        }

        return object : LiveData<List<PokemonEntity>>() {
            private var listener: ListenerRegistration? = null
            override fun onActive() {
                listener = collection.addSnapshotListener { snapshot, erro ->
                    if (erro == null && snapshot != null) {

                        val favoritos = snapshot.documents.mapNotNull { documento ->
                            documento.toPokemonEntity()
                        }.sortedBy { it.name.lowercase() }

                        postValue(favoritos)
                    }
                }
            }
            override fun onInactive() {
                listener?.remove()
                listener = null
            }
        }
    }

    private fun DocumentSnapshot.toPokemonEntity(): PokemonEntity? {
        val pokemonId = getLong("id")?.toInt()
            ?: id.toIntOrNull()
            ?: return null
        return PokemonEntity(
            id = pokemonId,
            name = getString("name") ?: "",
            height = getLong("height")?.toInt() ?: 0,
            weight = getLong("weight")?.toInt() ?: 0,
            image = getString("image"),
            hp = getLong("hp")?.toInt() ?: 0,
            attack = getLong("attack")?.toInt() ?: 0,
            defense = getLong("defense")?.toInt() ?: 0,
            speed = getLong("speed")?.toInt() ?: 0
        )
    }
}
