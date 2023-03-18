package br.dfranco.learn.events.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.jetbrains.annotations.NotNull
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.*

@Entity
class EventEntity(
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

        @Column
        @CreationTimestamp
        var creationDate: LocalDateTime? = null,

        @Column
        @UpdateTimestamp
        var updateDate: LocalDateTime? = null
)
