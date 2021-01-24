package com.jetherrodrigues.donusbank.domain.auth

import org.hibernate.annotations.BatchSize
import javax.persistence.*

@Entity
@Table(name = "tb_user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(unique = true)
        val username: String,
        val password: String,

        @ManyToMany
        @JoinTable(
                name = "user_authority",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "authority_name", referencedColumnName = "name")]
        )
        @BatchSize(size = 20)
        val authorities: Set<Authority> = hashSetOf()
)
