package br.dfranco.learn.events.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import java.util.*

@Entity
class LocationEntity (
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
        @CreationTimestamp
        var creationDate: LocalDateTime? = null,

        @Column
        @UpdateTimestamp
        var updateDate: LocalDateTime? = null
        ){
}