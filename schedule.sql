
-- 작성자(author) 테이블 생성
CREATE TABLE author (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     email VARCHAR(255) UNIQUE NOT NULL,
     password VARCHAR(255) NOT NULL,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 스케줄(schedule) 테이블 생성
CREATE TABLE schedule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    author_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES author(id)
);

-- 댓글(comment) 테이블 생성
CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    schedule_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (schedule_id) REFERENCES schedule(id),
    FOREIGN KEY (author_id) REFERENCES author(id)
);




-- 스케줄 CRUD 관련 SQL 문
-- 스케줄 생성

INSERT INTO schedule (title, content, author_id)
VALUES ('스케줄 제목', '스케줄 내용', ?);


--모든 스케줄 조회

SELECT * FROM schedule;


-- 특정 스케줄 ID로 조회

SELECT * FROM schedule WHERE id = ?;


-- 스케줄 수정

UPDATE schedule s
JOIN author a ON s.author_id = a.id
SET s.title = ?, s.content = ?
WHERE s.id = ? AND a.password = ?;

-- 스케줄 삭제

DELETE s FROM schedule s
JOIN author a ON s.author_id = a.id
WHERE s.id = ? AND a.password = ?;

-- 스케줄 페이지네이션

SELECT
    s.id,
    s.title,
    s.content,
    a.name AS author_name,
    (SELECT COUNT(*) FROM comment c WHERE c.schedule_id = s.id) AS comment_count,
    s.created_at,
    s.updated_at
FROM
    schedule s
JOIN
    author a ON s.author_id = a.id
ORDER BY
    s.updated_at DESC
    LIMIT 10 OFFSET 0;

---

-- 작성자(Author) 관련 SQL문
-- 회원가입 (작성자 생성)

INSERT INTO author (name, email, password)
VALUES ('작성자 이름', '작성자 이메일', '비밀번호');


-- 특정 작성자 ID로 조회

SELECT * FROM author WHERE id = ?;


-- 작성자 삭제 (비밀번호 확인 후 삭제)

DELETE FROM author WHERE id = 작성자ID AND password = '비밀번호';


-- 로그인 시 이메일과 비밀번호 확인

SELECT * FROM author WHERE email = '작성자 이메일' AND password = '비밀번호';




-- 스케줄 CRUD 관련 SQL 문
-- 댓글 생성

INSERT INTO comment (content, schedule_id, author_id)
VALUES ('댓글 내용', ?, ?);


-- 특정 스케줄 ID의 모든 댓글 조회

SELECT * FROM comment WHERE schedule_id = ?;


-- 댓글 수정

UPDATE comment c
JOIN author a ON c.author_id = a.id
SET c.content = ?
WHERE c.id = ? AND a.password = ?;


-- 댓글 삭제 (비밀번호 확인 후 삭제)

DELETE c FROM comment c
JOIN author a ON c.author_id = a.id
WHERE c.id = ? AND a.password = ?;




