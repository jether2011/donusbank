package com.jetherrodrigues.donusbank.domain.person

import com.jetherrodrigues.donusbank.domain.account.Account
import com.jetherrodrigues.donusbank.domain.auth.User
import com.jetherrodrigues.donusbank.domain.person.types.PersonType
import javax.persistence.*

@Entity
@Table(name = "tb_person")
data class Person(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val name: String,
        @Column(name = "tax_identifier", unique = true)
        val taxIdentifier: String,
        @Enumerated(EnumType.STRING)
        val type: PersonType = PersonType.PF,

        @OneToOne(optional = false, fetch = FetchType.EAGER)
        @JoinColumn(unique = true)
        val account: Account,

        @OneToOne(optional = false, fetch = FetchType.EAGER)
        @JoinColumn(unique = true)
        val user: User
)