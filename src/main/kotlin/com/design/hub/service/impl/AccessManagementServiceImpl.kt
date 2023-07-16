package com.design.hub.service.impl

import com.design.hub.repository.UserRepository
import com.design.hub.utils.I18n
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AccessManagementServiceImpl(private val userRepository: UserRepository) {

    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            userRepository.findByEmail(username)
                .orElseThrow {
                    UsernameNotFoundException(I18n.HTTP_4XX_404_NOT_FOUND)
                }
        }
    }
}
