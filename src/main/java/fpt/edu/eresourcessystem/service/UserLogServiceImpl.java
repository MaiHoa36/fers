package fpt.edu.eresourcessystem.service;

import fpt.edu.eresourcessystem.enums.CommonEnum;
import fpt.edu.eresourcessystem.enums.CourseEnum;
import fpt.edu.eresourcessystem.model.Course;
import fpt.edu.eresourcessystem.model.UserLog;
import fpt.edu.eresourcessystem.repository.UserLogRepository;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Service("userLogService")
public class UserLogServiceImpl implements UserLogService {
    private final UserLogRepository userLogRepository;
    private final MongoTemplate mongoTemplate;


    public UserLogServiceImpl(UserLogRepository userLogRepository, MongoTemplate mongoTemplate) {
        this.userLogRepository = userLogRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<UserLog> findAll() {
        return userLogRepository.findAll();
    }

    @Override
    public List<UserLog> findByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return userLogRepository.findByEmail(email);
    }

    @Override
    public List<UserLog> searchLog(String email, String role, LocalDateTime startDate, LocalDateTime endDate) {
        Criteria criteria = Criteria.where("time").gte(startDate).lte(endDate)
                .and("email").regex(Pattern.quote(email), "i")
                .and("role").regex(Pattern.quote(role), "i");

        Query query = new Query(criteria);
        return mongoTemplate.find(query, UserLog.class);
    }

    @Override
    public List<UserLog> findByUrl(String url) {
        return userLogRepository.findByUrl(url);
    }

    @Override
    public UserLog addUserLog(UserLog userLog) {
        if (null != userLog) {
            return userLogRepository.save(userLog);
        }
        return null;
    }

    @Override
    public List<Course> findStudentRecentView(String email) {
        String urlPrefix = "/student/courses/";
        Criteria criteria = Criteria.where("email").is(email)
                .and("url").regex("^" + urlPrefix)
                .and("createdDate").exists(true);

        // Sort by the "time" in descending order to get the most recent documents
        Query query = new Query(criteria).with(Sort.by(Sort.Order.desc("createdDate")));

        // Use a Pageable to limit the result set to 5 documents
        PageRequest pageable = PageRequest.of(0, 5);
        query.with(pageable);
        List<String> listCourseIds = mongoTemplate.findDistinct(query, "url", UserLog.class, String.class)
                .stream().map(o -> o.substring(o.indexOf(urlPrefix) + urlPrefix.length()))
                .toList();

        return mongoTemplate.find(
                Query.query(Criteria.where("id").in(listCourseIds)
                        .and("deleteFlg").is(CommonEnum.DeleteFlg.PRESERVED)
                        .and("status").is(CourseEnum.Status.PUBLISH)),
                Course.class
        );

    }

    @Override
    public Page<UserLog> getUserLogsBySearchAndDate(String search, LocalDate startDate, LocalDate endDate, String role, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Criteria criteria = new Criteria();

        // Match on email and URL containing the search string
        criteria.orOperator(
                Criteria.where("email").regex(Pattern.quote(search), "i"),
                Criteria.where("url").regex(Pattern.quote(search), "i")
        );

        // Date range filtering
        if (startDate != null && endDate != null) {
            criteria.and("createdDate").gte(startDate).lte(endDate);
        } else if (startDate != null) {
            criteria.and("createdDate").gte(startDate);
        } else if (endDate != null) {
            criteria.and("createdDate").lte(endDate);
        }

        // Role filtering
        if (role != null && !role.isEmpty() && !role.equalsIgnoreCase("all")) {
            criteria.and("role").is(role);
        }

        Query query = new Query(criteria).with(pageable);
        List<UserLog> result = mongoTemplate.find(query, UserLog.class, "user_logs");

        long totalCount = mongoTemplate.count(new Query(criteria), UserLog.class, "user_logs");

        return new PageImpl<>(result, pageable, totalCount);
    }
}
