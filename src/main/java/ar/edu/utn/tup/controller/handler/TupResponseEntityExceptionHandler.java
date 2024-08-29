package ar.edu.utn.tup.controller.handler;

import ar.edu.utn.tup.controller.exception.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TupResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> errorMessages = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        CustomApiError customApiError = new CustomApiError();
        customApiError.setErrorCode("VAL-1001");
        customApiError.setErrorMessage(errorMessages);

        return handleExceptionInternal(ex, customApiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = UniqueConstraintViolationException.class)
    protected ResponseEntity<Object> handleUniqueConstraintViolationException(UniqueConstraintViolationException ex, WebRequest request) {
        CustomApiError customApiError = new CustomApiError();
        customApiError.setErrorCode("VAL-1002");
        customApiError.setErrorMessage(List.of(ex.getMessage()));

        return handleExceptionInternal(ex, customApiError, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = InvalidFieldException.class)
    protected ResponseEntity<Object> handleInvalidFieldException(InvalidFieldException ex, WebRequest request) {
        CustomApiError customApiError = new CustomApiError();
        customApiError.setErrorCode("VAL-1003");
        customApiError.setErrorMessage(List.of(ex.getMessage()));

        return handleExceptionInternal(ex, customApiError, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        CustomApiError customApiError = new CustomApiError();
        customApiError.setErrorCode("VAL-1004");
        customApiError.setErrorMessage(List.of(ex.getMessage()));

        return handleExceptionInternal(ex, customApiError, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = InsufficientFundsException.class)
    protected ResponseEntity<Object> handleInsufficientFundsException(InsufficientFundsException ex, WebRequest request) {
        CustomApiError customApiError = new CustomApiError();
        customApiError.setErrorCode("VAL-1005");
        customApiError.setErrorMessage(List.of(ex.getMessage()));

        return handleExceptionInternal(ex, customApiError, new HttpHeaders(), HttpStatus.PAYMENT_REQUIRED, request);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        CustomApiError customApiError = new CustomApiError();
        customApiError.setErrorCode("VAL-1006");
        customApiError.setErrorMessage(List.of(ex.getMessage()));

        return handleExceptionInternal(ex, customApiError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = CurrencyMismatchException.class)
    protected ResponseEntity<Object> handleCurrencyMismatchException(CurrencyMismatchException ex, WebRequest request) {
        CustomApiError customApiError = new CustomApiError();
        customApiError.setErrorCode("VAL-1007");
        customApiError.setErrorMessage(List.of(ex.getMessage()));

        return handleExceptionInternal(ex, customApiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = InvalidTransferException.class)
    protected ResponseEntity<Object> handleInvalidTransferException(InvalidTransferException ex, WebRequest request) {
        CustomApiError customApiError = new CustomApiError();
        customApiError.setErrorCode("VAL-1008");
        customApiError.setErrorMessage(List.of(ex.getMessage()));

        return handleExceptionInternal(ex, customApiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = AccountInactiveException.class)
    protected ResponseEntity<Object> handleAccountInactiveException(AccountInactiveException ex, WebRequest request) {
        CustomApiError customApiError = new CustomApiError();
        customApiError.setErrorCode("VAL-1009");
        customApiError.setErrorMessage(List.of(ex.getMessage()));

        return handleExceptionInternal(ex, customApiError, new HttpHeaders(), HttpStatus.LOCKED, request);
    }

    @ExceptionHandler(value = InvalidFixedTermException.class)
    protected ResponseEntity<Object> handleInvalidFixedTermException(InvalidFixedTermException ex, WebRequest request) {
        CustomApiError customApiError = new CustomApiError();
        customApiError.setErrorCode("VAL-1010");
        customApiError.setErrorMessage(List.of(ex.getMessage()));

        return handleExceptionInternal(ex, customApiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (body == null) {
            CustomApiError error = new CustomApiError();
            error.setErrorCode("SRV-5001");
            error.setErrorMessage(List.of(ex.getMessage()));
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            body = error;
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
