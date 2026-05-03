package com.gamemini.api.exceptions;

import com.gamemini.api.dtos.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Map;

/**
 * <hr/>
 * <div style="background-color: black">
 *     <div>
 *          <h1 style="color: white; font-style: bold; padding-left: 5px">Workflows GlobalException Handle</h1>
 *          <ul style="padding-left: 20px">
 *              <li style="color:white"><strong style="color:orange">First:</strong> Service throw new exception</li>
 *              <li style="color: white"><strong style="color:orange">Next:</strong> GlobalException catchs corresponding exception and handle it</li>
 *              <li style="color: white">
 *                  <strong style="color:orange">Finally: </strong>
 *                  <div style="padding-left: 10px">
 *                      <p><strong>Case 1:</strong> Controller return ApiResponse after GlobalException handled.</p>
 *                      <p><strong>Case 2:</strong> If not any Exception throw from services, controller auto return ApiResponse.</p>
 *                  </div>
 *              </li>
 *          </ul>
 *     </div>
 *     <div>
 *         <h1 style="color: white; font-style: bold; padding-left: 5px">NOTICES</h1>
 *         <p style="color:white; padding-left: 20px">In the whole ExceptionHandlers has defined below, including: </p>
 *         <ol style="padding-left: 20px">
 *               <li style="color: white">
 *                   ExceptionHandler for <strong style="color: #EEEEE">MethodArgumentNotValidException</strong>,
 *                   this applies for Spring Validation at the class DTOs field, such as class
 *                   <strong style="color: orange">RequestUpdatePokemon</strong> with field
 *                   <strong style="color: #EEEEE">type</strong>.
 *                   <p>
 *                       This mean, when client send request with body {}, but field required incorrect,
 *                       <strong style="color: #EEEEE">GlobalException</strong> auto calls ExceptionHandler
 *                       <strong style="color: #EEEEE">MethodArgumentNotValidException</strong> to handle without manual
 *                       call it.
 *                   </p>
 *               </li>
 *               <li style="color: white">
 *                   ExceptionHandler for <strong style="color: #EEEEE">HttpMessageNotReadableException</strong>,
 *                   this applies for Validation data requested from client.
 *                   <p>
 *                       This mean, when client sent request without body {},  <strong style="color: #EEEEE">GlobalException</strong>
 *                       auto calls ExceptionHandler<strong style="color: #EEEEE">HttpMessageNotReadableException</strong>
 *                       to handle without manual call it.
 *                   </p>
 *               </li>
 *               <li style="color: white">
 *                   ExceptionHandler for <strong style="color: #EEEEE">DataAccessException</strong>,
 *                   this applies for Database access exception.
 *                   <p>
 *                       This mean, when application is running at runtime, if error occurred when interactive DB,
 *                       <strong style="color: #EEEEE">GlobalException</strong> auto calls ExceptionHandler
 *                       <strong style="color: #EEEEE">DataAccessException</strong> to handle without manual call it.
 *                   </p>
 *               </li>
 *               <li style="color: white">
 *                   ExceptionHandler for <strong style="color: #EEEEE">Exception</strong>,
 *                   this applies for all exception undefined or other unexpected
 *                   <p>
 *                       This mean, when application is running at runtime, if error occurred but undefined
 *                       <strong style="color: #EEEEE">GlobalException</strong> auto calls ExceptionHandler
 *                       <strong style="color: #EEEEE">Exception</strong> to handle without manual call it.
 *                   </p>
 *               </li>
 *           </ol>
 *     </div>
 * </div>
 * <hr/>
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /// LOI 404
    @ExceptionHandler(NotfoundException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleNotFound(NotfoundException e) {
        return ApiResponse.notFound(e.getMessage());
    }

    /// LOI 400
    @ExceptionHandler(BadRequestException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleBadRequest(BadRequestException e) {
        return ApiResponse.badRequest(e.getMessage());
    }

    /// Validation (body có nhưng sai field) - dung cho cac request body tu client
    /// Exception Handler nay ap dung cho Validation data (request body)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleValidation(MethodArgumentNotValidException e) {
        /// Dung getBidingResult().getObjectName() de xem Spring dang biding cua class DTO nao (user hay pokemon hay review)
        String DtoName = e.getBindingResult().getObjectName();
        System.out.println(DtoName + " Loi o day");
        List<String> message = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .toList();

        e.getBindingResult().getFieldErrors().forEach(err -> {
            System.out.println("FIELD: " + err.getField());
            System.out.println("ERROR: " + err.getDefaultMessage());
        });

        return ApiResponse.badRequest(message);
    }

    /// Body null khong co {} hoac sai Json - dung cho cac request body tu client
    /// Exception Handler nay ap dung cho Validation data (request body)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleInvalidBody(HttpMessageNotReadableException e) {
        System.out.println(e.getMessage());
        return ApiResponse.badRequest("Data invalid");
    }

    /// 500 - DB error
    @ExceptionHandler(DataAccessException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleDatabaseError(DataAccessException e) {
        log.error("Database error: ", e);
        return ApiResponse.internalServerError("Database error occurred");
    }

    /// Undefined other Exception - fallback (du phong)
    @ExceptionHandler(Exception.class)
    public <T> ResponseEntity<ApiResponse<T>> handleAll(Exception e) {
        log.error("Unexpected error: ", e);
        return ApiResponse.notFound("An error occurred!");
    }

    /// ExceptionHandler NoResourceFoundException xu ly ngoai le khi nguoi dung co tinh truy cap vao cac route khong ton
    /// tai trong ung dung.
    /// Khi nguoi dung co tinh truy cap vao cac route khong ton tai trong ung dung GlobalException tu dong xu ly truong hop
    /// nay ma khong can goi thu cong.
    @ExceptionHandler(NoResourceFoundException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleApiNotFound(NoResourceFoundException e) {
        log.error("Resource not found: ", e.getMessage());
        return ApiResponse.notFound("API Not Found!");
    }

    /**
     * ExceptionHandler DataIntegrityViolationException ap dung cho truong hop validation du lieu mang tinh rang buoc trong
     * co so du lieu DB nhu trung lap khong unique hoac cac rang buoc khac khai bao trong database.
     * Khi loi rang buoc du lieu xay ra thi GlobalException tuy dong goi ExceptionHandler DataIntegrityViolationException
     * de xu ly chung ma khong can phai goi thu cong.
     *
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        Map<String, String> errorCaseMap = Map.of(
                "uk_users_email", "Email already exists", /// Spring lay noi dung FK tu FK_name trong DB
                "uk_users_phone", "Phone already exists" /// Spring lay noi dung FK tu FK_name trong DB
        );
        String error = e.getMostSpecificCause().getMessage();
        System.out.println("Loi o day...");

        for (String key : errorCaseMap.keySet()) {
            if (error.contains(key))
                return ApiResponse.badRequest(errorCaseMap.get(key));
        }

        return ApiResponse.badRequest("Data is duplicated or existed");
    }

    /// ExceptionHandler OptimisticLockingFailureException ap dung cho muc dich khi tai cung 1 thoi diem co nhieu request tuong
    /// tac tren cung 1 du lieu. Ngoai le nay giup cho viec du lieu duoc bao toan chinh xac tranh bi sai du lieu khi du lieu duoc
    /// cap nhat troong cung mot thoi diem.
    /// GlobalException tu dong goi ExceptionHandler OptimisticLockingFailureException de xu ly khi xay ra truong hop tren ma khong
    /// can phai goi thu cong.
    /// <br /><br />
    /// Dieu kien ap dung ExceptionHandle OptimisticLockingFailureException can phai co: <br />
    ///     1. Tai cac class POJO phai them field version cho entity do, dong thoi them annotation @Version mac dinh cua Spring <br />
    ///     2. Tai tang Services, cac method nao mang tinh nhay cam nhu payment hoac update du lieu thi can phai them annotation @Transactional cho method do.
    /// <br /><br />
    /// Xem Demo tai class POJO Pokemon
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleOptimisticLockFailure(OptimisticLockingFailureException e) {
        return ApiResponse.badRequest("Data was modified by another request");
    }

    ///  XU ly loi kh nguoi dung co truy cap route va truyen tham so khong dung voi format ma API duoc dinh nghia trong
    /// controller. Global se tu dong xu ly cho truong hop nay ma khong can phai goi thu cong.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        System.out.println(e.getMessage());
        return ApiResponse.badRequest("Api not found");
    }

    /// ExceptionHandler custom xu ly query string URL khong dung tham so format quy dinh cua API.
    @ExceptionHandler(IllegalParamException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleIllegalParam(IllegalParamException e) {
        return ApiResponse.badRequest("Param invalid.");
    }

    /// Xu ly loi ngoai le BadCredentialException khi UserDetailsService xac thuc nguoi dung
    /// chua co trang thai dang nha@ thanh cong. Neu username not found, GlobalException tu dong xu ly loi
    /// BadCredentialException.
    /// BadCredentialException la loi gop chung neu username sai hoac passwrod sai, Spring security khong bao chinh xac saii username
    /// hay password de tranh hacker co the doan duoc neu username dung thi test password hoac nguoc lai
    @ExceptionHandler(BadCredentialsException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleUsernameNotFound(BadCredentialsException e) {
        log.error("BadCredentialsException.class ", e.getMessage());
        return ApiResponse.badRequest("Your account is invalid.");
    }
}
