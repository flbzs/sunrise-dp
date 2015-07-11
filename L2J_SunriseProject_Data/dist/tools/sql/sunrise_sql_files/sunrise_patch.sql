ALTER TABLE `characters` ADD `prefix_category` MEDIUMINT UNSIGNED DEFAULT 0;
ALTER TABLE `characters` ADD `enchant_bot` INT UNSIGNED DEFAULT 0;
ALTER TABLE `characters` ADD `enchant_chance` DOUBLE UNSIGNED DEFAULT 80;
ALTER TABLE `characters` ADD `achievementmobkilled` INT UNSIGNED DEFAULT 0;
ALTER TABLE `characters` ADD COLUMN `pccafe_points` INT NULL DEFAULT 0 AFTER `createDate`;