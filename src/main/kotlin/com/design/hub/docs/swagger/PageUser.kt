package com.design.hub.docs.swagger

import com.design.hub.payload.user.response.UserCreateResponse
import org.springframework.data.domain.Page

abstract class PageUser : Page<UserCreateResponse>
