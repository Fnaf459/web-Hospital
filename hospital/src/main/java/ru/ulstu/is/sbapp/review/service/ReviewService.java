package ru.ulstu.is.sbapp.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ulstu.is.sbapp.doctor.model.Doctor;
import ru.ulstu.is.sbapp.doctor.service.DoctorService;
import ru.ulstu.is.sbapp.patient.model.Patient;
import ru.ulstu.is.sbapp.patient.service.PatientService;
import ru.ulstu.is.sbapp.review.model.Review;
import ru.ulstu.is.sbapp.review.repository.ReviewRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
@Service
public class ReviewService {
    private EntityManager em;
    private final ReviewRepository reviewRepository;
    private  final DoctorService doctorService;
    private  final PatientService patientService;

    private final ValidatorUtil validatorUtil;

    public ReviewService(ReviewRepository reviewRepository, DoctorService doctorService,
                         PatientService patientService, ValidatorUtil validatorUtil) {
        this.reviewRepository = reviewRepository;
        this.doctorService = doctorService;
        this.validatorUtil = validatorUtil;
        this.patientService = patientService;
    }

    @Transactional
    public Review addReview(int reviewGradeDoctor, String reviewTextField, Long doctorID, Long patientID) {
        final Review review = new Review(reviewGradeDoctor, reviewTextField,
                doctorService.findDoctor(doctorID), patientService.findPatient(patientID));
        validatorUtil.validate(review);
        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public Review findReview(Long id) {
        final Optional<Review> review = reviewRepository.findById(id);
        return review.orElseThrow(() -> new ReviewNotFoundException(id));
    }

    @Transactional
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    @Transactional
    public Review updateReview(Long id, int reviewGradeDoctor, String reviewTextField,
                               Long doctorId, Long patientId) {
        if (reviewGradeDoctor == 0 && !StringUtils.hasText(reviewTextField)) {
            throw new IllegalArgumentException("Review grade and text is null or empty");
        }
        final Review currentReview = findReview(id);
        Doctor doctor = doctorService.findDoctor(doctorId);
        Patient patient = patientService.findPatient(patientId);
        currentReview.setGradeDoctor(reviewGradeDoctor);
        currentReview.setTextField(reviewTextField);
        currentReview.setDoctor(doctor);
        currentReview.setPatient(patient);
        validatorUtil.validate(currentReview);
        return reviewRepository.save(currentReview);
    }

    @Transactional
    public Review deleteReview(Long id) {
        final Review currentReview = findReview(id);
        reviewRepository.delete(currentReview);
        return currentReview;
    }
}