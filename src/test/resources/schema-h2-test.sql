-- H2 Test Database Schema Initialization

-- Create schemas
CREATE SCHEMA IF NOT EXISTS public;
CREATE SCHEMA IF NOT EXISTS private;

-- Create revision info table for Hibernate Envers
CREATE TABLE IF NOT EXISTS private.revinfo (
    rev INTEGER IDENTITY PRIMARY KEY,
    revtstmp BIGINT NOT NULL
);

-- Create audit table for Category
CREATE TABLE IF NOT EXISTS private.category_aud (
    id UUID NOT NULL,
    category_code VARCHAR(255),
    category_name VARCHAR(255),
    description TEXT,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    rev INTEGER NOT NULL,
    revtype TINYINT,
    PRIMARY KEY (id, rev),
    FOREIGN KEY (rev) REFERENCES private.revinfo(rev)
);

-- Create audit table for Article
CREATE TABLE IF NOT EXISTS private.article_aud (
    id UUID NOT NULL,
    content TEXT,
    excerpt TEXT,
    slug VARCHAR(255),
    article_title VARCHAR(255),
    status VARCHAR(50),
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    category_id UUID,
    rev INTEGER NOT NULL,
    revtype TINYINT,
    PRIMARY KEY (id, rev),
    FOREIGN KEY (rev) REFERENCES private.revinfo(rev)
);

-- Create audit table for ResponseMessage
CREATE TABLE IF NOT EXISTS private.response_message_aud (
    id UUID NOT NULL,
    status_code VARCHAR(50),
    message VARCHAR(255),
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255),
    rev INTEGER NOT NULL,
    revtype TINYINT,
    PRIMARY KEY (id, rev),
    FOREIGN KEY (rev) REFERENCES private.revinfo(rev)
);

-- Create audit table for ArticleStatistic
CREATE TABLE IF NOT EXISTS private.article_statistic_aud (
    id UUID NOT NULL,
    number_of_views BIGINT,
    number_of_reviews BIGINT,
    number_of_favourites BIGINT,
    rev INTEGER NOT NULL,
    revtype TINYINT,
    PRIMARY KEY (id, rev),
    FOREIGN KEY (rev) REFERENCES private.revinfo(rev)
);
