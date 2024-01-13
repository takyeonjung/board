package board.bbs.bbs.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code") // 외래키를 설정
    private User user;

    @OneToMany(mappedBy = "board" , fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)

    @OrderBy("createdDate asc") // 댓글 정렬
    private List<Comment> boardCommentList = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
        user.getBoardList().add(this);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }



}