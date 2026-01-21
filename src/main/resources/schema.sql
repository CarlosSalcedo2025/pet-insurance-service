CREATE TABLE IF NOT EXISTS quotes (
    id UUID PRIMARY KEY,
    amount DECIMAL(19, 2) NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    pet_name VARCHAR(100) NOT NULL,
    pet_species VARCHAR(20) NOT NULL,
    pet_breed VARCHAR(100) NOT NULL,
    pet_age INTEGER NOT NULL,
    pet_plan VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS policies (
    id UUID PRIMARY KEY,
    quote_id UUID NOT NULL,
    owner_name VARCHAR(255) NOT NULL,
    owner_id VARCHAR(50) NOT NULL,
    owner_email VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    issue_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_quote FOREIGN KEY (quote_id) REFERENCES quotes(id)
);
