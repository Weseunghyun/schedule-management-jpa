package com.example.schedulemangejpa.author.service;

import com.example.schedulemangejpa.author.dto.AuthorResponseDto;
import com.example.schedulemangejpa.author.dto.SignUpResponseDto;
import com.example.schedulemangejpa.author.entity.Author;
import com.example.schedulemangejpa.author.repository.AuthorRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public SignUpResponseDto signUp(String authorName, String authorEmail, String password) {

        Author savedAuthor = authorRepository.save(new Author(authorName, authorEmail, password));

        return new SignUpResponseDto(
            savedAuthor.getId(),
            savedAuthor.getName(),
            savedAuthor.getEmail(),
            savedAuthor.getCreatedAt(),
            savedAuthor.getModifiedAt()
        );
    }

    public AuthorResponseDto findById(Long authorId) {
        Author findAuthor = authorRepository.findByIdOrElseThrow(authorId);
        return new AuthorResponseDto(
            findAuthor.getId(),
            findAuthor.getName(),
            findAuthor.getEmail(),
            findAuthor.getCreatedAt(),
            findAuthor.getModifiedAt()
        );
    }

    public void deleteAuthor(Long authorId, String password) {
        Author findAuthor = authorRepository.findByIdOrElseThrow(authorId);

        //id를 통해 찾은 작성자의 패스워드와 요청할때 받은 패스워드가 일치하다면 삭제 진행
        if (findAuthor.getPassword().equals(password)) {
            authorRepository.delete(findAuthor);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "password not match!");
        }

    }

    //로그인이 정상적으로 되면 session에 authorId를 저장하고 true 리턴, 로그인이 정상적으로 되지않으면 false 리턴
    public boolean login(String authorEmail, String password, HttpServletRequest request) {
        //이메일을 통해 로그인을 수행할 작성자 객체를 DB로 부터 찾는다.
        //객체가 존재하지 않는다면 null을 반환하도록 함.
        Author findAuthor = authorRepository.findAuthorByEmail(authorEmail).orElseThrow(null);

        //객체가 null이 아니면서 패스워드도 일치한다면 수행
        if (findAuthor != null && findAuthor.getPassword().equals(password)) {
            //현재 요청에 대한 세션을 가져온다. 만약 세션이 존재하지않으면 새로운 세션을 생성
            HttpSession session = request.getSession();
            //세션에 authorId 라는 이름으로 작성자의 id를 저장한다. 이렇게 저장하고 나면 이후 요청에서도 세션을 통해
            //authorId에 접근을 할 수 있게된다.
            session.setAttribute("authorId", findAuthor.getId());

            return true;
        }

        return false;
    }

    public void logout(HttpServletRequest request) {
        //현재 세션을 가져온다. false 이므로 새로운 세션을 생성하지 않고 null을 반환한다.
        HttpSession session = request.getSession(false);

        //세션이 존재하면다면 invalidate로 세션을 무효화하여 서버측의 로그인 정보를 삭제한다.
        if (session != null) {
            session.invalidate();
        }
    }
}
