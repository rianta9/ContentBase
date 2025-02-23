package com.ryanlab.contentbase.service.general.content;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ryanlab.contentbase.core.exception.ResourceNotFoundException;
import com.ryanlab.contentbase.model.dto.response.content.ContentResponse;
import com.ryanlab.contentbase.model.entity.Content;
import com.ryanlab.contentbase.model.mapper.ContentMapper;
import com.ryanlab.contentbase.repository.jpa.ContentJpaRepository;
import com.ryanlab.contentbase.service.cached.RedisService;
import com.ryanlab.contentbase.service.general.contentstatistic.UpdateContentStatisticService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetContentService {
  final ContentJpaRepository contentJpaService;
  final ContentMapper contentMapper;
  final RedisService redisService;
  final UpdateContentStatisticService updateContentStatisticService;
  static int TOP_CONTENTS_SIZE = 10;
  static final String TOP_CONTENTS_CACHE_KEY_PREFIX = "top_contents:";

  /**
   * Retrieves a content by its ID.
   *
   * @param contentId the ID of the content to retrieve
   * @return the content response
   * @throws ResourceNotFoundException if the content is not found
   */
  public ContentResponse getContent(String contentId) {
    ContentResponse result = contentJpaService.findById(contentId)
                            .map(contentMapper::toContentResponse)
                            .orElseThrow(
                              () -> new ResourceNotFoundException(
                                String.format(
                                  "Content Not Found: %s",
                                  contentId),
                                new Object[] { "Content" },
                                new Object[] { String.format(
                                  "ContentId = %s",
                                  contentId) }));
    updateContentStatisticService.incrementViewCount(contentId);
    return result;
  }

  /**
   * Retrieves a page of contents based on the provided parameters.
   *
   * @param page     the page number
   * @param size     the page size
   * @param category the category to filter by
   * @param sort     the sort criteria (e.g., "field,asc")
   * @return the page of content responses
   */
  public Page<ContentResponse> getContents(int page, int size, String category,
    String sort) {
    if (page < 0 || size < 1) {
      throw new IllegalArgumentException(
        String.format("Invalid page or size: page: %d, size: %d", page, size));
    }
    Sort sortObject = parseSortCriteria(sort);
    Pageable pageable = PageRequest.of(page, size, sortObject);
    Page<Content> contentsPage =
      contentJpaService.getPublicContents(category, pageable);
    return contentsPage.map(contentMapper::toContentResponse);
  }

  /**
   * Parses the sort criteria from the provided string.
   *
   * @param sort the sort criteria (e.g., "field,asc")
   * @return the parsed sort object
   */
  private Sort parseSortCriteria(String sort) {
    String[] parts = sort.split(",");
    if (parts.length != 2) {
      throw new IllegalArgumentException(
        String.format(
          "Invalid sort criteria: value: %s, size: %d, valid size: %d",
          sort,
          parts.length,
          2));
    }
    return Sort.by(Sort.Direction.valueOf(parts[1].toUpperCase()), parts[0]);
  }

  /**
   * Retrieves a page of contents based on the provided search keyword.
   *
   * @param keyword the search keyword
   * @param page    the page number
   * @param size    the page size
   * @return the page of content responses
   */
  public Page<ContentResponse> search(String keyword, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Content> contentsPage =
      contentJpaService.searchContents(keyword, pageable);
    List<ContentResponse> contentsList =
      contentsPage.getContent()
                  .stream()
                  .map(contentMapper::toContentResponse)
                  .collect(Collectors.toList());
    return new PageImpl<>(
      contentsList,
      pageable,
      contentsPage.getTotalElements());
  }

  /**
   * Retrieves the top contents based on the provided category and time
   * criteria.
   * 
   * @param category the category to filter by
   * @param time     the time criteria (e.g., "day", "week", "month")
   * @return the list of content responses
   */
  public List<ContentResponse> getTopContents(String category, String time) {
    String cacheKey = TOP_CONTENTS_CACHE_KEY_PREFIX + category + ":" + time;

    List<ContentResponse> cached = redisService.get(cacheKey);
    if (cached != null) {
      return cached;
    }
    List<Content> topContents =
      contentJpaService.getTopContent(category, time, TOP_CONTENTS_SIZE);
    List<ContentResponse> result =
      topContents.stream()
                 .map(item -> contentMapper.toContentResponse(item))
                 .collect(Collectors.toList());
    redisService.set(cacheKey, result, 3600);
    return result;
  }

}
