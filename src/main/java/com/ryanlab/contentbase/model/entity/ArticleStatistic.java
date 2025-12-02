package com.ryanlab.contentbase.model.entity;

import java.time.LocalDateTime;

import org.springframework.lang.NonNull;

import com.ryanlab.contentbase.core.entity.CoreEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "article_statistic", schema = "private")
public class ArticleStatistic extends CoreEntity<ArticleStatistic> {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  @Column
  String articleId;
  @Column
  Double averagePoint;
  @Column
  Long numberOfReviews;
  @Column
  Long numberOfViews;
  @Column
  Long numberOfFollows;
  @Column
  Long numberOfFavourites;

  @Builder
  public ArticleStatistic(
    String articleId, Double averagePoint, Long numberOfReviews,
    Long numberOfViews, Long numberOfFollows, Long numberOfFavourites) {
    super();
    this.articleId = articleId;
    this.averagePoint = averagePoint;
    this.numberOfReviews = numberOfReviews;
    this.numberOfViews = numberOfViews;
    this.numberOfFollows = numberOfFollows;
    this.numberOfFavourites = numberOfFavourites;
  }

  @Builder
  public ArticleStatistic(
    @NonNull String id, String articleId, Double averagePoint,
    Long numberOfReviews, Long numberOfViews, Long numberOfFollows,
    Long numberOfFavourites, String createdBy, String updatedBy,
    LocalDateTime createdAt, LocalDateTime updatedAt) {
    super(id);
    this.articleId = articleId;
    this.averagePoint = averagePoint;
    this.numberOfReviews = numberOfReviews;
    this.numberOfViews = numberOfViews;
    this.numberOfFollows = numberOfFollows;
    this.numberOfFavourites = numberOfFavourites;
  }
}
