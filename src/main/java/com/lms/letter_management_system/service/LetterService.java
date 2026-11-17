package com.lms.letter_management_system.service;

import com.lms.letter_management_system.datatable.DataTableRequest;
import com.lms.letter_management_system.datatable.DataTablesResponse;
import com.lms.letter_management_system.dto.LetterRow;
import com.lms.letter_management_system.dto.LetterDetails;
import com.lms.letter_management_system.dto.LetterCounts;
import com.lms.letter_management_system.dto.LetterChartData;
import com.lms.letter_management_system.dto.SlimSelectDTO;
import com.lms.letter_management_system.repository.DivisionLvlDesRepo;
import com.lms.letter_management_system.repository.TopLevelDesRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LetterService {

    private final JdbcTemplate jdbc;
    private final AuditorAware<Integer> auditorAware;
    private final Path uploadRoot;
    private final TopLevelDesRepo topLevelDesRepo;
    private final DivisionLvlDesRepo divisionLvlDesRepo;

    private static final DateTimeFormatter WEEK_LABEL_FORMAT = DateTimeFormatter.ofPattern("EEE MM/dd");

    public LetterService(JdbcTemplate jdbc,
                         AuditorAware<Integer> auditorAware,
                         TopLevelDesRepo topLevelDesRepo,
                         DivisionLvlDesRepo divisionLvlDesRepo,
                         @Value("${app.letter-upload-dir:uploads/letters}") String uploadDir) {
        this.jdbc = jdbc;
        this.auditorAware = auditorAware;
        this.topLevelDesRepo = topLevelDesRepo;
        this.divisionLvlDesRepo = divisionLvlDesRepo;
        this.uploadRoot = Path.of(uploadDir).toAbsolutePath().normalize();
    }

    public DataTablesResponse<LetterRow> getLetters(DataTableRequest request) throws Exception {
        String from = " FROM letters l JOIN top_level_designation recipient ON recipient.id=l.address_to_id "
                + "JOIN top_level_designation sender ON sender.id=l.sent_by_id";
        long total = jdbc.queryForObject("SELECT COUNT(*)" + from, Long.class);
        String term = request.getSearch() == null ? "" : request.getSearch().getValue();
        String where = term == null || term.isBlank() ? "" : " WHERE CONCAT_WS(' ', l.id, l.letter_my_no, "
                + "l.subject, recipient.name, sender.name) LIKE ?";
        Object[] searchArgs = where.isEmpty() ? new Object[0] : new Object[]{"%" + term.trim() + "%"};
        long filtered = where.isEmpty() ? total : jdbc.queryForObject("SELECT COUNT(*)" + from + where, Long.class, searchArgs);

        String[] orderColumns = {"l.id", "l.letter_date", "l.letter_my_no", "recipient.name",
            "l.subject", "sender.name", "l.reply_required", "l.id"};
        String orderBy = " ORDER BY l.id DESC";
        if (request.getOrder() != null && !request.getOrder().isEmpty()) {
            DataTableRequest.Order order = request.getOrder().get(0);
            if (order.getColumn() >= 0 && order.getColumn() < orderColumns.length) {
                String direction = "asc".equalsIgnoreCase(order.getDir()) ? "ASC" : "DESC";
                orderBy = " ORDER BY " + orderColumns[order.getColumn()] + " " + direction;
            }
        }

        int start = Math.max(0, request.getStart());
        int length = request.getLength() <= 0 ? 10 : Math.min(request.getLength(), 500);
        String sql = "SELECT l.id, CAST(l.id AS CHAR) AS sno, l.letter_date AS letter_date, "
                + "recipient.name AS address_to, sender.name AS sent_by, l.subject, "
                + "l.letter_my_no, IF(l.reply_required, 'Yes', 'No') AS reply, "
                + "CASE WHEN l.reply_file_path IS NOT NULL THEN 'Replied' "
                + "ELSE 'Not Replied' END AS reply_status"
                + from + where + orderBy + " LIMIT ? OFFSET ?";
        Object[] args = new Object[searchArgs.length + 2];
        System.arraycopy(searchArgs, 0, args, 0, searchArgs.length);
        args[args.length - 2] = length;
        args[args.length - 1] = start;
        RowMapper<LetterRow> mapper = (rs, rowNum) -> {
            LetterRow row = new LetterRow();
            row.setId(rs.getInt("id"));
            row.setSno(rs.getString("sno"));
            row.setDate(rs.getDate("letter_date"));
            row.setAddressTo(rs.getString("address_to"));
            row.setSentBy(rs.getString("sent_by"));
            row.setSubject(rs.getString("subject"));
            row.setLetterMyNo(rs.getString("letter_my_no"));
            row.setReply(rs.getString("reply"));
            row.setReplyStatus(rs.getString("reply_status"));
            return row;
        };
        List<LetterRow> rows = jdbc.query(sql, mapper, args);
        return new DataTablesResponse<>(request.getDraw(), total, filtered, rows);
    }

    public Iterable<SlimSelectDTO> searchDesignations(String search) {
        String value = search == null ? "%" : "%" + search.trim() + "%";
        return topLevelDesRepo.getIdAndName(value);
    }

    public Iterable<SlimSelectDTO> searchDivisions(String search) {
        String value = search == null ? "%" : "%" + search.trim() + "%";
        return divisionLvlDesRepo.getIdAndName(value);
    }

    public Iterable<SlimSelectDTO> searchUsers(String search) {
        String term = "%" + (search == null ? "" : search.trim()) + "%";
        return jdbc.query("SELECT id AS value, full_name AS text FROM users "
                        + "WHERE status='active' AND full_name LIKE ? ORDER BY full_name LIMIT 30",
                (rs, rowNum) -> new SlimSelectDTO(String.valueOf(rs.getInt("value")), rs.getString("text")), term);
    }

    public LetterCounts getLetterCounts() {
        return jdbc.queryForObject("SELECT COUNT(*) AS all_letters, "
                        + "COALESCE(SUM(reply_file_path IS NOT NULL), 0) AS replied_letters, "
                        + "COALESCE(SUM(reply_file_path IS NULL), 0) AS not_replied_letters FROM letters",
                (rs, rowNum) -> new LetterCounts(rs.getLong("all_letters"),
                        rs.getLong("replied_letters"), rs.getLong("not_replied_letters")));
    }

    public LetterChartData getLetterChart(String period) {
        if (!List.of("day", "week", "month").contains(period)) {
            throw new IllegalArgumentException("Period must be day, week, or month.");
        }

        LocalDate today = jdbc.queryForObject("SELECT CURRENT_DATE", (rs, rowNum) -> rs.getDate(1).toLocalDate());
        List<String> labels = new ArrayList<>();
        int bucketCount;
        String bucketExpression;
        String dateCondition;
        if ("day".equals(period)) {
            bucketCount = 24;
            bucketExpression = "HOUR(ent_on)";
            dateCondition = "DATE(ent_on)=CURRENT_DATE";
            for (int hour = 0; hour < bucketCount; hour++) {
                labels.add(String.format("%02d:00", hour));
            }
        } else if ("week".equals(period)) {
            bucketCount = 7;
            bucketExpression = "WEEKDAY(ent_on)";
            dateCondition = "YEARWEEK(ent_on, 1)=YEARWEEK(CURRENT_DATE, 1)";
            LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1L);
            for (int day = 0; day < bucketCount; day++) {
                labels.add(monday.plusDays(day).format(WEEK_LABEL_FORMAT));
            }
        } else {
            bucketCount = today.lengthOfMonth();
            bucketExpression = "DAYOFMONTH(ent_on)-1";
            dateCondition = "YEAR(ent_on)=YEAR(CURRENT_DATE) AND MONTH(ent_on)=MONTH(CURRENT_DATE)";
            for (int day = 1; day <= bucketCount; day++) {
                labels.add(String.valueOf(day));
            }
        }

        long[] all = new long[bucketCount];
        long[] replied = new long[bucketCount];
        long[] notReplied = new long[bucketCount];
        String sql = "SELECT " + bucketExpression + " AS bucket, COUNT(*) AS all_count, "
                + "COALESCE(SUM(reply_file_path IS NOT NULL),0) AS replied_count, "
                + "COALESCE(SUM(reply_file_path IS NULL),0) AS not_replied_count FROM letters WHERE "
                + dateCondition + " GROUP BY bucket ORDER BY bucket";
        final int numberOfBuckets = bucketCount;
        jdbc.query(sql, rs -> {
            int bucket = rs.getInt("bucket");
            if (bucket >= 0 && bucket < numberOfBuckets) {
                all[bucket] = rs.getLong("all_count");
                replied[bucket] = rs.getLong("replied_count");
                notReplied[bucket] = rs.getLong("not_replied_count");
            }
        });

        return new LetterChartData(labels, toList(all), toList(replied), toList(notReplied));
    }

    private List<Long> toList(long[] values) {
        return java.util.Arrays.stream(values).boxed().toList();
    }

    public LetterDetails getLetterDetails(Integer id) {
        String sql = "SELECT l.id, CAST(l.id AS CHAR) AS sno, l.letter_date, l.document_date, "
                + "l.letter_my_no, recipient.name AS address_to, sender.name AS sent_by, l.subject, "
                + "l.reply_required, l.reply_letter_my_no, l.reply_date, l.status, "
                + "l.letter_file_path, l.reply_file_path "
                + "FROM letters l JOIN top_level_designation recipient ON recipient.id=l.address_to_id "
                + "JOIN top_level_designation sender ON sender.id=l.sent_by_id WHERE l.id=?";
        LetterDetails details = jdbc.queryForObject(sql, (rs, rowNum) -> {
            LetterDetails row = new LetterDetails();
            row.setId(rs.getInt("id"));
            row.setSno(rs.getString("sno"));
            row.setReceivedDate(rs.getDate("letter_date"));
            row.setDocumentDate(rs.getDate("document_date"));
            row.setLetterMyNo(rs.getString("letter_my_no"));
            row.setAddressTo(rs.getString("address_to"));
            row.setSentBy(rs.getString("sent_by"));
            row.setSubject(rs.getString("subject"));
            row.setReplyRequired(rs.getBoolean("reply_required"));
            row.setReplyLetterMyNo(rs.getString("reply_letter_my_no"));
            row.setReplyDate(rs.getDate("reply_date"));
            row.setStatus(rs.getString("status"));
            row.setLetterFileUrl("letter/letters/" + id + "/file/letter");
            if (rs.getString("reply_file_path") != null) {
                row.setReplyFileUrl("letter/letters/" + id + "/file/reply");
            }
            return row;
        }, id);
        details.setActionTakenBy(jdbc.query("SELECT d.name FROM letter_action_takers a "
                        + "JOIN top_level_designation d ON d.id=a.designation_id WHERE a.letter_id=? ORDER BY d.name",
                (rs, rowNum) -> rs.getString("name"), id));
        return details;
    }

    public Path getStoredFile(Integer letterId, String kind) {
        if (!"letter".equals(kind) && !"reply".equals(kind)) {
            return null;
        }
        String column = "letter".equals(kind) ? "letter_file_path" : "reply_file_path";
        List<String> paths = jdbc.query("SELECT " + column + " FROM letters WHERE id=?",
                (rs, rowNum) -> rs.getString(1), letterId);
        if (paths.isEmpty() || paths.get(0) == null) {
            return null;
        }
        Path file = uploadRoot.resolve(paths.get(0)).normalize();
        return file.startsWith(uploadRoot) && Files.isRegularFile(file) ? file : null;
    }

    @Transactional
    public int saveLetter(Integer addressToId, Integer sentById, String subject,
                          String letterMyNo, String documentDate, List<Integer> actionTakenBy,
                          boolean replyRequired, String replyLetterMyNo, String replyDate,
                          MultipartFile letterFile, MultipartFile replyFile) throws IOException {
        if (letterFile == null || letterFile.isEmpty()) {
            throw new IllegalArgumentException("Please upload the letter file.");
        }
        if (replyRequired && (replyLetterMyNo == null || replyLetterMyNo.isBlank())) {
            throw new IllegalArgumentException("Enter the reply letter number.");
        }
        if (replyRequired && (replyFile == null || replyFile.isEmpty())) {
            throw new IllegalArgumentException("Please upload the reply letter file.");
        }
        List<Path> storedFiles = new ArrayList<>();
        try {
            String letterPath = storeFile(letterFile, storedFiles);
            String replyPath = replyRequired ? storeFile(replyFile, storedFiles) : null;
            Integer actorId = auditorAware.getCurrentAuditor().orElse(null);

            jdbc.update("INSERT INTO letters (letter_date, address_to_id, sent_by_id, subject, "
                            + "letter_my_no, document_date, reply_required, reply_letter_my_no, "
                    + "reply_date, letter_file_path, reply_file_path, status, ent_by, mod_by) "
                            + "VALUES (CURRENT_DATE, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'active', ?, ?)",
                    addressToId, sentById, subject.trim(), letterMyNo.trim(), documentDate,
                    replyRequired, blankToNull(replyLetterMyNo), replyRequired ? blankToNull(replyDate) : null,
                    letterPath, replyPath, actorId, actorId);
            Integer letterId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

            if (actionTakenBy != null) {
                for (Integer userId : actionTakenBy.stream().filter(id -> id != null).distinct().toList()) {
                    jdbc.update("INSERT INTO letter_action_takers (letter_id, designation_id) VALUES (?, ?)", letterId, userId);
                }
            }
            return letterId;
        } catch (RuntimeException | IOException e) {
            for (Path file : storedFiles) {
                try {
                    Files.deleteIfExists(file);
                } catch (IOException ignored) {
                    // Keep the original save error as the useful exception.
                }
            }
            throw e;
        }
    }

    @Transactional
    public void saveReply(Integer letterId, String replyLetterMyNo, String replyDate,
                          MultipartFile replyFile) throws IOException {
        if (replyLetterMyNo == null || replyLetterMyNo.isBlank()) {
            throw new IllegalArgumentException("Enter the reply letter number.");
        }
        if (replyFile == null || replyFile.isEmpty()) {
            throw new IllegalArgumentException("Please upload the reply letter file.");
        }
        List<Path> storedFiles = new ArrayList<>();
        try {
            String filePath = storeFile(replyFile, storedFiles);
            int updated = jdbc.update("UPDATE letters SET reply_letter_my_no=?, reply_date=?, "
                            + "reply_file_path=?, mod_by=? WHERE id=? AND reply_required=TRUE "
                            + "AND reply_file_path IS NULL",
                    replyLetterMyNo.trim(), blankToNull(replyDate), filePath,
                    auditorAware.getCurrentAuditor().orElse(null), letterId);
            if (updated == 0) {
                throw new IllegalArgumentException("This letter does not need a reply or its reply has already been saved.");
            }
        } catch (RuntimeException | IOException e) {
            for (Path file : storedFiles) {
                try {
                    Files.deleteIfExists(file);
                } catch (IOException ignored) {
                    // Preserve the original save error.
                }
            }
            throw e;
        }
    }

    private String storeFile(MultipartFile file, List<Path> storedFiles) throws IOException {
        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null) {
            int dot = originalName.lastIndexOf('.');
            if (dot >= 0 && dot < originalName.length() - 1) {
                String candidate = originalName.substring(dot).toLowerCase();
                if (candidate.matches("\\.[a-z0-9]{1,10}")) {
                    extension = candidate;
                }
            }
        }
        if (!List.of(".pdf", ".doc", ".docx", ".jpg", ".jpeg", ".png").contains(extension)) {
            throw new IOException("Upload a PDF, Word document, JPG, or PNG file.");
        }
        Files.createDirectories(uploadRoot);
        String storedName = UUID.randomUUID() + extension;
        Path target = uploadRoot.resolve(storedName).normalize();
        if (!target.startsWith(uploadRoot)) {
            throw new IOException("Invalid upload path.");
        }
        file.transferTo(target);
        storedFiles.add(target);
        return uploadRoot.relativize(target).toString().replace('\\', '/');
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
