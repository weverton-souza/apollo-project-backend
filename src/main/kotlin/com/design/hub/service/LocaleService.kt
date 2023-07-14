package com.design.hub.service

import jakarta.servlet.http.HttpServletRequest

typealias LocaleService = (code: String, request: HttpServletRequest) -> String
