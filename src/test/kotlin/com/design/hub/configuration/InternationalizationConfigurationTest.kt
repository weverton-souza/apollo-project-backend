package com.design.hub.configuration

import com.design.hub.service.impl.LocaleServiceImpl
import com.design.hub.utils.I18n
import jakarta.servlet.http.HttpServletRequest
import java.util.Locale
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class InternationalizationConfigurationTest {

    @Autowired
    private lateinit var resolver: InternationalizationConfiguration

    @Autowired
    private lateinit var localeServiceImpl: LocaleServiceImpl

    @Mock
    private val request: HttpServletRequest? = null

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get locale default`() {
        `when`(request!!.getHeader("Accept-Language")).thenReturn("pt-BR")
        val result: Locale = resolver.resolveLocale(request)

        val message = this.localeServiceImpl.invoke(I18n.HTTP_4XX_404_NOT_FOUND, request)

        assertEquals(Locale.getDefault(), result)
        assertEquals("Recurso não encontrado", message)
    }

    @Test
    fun `should get en-US as locale`() {
        `when`(request!!.getHeader("Accept-Language")).thenReturn("en-US")
        val result: Locale = resolver.resolveLocale(request)

        val message = this.localeServiceImpl.invoke(I18n.HTTP_4XX_404_NOT_FOUND, request)

        assertEquals(Locale("en", "US"), result)
        assertEquals("Resource not found", message)
    }

    @Test
    fun `should get es-MX as locale`() {
        `when`(request!!.getHeader("Accept-Language")).thenReturn("es-MX")
        val result: Locale = resolver.resolveLocale(request)

        val message = this.localeServiceImpl.invoke(I18n.HTTP_4XX_404_NOT_FOUND, request)

        assertEquals(Locale("es", "MX"), result)
        assertEquals("Recurso no encontrado", message)
    }
}
