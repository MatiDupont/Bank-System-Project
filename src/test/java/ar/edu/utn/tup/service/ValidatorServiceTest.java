package ar.edu.utn.tup.service;

import ar.edu.utn.tup.service.ValidatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.jpa.hibernate.ddl-auto=none"})
public class ValidatorServiceTest {
    @Test
    @DisplayName("Test for validating valid NID´s.")
    public void testValidNID() {
        assertTrue(ValidatorService.isValidNID("44242242"));
        assertTrue(ValidatorService.isValidNID("24436491"));
    }

    @Test
    @DisplayName("Test for validating invalid NID´s.")
    public void testInvalidNID() {
        assertFalse(ValidatorService.isValidNID("testFailNID"));
        assertFalse(ValidatorService.isValidNID("1234"));
    }

    @Test
    @DisplayName("Test for validating valid emails.")
    public void testValidEmail() {
        assertTrue(ValidatorService.isValidEmail("matiasn.dupont@gmail.com"));
        assertTrue(ValidatorService.isValidEmail("lsalotto@gmail.com"));
    }

    @Test
    @DisplayName("Test for validating invalid emails.")
    public void testInvalidEmail() {
        assertFalse(ValidatorService.isValidEmail("matiasn.dupont@gmail"));
        assertFalse(ValidatorService.isValidEmail("lsalotto.comar"));
    }

    @Test
    @DisplayName("Test for validating strong password.")
    public void testStrongPassword() {
        String expectedResult = "Strong";

        assertEquals(expectedResult, ValidatorService.getPasswordStrength("test123#!%2024lab"));
        assertEquals(expectedResult, ValidatorService.getPasswordStrength("#test_123"));
    }

    @Test
    @DisplayName("Test for validating medium password.")
    public void testMediumPassword() {
        String expectedResult = "Medium";

        assertEquals(expectedResult, ValidatorService.getPasswordStrength("test123"));
        assertEquals(expectedResult, ValidatorService.getPasswordStrength("4567890test2024"));
    }

    @Test
    @DisplayName("Test for validating weak password.")
    public void testWeakPassword() {
        String expectedResult = "Weak";

        assertEquals(expectedResult, ValidatorService.getPasswordStrength("test__"));
        assertEquals(expectedResult, ValidatorService.getPasswordStrength("pw_allowed"));
    }

    @Test
    @DisplayName("Test for validating invalid password.")
    public void testInvalidPassword() {
        String expectedResult = "Invalid";

        assertEquals(expectedResult, ValidatorService.getPasswordStrength("fail"));
        assertEquals(expectedResult, ValidatorService.getPasswordStrength("123_456"));
    }

    @Test
    @DisplayName("Test for validating valid date.")
    public void testValidDate() {
        assertTrue(ValidatorService.isValidDate("2002/05/02"));
        assertTrue(ValidatorService.isValidDate("1975/04/03"));
    }

    @Test
    @DisplayName("Test for validating invalid date.")
    public void testInvalidDate() {
        assertFalse(ValidatorService.isValidDate("02/05/2002"));
        assertFalse(ValidatorService.isValidDate("04/1975/03"));
    }

    @Test
    @DisplayName("Test for validating valid name.")
    public void testValidName() {
        assertTrue(ValidatorService.isValidName("Matias Nicolas"));
        assertTrue(ValidatorService.isValidName("Matias"));
    }

    @Test
    @DisplayName("Test for validating invalid name.")
    public void testInvalidName() {
        assertFalse(ValidatorService.isValidName("matias nicolas"));
        assertFalse(ValidatorService.isValidName("Matias "));
    }

    @Test
    @DisplayName("Test for validating valid address.")
    public void testValidAddress() {
        assertTrue(ValidatorService.isValidAddress("Secret Location 111"));
        assertTrue(ValidatorService.isValidAddress("Location 123"));
    }

    @Test
    @DisplayName("Test for validating invalid address.")
    public void testInvalidAddress() {
        assertFalse(ValidatorService.isValidAddress("123 secret Location"));
        assertFalse(ValidatorService.isValidAddress("location"));
    }

    @Test
    @DisplayName("Test for validating valid telephone.")
    public void testValidTelephone() {
        assertTrue(ValidatorService.isValidTelephone("+12 7 3349018673"));
        assertTrue(ValidatorService.isValidTelephone("+172 3 2758455001"));
    }

    @Test
    @DisplayName("Test for validating invalid telephone.")
    public void testInvalidTelephone() {
        assertFalse(ValidatorService.isValidTelephone("+4444 9 4324237656"));
        assertFalse(ValidatorService.isValidTelephone("7 99 9362473284"));
    }

    @Test
    @DisplayName("Test for validating valid security code.")
    public void testValidSecurityCode() {
        assertTrue(ValidatorService.isValidSecurityCode("1234"));
        assertTrue(ValidatorService.isValidSecurityCode("123456"));
    }

    @Test
    @DisplayName("Test for validating invalid security code.")
    public void testInvalidSecurityCode() {
        assertFalse(ValidatorService.isValidSecurityCode("789"));
        assertFalse(ValidatorService.isValidSecurityCode("5678901"));
    }

    @Test
    @DisplayName("Test for validating valid alias.")
    public void testValidAlias() {
        assertTrue(ValidatorService.isValidAlias("test.alias7.sr"));
        assertTrue(ValidatorService.isValidAlias("test.bpcia"));
    }

    @Test
    @DisplayName("Test for validating invalid alias.")
    public void testInvalidAlias() {
        assertFalse(ValidatorService.isValidAlias("test.fail.alias.hsbc"));
        assertFalse(ValidatorService.isValidAlias("alias7"));
    }
}
