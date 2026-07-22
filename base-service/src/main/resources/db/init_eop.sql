CREATE TABLE IF NOT EXISTS cor_users (
    id TEXT NOT NULL DEFAULT uuid_generate_v4(),
    uid VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    last_login_info TIMESTAMPTZ,
    created_by VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_by VARCHAR(50) NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    deleted_at TIMESTAMPTZ,
    "version" INT8 NOT NULL DEFAULT 0,
    CONSTRAINT cor_users_pk PRIMARY KEY (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS cor_users_un ON cor_users (uid)
    WHERE deleted_at IS NULL;

CREATE TABLE IF NOT EXISTS cor_user_persons (
    id TEXT NOT NULL DEFAULT uuid_generate_v4(),
    user_id TEXT NOT NULL,
    nik VARCHAR(50) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    mobile_phone VARCHAR(20) NOT NULL,
    email VARCHAR(50),
    place_of_birth VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    marital_status VARCHAR(50) NOT NULL,
    parent_id TEXT,
    created_by VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_by VARCHAR(50) NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    deleted_at TIMESTAMPTZ,
    "version" INT8 NOT NULL DEFAULT 0,
    CONSTRAINT cor_user_persons_pk PRIMARY KEY (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS cor_user_persons_un ON cor_user_persons (nik)
    WHERE deleted_at IS NULL;

ALTER TABLE cor_user_persons
    DROP CONSTRAINT IF EXISTS fk_cor_user_persons_user;

ALTER TABLE cor_user_persons
    ADD CONSTRAINT fk_cor_user_persons_user
        FOREIGN KEY (user_id)
            REFERENCES cor_users(id);

ALTER TABLE cor_user_persons
    DROP CONSTRAINT IF EXISTS fk_cor_user_persons_parent;

ALTER TABLE cor_user_persons
    ADD CONSTRAINT fk_cor_user_persons_parent
        FOREIGN KEY (parent_id)
            REFERENCES cor_user_persons(id);
            

CREATE TABLE IF NOT EXISTS cor_liturgy_groups (
    id TEXT NOT NULL DEFAULT uuid_generate_v4(),
    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_by VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_by VARCHAR(50) NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    deleted_at TIMESTAMPTZ,
    "version" INT8 NOT NULL DEFAULT 0,
    CONSTRAINT cor_liturgy_groups_pk PRIMARY KEY(id)
);

CREATE UNIQUE INDEX IF NOT EXISTS cor_liturgy_groups_un ON cor_liturgy_groups (code)
    WHERE deleted_at IS NULL;

CREATE TABLE IF NOT EXISTS cor_liturgy_sequences (
     id TEXT NOT NULL DEFAULT uuid_generate_v4(),
     liturgy_group_id TEXT NOT NULL,
     sequence_number INT NOT NULL,
     title VARCHAR(255) NOT NULL,
     description TEXT,
     created_by VARCHAR(50) NOT NULL,
     created_at TIMESTAMPTZ NOT NULL,
     updated_by VARCHAR(50) NOT NULL,
     updated_at TIMESTAMPTZ NOT NULL,
     deleted_at TIMESTAMPTZ,
     "version" INT8 NOT NULL DEFAULT 0,
     CONSTRAINT cor_liturgy_sequences_pk PRIMARY KEY(id)
);

ALTER TABLE cor_liturgy_sequences
    DROP CONSTRAINT IF EXISTS fk_liturgy_group;

ALTER TABLE cor_liturgy_sequences
    ADD CONSTRAINT fk_liturgy_group
        FOREIGN KEY (liturgy_group_id)
            REFERENCES cor_liturgy_groups(id);

CREATE TABLE IF NOT EXISTS cor_events (
    id TEXT NOT NULL DEFAULT uuid_generate_v4(),
    liturgy_group_id TEXT NOT NULL,
    triwulan INT NOT NULL,
    "year" INT NOT NULL,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    event_start_time TIMESTAMP,
    event_end_time TIMESTAMP,
    event_type VARCHAR(50) NOT NULL,
    participant_data JSONB,
    created_by VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_by VARCHAR(50) NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    deleted_at TIMESTAMPTZ,
    "version" INT8 NOT NULL DEFAULT 0,
    CONSTRAINT cor_events_pk PRIMARY KEY (id)
);

ALTER TABLE cor_events
    DROP CONSTRAINT IF EXISTS fk_liturgy_event_group;

ALTER TABLE cor_events
    ADD CONSTRAINT fk_liturgy_event_group
        FOREIGN KEY (liturgy_group_id)
            REFERENCES cor_liturgy_groups(id);

CREATE TABLE IF NOT EXISTS cor_event_files (
   id TEXT NOT NULL DEFAULT uuid_generate_v4(),
   event_id TEXT NOT NULL,
   file_name VARCHAR(255) NOT NULL,
   file_type VARCHAR(50) NOT NULL,
   file_extension VARCHAR(20),
   file_size BIGINT,
   file_url TEXT NOT NULL,
   storage_type VARCHAR(50) DEFAULT 'LOCAL',
   is_primary BOOLEAN NOT NULL DEFAULT FALSE,
   created_by VARCHAR(50) NOT NULL,
   created_at TIMESTAMPTZ NOT NULL,
   updated_by VARCHAR(50) NOT NULL,
   updated_at TIMESTAMPTZ NOT NULL,
   deleted_at TIMESTAMPTZ,
   "version" INT8 NOT NULL DEFAULT 0,
   CONSTRAINT cor_event_files_pk PRIMARY KEY (id)
);

ALTER TABLE cor_event_files
    DROP CONSTRAINT IF EXISTS fk_cor_event_files_event;

ALTER TABLE cor_event_files
    ADD CONSTRAINT fk_cor_event_files_event
        FOREIGN KEY (event_id)
            REFERENCES cor_events(id);
