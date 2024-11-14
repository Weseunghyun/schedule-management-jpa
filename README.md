## 목차
- [일정 관리 앱 API 명세서](#일정-관리-앱-api-명세서)
- [필수 구현 API 명세](#필수-구현-api-명세)
- [도전 구현 API 명세](#도전-구현-api-명세)
- [ERD](#ERD)

# 일정 관리 앱 API 명세서

## 참고 사항

1. 로그인 필터가 적용되어 있기에 작성자를 생성, 로그인 후 다른 호출이 가능함
    
     (signup, login, logout을 제외하고 전부 로그인 필터에 적용됨)
    
2. 일정을 작성하기 위해서는 작성자가 필요하고 댓글을 작성하기 위해서는 일정이 필요함.
    
    즉 생성 과정이 선행 돼야함. (Schedule 생성 전 Author 생성, Comment 생성 전 Schedule 생성)
    

## 필수 구현 API 명세

<details>
<summary>작성자 관련 API 명세</summary>
    
    ### 0. 작성자 회원가입 (POST /api/authors/signup)
    
<details>
<summary>상세 내용 펼치기</summary>
  
        - **요청**
            - **Headers**:
                - `Content-Type: application/json`
            - **Body**:
                
                ```json
                {
                    "authorName": "예시명",
                    "authorEmail": "example@example.com",
                    "password" : "1234qwer"
                }
                ```
                
                | # | 이름 | 타입 | 설명 | Nullable |
                | --- | --- | --- | --- | --- |
                | 1 | authorName | String | 작성자명 | X |
                | 2 | authorEmail | String | 작성자 이메일 | X |
                | 3 | password | String | 작성자 비밀번호 | X |
            - 예시
                
                ```
                http://localhost:8080/api/authors/signup
                ```
                
        - **응답**
            - **Status Code**: 201 Created
            - **Body**:
                
                ```json
                {
                    "authorId": 1,
                    "authorName": "예시명",
                    "authorEmail": "example@example.com",
                    "createdAt": "2024-11-13T14:12:27.2238587",
                    "modifiedAt": "2024-11-13T14:12:27.2238587"
                }
                ```
                
                | # | 이름 | 타입 | 설명 | Nullable |
                | --- | --- | --- | --- | --- |
                | 1 | id | Long | 작성자 고유 식별자 | X |
                | 2 | name | String | 작성자명 | X |
                | 3 | email | String | 작성자 이메일 | X |
                | 4 | createdAt | String | 작성자 최초 등록 일시 | X |
                | 5 | modifiedAt | String | 수정 일시 | X |
        
        - 발생할 수 있는 예외 (@Valid 사용)
        1. 작성자명이 없거나 1~5자 이내가 아닌 경우
        2. 이메일이 없거나 정규식에 해당하는 양식이 아닌 경우
        3. 비밀번호가 없는 경우
        
  </details>
    
    ---
    
    ### 1. 작성자 조회  (GET /api/authors/{authorId})
    
<details>
<summary>상세 내용 펼치기</summary>
     
        - **요청**
            - **Path Variable**: `authorId`- 조회할 작성자의 고유 ID
            - 예시
            
            ```
            http://localhost:8080/api/authors/1
            ```
            
        - **응답**
            - **Status Code**: 200 OK
            - **Body**:
                
                ```json
                {
                    "id": 1,
                    "authorName": "예시 유저명",
                    "authorEmail": "example@example.com",
                    "createdAt": "2024-11-13T14:12:27.223859",
                    "modifiedAt": "2024-11-13T14:12:27.223859"
                }
                ```
                
                | # | 이름 | 타입 | 설명 | Nullable |
                | --- | --- | --- | --- | --- |
                | 1 | id | Long | 일정 고유 식별자 | X |
                | 2 | authorName | String | 작성자명 | X |
                | 3 | authorEmail | String | 작성자 이메일 | X |
                | 4 | createdAt | String | 최초 작성 일시 | X |
                | 5 | updatedAt | String | 수정 일시 | X |
        
        - 발생할 수 있는 에러
            1. 존재하지 않는 작성자 id를 요청했을 때 404 NOT FOUND 에러

  </details>
  
    ---
    
    ### 2. 작성자 삭제 (DELETE /api/authors/{authorId})
    
  <details>
<summary>상세 내용 펼치기</summary>
    
        - **요청**
            - **Headers**:
                - `Content-Type: application/json`
            - **Path Variable**: `id` - 삭제할 일정의 고유 ID
            - 예시
            
            ```
            http://localhost:8080/api/authors/1
            ```
            
            | # | 이름 | 타입 | 설명 | Nullable |
            | --- | --- | --- | --- | --- |
            | 1 | id  | Long  | 일정 고유 식별자 | X |
            - Body:
            
            ```json
            {
                "password" : "예시 패스워드"
            }
            ```
            
            | # | 이름 | 타입 | 설명 | Nullabel |
            | --- | --- | --- | --- | --- |
            | 1 | password | String | 작성자 패스워드 | X |
        
        - **응답**
            - **Status Code**: 200 OK
            - **Body**: 없음
        
        - 발생할 수 있는 에러
        1. 비밀번호 검증에 실패했을 때 403 Forbidden 에러
        2. 존재하지 않는 작성자를 삭제 시도했을 때 404 Not Found 에러
    
  </details>
    
    ---
    
    ### 3. 작성자 로그인 (POST /api/authors/login)
    
<details>
<summary>상세 내용 펼치기</summary>
  
        - **요청**
            - **HttpServletRequest** 를 사용해 요청 헤더, 요청 본문, 요청 메서드, 요청 URI 등 다양한 정보를 접근할 수 있도록 함
            - **Headers**:
                - `Content-Type: application/json`
            
            - 예시
            
            ```
            http://localhost:8080/api/authors/login
            ```
            
            - Body:
            
            ```json
            {
                "authorEmail": "example@example.com",
                "password" : "1234"
            }
            ```
            
            | # | 이름 | 타입 | 설명 | Nullable |
            | --- | --- | --- | --- | --- |
            | 1 | authorEmail | String | 작성자 이메일 | X |
            | 2 | password | String | 작성자 패스워드 | X |
        - **응답**
            - **Status Code**: 200 OK
            - **Body**: 없음
        
        - 발생할 수 있는 예외
        1. 작성자의 이메일에 해당하는 비밀번호가 맞지 않은 경우 401 리턴

  </details>
  
    ---
    
    ### 4. 작성자 로그아웃 (POST /api/authors/logout)
    
<details>
<summary>상세 내용 펼치기</summary>
  
        - **요청**
            - **HttpServletRequest** 를 사용해 요청 헤더, 요청 본문, 요청 메서드, 요청 URI 등 다양한 정보를 접근할 수 있도록 함
            - 예시
            
            ```
            http://localhost:8080/api/authors/logout
            ```
            
        - **응답**
            - **Status Code**: 200 OK
            - **Body**: 없음
            
</details>

</details>

---

<details>
<summary>일정 관련 API 명세</summary>
    
    ### 1. 일정 생성 (POST /api/schedules/{authorId}
    
<details>
<summary>상세 내용 펼치기</summary>
  
        - **요청 Request**
            - **Headers**:
                - `Content-Type: application/json`
            - **PathVariable:**
                - `authorId` - 작성자의 고유 식별자
            
            - 예시
            
            ```
            http://localhost:8080/api/schedules/1
            ```
            
            - **Body**:
                
                ```json
                {
                    "title": "할일 제목",
                    "content" : "할일 내용",
                }
                ```
                
                | # | 이름 | 타입 | 설명 | Nullable |
                | --- | --- | --- | --- | --- |
                | 1 | authorId | Long | 작성자 Id | x |
                | 2 | title | String | 할 일 제목 | x |
                | 3 | content | String | 할 일 내용 | x |
            
        - **응답 Response**
            - **Status Code**: 201 Created
            - **Body:**
                
                ```json
                {
                    "id": 1,
                    "title": "할일 제목",
                    "content": "할일 내용",
                    "authorId": 1,
                    "createdAt": "2024-11-13T15:19:32.522723",
                    "updatedAt": "2024-11-13T15:19:32.522723"
                }
                ```
                
            
            | # | 이름 | 타입 | 설명 | Nullable |
            | --- | --- | --- | --- | --- |
            | 1 | id | Long | 일정 고유 식별자 | X |
            | 2 | task | String | 할 일 내용 | X |
            | 3 | authorId | Long | 작성자 고유 식별자 | X |
            | 4 | createdAt | String | 최초 작성 일시 | X |
            | 5 | updatedAt | String | 수정 일시 | X |
        
        - 발생할 수 있는 에러
            1. 존재하지 않는 authorId 를 PathVariable로 넣었을 때 404 Not Found 에러
            2. 제목이나 본문 내용을 넣지 않았을 때 (@Valid 사용)
            3. 제목이 3자 이상 20자 이내가 아닐 때 (@Valid 사용)
        
   </details>
   
    ---
    
    ### 2. 전체 일정 조회 (GET /api/schedules)
    
<details>
<summary>상세 내용 펼치기</summary>
  
        - **요청**
            - 요청 예시
        
        ```
        http://localhost:8080/api/schedules
        ```
        
        - **응답**
            - **Status Code**: 200 OK
            - **Body**: (쿼리 파라미터 없이 GET 요청 보낸 경우)
                
                ```json
                [
                    {
                        "id": 1,
                        "authorId": 1,
                        "title": "할일 제목",
                        "content": "할일 내용",
                        "createdAt": "2024-11-13T15:47:15.465222",
                        "updatedAt": "2024-11-13T15:47:15.465222"
                    },
                    {
                        "id": 2,
                        "authorId": 1,
                        "title": "할일 제목2",
                        "content": "할일 내용2",
                        "createdAt": "2024-11-13T15:47:19.496261",
                        "updatedAt": "2024-11-13T15:47:19.496261"
                    },
                    {
                        "id": 3,
                        "authorId": 2,
                        "title": "할일 제목33",
                        "content": "할일 내용233",
                        "createdAt": "2024-11-13T15:47:51.541281",
                        "updatedAt": "2024-11-13T15:47:51.541281"
                    }
                ]
                ```
                
                | # | 이름 | 타입 | 설명 | Nullable |
                | --- | --- | --- | --- | --- |
                | 1 | id | Long | 일정 고유 식별자 | X |
                | 2 | authorId | String | 작성자 고유 식별자 | X |
                | 3 | title | String | 할 일 제목 | X |
                | 4 | content | String | 할 일 내용 |  |
                | 5 | createdAt | String | 작성 일시 | X |
                | 6 | updatedAt | String | 수정 일시 | X |
        
  </detials>
  
    ---
    
    ### 3. 특정 일정 조회 (GET /api/schedules/{scheduleId}
    
<details>
<summary>상세 내용 펼치기</summary>
  
        - **요청**
            - **Path Variable**: `schedulesId` - 조회할 일정의 고유 ID
            - 예시
            
            ```
            http://localhost:8080/api/schedules/1
            ```
            
        - **응답**
            - **Status Code**: 200 OK
            - **Body**:
                
                ```json
                {
                    "id": 1,
                    "authorId": 1,
                    "title": "할일 제목",
                    "content": "할일 내용",
                    "createdAt": "2024-11-13T15:54:30.370204",
                    "updatedAt": "2024-11-13T15:54:30.370204"
                }
                ```
                
                | # | 이름 | 타입 | 설명 | Nullable |
                | --- | --- | --- | --- | --- |
                | 1 | id | Long | 일정 고유 식별자 | X |
                | 2 | authorId | Long | 작성자 고유 식별자 | X |
                | 3 | title | String | 할 일 제목 | X |
                | 4 | content | String | 할 일 내용 | X |
                | 5 | createdAt | String | 최초 작성 일시 | X |
                | 6 | updatedAt | String | 수정 일시 | X |
        
        - 발생할 수 있는 에러
        1. 존재하지 않는 scheduleId를 요청할 때 404 Not Found 에러

  </details>
  
    ---
    
    ### 4. 일정 수정 (PUT /api/schedules/{schedulesId})
    
  <details>
<summary>상세 내용 펼치기</summary>
    
        - **요청**
            - **Headers**:
                - `Content-Type: application/json`
            - **Path Variable**: `schedulesId` - 수정할 일정의 고유 ID
            - **Body**:
                
                ```json
                {
                    "title": "수정된 할일 제목",
                    "content" : "수정된 할일 내용",
                    "password" : "수정할 게시글의 작성자 패스워드"
                }
                ```
                
                | # | 이름 | 타입 | 설명 | Nullable |
                | --- | --- | --- | --- | --- |
                | 1 | title | String | 수정된 할일 제목 | X |
                | 2 | content | String | 수정된 할일 내용 | X |
                | 3 | password | String | 패스워드 | X |
        - **응답**
            - **Status Code**: 200 OK
            - **Body**:
                
                ```json
                {
                    "id": 1,
                    "authorId": 1,
                    "title": "수정된 할일 제목",
                    "content" : "수정된 할일 내용",
                    "createdAt": "2024-11-01T05:36:49.000+00:00",
                    "updatedAt": "2024-11-02T07:37:31.000+00:00"
                }
                ```
                
            
        - 발생할 수 있는 예외처리
            1. 제목, 본문 내용, 패스워드 값을 넣지 않았을 때 (@Valid 사용)
            
            1. 제목이 3자이상 20자 이내가 아닐 때 (@Valid 사용)
            
            1. 존재하지 않는 scheduleId를 요청할 때 404 Not Found 에러
            
            1. 패스워드가 일치하지 않을 때 403 Forbidden 에러

  </details>
  
    ---
    
    ### 5. 일정 삭제 (DELETE /api/schedules/{schedulesId})
    
   <details>
<summary>상세 내용 펼치기</summary>
     
        - **요청**
            - **Path Variable**: `schedulesId`- 삭제할 일정의 고유 ID
            
            | # | 이름 | 타입 | 설명 | Nullable |
            | --- | --- | --- | --- | --- |
            | 1 | id  | Long  | 일정 고유 식별자 | X |
            - 예시
            
            ```
            http://localhost:8080/api/schedules/1
            ```
            
            - Body:
            
            ```json
            {
                "password" : "예시 패스워드"
            }
            ```
            
            | # | 이름 | 타입 | 설명 | Nullabel |
            | --- | --- | --- | --- | --- |
            | 1 | password | String | 작성자 패스워드 | X |
        - **응답**
            - **Status Code**: 200 OK
            - **Body**: 없음
        
        - 발생할 수 있는 에러
            1. 존재하지 않는 schedulesId를 요청할 때 404 Not Found 에러

</details>

</details>

---

## 도전 구현 API 명세

기본적인 댓글 조회 기능은 보통 우리가 사용하는 어플리케이션을 사용할 때

댓글 조회는 특정 게시글에 달린 모든 댓글을 쉽게 확인할 수 있게 하는 것이라고 생각했기에

특정 게시글의 모든 댓글을 조회하는 방식으로 구현

<details>
<summary>댓글 관련 api 명세 </summary>
    
    ### 1. 댓글 생성 (POST /api/comments)
    
  <details>
<summary>상세 내용 펼치기</summary>
    
        - **요청**
            - **Headers**:
                - `Content-Type: application/json`
            - **Body**:
            
            ```json
            {
            		"scheduleId": 1,
            		"authorId" : 1,
                "content": "댓글 내용"
            }
            ```
            
            | # | 이름 | 타입 | 설명 | Nullabel |
            | --- | --- | --- | --- | --- |
            | 1 | scheduleId | Long | 댓글을 작성할
            게시글 ID | X |
            | 2 | authorId | Long | 댓글을 다는 작성자의 ID | X |
            | 3 | content | String | 댓글 내용 | X |
            - url 예시
            
            ```
            http://localhost:8080/api/comments
            ```
            
        
        - **응답**
            - **Status Code**: `201 Created`
            - Body:
            
            ```json
            {
                "commentId": 1,
                "scheduleId": 1,
                "authorId": 1,
                "content": "댓글 내용",
                "createdAt": "2024-11-14T10:38:50.0108753",
                "updatedAt": "2024-11-14T10:38:50.0108753"
            }
            ```
            
            | # | 이름 | 타입 | 설명 | Nullabel |
            | --- | --- | --- | --- | --- |
            | 1 | commentId | Long | 댓글 고유 식별자 | X |
            | 2 | scheduleId | Long | 댓글을 작성할
            게시글 ID | X |
            | 3 | authorId | Long | 댓글을 작성하는 작성자 ID | X |
            | 4 | content | String | 댓글 내용 | X |
            | 5 | createdAt | String | 작성일 | X |
            | 6 | updatedAt | String | 수정일 | X |
        - 발생할 수 있는 에러
            1. 존재하지 않는 authorId, schedulesId를 요청할 때 404 Not Found 에러

  </details>
  
    ---
    
    ### 2. 특정 일정 모든 댓글 조회 (GET /api/schedules/{scheduleId}/comments)
    
  <details>
<summary>상세 내용 펼치기</summary>
    
        - **요청**
            - **Headers**: 없음
            - **Path Variable**: `scheduleId` - 댓글을 작성할 일정의 고유 ID
            - **Body**: 없음
            - 요청 예시
            
            ```
            http://localhost:8080/api/schedules/1/comments
            ```
            
        - **응답**
            - **Status Code**: `200 OK`
            - Body:
            
            ```json
            [
                {
                    "commentId": 1,
                    "scheduleId": 1,
                    "authorId": 1,
                    "content": "재밌네요 짞짞짞",
                    "createdAt": "2024-11-14T11:13:07.292872",
                    "updatedAt": "2024-11-14T11:13:07.292872"
                },
                {
                    "commentId": 2,
                    "scheduleId": 1,
                    "authorId": 1,
                    "content": "맛집이에요!",
                    "createdAt": "2024-11-14T11:13:24.826982",
                    "updatedAt": "2024-11-14T11:13:24.826982"
                }
            ]
            ```
            
            | # | 이름 | 타입 | 설명 | Nullabel |
            | --- | --- | --- | --- | --- |
            | 1 | commentId | Long | 댓글 고유 식별자 | X |
            | 2 | scheduleId | Long | 댓글을 작성할게시글 ID | X |
            | 3 | authorId | Long | 댓글을 작성하는 작성자 ID | X |
            | 4 | content | String | 댓글 내용 | X |
            | 5 | createdAt | String | 작성일 | X |
            | 6 | updatedAt | String | 수정일 | X |
        - 발생할 수 있는 에러
            1. 존재하지 않는 scheduleId를 요청할 때 404 Not Found 에러

  </details>
  
    ---
    
    ### 3. 댓글 수정 (PUT /api/comments/{commentId}
    
   <details>
<summary>상세 내용 펼치기</summary>
     
        - **요청**
            - **Headers**: 없음
            - **Path Variable**: `commentId`- 댓글을 작성할 일정의 고유 ID
            - 예시
            
            ```
            http://localhost:8080/api/comments/1
            ```
            
            - **Body**:
            
            ```json
            {
                "content": "수정된 댓글 내용 테스트!!",
                "password": "1234qwer"
            }
            ```
            
        - **응답**
            - **Status Code**: `200 OK`
            - Body:
            
            ```json
            {
                "commentId": 1,
                "scheduleId": 1,
                "authorId": 1,
                "content": "수정된 댓글 내용 테스트!!",
                "createdAt": "2024-11-14T11:13:07.292872",
                "updatedAt": "2024-11-14T11:14:49.7296013"
            }
            ```
            
            - 생성, 조회와 반환 타입이 같아서 표는 생략
            
        - 발생할 수 있는 에러
        1. content가 없이 요청할 때 (@Valid 사용)
        2. password가 일치하지 않을 때  403 Forbidden 에러
        3. 존재하지 않는 commentId를 요청할 때 404 Not Found 에러
    
    ---
    
    ### 4. 댓글 삭제 (DELETE /api/comments/{commentId})
    
    - 상세 내용 펼쳐보기
        - **요청**
            - **Headers**: 없음
            - **Path Variable**: `commentId`- 삭제할 댓글의 ID
            - 예시
            
            ```
            http://localhost:8080/api/comments/1
            ```
            
            - **Body**:
            
            ```json
            {
              "password": "작성자 비밀번호"
            }
            ```
            
        - **응답**
            - **Status Code**: `200 OK`
        
        - 발생할 수 있는 에러
            1. 존재하지 않는 commentId를 요청할 때 404 Not Found 에러
            2. 비밀번호가 일치하지 않을 때 403 Forbidden 에러

</details>

</details>

---

## ERD

- 필수구현 ERD
![image (3)](https://github.com/user-attachments/assets/da56907d-6879-472a-afe0-850c4b25fc75)


- 도전구현 ERD
![image (4)](https://github.com/user-attachments/assets/5ed88f1a-ad4c-4e1e-af8f-880804a8d493)
