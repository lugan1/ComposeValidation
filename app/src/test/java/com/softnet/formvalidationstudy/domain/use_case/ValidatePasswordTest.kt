package com.softnet.formvalidationstudy.domain.use_case

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ValidatePasswordTest {

    private lateinit var validatePassword: ValidatePassword

    @Before
    fun setUp() {
        validatePassword = ValidatePassword()
    }

    @Test
    fun `비밀번호가 비어있으면 에러를 반환한다`() {
        val result = validatePassword.execute("")
        assertFalse(result.isValid)
        assertEquals("비밀번호는 비어있으면 안됩니다.", result.error)
    }

    @Test
    fun `비밀번호가 8자 미만이면 에러를 반환한다`() {
        val result = validatePassword.execute("a1!Aa")
        assertFalse(result.isValid)
        assertEquals("길이 8~20, 영문, 숫자, 특수문자를 포함해야 합니다.", result.error)
    }

    @Test
    fun `비밀번호가 20자 초과이면 에러를 반환한다`() {
        val result = validatePassword.execute("a1!Aa12345678901234567890")
        assertFalse(result.isValid)
        assertEquals("길이 8~20, 영문, 숫자, 특수문자를 포함해야 합니다.", result.error)
    }

    @Test
    fun `비밀번호가 영문 대문자를 포함하지 않으면 에러를 반환한다`() {
        val result = validatePassword.execute("a1!a123")
        assertFalse(result.isValid)
        assertEquals("길이 8~20, 영문, 숫자, 특수문자를 포함해야 합니다.", result.error)
    }
}