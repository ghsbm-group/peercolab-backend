package com.ghsbm.group.peer.colab.domain.chat.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
public class MessageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;

  @Column(name = "post_date")
  private ZonedDateTime postDate;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "messageboard_id")
  private Long messageboardId;
}
