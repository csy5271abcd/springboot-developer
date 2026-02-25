package me.shinsunyoung.springbootdeveloper.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.shinsunyoung.springbootdeveloper.domain.Article;
import me.shinsunyoung.springbootdeveloper.dto.AddArticleRequest;
import me.shinsunyoung.springbootdeveloper.dto.UpdateArticleRequest;
import me.shinsunyoung.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
// 글 수정, 삭제 시 요청 헤더에 토큰 전달 -> 사용자 자신이 작성한 글인지 검증 가능
// 본인 글이 아닌데 수정, 삭제를 시도하는 경우 -> 예외 발생
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request,String username) {
        return blogRepository.save(request.toEntity(username));
    }

    // 블로그 글 전체 조회 메서드
    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    // 블로그 글 한 개 조회 메서드
    public Article findById(Long id){
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+id));
    }

    // 블로그 글 삭제 기능
    public void delete(Long id){
        Article article=blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional
    // 하나의 트랜잭션으로 묶음
    public Article update(Long id, UpdateArticleRequest request){
        Article article=blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(),request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Article article){
        String userName = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        if(!article.getAuthor().equals(userName)){
            throw new SecurityException("not authorized");
        }
    }
}


