-- Letter register tables. Run after `top_level_designation` is created.
-- S/N is the generated `id` value; it increments automatically for every saved letter.
CREATE TABLE IF NOT EXISTS `letters` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `letter_date` DATE NOT NULL DEFAULT (CURRENT_DATE),
    `address_to_id` INT NOT NULL,
    `sent_by_id` INT NOT NULL,
    `subject` VARCHAR(500) NOT NULL,
    `letter_my_no` VARCHAR(100) NOT NULL,
    `document_date` DATE NOT NULL,
    `reply_required` BOOLEAN NOT NULL DEFAULT FALSE,
    `reply_letter_my_no` VARCHAR(100) NULL,
    `reply_date` DATE NULL,
    `letter_file_path` VARCHAR(500) NOT NULL,
    `reply_file_path` VARCHAR(500) NULL,
    `status` VARCHAR(20) NOT NULL DEFAULT 'active',
    `ent_by` INT NULL,
    `ent_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `mod_by` INT NULL,
    `mod_on` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_letters_date` (`letter_date`),
    KEY `idx_letters_address_to` (`address_to_id`),
    KEY `idx_letters_sent_by` (`sent_by_id`),
    CONSTRAINT `fk_letters_address_to` FOREIGN KEY (`address_to_id`) REFERENCES `top_level_designation` (`id`),
    CONSTRAINT `fk_letters_sent_by` FOREIGN KEY (`sent_by_id`) REFERENCES `top_level_designation` (`id`),
    CONSTRAINT `fk_letters_ent_by` FOREIGN KEY (`ent_by`) REFERENCES `users` (`id`),
    CONSTRAINT `fk_letters_mod_by` FOREIGN KEY (`mod_by`) REFERENCES `users` (`id`)
);

-- A letter can be assigned to several action owners; each owner is stored separately.
CREATE TABLE IF NOT EXISTS `letter_action_takers` (
    `letter_id` INT NOT NULL,
    `designation_id` INT NOT NULL,
    PRIMARY KEY (`letter_id`, `designation_id`),
    CONSTRAINT `fk_letter_action_takers_letter` FOREIGN KEY (`letter_id`)
        REFERENCES `letters` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_letter_action_takers_designation` FOREIGN KEY (`designation_id`)
        REFERENCES `top_level_designation` (`id`)
);
