# ContentBase CMS: API Specifications & Examples

## 1. New Public APIs

### 1.1 Get Article by Slug
```
GET /api/public/articles/slug/{slug}
Content-Type: application/json

Response (200 OK):
{
  "id": "uuid",
  "articleId": "article-id-1",
  "title": "Article Title",
  "slug": "article-title",
  "summary": "Brief summary",
  "content": "Full HTML content",
  "authorId": "author-id",
  "avatarFileId": "file-id",
  "statusCode": "PUBLISHED",
  "publishedAt": "2025-11-30T10:00:00"
}
```

### 1.2 Get All Categories
```
GET /api/public/categories
Content-Type: application/json

Response (200 OK):
[
  {
    "id": "uuid-1",
    "categoryCode": "TECH",
    "categoryName": "Technology",
    "description": "Technology related articles"
  },
  {
    "id": "uuid-2",
    "categoryCode": "SPORTS",
    "categoryName": "Sports",
    "description": "Sports news and updates"
  }
]
```

### 1.3 Create Comment
```
POST /api/public/articles/{articleId}/comments
Content-Type: application/json
Authorization: Bearer {token}

Request:
{
  "content": "This is a great article!",
  "articleId": "article-id-1"
}

Response (200 OK):
{
  "id": "comment-uuid",
  "articleId": "article-id-1",
  "parentsCommentId": null,
  "username": "user@example.com",
  "content": "This is a great article!",
  "createdAt": "2025-11-30T10:30:00",
  "updatedAt": "2025-11-30T10:30:00"
}
```

### 1.4 Toggle Article Like
```
POST /api/public/articles/{articleId}/like
Content-Type: application/json
Authorization: Bearer {token}

Response (200 OK):
{
  "articleId": "article-id-1",
  "isLiked": true,
  "likeCount": 42
}

// Or if unlike:
{
  "articleId": "article-id-1",
  "isLiked": false,
  "likeCount": 41
}
```

---

## 2. New Admin APIs

### 2.1 Update Article Status
```
PUT /api/admin/articles/{articleId}/status
Content-Type: application/json
Authorization: Bearer {admin-token}
X-Authorization-Role: ADMIN

Request:
{
  "status": "PUBLISHED"
}

Valid Status Values:
- "DRAFT" - Article in draft mode
- "PENDING" - Awaiting review/approval
- "PUBLISHED" - Published and visible
- "HIDDEN" - Hidden from public view

Response (200 OK):
{
  "id": "uuid",
  "articleId": "article-id-1",
  "title": "Article Title",
  "slug": "article-title",
  "summary": "Brief summary",
  "content": "Full HTML content",
  "authorId": "author-id",
  "statusCode": "PUBLISHED",
  "publishedAt": "2025-11-30T10:30:00"
}

Error Responses:
- 404 Not Found: Article not found
- 400 Bad Request: Invalid status value
- 403 Forbidden: User doesn't have ADMIN role
- 500 Internal Server Error: Database error
```

---

## 3. Request/Response Models

### CategoryResponse
```json
{
  "id": "string (UUID)",
  "categoryCode": "string",
  "categoryName": "string",
  "description": "string"
}
```

### CommentResponse
```json
{
  "id": "string (UUID)",
  "articleId": "string (UUID)",
  "parentsCommentId": "string (UUID, nullable)",
  "username": "string",
  "content": "string",
  "createdAt": "ISO 8601 DateTime",
  "updatedAt": "ISO 8601 DateTime"
}
```

### ArticleLikeResponse
```json
{
  "articleId": "string (UUID)",
  "isLiked": "boolean",
  "likeCount": "number (long)"
}
```

### UpdateArticleStatusRequest
```json
{
  "status": "string (DRAFT|PENDING|PUBLISHED|HIDDEN)"
}
```

---

## 4. Error Handling

### Standard Error Response
```json
{
  "error": "ResourceNotFoundException",
  "message": "Article Not Found: article-id-123",
  "status": 404,
  "timestamp": "2025-11-30T10:30:00"
}
```

