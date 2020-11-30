package com.demoapp.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class User {
	@Id
	private String userId;
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	private String gender;
	private String email;
	private String phoneNumber;

}
