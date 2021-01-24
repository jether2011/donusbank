package com.jetherrodrigues.donusbank.repository

import com.jetherrodrigues.donusbank.domain.auth.User
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(username: String): Optional<User>

    @EntityGraph(attributePaths = ["authorities"])
    @Cacheable(cacheNames = ["findByLoginWithAuthorities"])
    fun findOneWithAuthoritiesByUsername(username: String): Optional<User>

}