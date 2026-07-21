
-- the script i used to run the postgres container

--docker run -d \
--  --name sbscorp-postgres \
--  -e POSTGRES_USER=abdellah \
--  -e POSTGRES_PASSWORD=abdellah \
--  -e POSTGRES_DB=sbscorp-knowledge \
--  -p 5432:5432 \
--  -d postgres


-- NexaCorp Internal Knowledge Database Schema




-- FAQs table
CREATE TABLE faqs (
    id SERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    department VARCHAR(50),
    visibility VARCHAR(20)
);

-- Release notes table
CREATE TABLE release_notes (
    id SERIAL PRIMARY KEY,
    version VARCHAR(20),
    summary TEXT,
    details TEXT,
    release_date DATE
);

-- Announcements (Email-style communications)
CREATE TABLE announcements (
    id SERIAL PRIMARY KEY,
    subject TEXT NOT NULL,
    body TEXT NOT NULL,
    category VARCHAR(50),
    effective_from DATE,
    effective_to DATE,
    source_type VARCHAR(20)
);
