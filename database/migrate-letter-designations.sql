-- Run this when the letter form's Address To and Sent By dropdowns use
-- top_level_designation, while the existing letters table still references users.
-- Check that existing letter IDs exist in top_level_designation before adding the new keys:
-- SELECT l.id, l.address_to_id FROM letters l
-- LEFT JOIN top_level_designation d ON d.id=l.address_to_id WHERE d.id IS NULL;
-- SELECT l.id, l.sent_by_id FROM letters l
-- LEFT JOIN top_level_designation d ON d.id=l.sent_by_id WHERE d.id IS NULL;

ALTER TABLE `letters`
    DROP FOREIGN KEY `fk_letters_address_to`;

ALTER TABLE `letters`
    DROP FOREIGN KEY `fk_letters_sent_by`;

ALTER TABLE `letters`
    ADD CONSTRAINT `fk_letters_address_to_designation`
        FOREIGN KEY (`address_to_id`) REFERENCES `top_level_designation` (`id`);

ALTER TABLE `letters`
    ADD CONSTRAINT `fk_letters_sent_by_designation`
        FOREIGN KEY (`sent_by_id`) REFERENCES `top_level_designation` (`id`);
