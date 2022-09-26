package com.bakhanovich.interviews.shoppingcart.exceptionHandler;

import com.bakhanovich.interviews.shoppingcart.exception.DuplicateException;
import com.bakhanovich.interviews.shoppingcart.exception.EntityNotFoundException;
import com.bakhanovich.interviews.shoppingcart.exception.MethodArgumentNotValidException;
import com.bakhanovich.interviews.shoppingcart.translator.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The class that handles all the errors of the application.
 */
@ControllerAdvice
public class ShoppingCartExceptionHandler {
    public static final String ERROR_CODE_FOR_OTHER_EXCEPTION = "40099";
    public static final String ERROR_CODE_FOR_SECURITY_EXCEPTION = "40066";
    public static final String ERROR_MESSAGE_FOR_OTHER_EXCEPTION = "Entered incorrect data.";

    @Autowired
    private Translator translator;

    /**
     * The setter of the {@link Translator}.
     *
     * @param translator is the value to set.
     */
    public void setTranslator(Translator translator) {
        this.translator = translator;
    }

    /**
     * Handles all the {@link EntityNotFoundException}.
     *
     * @param exception is the {@link EntityNotFoundException} to handle.
     * @return response that contains the data of the {@link EntityNotFoundException}.
     */
    @ExceptionHandler
    public ResponseEntity<ExceptionData> handleEntityNotFoundException(EntityNotFoundException exception) {
        ExceptionData exceptionData = new ExceptionData();
        exceptionData.setErrorCode(exception.getErrorCode());
        exceptionData.setErrorMessage(exception.getErrorMessage());
        return new ResponseEntity<>(exceptionData, HttpStatus.NOT_FOUND);
    }

//    /**
//     * Handles all the {@link AccessDeniedException}.
//     *
//     * @param exception is the {@link AccessDeniedException} to handle.
//     * @return response that contains the data of the {@link AccessDeniedException}.
//     */
//    @ExceptionHandler
//    public ResponseEntity<ExceptionData> handleAccessDeniedException(AccessDeniedException exception) {
//        ExceptionData exceptionData = new ExceptionData();
//        exceptionData.setErrorCode(ERROR_CODE_FOR_SECURITY_EXCEPTION);
//        exceptionData.setErrorMessage(exception.getMessage());
//        return new ResponseEntity<>(exceptionData, HttpStatus.FORBIDDEN);
//    }

    /**
     * Handles all the {@link DuplicateException}.
     *
     * @param exception is the {@link DuplicateException} to handle.
     * @return response that contains the data of the {@link DuplicateException}.
     */
    @ExceptionHandler
    public ResponseEntity<ExceptionData> handleDuplicateException(DuplicateException exception) {
        ExceptionData exceptionData = new ExceptionData();
        exceptionData.setErrorCode(exception.getErrorCode());
        exceptionData.setErrorMessage(exception.getErrorMessage());
        return new ResponseEntity<>(exceptionData, HttpStatus.CONFLICT);
    }

    /**
     * Handles all the {@link MethodArgumentNotValidException}.
     *
     * @param exception is the {@link MethodArgumentNotValidException} to handle.
     * @return response that contains the data of the {@link MethodArgumentNotValidException}.
     */
    @ExceptionHandler
    public ResponseEntity<ExceptionData> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ExceptionData exceptionData = new ExceptionData();
        exceptionData.setErrorCode(exception.getErrorCode());
        exceptionData.setErrorMessage(exception.getErrorMessage());
        return new ResponseEntity<>(exceptionData, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all the {@link Exception} except:
     * - {@link MethodArgumentNotValidException},
     * - {@link EntityNotFoundException}.
     * - {@link DuplicateException}.
     *
     * @param exception is the {@link Exception} to handle.
     * @return response that contains the data of the {@link Exception}.
     */
    @ExceptionHandler
    public ResponseEntity<ExceptionData> handleOtherException(Exception exception) {
        ExceptionData exceptionData = new ExceptionData();
        exceptionData.setErrorCode(ERROR_CODE_FOR_OTHER_EXCEPTION);
        exceptionData.setErrorMessage(ERROR_MESSAGE_FOR_OTHER_EXCEPTION);
        return new ResponseEntity<>(exceptionData, HttpStatus.BAD_REQUEST);
    }
}
