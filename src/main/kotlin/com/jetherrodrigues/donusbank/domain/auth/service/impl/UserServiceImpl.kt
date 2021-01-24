package com.jetherrodrigues.donusbank.domain.auth.service.impl

import com.jetherrodrigues.donusbank.domain.auth.User
import com.jetherrodrigues.donusbank.domain.auth.service.UserService
import com.jetherrodrigues.donusbank.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    @Transactional(readOnly = true)
    override fun findOneWithAuthoritiesByUsername(username: String): Optional<User> {
        return userRepository.findOneWithAuthoritiesByUsername(username)
    }

}