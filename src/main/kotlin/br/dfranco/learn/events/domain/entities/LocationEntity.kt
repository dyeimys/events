package br.dfranco.learn.events.domain.entities

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import java.util.*

@Entity
class LocationEntity(
        @Id
        @GeneratedValue
        var id: UUID? = null,

        @Column
        @NotNull
        var name: String,

        @Column
        @NotNull
        var address: String,

        @Column
        var creationDate: LocalDateTime? = null,

        @Column
        var updateDate: LocalDateTime? = null
) {
    @PrePersist
    fun prePersist() {
        creationDate = LocalDateTime.now()
    }

    @PreUpdate
    fun preUpdate() {
        updateDate = LocalDateTime.now()
    }
}