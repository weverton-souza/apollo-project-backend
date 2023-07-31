package com.design.hub.configuration

import com.design.hub.repository.UserRepository
import com.design.hub.utils.I18n
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Configuration
class GlobalConfiguration(private val userRepository: UserRepository) {

    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username: String ->
            userRepository.findByEmail(username)
                .orElseThrow { UsernameNotFoundException(I18n.HTTP_4XX_404_NOT_FOUND) }
        }
    }
}
