package com.example.reservation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReviewRepositoryTest {

  @Autowired
  ReviewRepository reviewRepository;

  @Autowired
  OwnerRepository ownerRepository;

  @Autowired
  UserRepository userRepository;

//  @Test
//  @DisplayName("유저 1명이 다른 가게 2개의 리뷰 작성 -> 유저 측 조회")
//  void relationshipTest1() {
//    // given
//    User user1 = User.builder()
//      .username("goo")
//      .email("goo@naver.com")
//      .build();
//    userRepository.save(user1);
//
//    Owner owner1 = Owner.builder()
//      .busNumber("123")
//      .ownerName("사장님1")
//      .email("sj@naver.com")
//      .build();
//    Owner owner2 = Owner.builder()
//      .busNumber("1234")
//      .ownerName("사장님2")
//      .email("sj2@naver.com")
//      .build();
//    ownerRepository.save(owner1);
//    ownerRepository.save(owner2);
//
//    // when
//    Review review1 = Review.builder()
//      .title("1111제목입니다.")
//      .owner(owner1)
//      .user(user1)
//      .build();
//    Review review2 = Review.builder()
//      .title("2222제목입니다.")
//      .owner(owner2)
//      .user(user1)
//      .build();
//    reviewRepository.save(review1);
//    reviewRepository.save(review2);
//
//    // then
//    List<Review> reviews = userRepository.findById(user1.getId()).get().getReviews();
//    for (Review review : reviews) {
//      System.out.println("============" + review.getTitle() + "============");
//    }
//  }
//
//  @Test
//  @DisplayName("유저 2명이 한 가게의 리뷰 각각 작성 -> 점장 측 조회")
//  void relationshipTest2() {
//    // given
//    User user1 = User.builder()
//      .username("goo1")
//      .email("goo1@naver.com")
//      .build();
//    userRepository.save(user1);
//    User user2 = User.builder()
//      .username("goo2")
//      .email("goo2@naver.com")
//      .build();
//    userRepository.save(user1);
//    userRepository.save(user2);
//
//    Owner owner1 = Owner.builder()
//      .busNumber("123")
//      .ownerName("사장님1")
//      .email("sj@naver.com")
//      .build();
//    ownerRepository.save(owner1);
//
//    // when
//    Review review1 = Review.builder()
//      .title("1번 유저의 리뷰")
//      .owner(owner1)
//      .user(user1)
//      .build();
//    Review review2 = Review.builder()
//      .title("2번 유저의 리뷰")
//      .owner(owner1)
//      .user(user2)
//      .build();
//    reviewRepository.save(review1);
//    reviewRepository.save(review2);
//
//    // then
//    List<Review> reviews = ownerRepository.findById(owner1.getId()).get().getReviews();
//    for (Review review : reviews) {
//      System.out.println("============" + review.getTitle() + "============");
//    }
//  }


}