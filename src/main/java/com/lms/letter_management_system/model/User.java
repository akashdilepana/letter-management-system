package com.lms.letter_management_system.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor 
@NoArgsConstructor 
@Setter 
@Getter 
@ToString 
@Table("users") 
public class User {
  @Id
  private Integer id;
  private String username;
  private String full_name;
  private String email;
  private AggregateReference<UserType, Integer> userType;
  @Transient
  private String userTypeName;
  private String status;
}
