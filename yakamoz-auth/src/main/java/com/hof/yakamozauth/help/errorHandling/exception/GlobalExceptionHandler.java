package com.hof.yakamozauth.help.errorHandling.exception;

import com.hof.yakamozauth.help.errorHandling.MessageTextResolver;
import com.hof.yakamozauth.help.service.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private final MonitoringService monitoringService;

	private final MessageTextResolver messageTextResolver;

	@ExceptionHandler(YakamozAuthValidationException.class)
	ResponseEntity<ApiResponse> handleCmsValidationException(YakamozAuthValidationException ex, Locale locale) {
		monitoringService.error("CMS validation exception occured. errorCode: {}, message: {}", ex.getErrorCode(), ex.getValidationResponse());

		Integer responseCode = null;
		ErrorCode errorCode = ex.getErrorCode();
		if (errorCode == null) {
			responseCode = 1000;
		} else {
			responseCode = errorCode.code();
		}

		String responseMessage = messageTextResolver.resolve(ex, locale);

		HttpStatus httpStatus = HttpStatus.OK;
		if (errorCode instanceof HttpAwareErrorCode) {
			httpStatus = ((HttpAwareErrorCode) errorCode).httpStatus();
		}

		ApiResponse apiResponse = ApiResponse.of(responseCode, responseMessage);
		apiResponse.getOperationResult().setValidationResponse(ex.getValidationResponse());

		return ResponseEntity.status(httpStatus.value()).body(apiResponse);
	}


	@ExceptionHandler(BaseRuntimeException.class)
	ResponseEntity<ApiResponse> handleBaseRuntimeException(BaseRuntimeException ex, Locale locale) {
		monitoringService.error("Base Runtime Exception. errorCode: {}, message: {}", ex.getErrorCode(), ex.getMessage(), ex);

		Integer responseCode = null;
		ErrorCode errorCode = ex.getErrorCode();
		if (errorCode == null) {
			responseCode = 1000;
		} else {
			responseCode = errorCode.code();
		}

		String responseMessage = messageTextResolver.resolve(ex, locale);

		HttpStatus httpStatus = HttpStatus.OK;
		if (errorCode instanceof HttpAwareErrorCode) {
			httpStatus = ((HttpAwareErrorCode) errorCode).httpStatus();
		}
		return ResponseEntity.status(httpStatus.value()).body(ApiResponse.of(responseCode, responseMessage));
	}
	
    @ExceptionHandler(AuthBusinessException.class)
    ResponseEntity<ApiResponse> handleAuthBusinessExceptions(AuthBusinessException ex, Locale locale) {
    	monitoringService.error("Number format exception occured: [{}], msg: [{}]", ex, ex.getClass().getName(), ex.getMessage());
    	
    	Integer responseCode = null;
		ErrorCode errorCode = ex.getErrorCode();
		if (errorCode == null) {
			responseCode = 1000;
		} else {
			responseCode = errorCode.code();
		}

		String responseMessage = messageTextResolver.resolve(ex, locale);

		HttpStatus httpStatus = HttpStatus.OK;
		if (errorCode instanceof HttpAwareErrorCode) {
			httpStatus = ((HttpAwareErrorCode) errorCode).httpStatus();
		}
		return ResponseEntity.status(httpStatus.value()).body(ApiResponse.of(responseCode, responseMessage));
    }
	
	@ExceptionHandler(CmsRuntimeException.class)
	ResponseEntity<ApiResponse> handleCmsRuntimeException(CmsRuntimeException ex, Locale locale) {
		Integer errorCode = ex.getErrorCode();
		if (errorCode == null) {
			errorCode = 1000;
		}
		monitoringService.error("CmsRuntimeException occured: [{}], msg: [{}]", ex, ex.getClass().getName(), ex.getMessage());
		return ResponseEntity.ok(ApiResponse.of(errorCode, ex.getMessage()));
	}

	@ExceptionHandler(CmsBusinessException.class)
	ResponseEntity<ApiResponse> handleCmsBusinessException(CmsBusinessException ex, Locale locale) {
		Integer errorCode = ex.getErrorCode();
		if (errorCode == null) {
			errorCode = 1000;
		}
		monitoringService.error("CmsBusinessException occured: [{}], msg: [{}]", ex, ex.getClass().getName(), ex.getMessage());
		return ResponseEntity.ok(ApiResponse.of(errorCode, ex.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	ResponseEntity<ApiResponse> handleException(Exception ex, Locale locale) {
		monitoringService.error("Unhandled exception occured: [{}], msg: [{}]", ex, ex.getClass().getName(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponse.of(1000, "General Error! " + ex.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex, Locale locale) {
		monitoringService.error("Illegal argument exception occured: [{}], msg: [{}]", ex, ex.getClass().getName(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.OK)
				.body(ApiResponse.of(1001, "Illegal argument exception! " + ex.getMessage()));
	}

	@ExceptionHandler(IllegalStateException.class)
	ResponseEntity<ApiResponse> handleIllegalStateException(IllegalStateException ex, Locale locale) {
		monitoringService.error("Illegal state exception occured: [{}], msg: [{}]", ex, ex.getClass().getName(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.OK)
				.body(ApiResponse.of(1001, "Illegal state exception! " + ex.getMessage()));
	}

	@ExceptionHandler(NumberFormatException.class)
	ResponseEntity<ApiResponse> handleNumberFormatException(NumberFormatException ex, Locale locale) {
		monitoringService.error("Number format exception occured: [{}], msg: [{}]", ex, ex.getClass().getName(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ApiResponse.of(1001, "Number format exception! " + ex.getMessage()));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ValidationResponse validationResponse = ValidationResponse.empty();
		ex.getBindingResult().getAllErrors()
				.forEach(objectError -> {
					String errorMessage = objectError.getDefaultMessage();
					String fieldName = ((FieldError) objectError).getField();

					String validationText = messageTextResolver.resolve("CMS-" + errorMessage, fieldName);
					validationResponse.addError(validationText);
				});

		ApiResponse apiResponse = ApiResponse.of(1001,"Missing Argument");
		apiResponse.getOperationResult().setValidationResponse(validationResponse);

		return ResponseEntity.badRequest().body(apiResponse);
	}
}
