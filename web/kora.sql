CREATE TABLE `users` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(140),
  `email` varchar(100) NOT NULL,
  `phone` varchar(14) NOT NULL,
  `session_cookie` varchar(11) NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `accounts` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(12) NOT NULL,
  `starting_amount` int(30) NOT NULL,
  `balance` int(30) NOT NULL,
  `plan_type` varchar(10) NOT NULL,
  `records` text,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;