### Validation Error Response
```json
{
  "error": "MethodArgumentNotValidException",
  "message": "Validation failed",
  "fieldErrors": [
    {
      "field": "status",
      "message": "must not be blank"
    }
  ],
  "status": 400,
  "timestamp": "2025-11-30T10:30:00"
}
```

---

## 5. Security & Authentication

### Required Headers for Protected Endpoints
```
Authorization: Bearer {jwt_token}
Content-Type: application/json
```

### Role-Based Access Control (RBAC)

| Endpoint | Required Role | Public |
|----------|---------------|--------|
| GET /api/public/* | None | ✅ Yes |
| POST /api/public/articles/{id}/comments | None | ✅ Yes |
| POST /api/public/articles/{id}/like | None | ✅ Yes |
| PUT /api/admin/articles/{id}/status | ADMIN | ❌ No |
| POST /api/admin/articles | ADMIN | ❌ No |
| DELETE /api/admin/articles/{id} | ADMIN | ❌ No |

---

## 6. Redis Caching Strategy

### Like Prevention Cache
```
Key: article_likes:{articleId}:{username}
Value: 1 (boolean flag)
TTL: 24 hours (86400 seconds)
Purpose: Prevent duplicate likes from same user
```

### View Count Cache
```
Key: article_views:{articleId}
Value: count (long)
TTL: Synced to DB every 5 minutes
Purpose: Optimize view count tracking
```

---

## 7. Database Query Examples

### Find Article by Slug
```sql
SELECT c FROM Article c 
WHERE c.slug = ? AND c.statusCode = 'PUBLISHED'
```

### Get Articles by Category
```sql
SELECT c FROM Article c 
JOIN ArticleCategory ac ON c.articleId = ac.articleId 
WHERE ac.categoryCode = ? AND c.statusCode = 'PUBLISHED'
```

### Get Trending Articles
```sql
SELECT c FROM Article c 
WHERE c.statusCode = ? 
ORDER BY c.publishedAt DESC
```

---

## 8. Implementation Checklist for Testing

### Public API Tests
- [ ] Retrieve article by valid slug → Returns 200
- [ ] Retrieve article by invalid slug → Returns 404
- [ ] Get all categories → Returns list with 200
- [ ] Create comment without auth → Returns 200 (anonymous)
- [ ] Create comment with invalid article ID → Returns 404
- [ ] Toggle like first time → Returns isLiked: true
- [ ] Toggle like second time → Returns isLiked: false
- [ ] Like count increments/decrements correctly

### Admin API Tests
- [ ] Update status to PUBLISHED → Returns updated article
- [ ] Update status to DRAFT → Clears publishedAt
- [ ] Update with invalid status → Returns 400
- [ ] Update without ADMIN role → Returns 403
- [ ] Update non-existent article → Returns 404

---

## 9. Performance Notes

### Expected Response Times (SLA)
- GET /api/public/articles/slug/{slug}: < 200ms
- GET /api/public/categories: < 100ms
- POST /api/public/articles/{id}/comments: < 300ms
- POST /api/public/articles/{id}/like: < 150ms (with Redis cache)
- PUT /api/admin/articles/{id}/status: < 250ms

### Caching Strategy
- Categories: 1 hour (Redis)
- Articles: 30 minutes (Redis)
- Like status: Per-session (Redis key pattern)

---

## 10. Future API Enhancements (Planned)

### File Upload API
```
POST /api/v1/upload
multipart/form-data
- file: File (required)

Response:
{
  "fileId": "uuid",
  "fileName": "image.jpg",
  "fileSize": 204800,
  "fileUrl": "/files/uuid/image.jpg",
  "uploadedAt": "2025-11-30T10:30:00"
}
```

### Advanced Filtering
```
GET /api/v1/articles?
  category=TECH&
  author=author-id&
  status=PUBLISHED&
  startDate=2025-01-01&
  endDate=2025-12-31&
  page=0&
  size=20
```

---

**API Specification Version:** 1.0  
**Last Updated:** 2025-11-30  
**Status:** Ready for Testing
