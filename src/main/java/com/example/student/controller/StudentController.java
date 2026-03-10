package com.example.student.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.dto.request.ClassRequest;
import com.example.student.dto.request.EnrollmentRequest;
import com.example.student.dto.request.GradeRequest;
import com.example.student.dto.request.InvoiceRequest;
import com.example.student.dto.request.PaymentRequest;
import com.example.student.dto.request.StudentRequest;
import com.example.student.dto.response.StudentResponse;
import com.example.student.entity.ClassEntity;
import com.example.student.entity.Enrollment;
import com.example.student.entity.Grade;
import com.example.student.entity.Invoice;
import com.example.student.entity.Payment;
import com.example.student.entity.Student;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.EnrollmentRepository;
import com.example.student.repository.GradeRepository;
import com.example.student.repository.InvoiceRepository;
import com.example.student.repository.PaymentRepository;
import com.example.student.repository.StudentRepository;
import com.example.student.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Student Management", description = "API quản lý thông tin sinh viên (CRUD, tìm kiếm, lọc, kích hoạt/vô hiệu hóa)")
@RestController
@RequestMapping(value = "/api/students", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final ClassRepository classRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final GradeRepository gradeRepo;
    private final InvoiceRepository invoiceRepo;
    private final PaymentRepository paymentRepo;
    private final StudentRepository studentRepo;

    private final StudentService service;

    @Operation(
            summary = "Tạo sinh viên mới",
            description = "Tạo một sinh viên mới. Email phải duy nhất trong hệ thống."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Sinh viên được tạo thành công",
                content = @Content(schema = @Schema(implementation = StudentResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dữ liệu đầu vào không hợp lệ (validation error)"),
        @ApiResponse(responseCode = "409", description = "Email đã tồn tại")
    })
    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
        StudentResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Lấy danh sách sinh viên", description = "Trả về danh sách sinh viên có hỗ trợ phân trang và sắp xếp")
    @ApiResponse(responseCode = "200", description = "Danh sách sinh viên")
    @GetMapping
    public ResponseEntity<Page<StudentResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @Operation(summary = "Lấy thông tin sinh viên theo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tìm thấy sinh viên"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy sinh viên")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getById(
            @PathVariable @Parameter(description = "ID của sinh viên") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cập nhật toàn bộ thông tin sinh viên")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
        @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy sinh viên"),
        @ApiResponse(responseCode = "409", description = "Email mới đã tồn tại")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Xóa sinh viên", description = "Xóa vĩnh viễn sinh viên khỏi hệ thống")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Xóa thành công"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy sinh viên")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Vô hiệu hóa (deactivate) sinh viên")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vô hiệu hóa thành công"),
        @ApiResponse(responseCode = "400", description = "Sinh viên đã ở trạng thái inactive"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy sinh viên")
    })
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Map<String, String>> deactivate(@PathVariable Long id) {
        service.deactivate(id);
        return ResponseEntity.ok(Map.of("message", "Student deactivated successfully"));
    }

    @Operation(summary = "Kích hoạt (activate) sinh viên")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Kích hoạt thành công"),
        @ApiResponse(responseCode = "400", description = "Sinh viên đã ở trạng thái active"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy sinh viên")
    })
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Map<String, String>> activate(@PathVariable Long id) {
        service.activate(id);
        return ResponseEntity.ok(Map.of("message", "Student activated successfully"));
    }

    @Operation(summary = "Tìm sinh viên theo email")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tìm thấy sinh viên"),
        @ApiResponse(responseCode = "404", description = "Không tìm thấy sinh viên với email này")
    })
    @GetMapping("/search")
    public ResponseEntity<StudentResponse> searchByEmail(
            @RequestParam @Parameter(description = "Email cần tìm") String email) {
        return ResponseEntity.ok(service.findByEmail(email));
    }

    @Operation(summary = "Lọc sinh viên theo khoảng GPA")
    @ApiResponse(responseCode = "200", description = "Danh sách sinh viên thỏa mãn khoảng GPA")
    @GetMapping("/filter")
    public ResponseEntity<List<StudentResponse>> filterByGpa(
            @RequestParam @Parameter(description = "GPA tối thiểu") Double minGpa,
            @RequestParam @Parameter(description = "GPA tối đa") Double maxGpa) {
        return ResponseEntity.ok(service.filterByGpa(minGpa, maxGpa));
    }

    @Operation(summary = "Lấy tất cả sinh viên đang hoạt động (active = true)")
    @ApiResponse(responseCode = "200", description = "Danh sách sinh viên đang hoạt động")
    @GetMapping("/active")
    public ResponseEntity<List<StudentResponse>> getActiveStudents() {
        return ResponseEntity.ok(service.getActiveStudents());
    }

    @PostMapping("/students")
    public StudentResponse createStudent(@Valid @RequestBody StudentRequest request) {
        return studentService.create(request);
    }

    @GetMapping("/students")
    public List<StudentResponse> getStudents() {
        return studentService.getAll(null).getContent();
    }

    @GetMapping("/students/{id}")
    public StudentResponse getStudent(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @PostMapping("/classes")
    public ClassEntity createClass(@RequestBody ClassRequest request) {

        ClassEntity clazz = new ClassEntity();

        clazz.setCode(request.getCode());
        clazz.setName(request.getName());
        clazz.setCapacity(request.getCapacity());
        clazz.setStartDate(request.getStartDate());
        clazz.setEndDate(request.getEndDate());

        return classRepo.save(clazz);
    }

    @PostMapping("/enrollments")
    public Enrollment enroll(@RequestBody EnrollmentRequest req) {

        Student student = studentRepo.findById(req.getStudentId()).orElseThrow();
        ClassEntity clazz = classRepo.findById(req.getClassId()).orElseThrow();

        Enrollment e = new Enrollment();
        e.setStudent(student);
        e.setClazz(clazz);
        e.setEnrolledAt(LocalDateTime.now());

        return enrollmentRepo.save(e);
    }

    @GetMapping("/classes/{id}/students")
    public List<Student> getStudentsInClass(@PathVariable Long id) {

        List<Enrollment> enrollments = enrollmentRepo.findByClazzId(id);

        return enrollments.stream()
                .map(Enrollment::getStudent)
                .toList();
    }

    @GetMapping("/students/{id}/classes")
    public List<ClassEntity> getClassesOfStudent(@PathVariable Long id) {

        List<Enrollment> enrollments = enrollmentRepo.findByStudentId(id);

        return enrollments.stream()
                .map(Enrollment::getClazz)
                .toList();
    }

    @PostMapping("/grades")
    public Grade createGrade(@RequestBody GradeRequest req) {

        Enrollment enrollment = enrollmentRepo
                .findById(req.getEnrollmentId())
                .orElseThrow();

        Grade grade = new Grade();

        grade.setEnrollment(enrollment);
        grade.setScore(req.getScore());
        grade.setGradedAt(LocalDateTime.now());

        return gradeRepo.save(grade);
    }

    @PostMapping("/invoices")
    public Invoice createInvoice(@RequestBody InvoiceRequest req) {

        Student student = studentRepo.findById(req.getStudentId()).orElseThrow();

        Invoice invoice = new Invoice();

        invoice.setStudent(student);
        invoice.setTerm(req.getTerm());
        invoice.setAmountTotal(req.getAmountTotal());
        invoice.setAmountPaid(0.0);
        invoice.setStatus("UNPAID");
        invoice.setDueDate(req.getDueDate());

        return invoiceRepo.save(invoice);
    }

    @PostMapping("/invoices/{id}/pay")
    public String payInvoice(
            @PathVariable Long id,
            @RequestBody PaymentRequest req) {

        Invoice invoice = invoiceRepo.findById(id).orElseThrow();

        if (invoice.getAmountPaid() + req.getAmount() > invoice.getAmountTotal()) {
            throw new RuntimeException("Overpayment");
        }

        Payment payment = new Payment();

        payment.setInvoice(invoice);
        payment.setAmount(req.getAmount());
        payment.setMethod(req.getMethod());
        payment.setPaidAt(LocalDateTime.now());

        paymentRepo.save(payment);

        invoice.setAmountPaid(invoice.getAmountPaid() + req.getAmount());

        if (invoice.getAmountPaid().equals(invoice.getAmountTotal())) {
            invoice.setStatus("PAID"); 
        }else {
            invoice.setStatus("PARTIALLY_PAID");
        }

        invoiceRepo.save(invoice);

        return "Payment success";
    }

    @GetMapping("/students/{id}/invoices")
    public List<Invoice> getInvoices(@PathVariable Long id) {
        return invoiceRepo.findByStudentId(id);
    }

    @GetMapping("/invoices/{id}/payments")
    public List<Payment> getPayments(@PathVariable Long id) {
        return paymentRepo.findByInvoiceId(id);
    }
}
