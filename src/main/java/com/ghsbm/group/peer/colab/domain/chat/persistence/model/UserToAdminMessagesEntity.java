package com.ghsbm.group.peer.colab.domain.chat.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_to_admin_messages")
public class UserToAdminMessagesEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Email
  @Size(min = 5, max = 254)
  @Column(name = "user_email")
  private String userEmail;

  private String content;

  private String subject;
}
