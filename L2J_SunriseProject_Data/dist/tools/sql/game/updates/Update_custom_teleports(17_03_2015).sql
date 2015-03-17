ALTER TABLE `custom_teleports` ADD `onlyForNoble` TINYINT UNSIGNED DEFAULT 0;
ALTER TABLE `custom_teleports` ADD `itemIdToGet` int(10) UNSIGNED DEFAULT 57;
ALTER TABLE `custom_teleports` ADD `teleportPrice` int(10) UNSIGNED DEFAULT 1000;