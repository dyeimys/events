package br.dfranco.learn.events.entities

import br.dfranco.learn.events.enuns.EventStatusEnum
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import java.util.*

@Entity
data class EventEntity(
        @Id
        @GeneratedValue
        var id: UUID? = null,

        @Column
        @NotNull
        var name: String,

        @Column
        @NotNull
        var date: LocalDateTime,

        @NotNull
        @ManyToOne
        var location: LocationEntity,

        @Column
        @NotNull
        var owner: String,


        @NotNull
        @Enumerated
        var status: EventStatusEnum = EventStatusEnum.UNPUBLISHED,

        @Column
        var creationDate: LocalDateTime? = null,

        @Column
        var updateDate: LocalDateTime? = null,
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
