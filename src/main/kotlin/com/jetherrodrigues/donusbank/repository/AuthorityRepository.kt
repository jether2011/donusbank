package com.jetherrodrigues.donusbank.repository

import com.jetherrodrigues.donusbank.domain.auth.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepository : JpaRepository<Authority, String> {
}