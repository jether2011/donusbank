package com.jetherrodrigues.donusbank.domain.auth.service.impl

import com.jetherrodrigues.donusbank.domain.auth.User
import com.jetherrodrigues.donusbank.domain.auth.exception.UserAlreadyExistsException
import com.jetherrodrigues.donusbank.domain.auth.service.UserService
import com.jetherrodrigues.donusbank.domain.config.ExceptionMessage.USER_ALREADY_EXISTS
import com.jetherrodrigues.donusbank.domain.exception.NotFoundException
import com.jetherrodrigues.donusbank.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun create(user: User): User {
        userRepository.findByUsername(user.username).ifPresent {
            throw UserAlreadyExistsException(USER_ALREADY_EXISTS.format(user.username))
        }

        return userRepository.save(user)
    }

    @Transactional(readOnly = true)
    override fun findOneWithAuthoritiesByUsername(username: String): User {
        return userRepository.findOneWithAuthoritiesByUsername(username).orElseThrow {
            throw NotFoundException("User [%s] not found")
        }
    }

}