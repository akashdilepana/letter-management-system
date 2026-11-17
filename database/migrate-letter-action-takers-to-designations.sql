-- Run this once if letter_action_takers was created with the earlier user_id column.
-- This preserves the stored numeric IDs while changing the referenced lookup table.
ALTER TABLE `letter_action_takers`
    DROP FOREIGN KEY `fk_letter_action_takers_user`;

ALTER TABLE `letter_action_takers`
    CHANGE COLUMN `user_id` `designation_id` INT NOT NULL;

ALTER TABLE `letter_action_takers`
    ADD CONSTRAINT `fk_letter_action_takers_designation`
        FOREIGN KEY (`designation_id`) REFERENCES `top_level_designation` (`id`);
